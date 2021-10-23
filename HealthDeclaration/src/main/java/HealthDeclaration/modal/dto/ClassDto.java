package HealthDeclaration.modal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassDto {
    private Long id;

    private String className;

    private Long teacherID;

    private String teacherAccount;

    private String teacherName;
}
