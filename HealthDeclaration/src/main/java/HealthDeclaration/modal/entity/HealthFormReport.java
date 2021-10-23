package HealthDeclaration.modal.entity;

import HealthDeclaration.common.base.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import java.util.Date;

@Data
@Entity
public class HealthFormReport extends BaseEntity {
    private boolean diChuyen;
    private boolean dauHieuCovid;
    private boolean tiepXucCovider;
    private boolean tiepXucForeign;
    private boolean tiepXucNguoiCoBieuHienCovid;
    private String tenPhuongTien;
    private String SoHieu;
    private Date startDate;
    private int startProvinceId;
    private int startDistrictId;
    private int startVillageId;
    private String startDetailAddress;
    private Date endDate;
    private int endProvinceId;
    private int endDistrictId;
    private int endVillageId;
    private String endDetailAddress;
    private String username;
}
