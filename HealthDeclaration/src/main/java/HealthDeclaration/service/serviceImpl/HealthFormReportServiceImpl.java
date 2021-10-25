package HealthDeclaration.service.serviceImpl;

import HealthDeclaration.common.base.service.BaseService;
import HealthDeclaration.modal.entity.HealthFormReport;
import HealthDeclaration.modal.request.HealthFormReportAdd;
import HealthDeclaration.repository.IHealthFormReportRepository;
import HealthDeclaration.service.IHealthFormReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class HealthFormReportServiceImpl extends BaseService implements IHealthFormReportService {
    @Autowired
    IHealthFormReportRepository repository;

    @Override
    public HealthFormReport add(HealthFormReportAdd formReportAdd) {
        HealthFormReport report = new HealthFormReport();
        report.setCreatedBy(getLoggedInUsername());
        report.setCreatedTime(new Date());
        report.setModifiedBy(getLoggedInUsername());
        report.setModifiedTime(new Date());
        report.setDeleted(false);
        //
        report.setDiChuyen(formReportAdd.isDiChuyen());
        report.setDauHieuCovid(formReportAdd.isDauHieuCovid());
        report.setTiepXucCovider(formReportAdd.isTiepXucCovider());
        report.setTiepXucForeign(formReportAdd.isTiepXucForeign());
        report.setTiepXucNguoiCoBieuHienCovid(formReportAdd.isTiepXucNguoiCoBieuHienCovid());
        //
        report.setTenPhuongTien(formReportAdd.getTenPhuongTien());
        report.setSoHieu(formReportAdd.getSoHieu());
        report.setStartDate(formReportAdd.getStartDate());
        report.setEndProvince(formReportAdd.getStartProvince());
        report.setStartDistrict(formReportAdd.getStartDistrict());
        report.setStartWard(formReportAdd.getStartWard());
        report.setStartDetailAddress(formReportAdd.getStartDetailAddress());
        report.setEndDate(formReportAdd.getEndDate());
        report.setEndProvince(formReportAdd.getEndProvince());
        report.setEndDistrict(formReportAdd.getEndDistrict());
        report.setEndWard(formReportAdd.getEndWard());
        report.setEndDetailAddress(formReportAdd.getEndDetailAddress());
        return repository.save(report);
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
