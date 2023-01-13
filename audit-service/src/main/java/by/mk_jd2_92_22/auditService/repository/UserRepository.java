package by.mk_jd2_92_22.auditService.repository;

import by.mk_jd2_92_22.auditService.model.UserAudit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserAudit, UUID> {
}
