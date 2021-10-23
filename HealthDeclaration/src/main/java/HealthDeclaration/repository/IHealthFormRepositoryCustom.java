package HealthDeclaration.repository;

import HealthDeclaration.form.HealthFormSearch;
import HealthDeclaration.modal.dto.HealthFormDto;

import java.util.List;

public interface IHealthFormRepositoryCustom {
    List<HealthFormDto> searchHealthForm(HealthFormSearch healthFormSearch, int pageIndex, int pageSize);
}
