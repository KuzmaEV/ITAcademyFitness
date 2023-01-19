package by.mk_jd2_92_22.reportService.repositories;

import by.mk_jd2_92_22.reportService.model.Report;
import by.mk_jd2_92_22.reportService.model.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IReportRepository extends JpaRepository<Report, UUID> {

    Page<Report> findAllByprofileId(Pageable pageable, UUID userId);

    Optional<Report> findByUuidAndStatus(UUID report, Status status);
}
