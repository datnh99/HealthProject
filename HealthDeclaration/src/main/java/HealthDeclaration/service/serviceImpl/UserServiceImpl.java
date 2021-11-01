package HealthDeclaration.service.serviceImpl;

import HealthDeclaration.common.base.service.BaseService;
import HealthDeclaration.common.utils.ObjectUtils;
import HealthDeclaration.common.utils.StringUtils;
import HealthDeclaration.constants.RoleConstant;
import HealthDeclaration.form.UserAddForm;
import HealthDeclaration.form.UserFormSearch;
import HealthDeclaration.modal.dto.UserDto;
import HealthDeclaration.modal.entity.Class;
import HealthDeclaration.modal.entity.Role;
import HealthDeclaration.modal.entity.User;
import HealthDeclaration.modal.request.UserChangePassForm;
import HealthDeclaration.modal.request.UserUpdateForm;
import HealthDeclaration.repository.IUserRepository;
import HealthDeclaration.repository.IUserRepositoryCustom;
import HealthDeclaration.service.IClassService;
import HealthDeclaration.service.IRoleService;
import HealthDeclaration.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl extends BaseService implements IUserService {
    @Autowired
    IUserRepository repository;

    @Autowired
    IUserRepositoryCustom userRepositoryCustom;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IClassService classService;

    @Override
    public List<User> getAll() {
        return repository.findAll();
    }

    @Override
    public User getByUsername(String username) {
        return repository.getByUsername(username);
    }

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
        user.setProvinceCode(updateForm.getProvinceCode());
        user.setDistrictCode(updateForm.getDistrictCode());
        user.setWardCode(updateForm.getWardCode());
        user.setRoleCode(updateForm.getRoleCode());
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
        Role teacherRole = roleService.getByCode(RoleConstant.ROLE_GIAO_VIEN_CHU_NHIEM);
        if(!ObjectUtils.isNullorEmpty(teacherRole)) {
            return userRepositoryCustom.searchTeacherByName(teacherName, teacherRole.getRoleCode() , pPageIndex, pageSize);
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
        List<String> roleCodeOFUser = getLoggedInUserRoles();
        List<UserDto> resultList = new ArrayList<>();
        if(ObjectUtils.isNullorEmpty(roleCodeOFUser)) {
            throw new IllegalArgumentException("You don't have permission to see that data!");
        }
        if(roleCodeOFUser.get(0).equalsIgnoreCase(RoleConstant.ROLE_GIAO_VIEN_CHU_NHIEM)) {
            List<Class> clazzList = classService.getByTeacherUser(getLoggedInUsername());

            if (!ObjectUtils.isNullorEmpty(formSearch.getGender())) {
                String gender = StringUtils.removeAccent(formSearch.getGender()).toLowerCase();
                if (gender.equalsIgnoreCase("nam")) {
                    formSearch.setGenderSearch(true);
                } else if (gender.equalsIgnoreCase("nu")) {
                    formSearch.setGenderSearch(false);
                } else {
                    return null;
                }
            }
            if (!ObjectUtils.isNullorEmpty(clazzList)) {
                formSearch.setClassID(clazzList.get(clazzList.size() - 1).getId());
            }
            resultList = userRepositoryCustom.searchStudentToManagement(formSearch, pageIndex, pageSize);
        } else if (roleCodeOFUser.get(0).equalsIgnoreCase(RoleConstant.ROLE_HIEU_TRUONG)) {
            if (!ObjectUtils.isNullorEmpty(formSearch.getGender())) {
                String gender = StringUtils.removeAccent(formSearch.getGender()).toLowerCase();
                if (gender.equalsIgnoreCase("nam")) {
                    formSearch.setGenderSearch(true);
                } else if (gender.equalsIgnoreCase("nu")) {
                    formSearch.setGenderSearch(false);
                } else {
                    return null;
                }
            }
            resultList = userRepositoryCustom.searchStudentToManagement(formSearch, pageIndex, pageSize);
        } else {
            throw new IllegalArgumentException("You don't have permission to see that data!");
        }
        if(!ObjectUtils.isNullorEmpty(resultList)) {
            for(int i = 0 ; i < resultList.size() ; i++) {
                resultList.get(i).setIndex(pageSize * (pageIndex - 1) + i + 1);
            };
        }
        return resultList;
    }

    @Override
    public Long countSearchUserToManagement(UserFormSearch formSearch) {

        List<Class> clazzList = classService.getByTeacherUser(getLoggedInUsername());

        if(!ObjectUtils.isNullorEmpty(formSearch.getGender())) {
            String gender = StringUtils.removeAccent(formSearch.getGender()).toLowerCase();
            if(gender.equalsIgnoreCase("nam")) {
                formSearch.setGenderSearch(true);
            } else if (gender.equalsIgnoreCase("nu")) {
                formSearch.setGenderSearch(false);
            } else {
                return null;
            }
        }

        if(!ObjectUtils.isNullorEmpty(clazzList)) {
            formSearch.setClassID(clazzList.get(clazzList.size() - 1).getId());
        }
        return userRepositoryCustom.countSearchUserToManagement(formSearch);
    }

    @Override
    public User addNewStudent(UserAddForm userAddForm) {
        User user = new User();
        user.setCreatedBy("hieppv4");
        user.setCreatedTime(new Date());
        user.setModifiedBy("hieppv4");
        user.setModifiedTime(new Date());
        user.setDeleted(false);
        if(!ObjectUtils.isNullorEmpty(userAddForm.getFullName())) {
            user.setFullName(userAddForm.getFullName());
        } else {
            throw new IllegalArgumentException("Full name can not be blank!");
        }
        user.setDob(userAddForm.getDob());
        user.setGender(userAddForm.getGender());
        user.setPhoneNumber(userAddForm.getPhoneNumber());
        user.setParentPhoneNumber(userAddForm.getParentPhoneNumber());
        user.setProvinceCode(userAddForm.getProvinceCode());
        user.setDistrictCode(userAddForm.getDistrictCode());
        user.setWardCode(userAddForm.getWardCode());
        user.setAddressDetail(userAddForm.getAddressDetail());
        Role role = roleService.getByCode(RoleConstant.ROLE_HOC_SINH);
        if(!ObjectUtils.isNullorEmpty(role)) {
            user.setRoleCode(role.getRoleCode());
        }
        String username = getNewAccountWithFullName(userAddForm.getFullName());
        if(!ObjectUtils.isNullorEmpty(username)) {
            user.setUsername(username);
        } else {
            throw new IllegalArgumentException("Fail in create account!");
        }
        user.setPassword(userAddForm.getPassword());
        user.setClassID(userAddForm.getClassID());
        return repository.save(user);
    }

    @Override
    public List<UserDto> searchTeacherFreeByName(String teacherName) {
        return repository.getTeacherFreeByName(RoleConstant.ROLE_GIAO_VIEN_CHU_NHIEM, "%" + teacherName + "%");
    }

    private String getNewAccountWithFullName(String fullName) {
        String account = null;
        List<String> list = Arrays.asList(fullName.split(" "));
        int sizeName = list.size();
        if( sizeName > 1) {
            account = list.get(sizeName - 1);
            for (int i = 0 ; i < sizeName - 1; i++) {
                account = account + list.get(i).substring(0, 1);
            }
        }
        account = account.toLowerCase();
        List<String> accList =  repository.getLastAccountByAccount(account + "%");
        if(ObjectUtils.isNullorEmpty(accList)) {
            return account;
        }
        for (int i = accList.size() - 1; i >= 0; i--) {
            String accountInList = accList.get(i);
            accountInList = accountInList.replace(account, "");
            if(StringUtils.isNumeric(accountInList)) {
                account = account + (Integer.valueOf(accountInList) + 1);
                break;
            }
        }
        return account;
    }

}
