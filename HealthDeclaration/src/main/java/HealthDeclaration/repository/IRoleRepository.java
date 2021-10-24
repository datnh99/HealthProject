package HealthDeclaration.repository;

import HealthDeclaration.modal.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {

    @Query(value = "select r FROM Role r where r.roleCode = :roleCode and r.deleted = false")
    Role findByCode(@Param("roleCode") String code);
}
