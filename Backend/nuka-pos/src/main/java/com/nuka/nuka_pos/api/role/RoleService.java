package com.nuka.nuka_pos.api.role;

import com.nuka.nuka_pos.api.role.exceptions.RoleAlreadyExistsException;
import com.nuka.nuka_pos.api.role.exceptions.RoleNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void createRole(Role role) {
        Optional<Role> existingRole = roleRepository.findByName(role.getName());
        if (existingRole.isPresent()) {
            throw new RoleAlreadyExistsException("Role with name '" + role.getName() + "' already exists.");
        }
        roleRepository.save(role);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role getRoleById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException("Role with ID " + id + " not found."));
    }

    public void updateRole(Long id, Role updatedRole) {
        Role existingRole = roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException("Role with ID " + id + " not found."));

        // Optional: check if the new name already exists (and is not the same role)
        Optional<Role> roleWithSameName = roleRepository.findByName(updatedRole.getName());
        if (roleWithSameName.isPresent() && !roleWithSameName.get().getId().equals(id)) {
            throw new RoleAlreadyExistsException("Another role with the name '" + updatedRole.getName() + "' already exists.");
        }

        existingRole.setName(updatedRole.getName());
        roleRepository.save(existingRole);
    }

    public void deleteRole(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new RoleNotFoundException("Role with ID " + id + " not found.");
        }
        roleRepository.deleteById(id);
    }
}
