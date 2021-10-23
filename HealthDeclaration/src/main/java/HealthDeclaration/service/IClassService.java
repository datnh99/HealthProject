package HealthDeclaration.service;

import HealthDeclaration.form.ClassFormSearch;
import HealthDeclaration.modal.dto.ClassDto;

import java.util.List;

public interface IClassService {
    List<ClassDto> getClasses();

    List<ClassDto> searchClass(ClassFormSearch classFormSearch, int pageIndex, int pageSize);

    Long countSearchClass(ClassFormSearch classFormSearch);
}
