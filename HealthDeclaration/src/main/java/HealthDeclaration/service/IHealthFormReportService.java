package HealthDeclaration.service;

import HealthDeclaration.form.ClassFormSearch;
import HealthDeclaration.modal.dto.ClassDto;
import HealthDeclaration.modal.dto.HealthFormDto;
import HealthDeclaration.modal.entity.HealthFormReport;
import HealthDeclaration.modal.request.HealthFormReportAdd;

import java.util.List;

public interface IHealthFormReportService {
    HealthFormReport add(HealthFormReportAdd formReportAdd);

    boolean delete(Long id);

    List<HealthFormDto> searchHealthFormReport(Long userId, int pageIndex, int pageSize);

    Long countSearchHealthFormReport(Long userId);

    HealthFormReport getById(Long id);
}
