package HealthDeclaration.modal.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Role {
    @Id
    private String roleCode;
    private String roleName;
}
