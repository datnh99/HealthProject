package HealthDeclaration.modal.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;

@Data
@Entity
public class Account {
    @Id
    private String username;
    private String password;
    private String fullName;
    private Date dob;
    private boolean gender;
    @Column(length = 10)
    private String phoneNumber;
    @Column(length = 10)
    private String parentPhoneNumber;
    private String createBy;
    private Date createTime;
    private int classId;
    private int provinceId;
    private int districtId;
    private int villageId;
    private boolean flg;
}
