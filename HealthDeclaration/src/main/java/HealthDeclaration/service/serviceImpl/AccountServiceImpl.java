package HealthDeclaration.service.serviceImpl;

import HealthDeclaration.modal.entity.Account;
import HealthDeclaration.repository.IAccountRepository;
import HealthDeclaration.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountServiceImpl implements IAccountService {
    @Autowired
    IAccountRepository repository;

    @Override
    public List<Account> getAll() {
        return repository.findAll();
    }

    @Override
    public Account getByUsername(String username) {
        return repository.getByUsername(username);
    }

    @Override
    public Account getByTen(String username) {
        return repository.getByTen(username);
    }

    @Override
    public Account add(Account account) {
        return repository.save(account);
    }

    @Override
    public Account update(Account account) {
        return repository.save(account);
    }

    @Override
    public void deleteByUsername(String username) {
        repository.deleteByUsername(username);
    }

}
