package HealthDeclaration.modal.request;

import lombok.Data;

import java.util.Date;

@Data
public class UserUpdateForm {
    private Long id;
    private String fullName;
    private Date dob;
    private boolean gender;
    private String phoneNumber;
    private String parentPhoneNumber;
    private Long provinceCode;
    private Long districtCode;
    private Long wardCode;
    private String roleCode;
    private String addressDetail;
}
