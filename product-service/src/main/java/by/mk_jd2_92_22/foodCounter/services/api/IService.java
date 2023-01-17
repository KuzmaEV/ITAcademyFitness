package by.mk_jd2_92_22.foodCounter.services.api;

import by.mk_jd2_92_22.foodCounter.services.dto.PageDTO;
import org.springframework.http.HttpHeaders;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IService<ENTITY, DTO> {
    ENTITY create(DTO item, HttpHeaders token);
    ENTITY get(UUID uuid);
    PageDTO<ENTITY> get(int page, int size);
    ENTITY update(UUID uuid, LocalDateTime dtUpdate, DTO item, HttpHeaders token);
    void delete(UUID uuid, LocalDateTime dtUpdate, HttpHeaders token);
}
