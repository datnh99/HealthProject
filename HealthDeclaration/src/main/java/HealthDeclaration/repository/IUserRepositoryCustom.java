package HealthDeclaration.repository;

import HealthDeclaration.form.UserFormSearch;
import HealthDeclaration.modal.dto.UserDto;

import java.util.List;

public interface IUserRepositoryCustom {

    List<UserDto> searchTeacherByName(String teacherName, Long roleID, int pageIndex, int pageSize);

    List<UserDto> searchUserToManagement(UserFormSearch formSearch, Long roleID, int pageIndex, int pageSize);

    Long countSearchUserToManagement(UserFormSearch formSearch, Long id);
}
