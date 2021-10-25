package HealthDeclaration.repository;

import HealthDeclaration.modal.dto.UserDto;
import HealthDeclaration.modal.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<User, Long>, CrudRepository<User, Long> {
    User getByUsername(String username);

    @Query("update User acc set acc.deleted = true where acc.username = :username")
    List<String> deleteByUsername(@Param("username") String username);

    @Query(value = "select new HealthDeclaration.modal.dto.UserDto(u.id, u.username, u.fullName) " +
            " FROM User u where u.fullName like :teacherName AND u.roleID = :teacherRole and u.deleted = false")
    List<UserDto> searchTeacherByName(@Param("teacherName") String teacherName, @Param("teacherRole") Long teacherRole);


}
