package HealthDeclaration.form;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserFormSearch {
    String fullName;

    String gender;

    Boolean genderSearch;

    String wardName;

    String districtName;

    String provinceName;
}
