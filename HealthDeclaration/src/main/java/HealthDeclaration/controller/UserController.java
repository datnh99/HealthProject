package HealthDeclaration.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import HealthDeclaration.common.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import HealthDeclaration.common.response.utils.ResponseUtils;
import HealthDeclaration.form.UserAddForm;
import HealthDeclaration.form.UserFormSearch;
import HealthDeclaration.modal.dto.UserDto;
import HealthDeclaration.modal.entity.User;
import HealthDeclaration.modal.request.UserChangePassForm;
import HealthDeclaration.modal.request.UserUpdateForm;
import HealthDeclaration.service.IUserService;
import HealthDeclaration.vo.ResponseMessage;
import lombok.extern.log4j.Log4j2;

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
			return ResponseUtils.buildResponseMessage(false, responseMessage);
		}
		return ResponseUtils.buildResponseMessage(true, responseMessage);
	}

	@GetMapping("/getAll")
	private ResponseEntity getAll() {
		// ResponseMessa
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
			@Param("pageIndex") int pageIndex, @Param("pageSize") int pageSize) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
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

	@GetMapping("/search-teacher-free-by-name")
	private ResponseEntity searchTeacherFreeByName(@Param("teacherName") String teacherName) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			responseMessage.setSuccess(true);
			List<UserDto> result = service.searchTeacherFreeByName(teacherName);
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

    @PostMapping("/search-user-to-management")
    private ResponseEntity searchUserToManagement(@RequestBody UserFormSearch formSearch,
                                               @Param("pageIndex") int pageIndex,
                                               @Param("pageSize") int pageSize) {
        ResponseMessage responseMessage = new ResponseMessage();
        try{
            responseMessage.setSuccess(true);
            List<UserDto> result = service.searchUserToManagement(formSearch, pageIndex, pageSize);
            Long total = service.countSearchUserToManagement(formSearch);
			User teacher = null;
			if (!ObjectUtils.isNullorEmpty(result)) {
				teacher = service.getByUsername(result.get(0).getTeacherUsername());
			}
            Map<String, Object> results = new HashMap<>();
            results.put("items", result);
            results.put("total", total);
			results.put("teacher", teacher);
            responseMessage.setData(results);
        } catch (Exception e) {
            log.error(e);
            responseMessage.setSuccess(false);
            return ResponseUtils.buildResponseMessage(false, responseMessage);
        }
        return ResponseUtils.buildResponseMessage(true, responseMessage);
    }

	@PostMapping("/search-teacher-to-management")
	private ResponseEntity searchTeacherToManagement(@RequestBody UserFormSearch formSearch,
												  @Param("pageIndex") int pageIndex,
												  @Param("pageSize") int pageSize) {
		ResponseMessage responseMessage = new ResponseMessage();
		try{
			responseMessage.setSuccess(true);
			List<UserDto> result = service.searchTeacherToManagement(formSearch, pageIndex, pageSize);
			Long total = service.countSearchTeacherToManagement(formSearch);
			Map<String, Object> results = new HashMap<>();
			results.put("items", result);
			results.put("total", total);
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
			responseMessage.setData(service.update(updateForm));
			responseMessage.setSuccess(true);
			responseMessage.setData(true);
		} catch (Exception e) {
			responseMessage.setSuccess(false);
			responseMessage.setData(false);
			return ResponseUtils.buildResponseMessage(false, responseMessage);
		}
		return ResponseUtils.buildResponseMessage(true, responseMessage);
	}

	@DeleteMapping("/delete")
	public ResponseEntity delete(@RequestParam("id") Long id) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			service.delete(id);
			responseMessage.setSuccess(true);
			responseMessage.setData(true);
		} catch (Exception e) {
			responseMessage.setSuccess(false);
			responseMessage.setData(false);
			return ResponseUtils.buildResponseMessage(false, responseMessage);
		}
		return ResponseUtils.buildResponseMessage(true, responseMessage);
	}

	@GetMapping("/get-user-by-username")
	public ResponseEntity getByUsername(@RequestParam String username) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			responseMessage.setSuccess(true);
			responseMessage.setData(service.getByUsername(username));
		} catch (Exception e) {
			responseMessage.setSuccess(false);
			responseMessage.setData(false);
			return ResponseUtils.buildResponseMessage(false, responseMessage);
		}
		return ResponseUtils.buildResponseMessage(true, responseMessage);
	}

    @PostMapping("/add-new-student")
    private ResponseEntity addNewStudent(@RequestBody UserAddForm userAddForm) {
        ResponseMessage responseMessage = new ResponseMessage();
        try{
            User user = service.addNewStudent(userAddForm);
            Map<String, Object> results = new HashMap<>();
            results.put("items", user);
            responseMessage.setSuccess(true);
            responseMessage.setData(results);
        } catch (Exception e) {
            log.error(e);
            responseMessage.setSuccess(false);
            return ResponseUtils.buildResponseMessage(false, responseMessage);
        }
        return ResponseUtils.buildResponseMessage(true, responseMessage);
    }

	@PostMapping("/add-new-teacher")
	private ResponseEntity addNewTeacher(@RequestBody UserAddForm userAddForm) {
		ResponseMessage responseMessage = new ResponseMessage();
		try{
			User user = service.addNewTeacher(userAddForm);
			Map<String, Object> results = new HashMap<>();
			results.put("items", user);
			responseMessage.setSuccess(true);
			responseMessage.setData(results);
		} catch (Exception e) {
			log.error(e);
			responseMessage.setSuccess(false);
			return ResponseUtils.buildResponseMessage(false, responseMessage);
		}
		return ResponseUtils.buildResponseMessage(true, responseMessage);
	}

	@GetMapping("/reset-password-by-username")
	public ResponseEntity resetPasswordByUsername(@RequestParam String username) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			responseMessage.setSuccess(true);
			responseMessage.setData(service.resetPasswordByUsername(username));
		} catch (Exception e) {
			responseMessage.setSuccess(false);
			responseMessage.setData(false);
			return ResponseUtils.buildResponseMessage(false, responseMessage);
		}
		return ResponseUtils.buildResponseMessage(true, responseMessage);
	}

	@PostMapping("/change-password-by-username")
	public ResponseEntity changePasswordByUsername(@RequestBody UserChangePassForm form) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			String changePass = service.changePasswordByUsername(form);
			if(ObjectUtils.isNullorEmpty(changePass)) {
				responseMessage.setSuccess(false);
				responseMessage.setData(false);
				return ResponseUtils.buildResponseMessage(false, responseMessage);
			}
			responseMessage.setSuccess(true);
			responseMessage.setData(true);
		} catch (Exception e) {
			responseMessage.setSuccess(false);
			responseMessage.setData(false);
			return ResponseUtils.buildResponseMessage(false, responseMessage);
		}
		return ResponseUtils.buildResponseMessage(true, responseMessage);
	}
}
