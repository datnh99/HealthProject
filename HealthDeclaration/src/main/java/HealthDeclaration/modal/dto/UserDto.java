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
    private int index;
    private Long id;
    private String userName;
    private String fullName;
    private Date dob;
    private Boolean gender;
    private String phoneNumber;
    private String  parentPhoneNumber;
    private Long provinceCode;
    private String provinceName;
    private Long districtCode;
    private String districtName;
    private Long wardCode;
    private String wardName;
    private String addressDetail;

    public UserDto(Long id, String userName, String fullName) {
        this.id = id;
        this.userName = userName;
        this.fullName = fullName;
    }

    public UserDto(Long id, String userName, String fullName, Date dob, Boolean gender, String phoneNumber, String parentPhoneNumber, Long provinceCode, String provinceName, Long districtCode, String districtName, Long wardCode, String wardName, String addressDetail) {
        this.id = id;
        this.userName = userName;
        this.fullName = fullName;
        this.dob = dob;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.parentPhoneNumber = parentPhoneNumber;
        this.provinceCode = provinceCode;
        this.provinceName = provinceName;
        this.districtCode = districtCode;
        this.districtName = districtName;
        this.wardCode = wardCode;
        this.wardName = wardName;
        this.addressDetail = addressDetail;
    }
}
