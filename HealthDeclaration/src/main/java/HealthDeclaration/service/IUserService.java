package HealthDeclaration.service;

import HealthDeclaration.form.UserAddForm;
import HealthDeclaration.form.UserFormSearch;
import HealthDeclaration.modal.dto.UserDto;
import HealthDeclaration.modal.entity.User;
import HealthDeclaration.modal.request.UserChangePassForm;
import HealthDeclaration.modal.request.UserUpdateForm;

import java.awt.*;
import java.util.List;

public interface IUserService {
    List<User> getAll();

    User getByUsername(String username);

//    User getByTen(String username);

    User add(User user);

    boolean update(UserUpdateForm updateForm);

    void deleteByUsername(String username);

    public void delete(Long id);

    List<UserDto> searchTeacherByName(String teacherName, int pageIndex, int pageSize);

    boolean changePassword(UserChangePassForm form);

    List<UserDto> searchUserToManagement(UserFormSearch formSearch, int pageIndex, int pageSize);

    Long countSearchUserToManagement(UserFormSearch formSearch);

    User addNewStudent(UserAddForm userAddForm);

    List<UserDto> searchTeacherFreeByName(String teacherName);
}
