package HealthDeclaration.repository;

import HealthDeclaration.modal.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Long>, CrudRepository<Account, Long> {
    Account getByUsername(String username);
}
