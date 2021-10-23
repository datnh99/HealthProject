package HealthDeclaration.repository;

import HealthDeclaration.form.ClassFormSearch;
import HealthDeclaration.modal.dto.ClassDto;
import HealthDeclaration.modal.entity.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface IClassRepository extends JpaRepository<Class, Long>, CrudRepository<Class, Long> {

//    @Query(value = "SELECT new HealthDeclaration.modal.dto.ClassDto(cl.id, cl.name, acc.id, acc.username, acc.fullName)" +
//            " from Class cl JOIN Account acc ON acc.id = cl.teacherId " +
//            " where cl.name like :className and (acc.username like :teacherName or acc.fullName like :teacherName) " +
//            " ORDER BY cl.name ASC ")
//    List<ClassDto> searchClass(@Param("className") String className,@Param("teacherName")  String teacherName, Pageable pageable);
}
