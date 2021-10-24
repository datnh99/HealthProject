package HealthDeclaration.service.serviceImpl;

import HealthDeclaration.modal.entity.User;
import HealthDeclaration.repository.IUserRepository;
import HealthDeclaration.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    IUserRepository repository;

    @Override
    public List<User> getAll() {
        return repository.findAll();
    }

    @Override
    public User getByUsername(String username) {
        return repository.getByUsername(username);
    }

//    @Override
//    public User getByTen(String username) {
//        return repository.getByTen(username);
//    }

    @Override
    public User add(User user) {
        return repository.save(user);
    }

    @Override
    public User update(User user) {
        return repository.save(user);
    }

    @Override
    public void deleteByUsername(String username) {
        repository.deleteByUsername(username);
    }

}
