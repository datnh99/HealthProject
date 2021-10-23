package HealthDeclaration.repository.impl;

import HealthDeclaration.common.base.repository.BaseRepository;
import HealthDeclaration.form.ClassFormSearch;
import HealthDeclaration.modal.dto.ClassDto;
import HealthDeclaration.repository.IClassRepositoryCustom;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
@Transactional
@Log4j2
public class IClassRepositoryCustomImpl extends BaseRepository implements IClassRepositoryCustom {

    @Override
    public List<ClassDto> searchClass(ClassFormSearch classFormSearch, int pageIndex, int pageSize) {
        TypedQuery<ClassDto> query = this.buildSearchNotification(false , classFormSearch, ClassDto.class);
        query.setFirstResult((pageIndex - 1) * pageSize).setMaxResults(pageSize);
        return query.getResultList();
    }

    private <T> TypedQuery<T> buildSearchNotification2(final boolean count, ClassFormSearch classFormSearch, Class<T> clazz) {
        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        String baseQuery =
                "select HealthDeclaration.modal.dto.ClassDto(cl.id, cl.name, acc.id, acc.username, acc.fullName) "
                        + "from Class cl join Account acc " +
                        "ON acc.id = cl.teacherId "
                        + " where 1=1 ";
        StringBuilder sql = new StringBuilder(baseQuery);
        Map<String, Object> params = new HashMap<>();

        sql.append(" ORDER BY cl.name ASC");
        return super.createQuery(sql.toString(), params, clazz);
    }

    private <T> TypedQuery<T> buildSearchNotification(final boolean count, ClassFormSearch classFormSearch, Class<T> clazz) {
        StringBuilder sql = new StringBuilder();
        sql.append("select new HealthDeclaration.modal.dto.ClassDto(cl.id, cl.name, acc.id, acc.username, acc.fullName) "
            + "from Class cl join Account acc " +
            "ON acc.id = cl.teacherId "
            + " where 1=1 ");
        if (count) {
            sql.append("select count(cl) "
                + "from Class cl join Account acc " +
                "ON acc.id = cl.teacherId "
                + " where 1=1 ");
        }
        Map<String, Object> params = new HashMap<>();
        if (!ObjectUtils.isEmpty(classFormSearch.getClassName())) {
            sql.append(" and LOWER(cl.name) like :className ");
            params.put("className", "%" + classFormSearch.getClassName().toLowerCase() + "%");
        }
        if (!ObjectUtils.isEmpty(classFormSearch.getTeacherName())) {
            sql.append(" and ( LOWER(acc.username) like :teacherName OR LOWER(acc.fullName) like :teacherName)");
            params.put("teacherName", "%" + classFormSearch.getTeacherName().toLowerCase() + "%");
        }
        if (!count) {
            sql.append(" ORDER BY cl.name ASC");
        }
        return super.createQuery(sql.toString(), params, clazz);
    }

}
