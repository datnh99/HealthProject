package HealthDeclaration.service.serviceImpl;

import HealthDeclaration.common.base.service.BaseService;
import HealthDeclaration.form.ClassFormSearch;
import HealthDeclaration.modal.dto.ClassDto;
import HealthDeclaration.modal.entity.Class;
import HealthDeclaration.modal.request.ClassAddForm;
import HealthDeclaration.modal.request.ClassUpdateForm;
import HealthDeclaration.repository.IUserRepository;
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
public class ClassServiceImpl extends BaseService implements IClassService {
    @Autowired
    IClassRepository repository;

    @Autowired
    IClassRepositoryCustom classRepositoryCustom;

    @Autowired
    ModelMapper mapper;

    @Autowired
    IUserRepository accountRepository;

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
        String username = getLoggedInUsername();
        clazz.setCreatedBy(username);
        clazz.setCreatedTime(new Date());
        clazz.setModifiedBy(username);
        clazz.setModifiedTime(new Date());
        clazz.setName(c.getName());
        clazz.setTeacherId(c.getTeacherId());
        clazz.setDeleted(false);
        return repository.save(clazz);
    }

    @Override
    public Class updateClass(ClassUpdateForm c) {
        Class clazz = repository.getById(c.getId());
        clazz.setModifiedBy(getLoggedInUsername());
        clazz.setModifiedTime(new Date());
        clazz.setName(c.getName());
        clazz.setTeacherId(c.getTeacherId());
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
