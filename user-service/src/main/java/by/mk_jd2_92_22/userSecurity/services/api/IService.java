package by.mk_jd2_92_22.userSecurity.services.api;

import by.mk_jd2_92_22.userSecurity.model.dto.PageDTO;
import org.springframework.http.HttpHeaders;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IService<ENTITY, DTO> {
    ENTITY create(DTO item, HttpHeaders token);
    ENTITY get(UUID uuid);
    PageDTO<ENTITY> get(int page, int size);
    void update(UUID uuid, LocalDateTime dtUpdate, DTO item, HttpHeaders token);


}
