package HealthDeclaration.service.serviceImpl;

import HealthDeclaration.common.utils.ObjectUtils;
import HealthDeclaration.constants.RoleConstant;
import HealthDeclaration.modal.dto.UserDto;
import HealthDeclaration.modal.entity.Role;
import HealthDeclaration.modal.entity.User;
import HealthDeclaration.modal.request.UserChangePassForm;
import HealthDeclaration.repository.IUserRepository;
import HealthDeclaration.repository.IUserRepositoryCustom;
import HealthDeclaration.service.IRoleService;
import HealthDeclaration.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    IUserRepository repository;

    @Autowired
    IUserRepositoryCustom userRepositoryCustom;

    @Autowired
    private IRoleService roleService;

    @Override
    public List<User> getAll() {
        return repository.findAll();
    }

    @Override
    public User getByUsername(String username) {
        return repository.getByUsername(username);
    }

//    @Override
//    public User getByTen(String username) {
//        return repository.getByTen(username);
//    }

    @Override
    public User add(User user) {
        return repository.save(user);
    }

    @Override
    public User update(User user) {
        return repository.save(user);
    }

    @Override
    public void deleteByUsername(String username) {
        repository.deleteByUsername(username);
    }

    @Override
    public List<UserDto> searchTeacherByName(String teacherName, int pPageIndex, int pageSize) {
//        int pageIndex = pPageIndex-1;
//        pageIndex = pageIndex > 0 ? pageIndex : 0;
//        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Role teacherRole = roleService.getByCode(RoleConstant.ROLE_GVCN);
        if(!ObjectUtils.isNullorEmpty(teacherRole)) {
//            return repository.searchTeacherByName(teacherName, teacherRole.getId());
            return userRepositoryCustom.searchTeacherByName(teacherName, teacherRole.getId() , pPageIndex, pageSize);
        }
        return null;
    }

    @Override
    public boolean changePassword(UserChangePassForm form) {
        User user = repository.getByUsername(form.getUsername());
        if (user == null) {
            return false;
        }
        if (!user.getPassword().equals(form.getOldPassword())) {
            return false;
        }
        // newpass # oldpass
        if (user.getPassword().equals(form.getNewPassword())) {
            return false;
        }
        if (!form.getNewPassword().equals(form.getConfirmPassword())) {
            return false;
        }
        user.setPassword(form.getNewPassword());
        repository.save(user);
        return true;
    }

}
