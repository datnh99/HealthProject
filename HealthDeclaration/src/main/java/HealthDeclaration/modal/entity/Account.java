package HealthDeclaration.modal.entity;

import HealthDeclaration.common.base.entity.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Account extends BaseEntity {

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "gender")
    private boolean gender;

    @Column(name = "phone_number", length = 10)
    private String phoneNumber;

    @Column(name = "parent_phone_number", length = 10)
    private String parentPhoneNumber;

    @Column(name = "province_id")
    private Long provinceId;

    @Column(name = "district_id")
    private Long districtId;

    @Column(name = "village_id")
    private Long villageId;

    @Column(name = "role_id")
    private Long roleID;
}
