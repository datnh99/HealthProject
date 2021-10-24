package HealthDeclaration.controller;

import HealthDeclaration.common.response.utils.ResponseUtils;
import HealthDeclaration.modal.dto.UserDto;
import HealthDeclaration.modal.entity.User;
import HealthDeclaration.service.IUserService;
import HealthDeclaration.vo.ResponseMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@Log4j2
public class UserController {
    @Autowired
    IUserService service;

    @GetMapping("/getAll")
    private ResponseEntity getAll() {
        //ResponseMessa
        try {
            List<User> list = service.getAll();
            if (list.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search-teacher-by-name")
    private ResponseEntity searchTeacherByName(@Param("teacherName") String teacherName,
                                        @Param("pageIndex") int pageIndex,
                                        @Param("pageSize") int pageSize) {
        ResponseMessage responseMessage = new ResponseMessage();
        try{
            responseMessage.setSuccess(true);
            List<UserDto> result = service.searchTeacherByName(teacherName, pageIndex, pageSize);
            Map<String, Object> results = new HashMap<>();
            results.put("items", result);
            responseMessage.setData(results);
        } catch (Exception e) {
            log.error(e);
            responseMessage.setSuccess(false);
            return ResponseUtils.buildResponseMessage(false, responseMessage);
        }
        return ResponseUtils.buildResponseMessage(true, responseMessage);
    }

    @GetMapping("/getOne")
    private ResponseEntity<User> getByUsername(@RequestParam("username") String username) {
//        try {
//            User user = service.getByTen(username);
//            if (user == null) {
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            }
//            return new ResponseEntity<>(user, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
        return null;
    }

    @PostMapping("/add")
    private ResponseEntity<User> add(@RequestBody User user) {
        try {
            return new ResponseEntity<>(service.add(user), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    private ResponseEntity<User> updateEmployee(@RequestBody User user) {
        if (user != null) {
            return new ResponseEntity<>(service.update(user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    @DeleteMapping("/delete/{username}")
//    private ResponseEntity<Boolean> addEmployee(@PathVariable String username) {
//        try {
//            Account account = service.deleteByUsername(username);
//            if (account.getUsername() == null) {
//                return new ResponseEntity<>(false, HttpStatus.NO_CONTENT);
//            }
//            return new ResponseEntity<>(true, HttpStatus.NO_CONTENT);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
