package HealthDeclaration.repository;

import HealthDeclaration.modal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface IUserRepository extends JpaRepository<User, Long>, CrudRepository<User, Long> {
    User getByUsername(String username);

    @Query("select r.roleCode from User u join Role r ON r.roleCode = u.roleCode WHERE u.username = :username")
    String getUserRoleByUsername(@Param("username") String username);

    @Query("update User acc set acc.deleted = true where acc.username = :username")
    List<String> deleteByUsername(@Param("username") String username);

    @Query(value = "SELECT u.username FROM User u WHERE u.username LIKE :account ORDER BY u.username ASC")
    List<String> getLastAccountByAccount(@Param("account") String account);
}
