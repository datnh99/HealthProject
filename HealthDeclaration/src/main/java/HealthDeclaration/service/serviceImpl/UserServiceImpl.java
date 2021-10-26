package HealthDeclaration.service.serviceImpl;

import HealthDeclaration.common.base.service.BaseService;
import HealthDeclaration.common.utils.ObjectUtils;
import HealthDeclaration.constants.RoleConstant;
import HealthDeclaration.form.UserFormSearch;
import HealthDeclaration.modal.dto.UserDto;
import HealthDeclaration.modal.entity.Role;
import HealthDeclaration.modal.entity.User;
import HealthDeclaration.modal.request.UserChangePassForm;
import HealthDeclaration.modal.request.UserUpdateForm;
import HealthDeclaration.repository.IUserRepository;
import HealthDeclaration.repository.IUserRepositoryCustom;
import HealthDeclaration.service.IRoleService;
import HealthDeclaration.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl extends BaseService implements IUserService {
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
    public boolean update(UserUpdateForm updateForm) {
        User user = repository.getById(updateForm.getId());
        user.setModifiedBy(getLoggedInUsername());
        user.setModifiedTime(new Date());
        user.setFullName(updateForm.getFullName());
        user.setDob(updateForm.getDob());
        user.setGender(updateForm.isGender());
        user.setPhoneNumber(updateForm.getPhoneNumber());
        user.setParentPhoneNumber(updateForm.getParentPhoneNumber());
        user.setProvinceId(updateForm.getProvinceId());
        user.setDistrictId(updateForm.getDistrictId());
        user.setWardId(updateForm.getWardId());
        user.setProvinceName(updateForm.getProvinceName());
        user.setDistrictName(updateForm.getDistrictName());
        user.setWardName(updateForm.getWardName());
        user.setAddressDetail(updateForm.getAddressDetail());
        repository.save(user);
        return true;
    }

    @Override
    public void deleteByUsername(String username) {

    }

    @Override
    public void delete(Long id) {
        User user = repository.getById(id);
        user.setDeleted(true);
        repository.save(user);
    }

    @Override
    public List<UserDto> searchTeacherByName(String teacherName, int pPageIndex, int pageSize) {
//        int pageIndex = pPageIndex-1;
//        pageIndex = pageIndex > 0 ? pageIndex : 0;
//        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Role teacherRole = roleService.getByCode(RoleConstant.ROLE_GIAO_VIEN_CHU_NHIEM);
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

    @Override
    public List<UserDto> searchUserToManagement(UserFormSearch formSearch, int pageIndex, int pageSize) {
        // Get role of user
        String userRole = repository.getUserRoleByUsername(getLoggedInUsername());
        Long roleID = null;

        // If role teacher ==> search hoc sinh
        if(userRole.equalsIgnoreCase(RoleConstant.ROLE_GIAO_VIEN_CHU_NHIEM)) {
            Role studentRole = roleService.getByCode(RoleConstant.ROLE_HOC_SINH);
            roleID = studentRole.getId();
        }
        return userRepositoryCustom.searchUserToManagement(formSearch, roleID, pageIndex, pageSize);
    }

    @Override
    public Long countSearchUserToManagement(UserFormSearch formSearch) {
        // Get role of user
        String userRole = repository.getUserRoleByUsername(getLoggedInUsername());
        Long roleID = null;

        // If role teacher ==> search hoc sinh
        if(userRole.equalsIgnoreCase(RoleConstant.ROLE_GIAO_VIEN_CHU_NHIEM)) {
            Role studentRole = roleService.getByCode(RoleConstant.ROLE_HOC_SINH);
            roleID = studentRole.getId();
        }
        return userRepositoryCustom.countSearchUserToManagement(formSearch, roleID);
    }

}
