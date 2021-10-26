package HealthDeclaration.modal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HealthFormDto {
    private Long index;
    private Long id;
    private Date ngayKhaiBao;
    private Long userId;
    private String reportType;
    private String status;

    public HealthFormDto(Long id, Date ngayKhaiBao, Long userId, String reportType, String status) {
        this.id = id;
        this.ngayKhaiBao = ngayKhaiBao;
        this.userId = userId;
        this.reportType = reportType;
        this.status = status;
    }
}

