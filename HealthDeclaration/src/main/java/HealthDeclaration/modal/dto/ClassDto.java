package HealthDeclaration.modal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassDto {
    private Long index;

    private Long id;

    private String className;

    private Long teacherID;

    private String teacherAccount;

    private String teacherName;

    private Boolean isActive;

    public ClassDto(Long id, String className, Long teacherID, String teacherAccount, String teacherName, Boolean isActive) {
        this.id = id;
        this.className = className;
        this.teacherID = teacherID;
        this.teacherAccount = teacherAccount;
        this.teacherName = teacherName;
        this.isActive = isActive;
    }

    public ClassDto(Long id, String className) {
        this.id = id;
        this.className = className;
    }
}
