package HealthDeclaration.repository;

import HealthDeclaration.modal.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Long> {
    Account getByUsername(String username);

    @Query("update account acc set acc.flg = true where acc.account = :username")
    List<String> deleteByUsername(@Param("username") String username);

    @Query(value = "select a from Account a where a.username = :username")
    Account getByTen(@Param("username") String username);
}
