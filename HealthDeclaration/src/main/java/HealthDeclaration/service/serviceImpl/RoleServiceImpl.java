package HealthDeclaration.service.serviceImpl;

import HealthDeclaration.modal.entity.Role;
import HealthDeclaration.repository.IRoleRepository;
import HealthDeclaration.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public Role getByCode(String code) {
        return roleRepository.findByCode(code);
    }
}
