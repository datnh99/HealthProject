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
    //
    private String tenPhuongTien;
    private String SoHieu;
    private Date startDate;
    private String startProvince;
    private String startDistrict;
    private String startWard;
    private String startDetailAddress;
    private Date endDate;
    private String endProvince;
    private String endDistrict;
    private String endWard;
    private String endDetailAddress;
    //
    private Date ngayKhaiBao;
    private Long userId;
}
