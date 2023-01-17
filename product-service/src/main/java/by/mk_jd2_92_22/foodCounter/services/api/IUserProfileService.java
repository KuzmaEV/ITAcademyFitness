package by.mk_jd2_92_22.foodCounter.services.api;

import by.mk_jd2_92_22.foodCounter.model.UserProfile;
import by.mk_jd2_92_22.foodCounter.security.customDatail.entity.CustomUserDetails;

public interface IUserProfileService {

    UserProfile create(CustomUserDetails user);
}
