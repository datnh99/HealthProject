package HealthDeclaration.controller;

import HealthDeclaration.common.response.utils.ResponseUtils;
import HealthDeclaration.form.HealthAddForm;
import HealthDeclaration.form.ReportManagementSearchForm;
import HealthDeclaration.modal.dto.HealthReportDTO;
import HealthDeclaration.service.HealthReportService;
import HealthDeclaration.service.ReportManagementService;
import HealthDeclaration.vo.ResponseMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/health-report")
@Log4j2
public class HealthReportController {
    @Autowired
    private HealthReportService healthReportService;

    @Autowired
    private ReportManagementService reportManagementService;


    @PostMapping("/add")
    public ResponseEntity add(@RequestBody HealthAddForm form) {
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            responseMessage.setSuccess(true);
            responseMessage.setData(healthReportService.add(form));
        } catch (Exception e) {
            log.error(e);
            responseMessage.setSuccess(false);
            return ResponseUtils.buildResponseMessage(false, responseMessage);
        }
        return ResponseUtils.buildResponseMessage(true, responseMessage);
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity<?> searchUserReport(@RequestBody ReportManagementSearchForm form, int pageIndex, int pageSize) throws Exception {
        Map<String, Object> mapResult = new HashMap<>();
        mapResult.put("items", reportManagementService.searchStudent(form, pageIndex, pageSize));
        mapResult.put("total", reportManagementService.countStudent(form));
        return ResponseEntity.ok(mapResult);
    }

    @RequestMapping(value = "/get-report-by-username", method = RequestMethod.POST)
    public ResponseEntity<?> getReportsByUsername(@RequestParam String username) throws Exception {
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            responseMessage.setSuccess(true);
            List<HealthReportDTO> result = healthReportService.getReportsByUsername(username);
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
}
