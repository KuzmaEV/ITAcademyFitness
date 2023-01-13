package by.mk_jd2_92_22.foodCounter.services;

import by.mk_jd2_92_22.foodCounter.core.builder.ProfileBuilder;
import by.mk_jd2_92_22.foodCounter.repositories.ProfileRepository;
import by.mk_jd2_92_22.foodCounter.model.Profile;
import by.mk_jd2_92_22.foodCounter.model.UserProfile;
import by.mk_jd2_92_22.foodCounter.services.api.IProfileService;
import by.mk_jd2_92_22.foodCounter.services.dto.ProfileDTO;
import by.mk_jd2_92_22.foodCounter.services.dto.ProfileResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class ProfileService implements IProfileService {

    private final ProfileRepository dao;

    public ProfileService(ProfileRepository dao) {
        this.dao = dao;
    }

    @Override
    @Transactional
    public Profile create(ProfileDTO item) {

        UUID user = UUID.randomUUID();//TODO достать из токена

        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);

        Profile profile = ProfileBuilder.create().setUuid(UUID.randomUUID())
                .setDtCreate(now)
                .setDtCreate(now)
                .setWeight(item.getWeight())
                .setHeight(item.getHeight())
                .setTarget(item.getTarget())
                .setDtBirthday(item.getDtBirthday())
                .setActivityType(item.getActivityType())
                .setSex(item.getSex())
                .setUser(user)
                .build();

        return dao.save(profile);
    }

    @Override
    public ProfileResponseDTO get(UUID uuid) {


        Profile profile = dao.findById(uuid).orElseThrow(() ->
                new IllegalArgumentException("Profile is not found"));

        //TODO restTemplate get from user-service
        UserProfile userProfile = new UserProfile();
        userProfile.setUuid(profile.getUuid());
        userProfile.setDtCreate(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        userProfile.setDtUpdate(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));

        return new ProfileResponseDTO(profile.getUuid(),
                profile.getDtCreate(),
                profile.getDtUpdate(),
                profile.getHeight(),
                profile.getWeight(),
                profile.getDtBirthday(),
                profile.getTarget(),
                profile.getActivityType(),
                profile.getSex(),
                userProfile);
    }

    @Override
    @Transactional
    public Profile update(UUID uuid, LocalDateTime dtUpdate, ProfileDTO item) {

        Profile profile = dao.findById(uuid).orElseThrow(() ->
                new IllegalArgumentException("Profile is not found"));

        if (!profile.getDtUpdate().isEqual(dtUpdate)){
            throw new IllegalArgumentException("wasn't update, try again");
        }

        profile.setWeight(item.getWeight());
        profile.setHeight(item.getHeight());
        profile.setTarget(item.getTarget());
        profile.setDtBirthday(item.getDtBirthday());
        profile.setActivityType(item.getActivityType());
        profile.setSex(item.getSex());

        return dao.save(profile);
    }

    @Override
    @Transactional
    public void delete(UUID uuid, LocalDateTime dtUpdate) {

        Profile profile = dao.findById(uuid).orElseThrow(() ->
                new IllegalArgumentException("Profile is not found"));

        if (!profile.getDtUpdate().isEqual(dtUpdate)){
            throw new IllegalArgumentException("wasn't delete, try again");
        }

        dao.delete(profile);

    }

}
