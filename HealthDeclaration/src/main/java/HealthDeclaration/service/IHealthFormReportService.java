package HealthDeclaration.service;

import HealthDeclaration.modal.entity.HealthFormReport;
import HealthDeclaration.modal.request.HealthFormReportAdd;

public interface IHealthFormReportService {
    HealthFormReport add(HealthFormReportAdd formReportAdd);

    boolean delete(Long id);
}
