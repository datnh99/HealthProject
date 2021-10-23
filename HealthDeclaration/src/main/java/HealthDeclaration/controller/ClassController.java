package HealthDeclaration.controller;

import HealthDeclaration.modal.dto.ClassDto;
import HealthDeclaration.service.IClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/class")
public class ClassController {
    @Autowired
    IClassService service;

    @GetMapping("/getAll")
    public List<ClassDto> getClasses() {
        return service.getClasses();
    }
}
