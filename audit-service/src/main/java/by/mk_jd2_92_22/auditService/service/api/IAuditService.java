package by.mk_jd2_92_22.auditService.service.api;

import by.mk_jd2_92_22.auditService.dto.AuditRequestDTO;
import by.mk_jd2_92_22.auditService.dto.PageDTO;
import by.mk_jd2_92_22.auditService.model.Audit;

public interface IAuditService {
    PageDTO<Audit> get(int page, int size);
    Audit get(String id);
    Audit create(AuditRequestDTO dto);
}
