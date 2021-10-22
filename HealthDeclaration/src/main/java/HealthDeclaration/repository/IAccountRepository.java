package HealthDeclaration.repository;

import HealthDeclaration.modal.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Long>, CrudRepository<Account, Long> {
    Account getByUsername(String username);

//    @Modifying
//    @Query("UPDATE account set flg = true where username = ?1")
//    Account deleteByUsername(String username);
}
