package HealthDeclaration.controller;

import HealthDeclaration.common.response.utils.ResponseUtils;
import HealthDeclaration.modal.request.ClassAddForm;
import HealthDeclaration.modal.request.HealthFormReportAdd;
import HealthDeclaration.service.IHealthFormReportService;
import HealthDeclaration.vo.ResponseMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/health-form-report")
@Log4j2
@CrossOrigin(origins = "http://localhost:8181")
public class HealthFormReportController {
    @Autowired
    IHealthFormReportService service;

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody HealthFormReportAdd formReportAdd) {
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            responseMessage.setSuccess(true);
            responseMessage.setData(service.add(formReportAdd));
        } catch (Exception e) {
            log.error(e);
            responseMessage.setSuccess(false);
            return  ResponseUtils.buildResponseMessage(false, responseMessage);
        }
        return  ResponseUtils.buildResponseMessage(true, responseMessage);
    }
}
