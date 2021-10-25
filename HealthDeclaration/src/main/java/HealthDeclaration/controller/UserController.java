package HealthDeclaration.controller;

import HealthDeclaration.common.response.utils.ResponseUtils;
import HealthDeclaration.modal.dto.UserDto;
import HealthDeclaration.modal.entity.User;
import HealthDeclaration.modal.request.ClassUpdateForm;
import HealthDeclaration.modal.request.UserChangePassForm;
import HealthDeclaration.modal.request.UserUpdateForm;
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

    @PutMapping("/changePassword")
    public ResponseEntity changePassword(@RequestBody UserChangePassForm form) {
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            responseMessage.setSuccess(true);
            responseMessage.setData(service.changePassword(form));
        } catch (Exception e) {
            responseMessage.setSuccess(false);
            return  ResponseUtils.buildResponseMessage(false, responseMessage);
        }
        return  ResponseUtils.buildResponseMessage(true, responseMessage);
    }

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

    @PutMapping("/update")
    public ResponseEntity updateClass(@RequestBody UserUpdateForm updateForm) {
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            responseMessage.setSuccess(true);
            responseMessage.setData(service.update(updateForm));
        } catch (Exception e) {
            responseMessage.setSuccess(false);
            return  ResponseUtils.buildResponseMessage(false, responseMessage);
        }
        return  ResponseUtils.buildResponseMessage(true, responseMessage);
    }

    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestParam Long id) {
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            responseMessage.setSuccess(true);
            service.delete(id);
            responseMessage.setData(id);
        } catch (Exception e) {
            responseMessage.setSuccess(false);
            responseMessage.setData(false);
            return  ResponseUtils.buildResponseMessage(false, responseMessage);
        }
        return  ResponseUtils.buildResponseMessage(true, responseMessage);
    }


}
