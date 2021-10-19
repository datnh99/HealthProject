package HealthDeclaration.service.serviceImpl;

import HealthDeclaration.modal.dto.ClassDto;
import HealthDeclaration.modal.entity.Class2021;
import HealthDeclaration.repository.IClassRepository;
import HealthDeclaration.service.IClassService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassServiceImpl implements IClassService {
    @Autowired
    IClassRepository repository;

    @Autowired
    ModelMapper mapper;

    @Override
    public List<ClassDto> getClasses() {
        List<Class2021> list = repository.findAll();
        List<ClassDto> classes = list.stream().map(s -> mapToClassDto(s)).collect(Collectors.toList());
        return classes;
    }

    private ClassDto mapToClassDto(Class2021 c) {
        ClassDto classDto = mapper.map(c, ClassDto.class);
        return classDto;
    }

    private Class2021 mapToClass(ClassDto classDto) {
        Class2021 c = mapper.map(classDto, Class2021.class);
        return c;
    }
}
