package HealthDeclaration.repository;

import HealthDeclaration.modal.entity.Ward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IWardRepository extends JpaRepository<Ward, Long>, CrudRepository<Ward, Long> {
}
