package HealthDeclaration.modal.request;

import lombok.Data;

import java.util.Date;

@Data
public class HealthFormReportAdd {
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
    private String reportType;
    private Long userId;
}
