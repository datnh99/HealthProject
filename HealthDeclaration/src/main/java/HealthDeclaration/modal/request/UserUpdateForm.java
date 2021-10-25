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
    private Long provinceId;
    private Long districtId;
    private Long wardId;
}
