package HealthDeclaration.service;

import HealthDeclaration.form.HealthReportAddForm;
import HealthDeclaration.modal.dto.HealthFormDto;
import HealthDeclaration.modal.entity.HealthFormReport;

import java.util.List;

public interface IHealthFormReportService {
    HealthFormReport add(HealthReportAddForm formReportAdd);

    void delete(Long id);

    List<HealthFormDto> searchHealthFormReport(Long userId, int pageIndex, int pageSize);

    Long countSearchHealthFormReport(Long userId);

    HealthFormReport getById(Long id);
}
