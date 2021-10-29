package HealthDeclaration.service;

import HealthDeclaration.modal.dto.ProvinceDto;
import HealthDeclaration.modal.request.ProvinceAddRequest;

import java.util.List;

public interface IProvinceService {
    List<ProvinceDto> getAll();

    void add(List<ProvinceAddRequest> list);
}
