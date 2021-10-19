package HealthDeclaration.service;

import HealthDeclaration.modal.dto.ClassDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IClassService {
    List<ClassDto> getClasses();
}
