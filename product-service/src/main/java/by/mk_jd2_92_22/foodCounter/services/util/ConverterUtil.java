package by.mk_jd2_92_22.foodCounter.services.util;

import by.mk_jd2_92_22.foodCounter.model.Profile;
import by.mk_jd2_92_22.foodCounter.model.UserProfile;
import by.mk_jd2_92_22.foodCounter.services.dto.PageDTO;
import by.mk_jd2_92_22.foodCounter.services.dto.ProfileResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConverterUtil {

    public ProfileResponseDTO convert(Profile profile, UserProfile userProfile){

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

    public PageDTO<ProfileResponseDTO> convert(Page<Profile> page, UserProfile userProfile){

        final ArrayList<ProfileResponseDTO> content = new ArrayList<>();
        final List<Profile> contentProfile = page.getContent();

        for (Profile profile : contentProfile) {
            content.add(this.convert(profile, userProfile));
        }

        return new PageDTO<>(
                    page.getNumber(),
                    page.getSize(),
                    page.getTotalPages(),
                    (int)page.getTotalElements(),
                    page.isFirst(),
                    page.getNumberOfElements(),
                    page.isLast(),
                    content);

    }
}
