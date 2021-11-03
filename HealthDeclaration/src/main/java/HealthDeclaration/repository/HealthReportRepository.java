package HealthDeclaration.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import HealthDeclaration.modal.entity.HealthReport;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthReportRepository  extends JpaRepository<HealthReport, Long>{

}
