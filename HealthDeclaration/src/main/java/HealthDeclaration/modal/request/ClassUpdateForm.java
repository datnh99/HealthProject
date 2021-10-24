package HealthDeclaration.modal.request;

import lombok.Data;

@Data
public class ClassUpdateForm {
    private Long id;
    private String name;
    private Long teacherId;
}
