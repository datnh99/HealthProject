package HealthDeclaration.repository;

import HealthDeclaration.modal.dto.ProvinceDto;
import HealthDeclaration.modal.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IProvinceRepository extends JpaRepository<Province, Long> {
    @Query(value = "select new HealthDeclaration.modal.dto.ProvinceDto(p.id, p.code, p.name) from Province p")
    List<ProvinceDto> getAll();
}
