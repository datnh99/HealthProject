package HealthDeclaration.form;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserFormSearch {
    String fullName;

    Boolean gender;

    String wardName;

    String districtName;

    String provinceName;
}
