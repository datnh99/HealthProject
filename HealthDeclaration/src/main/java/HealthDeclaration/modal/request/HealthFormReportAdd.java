package HealthDeclaration.modal.request;

import lombok.Data;

import java.util.Date;

@Data
public class HealthFormReportAdd {
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
}
