package HealthDeclaration.modal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String thua;

    private Long id;
    private String userName;
    private String fullName;
    private Date dob;
    private Boolean gender;
    private String phoneNumber;
    private String  parentPhoneNumber;
    private Long provinceId;
    private String provinceName;
    private Long districtId;
    private String districtName;
    private Long wardId;
    private String wardName;
    private String addressDetail;

    public UserDto(Long id, String userName, String fullName) {
        this.id = id;
        this.userName = userName;
        this.fullName = fullName;
    }

    public UserDto(Long id, String userName, String fullName, Date dob, Boolean gender, String phoneNumber, String parentPhoneNumber, Long provinceId, String provinceName, Long districtId, String districtName, Long wardId, String wardName, String addressDetail) {
        this.id = id;
        this.userName = userName;
        this.fullName = fullName;
        this.dob = dob;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.parentPhoneNumber = parentPhoneNumber;
        this.provinceId = provinceId;
        this.provinceName = provinceName;
        this.districtId = districtId;
        this.districtName = districtName;
        this.wardId = wardId;
        this.wardName = wardName;
        this.addressDetail = addressDetail;
    }
}
