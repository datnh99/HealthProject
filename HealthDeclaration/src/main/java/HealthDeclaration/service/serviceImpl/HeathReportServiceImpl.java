package HealthDeclaration.service.serviceImpl;

import java.util.Date;

import HealthDeclaration.modal.entity.User;
import HealthDeclaration.modal.request.UserUpdateForm;
import HealthDeclaration.service.IUserService;
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

	@Autowired
	private IUserService userService;

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
			report.setSicking(form.getSicking());
			report.setAddressDetail(form.getAddressDetail());
			healthReportRepository.save(report);


			UserUpdateForm user = new UserUpdateForm();
			user.setUsername(username);
//			user.setFullName(report.getStudentName());
			user.setGender(report.getGender());
			user.setDob(report.getDateOfBirth());
			user.setPhoneNumber(report.getPhoneNumber());
			user.setProvinceCode(report.getProvinceCode());
			user.setDistrictCode(report.getDistrictCode());
			user.setWardCode(report.getWardCode());
			user.setAddressDetail(report.getAddressDetail());
			userService.update(user);
			return true;
		} catch (Exception e) {
			return false;
		}

	}
}
