package HealthDeclaration.modal.entity;

import java.util.Date;

import javax.persistence.Entity;

import HealthDeclaration.common.base.entity.BaseEntity;
import lombok.Data;
@Data
@Entity
public class HealthReport extends BaseEntity {
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
	private String wardCode;
	private String addressDetail;
	private String inconstantNote;
	private Boolean closeToPatient;
	private Boolean closeToCountry;
	private Boolean closeToRiskingPeople;
}
