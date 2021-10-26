package HealthDeclaration.repository;

import HealthDeclaration.common.base.repository.BaseRepository;
import HealthDeclaration.modal.entity.HealthFormReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IHealthFormReportRepository extends JpaRepository<HealthFormReport, Long> {
}
