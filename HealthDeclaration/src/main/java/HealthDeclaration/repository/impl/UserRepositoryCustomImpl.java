package HealthDeclaration.repository.impl;

import HealthDeclaration.common.base.repository.BaseRepository;
import HealthDeclaration.common.utils.ObjectUtils;
import HealthDeclaration.form.ClassFormSearch;
import HealthDeclaration.form.UserFormSearch;
import HealthDeclaration.modal.dto.ClassDto;
import HealthDeclaration.modal.dto.UserDto;
import HealthDeclaration.repository.IUserRepositoryCustom;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
@Log4j2
public class UserRepositoryCustomImpl extends BaseRepository implements IUserRepositoryCustom {

    @Override
    public List<UserDto> searchTeacherByName(String teacherName, Long roleID, int pageIndex, int pageSize) {
        TypedQuery<UserDto> query = this.buildSearchTeacher(teacherName , roleID, UserDto.class);
        query.setFirstResult((pageIndex - 1) * pageSize).setMaxResults(pageSize);
        return query.getResultList();
    }

    @Override
    public List<UserDto> searchUserToManagement(UserFormSearch formSearch, Long roleID, int pageIndex, int pageSize) {
        TypedQuery<UserDto> query = this.buildSearchUserToManagement(formSearch , roleID, UserDto.class, false);
        query.setFirstResult((pageIndex - 1) * pageSize).setMaxResults(pageSize);
        return query.getResultList();
    }

    @Override
    public Long countSearchUserToManagement(UserFormSearch formSearch, Long roleID) {
        TypedQuery<Long> query = this.buildSearchUserToManagement(formSearch , roleID, Long.class, true);
        return query.getSingleResult();
    }

    private <T> TypedQuery<T> buildSearchUserToManagement(UserFormSearch formSearch, Long roleID, Class<T> clazz, Boolean count) {
        StringBuilder sql = new StringBuilder();
        if(count) {
            sql.append("select count(u.id) "
                    + "from User u "
                    + " where 1=1 and u.deleted = false ");
        } else {
            sql.append("select new HealthDeclaration.modal.dto.UserDto(u.id, u.username, u.fullName, u.dob, u.gender, u.phoneNumber, " +
                    " u.parentPhoneNumber, u.provinceId, u.provinceName, u.districtId, u.districtName, u.wardId, u.wardName, u.addressDetail ) "
                    + "from User u "
                    + " where 1=1 and u.deleted = false ");
        }
        Map<String, Object> params = new HashMap<>();
        if (!ObjectUtils.isNullorEmpty(formSearch.getFullName())) {
            sql.append(" and LOWER(u.fullName) like :studentName ");
            params.put("studentName", "%" + formSearch.getFullName().toLowerCase() + "%");
        }
        if (!ObjectUtils.isNullorEmpty(formSearch.getGenderSearch())) {
            sql.append(" and u.gender = :gender ");
            params.put("gender",  formSearch.getGenderSearch());
        }
        if (!ObjectUtils.isNullorEmpty(formSearch.getDistrictName())) {
            sql.append(" and LOWER(u.districtName) like :district ");
            params.put("district",  "%" + formSearch.getDistrictName().toLowerCase() + "%");
        }
        if (!ObjectUtils.isNullorEmpty(formSearch.getProvinceName())) {
            sql.append(" and LOWER(u.provinceName) like :provinceName ");
            params.put("provinceName",  "%" + formSearch.getProvinceName().toLowerCase() + "%");
        }
        if (!ObjectUtils.isNullorEmpty(formSearch.getWardName())) {
            sql.append(" and LOWER(u.wardName) like :wardName ");
            params.put("wardName", "%" + formSearch.getWardName().toLowerCase() + "%");
        }
        if (!ObjectUtils.isNullorEmpty(roleID)) {
            sql.append(" and u.roleID = :roleID");
            params.put("roleID", roleID);
        }
        if(!count) {
            sql.append(" ORDER BY u.fullName ASC");
        }
        return super.createQuery(sql.toString(), params, clazz);
    }

    private <T> TypedQuery<T> buildSearchTeacher(String teacherName, Long roleID, Class<T> clazz) {
        StringBuilder sql = new StringBuilder();
        sql.append("select new HealthDeclaration.modal.dto.UserDto(u.id, u.username, u.fullName) "
                    + "from User u "
                    + " where 1=1 and u.deleted = false ");
        Map<String, Object> params = new HashMap<>();
        if (!ObjectUtils.isNullorEmpty(teacherName)) {
            sql.append(" and LOWER(u.fullName) like :teacherName ");
            params.put("teacherName", "%" + teacherName.toLowerCase() + "%");
        }
        if (!ObjectUtils.isNullorEmpty(roleID)) {
            sql.append(" and u.roleID = :roleID");
            params.put("roleID", roleID);
        }
        sql.append(" ORDER BY u.fullName ASC");
        return super.createQuery(sql.toString(), params, clazz);
    }
}
