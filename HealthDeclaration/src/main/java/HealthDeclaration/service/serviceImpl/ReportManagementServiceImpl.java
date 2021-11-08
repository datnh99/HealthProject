package HealthDeclaration.service.serviceImpl;

import HealthDeclaration.common.base.service.BaseService;
import HealthDeclaration.form.ReportManagementSearchForm;
import HealthDeclaration.modal.dto.UserDto;
import HealthDeclaration.repository.ReportManagementRepositoryCustom;
import HealthDeclaration.service.ReportManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportManagementServiceImpl extends BaseService implements ReportManagementService {

    @Autowired
    private ReportManagementRepositoryCustom reportManagementRepository;

    @Override
    public List<UserDto> searchStudent(ReportManagementSearchForm form, int pageIndex, int pageSize) {
        return reportManagementRepository.searchStudent(form,pageIndex,pageSize);
    }

    @Override
    public Long countStudent(ReportManagementSearchForm form) {
        return reportManagementRepository.countStudent(form);
    }
}
