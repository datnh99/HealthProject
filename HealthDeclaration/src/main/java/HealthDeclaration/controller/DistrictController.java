package HealthDeclaration.controller;


import HealthDeclaration.common.response.utils.ResponseUtils;
import HealthDeclaration.form.DistrictAddForm;
import HealthDeclaration.modal.request.UserChangePassForm;
import HealthDeclaration.service.IDistrictService;
import HealthDeclaration.service.IUserService;
import HealthDeclaration.vo.ResponseMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/district")
@Log4j2
public class DistrictController {

    @Autowired
    private IDistrictService districtService;

    @PostMapping("/add-new-district")
    public ResponseEntity addListDistrict(@RequestBody List<DistrictAddForm> formList) {
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            districtService.addListDistrict(formList);
            responseMessage.setSuccess(true);
            responseMessage.setData(true);
        } catch (Exception e) {
            responseMessage.setSuccess(false);
            return  ResponseUtils.buildResponseMessage(false, responseMessage);
        }
        return  ResponseUtils.buildResponseMessage(true, responseMessage);
    }
}
