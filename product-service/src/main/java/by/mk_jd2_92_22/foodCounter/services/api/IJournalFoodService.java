package by.mk_jd2_92_22.foodCounter.services.api;

import by.mk_jd2_92_22.foodCounter.model.JournalFood;
import by.mk_jd2_92_22.foodCounter.services.dto.JournalFoodDTO;
import by.mk_jd2_92_22.foodCounter.services.dto.PageDTO;
import org.springframework.http.HttpHeaders;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IJournalFoodService{

    JournalFood create(JournalFoodDTO item, UUID profile, HttpHeaders token);

    JournalFood get(UUID uuid, UUID profile);

    PageDTO<JournalFood> get(int page, int size, UUID profile);

    JournalFood update(UUID uuid, LocalDateTime dtUpdate, JournalFoodDTO item, UUID profile, HttpHeaders token);

    void delete(UUID uuid, LocalDateTime dtUpdate, UUID profile, HttpHeaders token);
}
