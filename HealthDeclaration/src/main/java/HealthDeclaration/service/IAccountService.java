package HealthDeclaration.service;

import HealthDeclaration.modal.entity.Account;

import java.util.List;

public interface IAccountService {
    List<Account> getAll();

    Account getByUsername(String username);

    Account add(Account account);

    Account update(Account account);

    //Account deleteByUsername(String account);
}
