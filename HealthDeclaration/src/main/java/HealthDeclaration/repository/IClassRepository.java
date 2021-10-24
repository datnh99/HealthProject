package HealthDeclaration.repository;

import HealthDeclaration.form.ClassFormSearch;
import HealthDeclaration.modal.dto.ClassDto;
import HealthDeclaration.modal.entity.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface IClassRepository extends JpaRepository<Class, Long>, CrudRepository<Class, Long> {

    @Query(value = "Select cl FROM Class cl where cl.name = :className")
    Class getByClassName(@Param("className") String className);
}
