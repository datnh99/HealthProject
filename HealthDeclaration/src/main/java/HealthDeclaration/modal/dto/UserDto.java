package HealthDeclaration.modal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;

    private String userName;

    private String fullName;

    private Date dob;

    public UserDto(Long id, String userName, String fullName) {
        this.id = id;
        this.userName = userName;
        this.fullName = fullName;
    }
}
