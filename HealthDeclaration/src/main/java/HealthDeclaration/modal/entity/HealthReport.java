package HealthDeclaration.modal.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import HealthDeclaration.common.base.constant.BaseEntityConstant;
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
	private Long provinceCode;
	private Long districtCode;
	private Long wardCode;
	private String addressDetail;
	private String inconstantNote;
	private Boolean closeToPatient;
	private Boolean closeToCountry;
	private Boolean closeToRiskingPeople;
	private Boolean contactToPlace;
	private Boolean sicking;

}
