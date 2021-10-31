package HealthDeclaration.repository;

import HealthDeclaration.modal.entity.Class;
import HealthDeclaration.modal.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IDistrictRepository extends JpaRepository<District, Long>, CrudRepository<District, Long> {

}
