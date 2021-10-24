package HealthDeclaration.repository.impl;

import HealthDeclaration.common.base.repository.BaseRepository;
import HealthDeclaration.common.utils.ObjectUtils;
import HealthDeclaration.form.ClassFormSearch;
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
