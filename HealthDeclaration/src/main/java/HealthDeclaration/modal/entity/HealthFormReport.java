package HealthDeclaration.modal.entity;

import HealthDeclaration.common.base.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import java.util.Date;

@Data
@Entity
public class HealthFormReport extends BaseEntity {
    private Boolean diChuyen;
    private Boolean dauHieuCovid;
    private Boolean tiepXucCovider;
    private Boolean tiepXucForeign;
    private Boolean tiepXucNguoiCoBieuHienCovid;
    //
    private String tenPhuongTien;
    private String soHieu;
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
    private Long userId;       ///
    private String reportType;  ///
    private String status;  ///
}
