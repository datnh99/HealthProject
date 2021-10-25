package HealthDeclaration.repository;

import HealthDeclaration.modal.entity.HealthFormReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IHealthFormReportRepository extends JpaRepository<HealthFormReport, Long> {
}
