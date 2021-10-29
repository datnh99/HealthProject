package HealthDeclaration.modal.dto;

import lombok.Data;

@Data
public class ProvinceDto {
    private Long id;
    private Long code;
    private String name;

    public ProvinceDto(Long id, Long code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }
}
