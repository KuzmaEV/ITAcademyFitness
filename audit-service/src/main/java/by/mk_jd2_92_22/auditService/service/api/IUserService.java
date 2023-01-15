package by.mk_jd2_92_22.auditService.service.api;

import by.mk_jd2_92_22.auditService.model.UserAudit;
import org.springframework.http.HttpHeaders;

import java.util.UUID;

public interface IUserService {

    UserAudit create(UUID user, HttpHeaders token);
}
