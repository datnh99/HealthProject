package HealthDeclaration.controller;

import HealthDeclaration.common.response.utils.ResponseUtils;
import HealthDeclaration.form.ClassFormSearch;
import HealthDeclaration.modal.dto.ClassDto;
import HealthDeclaration.service.IClassService;
import HealthDeclaration.vo.ResponseMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/class")
@Log4j2
public class ClassController {
    @Autowired
    IClassService classService;

    @GetMapping("/getAll")
    public List<ClassDto> getClasses() {
        return classService.getClasses();
    }

    @PostMapping("/search-class")
    public ResponseEntity searchClasses(@RequestBody ClassFormSearch classFormSearch
            , @RequestParam("pageIndex") int pageIndex
            , @RequestParam("pageSize") int pageSize) {
        ResponseMessage responseMessage = new ResponseMessage();
        try{
            responseMessage.setSuccess(true);
            List<ClassDto> result = classService.searchClass(classFormSearch, pageIndex, pageSize);
            responseMessage.setData(result);
        } catch (Exception e) {
            log.error(e);
            responseMessage.setSuccess(false);
            return ResponseUtils.buildResponseMessage(false, responseMessage);
        }
        return ResponseUtils.buildResponseMessage(true, responseMessage);
    }
}
