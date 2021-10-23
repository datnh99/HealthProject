package HealthDeclaration.modal.dto;

import lombok.Data;

import java.util.Date;

@Data
public class HealthFormDto {
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

