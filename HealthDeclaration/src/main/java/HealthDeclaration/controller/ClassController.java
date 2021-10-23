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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/class")
@Log4j2
@CrossOrigin(origins = "http://localhost:8181")
public class ClassController {
    @Autowired
    IClassService classService;

    @GetMapping("/getAll")
    public List<ClassDto> getClasses() {
        return classService.getClasses();
    }

    @RequestMapping(value = "/search-class", method = RequestMethod.POST)
    public ResponseEntity searchClasses(@RequestBody ClassFormSearch classFormSearch
            , @RequestParam("pageIndex") int pageIndex
            , @RequestParam("pageSize") int pageSize) {
        ResponseMessage responseMessage = new ResponseMessage();
        try{
            responseMessage.setSuccess(true);
            List<ClassDto> result = classService.searchClass(classFormSearch, pageIndex, pageSize);
            Long total = classService.countSearchClass(classFormSearch);
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
}
