package HealthDeclaration.service;

import HealthDeclaration.modal.entity.Role;

public interface IRoleService {

    Role getByCode(String code);
}
