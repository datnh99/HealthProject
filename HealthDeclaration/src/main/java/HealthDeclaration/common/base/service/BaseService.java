package HealthDeclaration.common.base.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BaseService {

	private ModelMapper modelMapper;

	public ModelMapper getModelMapper() {
		return modelMapper;
	}

	@Autowired
	public void setModelMapper(final ModelMapper pModelMapper) {
		modelMapper = pModelMapper;
	}
	
	public String getLoggedInUsername() {
		return "duynd";
	}
}
