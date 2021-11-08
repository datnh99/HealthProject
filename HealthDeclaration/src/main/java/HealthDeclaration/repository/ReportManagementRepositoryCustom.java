package HealthDeclaration.repository;

import HealthDeclaration.form.ReportManagementSearchForm;
import HealthDeclaration.modal.dto.UserDto;

import java.util.List;

public interface ReportManagementRepositoryCustom {
    /**
     *
     * @param form
     * @param pageIndex
     * @param pageSize
     * @return
     */
    List<UserDto> searchStudent(ReportManagementSearchForm form, int pageIndex, int pageSize);

    /**
     *
     * @param form
     * @return
     */
    Long countStudent(ReportManagementSearchForm form);
}
