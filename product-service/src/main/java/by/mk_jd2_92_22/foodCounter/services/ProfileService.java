package by.mk_jd2_92_22.foodCounter.services;

import by.mk_jd2_92_22.foodCounter.services.builder.ProfileBuilder;
import by.mk_jd2_92_22.foodCounter.repositories.ProfileRepository;
import by.mk_jd2_92_22.foodCounter.model.Profile;
import by.mk_jd2_92_22.foodCounter.model.UserProfile;
import by.mk_jd2_92_22.foodCounter.services.api.IProfileService;
import by.mk_jd2_92_22.foodCounter.services.dto.PageDTO;
import by.mk_jd2_92_22.foodCounter.services.dto.ProfileDTO;
import by.mk_jd2_92_22.foodCounter.services.dto.ProfileResponseDTO;
import by.mk_jd2_92_22.foodCounter.services.dto.Type;
import by.mk_jd2_92_22.foodCounter.services.util.ConverterUtil;
import by.mk_jd2_92_22.foodCounter.services.util.AuditProvider;
import by.mk_jd2_92_22.foodCounter.services.util.UserServiceProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class ProfileService implements IProfileService {

    private final ProfileRepository dao;
    private final UserServiceProvider getUser;
    private final AuditProvider creatingAudit;
    private final ConverterUtil converterUtil;

    public ProfileService(ProfileRepository dao, UserServiceProvider getUser,
                          AuditProvider creatingAudit, ConverterUtil converterUtil) {
        this.dao = dao;
        this.getUser = getUser;
        this.creatingAudit = creatingAudit;
        this.converterUtil = converterUtil;
    }


    @Override
    @Transactional
    public ProfileResponseDTO create(ProfileDTO item, HttpHeaders token) {

        final String text = "new Profile created";

        final UserProfile userProfile = this.getUser.getUserProfileHolder();
        final LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);

        Profile profile = ProfileBuilder.create().setUuid(UUID.randomUUID())
                .setDtCreate(now)
                .setDtUpdate(now)
                .setWeight(item.getWeight())
                .setHeight(item.getHeight())
                .setTarget(item.getTarget())
                .setDtBirthday(item.getDtBirthday())
                .setActivityType(item.getActivityType())
                .setSex(item.getSex())
                .setUserId(userProfile.getUuid())
                .build();

        final Profile newProfile = this.dao.save(profile);

        this.creatingAudit.create(userProfile.getUuid(), text, Type.PROFILE, token);

        return this.converterUtil.convert(newProfile, userProfile);
    }

    @Override
    public ProfileResponseDTO  get(UUID profileId) {

        final UserProfile userProfile = this.getUser.getUserProfileHolder();


        final Profile profile = this.dao.findById(profileId).orElseThrow(() ->
                new IllegalArgumentException("Такого профеля не сууществует"));

        if(!profile.getUser().equals(userProfile.getUuid())){
            throw new IllegalStateException("Нельзя получит данные чужого пользователя");
        }

        return this.converterUtil.convert(profile, userProfile);
    }

    @Override
    public PageDTO<ProfileResponseDTO> get(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        final UserProfile userProfile = this.getUser.getUserProfileHolder();
        final Page<Profile> profilePage = this.dao.findAllByUser(pageable,userProfile.getUuid());

        return this.converterUtil.convert(profilePage, userProfile);
    }

    @Override
    @Transactional
    public ProfileResponseDTO  update(UUID uuid, LocalDateTime dtUpdate, ProfileDTO item, HttpHeaders token) {

        final String text = "Profile updated";

        final UserProfile userProfile = this.getUser.getUserProfileHolder();

        Profile profile = this.dao.findById(uuid).orElseThrow(() ->
                new IllegalArgumentException("Такого профеля не сууществует"));

        if (!userProfile.getUuid().equals(profile.getUser())){
            throw new IllegalStateException("Нельзя редоктировать чужие профили");
        }

        if (!profile.getDtUpdate().isEqual(dtUpdate)){
            throw new IllegalArgumentException("Не удалось обнавить, было кем-то изменино раньше." +
                    " Попробуйте еще раз!");
        }

        profile.setWeight(item.getWeight());
        profile.setHeight(item.getHeight());
        profile.setTarget(item.getTarget());
        profile.setDtBirthday(item.getDtBirthday());
        profile.setActivityType(item.getActivityType());
        profile.setSex(item.getSex());


        Profile profileUpdated = this.dao.save(profile);

        this.creatingAudit.create(profileUpdated.getUser(), text, Type.PROFILE, token);

        return this.converterUtil.convert(profileUpdated, userProfile);
    }

    @Override
    @Transactional
    public void delete(UUID uuid, LocalDateTime dtUpdate, HttpHeaders token) {

        final String text = "Profile deleted";
        final UserProfile userProfileHolder = this.getUser.getUserProfileHolder();

        Profile profile = this.dao.findById(uuid).orElseThrow(() ->
                new IllegalArgumentException("Такого профеля не сууществует"));

        if (!profile.getUser().equals(userProfileHolder.getUuid())){
            throw new IllegalStateException("Нельзя удалять чужие профили");
        }

        if (!profile.getDtUpdate().isEqual(dtUpdate)){
            throw new IllegalArgumentException("Не удалось удалить, было кем-то изменино раньше." +
                    " Попробуйте еще раз!");
        }

        this.dao.delete(profile);

        this.creatingAudit.create(profile.getUser(), text, Type.PROFILE, token);

    }

}
