package HealthDeclaration.service;

import HealthDeclaration.modal.dto.UserDto;
import HealthDeclaration.modal.entity.User;
import HealthDeclaration.modal.request.UserChangePassForm;

import java.awt.*;
import java.util.List;

public interface IUserService {
    List<User> getAll();

    User getByUsername(String username);

//    User getByTen(String username);

    User add(User user);

    User update(User user);

    void deleteByUsername(String username);

    List<UserDto> searchTeacherByName(String teacherName, int pageIndex, int pageSize);

    boolean changePassword(UserChangePassForm form);
}
