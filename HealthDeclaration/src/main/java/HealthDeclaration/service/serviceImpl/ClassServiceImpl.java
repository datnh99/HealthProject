package HealthDeclaration.service.serviceImpl;

import HealthDeclaration.form.ClassFormSearch;
import HealthDeclaration.modal.dto.ClassDto;
import HealthDeclaration.modal.entity.Class;
import HealthDeclaration.repository.IClassRepository;
import HealthDeclaration.repository.IClassRepositoryCustom;
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
    IClassRepositoryCustom classRepositoryCustom;

    @Autowired
    ModelMapper mapper;

    @Override
    public List<ClassDto> getClasses() {
        List<Class> list = repository.findAll();
        List<ClassDto> classes = list.stream().map(s -> mapToClassDto(s)).collect(Collectors.toList());
        return classes;
    }

    @Override
    public List<ClassDto> searchClass(ClassFormSearch classFormSearch, int pageIndex, int pageSize) {
        return classRepositoryCustom.searchClass(classFormSearch, pageIndex, pageSize);
    }

    private ClassDto mapToClassDto(Class c) {
        ClassDto classDto = mapper.map(c, ClassDto.class);
        return classDto;
    }

    private Class mapToClass(ClassDto classDto) {
        Class c = mapper.map(classDto, Class.class);
        return c;
    }
}
