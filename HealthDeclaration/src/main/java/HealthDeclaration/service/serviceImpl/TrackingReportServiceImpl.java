package HealthDeclaration.service.serviceImpl;

import HealthDeclaration.common.base.service.BaseService;
import HealthDeclaration.form.HealthReportAddForm;
import HealthDeclaration.modal.dto.HealthFormDto;
import HealthDeclaration.modal.entity.TrackingReport;
import HealthDeclaration.repository.ITrackingReportRepository;
import HealthDeclaration.repository.ITrackingReportRepositoryCustom;
import HealthDeclaration.service.ITrackingReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TrackingReportServiceImpl extends BaseService implements ITrackingReportService {
    @Autowired
    private ITrackingReportRepository iTrackingReportRepository;

    @Autowired
    private ITrackingReportRepositoryCustom iTrackingReportRepositoryCustom;

    @Override
    public TrackingReport add(HealthReportAddForm formReportAdd) {
        TrackingReport report = new TrackingReport();
        report.setCreatedBy(getLoggedInUsername());
        report.setCreatedTime(new Date());
        report.setModifiedBy(getLoggedInUsername());
        report.setModifiedTime(new Date());

        report.setUsername(formReportAdd.getUsername());
        report.setStudentName(formReportAdd.getStudentName());
        report.setVerificationId(formReportAdd.getVerificationId());
        report.setGender(formReportAdd.getGender());
        report.setDateOfBirth(formReportAdd.getDateOfBirth());
        report.setPhoneNumber(formReportAdd.getPhoneNumber());
        report.setHealthInsuranceId(formReportAdd.getHealthInsuranceId());
        report.setEmail(formReportAdd.getEmail());

        report.setProvinceCode(formReportAdd.getProvinceCode());
        report.setDistrictCode(formReportAdd.getDistrictCode());
        report.setWardCode(report.getWardCode());
        report.setAddressDetail(formReportAdd.getAddressDetail());

        report.setProvinceCodeFrom(formReportAdd.getProvinceCodeFrom());
        report.setDistrictCodeFrom(formReportAdd.getDistrictCodeFrom());
        report.setWardCodeFrom(report.getWardCodeFrom());
        report.setAddressDetailFrom(formReportAdd.getAddressDetailFrom());

        report.setProvinceCodeTo(formReportAdd.getProvinceCodeTo());
        report.setDistrictCodeTo(formReportAdd.getDistrictCodeTo());
        report.setWardCodeTo(report.getWardCodeTo());
        report.setAddressDetailTo(formReportAdd.getAddressDetailTo());


        report.setContactToPlace(formReportAdd.getContactToPlace());
        report.setSicking(formReportAdd.getSicking());
        report.setCloseToRiskingPeople(formReportAdd.getCloseToRiskingPeople());
        report.setCloseToCountry(formReportAdd.getCloseToCountry());
        report.setCloseToSicking(formReportAdd.getCloseToSicking());

        return iTrackingReportRepository.save(report);
    }

    @Override
    public void delete(Long id) {
        iTrackingReportRepository.deleteById(id);
    }

    @Override
    public List<HealthFormDto> searchHealthFormReport(Long userId, int pageIndex, int pageSize) {
        List<HealthFormDto> list = iTrackingReportRepositoryCustom.searchHealthForm(userId, pageIndex, pageSize);
        for(int i = 0 ; i < list.size(); i++) {
            list.get(i).setIndex((long) ((pageSize * (pageIndex - 1)) + (i + 1)));
        }
        return list;
    }

    @Override
    public Long countSearchHealthFormReport(Long userId) {
        return iTrackingReportRepositoryCustom.countSearchHealthFormReport(userId);
    }

    @Override
    public TrackingReport getById(Long id) {
        TrackingReport report = iTrackingReportRepository.findById(id).get();
        return report;
    }
}