package by.mk_jd2_92_22.auditService.repository;

import by.mk_jd2_92_22.auditService.model.Audit;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AuditRepository extends JpaRepository<Audit, Long> {
}
