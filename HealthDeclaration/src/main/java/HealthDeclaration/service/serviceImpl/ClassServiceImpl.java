package HealthDeclaration.service.serviceImpl;

import HealthDeclaration.form.ClassFormSearch;
import HealthDeclaration.modal.dto.ClassDto;
import HealthDeclaration.modal.entity.Class;
import HealthDeclaration.modal.request.ClassAddForm;
import HealthDeclaration.modal.request.ClassUpdateForm;
import HealthDeclaration.repository.IAccountRepository;
import HealthDeclaration.repository.IClassRepository;
import HealthDeclaration.repository.IClassRepositoryCustom;
import HealthDeclaration.service.IClassService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    @Autowired
    IAccountRepository accountRepository;

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

    @Override
    public Long countSearchClass(ClassFormSearch classFormSearch) {
        return classRepositoryCustom.countSearchClass(classFormSearch);
    }

    @Override
    public Class addClass(ClassAddForm c) {
        Class clazz = new Class();
        clazz.setCreatedBy(c.getTeacherUsername());
        clazz.setCreatedTime(new Date());
        clazz.setModifiedBy(c.getTeacherUsername());
        clazz.setModifiedTime(new Date());
        clazz.setName(c.getName());
        clazz.setTeacherId(accountRepository.getIdByUsername(c.getTeacherUsername()));
        clazz.setDeleted(false);
        return repository.save(clazz);
    }

    @Override
    public Class updateClass(ClassUpdateForm c) {
        Class clazz = repository.getById(c.getId());
        clazz.setModifiedBy(c.getTeacherUsername());
        clazz.setModifiedTime(new Date());
        clazz.setName(c.getName());
        clazz.setTeacherId(accountRepository.getIdByUsername(c.getTeacherUsername()));
        System.out.println(clazz);
        return repository.save(clazz);
    }

    @Override
    public void deleteClass(int id) {
        Class clazz = repository.getById(new Long(id));
        clazz.setDeleted(true);
        repository.save(clazz);
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
