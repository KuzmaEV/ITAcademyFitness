package by.mk_jd2_92_22.foodCounter.services.api;

import by.mk_jd2_92_22.foodCounter.services.dto.PageDTO;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IService<ENTITY, DTO> {
    ENTITY create(DTO item);
    ENTITY get(UUID uuid);
    PageDTO<ENTITY> get(int page, int size);
    ENTITY update(UUID uuid, LocalDateTime dtUpdate, DTO item);
    void delete(UUID uuid, LocalDateTime dtUpdate);
}
