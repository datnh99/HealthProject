package HealthDeclaration.repository;

import HealthDeclaration.modal.entity.Class2021;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClassRepository extends JpaRepository<Class2021, Long>, CrudRepository<Class2021, Long> {
}
