package HealthDeclaration.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import HealthDeclaration.modal.entity.HealthReport;

public interface HealthReportRepository  extends JpaRepository<HealthReport, Long>{

}
