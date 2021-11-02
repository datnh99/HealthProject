package HealthDeclaration.service.serviceImpl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import HealthDeclaration.common.base.service.BaseService;
import HealthDeclaration.form.HealthAddForm;
import HealthDeclaration.modal.entity.HealthReport;
import HealthDeclaration.repository.HealthReportRepository;
import HealthDeclaration.service.HealthReportService;

@Service
public class HeathReportServiceImpl extends BaseService implements HealthReportService {

	@Autowired
	private HealthReportRepository healthReportRepository;

	@Override
	public boolean add(HealthAddForm form) {
		try {
			String username = getLoggedInUsername();
			HealthReport report = new HealthReport();
			report.setCreatedBy(username);
			report.setModifiedBy(username);
			report.setModifiedTime(new Date());
			report.setCreatedTime(new Date());
			report.setCloseToCountry(form.getCloseToCountry());
			report.setCloseToPatient(form.getCloseToPatient());
			report.setCloseToRiskingPeople(form.getCloseToRiskingPeople());
			report.setStudentName(form.getStudentName());
			report.setVerificationId(form.getVerificationId());
			report.setGender(form.getGender());
			report.setDateOfBirth(form.getDateOfBirth());
			report.setPhoneNumber(form.getPhoneNumber());
			report.setHealthInsuranceId(form.getHealthInsuranceId());
			report.setEmail(form.getEmail());
			report.setProvinceCode(form.getProvinceCode());
			report.setDistrictCode(form.getDistrictCode());
			report.setWardCode(report.getWardCode());
			report.setInconstantNote(form.getInconstantNote());
			report.setAddressDetail(form.getAddressDetail());
			healthReportRepository.save(report);
			return true;
		} catch (Exception e) {
			return false;
		}

	}
}
