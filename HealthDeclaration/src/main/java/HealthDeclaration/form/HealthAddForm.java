package HealthDeclaration.form;

import java.util.Date;

import lombok.Data;
@Data
public class HealthAddForm {
	private String username;
	private String studentName;
	private String verificationId;
	private Boolean gender;
	private Date dateOfBirth;
	private String phoneNumber;
	private String healthInsuranceId;
	private String email;
	private String provinceCode;
	private String districtCode;
	private String addressDetail;
	private String wardCode;
	private String inconstantNote;
	private Boolean closeToPatient;
	private Boolean closeToCountry;
	private Boolean closeToRiskingPeople;
}
