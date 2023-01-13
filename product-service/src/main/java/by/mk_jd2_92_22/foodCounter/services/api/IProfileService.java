package by.mk_jd2_92_22.foodCounter.services.api;

import by.mk_jd2_92_22.foodCounter.model.Profile;
import by.mk_jd2_92_22.foodCounter.services.dto.ProfileDTO;
import by.mk_jd2_92_22.foodCounter.services.dto.ProfileResponseDTO;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IProfileService {

    Profile create(ProfileDTO item);
    ProfileResponseDTO get(UUID uuid);
    Profile update(UUID uuid, LocalDateTime dtUpdate, ProfileDTO item);
    void delete(UUID uuid, LocalDateTime dtUpdate);
}
