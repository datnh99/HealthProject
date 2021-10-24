package HealthDeclaration.repository;

import HealthDeclaration.modal.dto.UserDto;

import java.util.List;

public interface IUserRepositoryCustom {

    List<UserDto> searchTeacherByName(String teacherName, Long roleID, int pageIndex, int pageSize);
}
