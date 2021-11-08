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

import javax.transaction.Transactional;

@Service
public class HeathReportServiceImpl extends BaseService implements HealthReportService {

	@Autowired
	private HealthReportRepository healthReportRepository;

	@Autowired
	private IUserService userService;

	@Override
	@Transactional
	public boolean add(HealthAddForm form) {
		try {
			String username = getLoggedInUsername();
			HealthReport report = new HealthReport();
			report.setCreatedBy(username);
			report.setModifiedBy(username);
			report.setModifiedTime(new Date());
			report.setCreatedTime(new Date());

			report.setUsername(username);
			report.setStudentName(form.getStudentName());
			report.setVerificationId(form.getVerificationId());
			report.setGender(form.getGender());
			report.setDateOfBirth(form.getDateOfBirth());
			report.setPhoneNumber(form.getPhoneNumber());
			report.setHealthInsuranceId(form.getHealthInsuranceId());
			report.setEmail(form.getEmail());
			report.setProvinceCode(form.getProvinceCode());
			report.setDistrictCode(form.getDistrictCode());
			report.setWardCode(form.getWardCode());
			report.setAddressDetail(form.getAddressDetail());

			report.setContactToPlace(form.getContactToPlace());
			report.setSicking(form.getSicking());
			report.setCloseToRiskingPeople(form.getCloseToRiskingPeople());
			report.setCloseToCountry(form.getCloseToCountry());
			report.setCloseToSicking(form.getCloseToSicking());
			healthReportRepository.save(report);

			// Update user infor
			UserUpdateForm user = new UserUpdateForm();
			user.setUsername(username);
			user.setFullName(form.getStudentName());
			user.setGender(report.getGender());
			user.setDob(report.getDateOfBirth());
			user.setPhoneNumber(report.getPhoneNumber());
			user.setHealthInsuranceId(report.getHealthInsuranceId());
			user.setGmail(report.getEmail());
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
