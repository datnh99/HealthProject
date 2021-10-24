package HealthDeclaration.service;

import HealthDeclaration.modal.entity.User;

import java.util.List;

public interface IUserService {
    List<User> getAll();

    User getByUsername(String username);

    User getByTen(String username);

    User add(User user);

    User update(User user);

    void deleteByUsername(String username);
}
