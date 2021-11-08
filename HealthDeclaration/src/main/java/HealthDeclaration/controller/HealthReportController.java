package HealthDeclaration.controller;

import HealthDeclaration.common.response.utils.ResponseUtils;
import HealthDeclaration.form.HealthAddForm;
import HealthDeclaration.service.HealthReportService;
import HealthDeclaration.vo.ResponseMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/health-report")
@Log4j2
public class HealthReportController {
    @Autowired
    private HealthReportService healthReportService;
//
//    @GetMapping(value = "/search-health-form-report")
//    public ResponseEntity searchHealthFormReport(@RequestParam Long userId
//            , @RequestParam("pageIndex") int pageIndex
//            , @RequestParam("pageSize") int pageSize) {
//        ResponseMessage responseMessage = new ResponseMessage();
//        try{
//            responseMessage.setSuccess(true);
//            List<HealthFormDto> result = service.searchHealthFormReport(userId, pageIndex, pageSize);
//            Long total = service.countSearchHealthFormReport(userId);
//            Map<String, Object> results = new HashMap<>();
//            results.put("items", result);
//            results.put("total", total);
//            responseMessage.setData(results);
//        } catch (Exception e) {
//            log.error(e);
//            responseMessage.setSuccess(false);
//            return ResponseUtils.buildResponseMessage(false, responseMessage);
//        }
//        return ResponseUtils.buildResponseMessage(true, responseMessage);
//    }

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody HealthAddForm form) {
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            responseMessage.setSuccess(true);
            responseMessage.setData(healthReportService.add(form));
        } catch (Exception e) {
            log.error(e);
            responseMessage.setSuccess(false);
            return  ResponseUtils.buildResponseMessage(false, responseMessage);
        }
        return  ResponseUtils.buildResponseMessage(true, responseMessage);
    }

//    @GetMapping(value = "/getById")
//    public ResponseEntity getOne(@RequestParam Long id) {
//        ResponseMessage responseMessage = new ResponseMessage();
//        try{
//            responseMessage.setSuccess(true);
//            responseMessage.setData(service.getById(id));
//        } catch (Exception e) {
//            log.error(e);
//            responseMessage.setSuccess(false);
//            return ResponseUtils.buildResponseMessage(false, responseMessage);
//        }
//        return ResponseUtils.buildResponseMessage(true, responseMessage);
//    }
}
