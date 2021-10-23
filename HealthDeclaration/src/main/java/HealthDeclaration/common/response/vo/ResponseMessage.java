package HealthDeclaration.common.response.vo;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessage {

	/**
	 * Success flag
	 */
	private boolean success;

	/**
	 * List message
	 */
	private List<Message> message;

	/**
	 * Response data
	 */
	private Object data;

}
