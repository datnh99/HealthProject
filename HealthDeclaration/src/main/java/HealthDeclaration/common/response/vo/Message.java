package HealthDeclaration.common.response.vo;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    /**
     * code
     */
    private String code;

    /**
     * Message
     */
    private String msg;
}
