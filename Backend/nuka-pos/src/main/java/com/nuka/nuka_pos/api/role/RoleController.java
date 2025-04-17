package com.nuka.nuka_pos.api.role;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * RoleController is responsible for handling HTTP requests related to roles.
 * It provides endpoints for managing roles in the application, including
 * creating, updating, retrieving, and deleting roles.
 */
@RestController
@RequestMapping("/api/secure/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * Fetches all roles in the system.
     *
     * @return a list of roles
     */
    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    /**
     * Fetches a role by its ID.
     *
     * @param id the ID of the role
     * @return the role details
     */
    @GetMapping("/{id}")
    public ResponseEntity<RoleRequest> getRoleById(@PathVariable Long id) {
        Role role = roleService.getRoleById(id);
        RoleRequest roleRequest = new RoleRequest();
        roleRequest.setId(id);
        roleRequest.setName(role.getName());
        roleRequest.setDescription(role.getDescription());
        return new ResponseEntity<>(roleRequest, HttpStatus.OK);
    }

    /**
     * Creates a new role.
     *
     * @param roleRequest the role data to create
     * @return the created role
     */
    @PostMapping
    public ResponseEntity<Void> createRole(@RequestBody RoleRequest roleRequest) {
        Role role = new Role();
        role.setName(roleRequest.getName());
        role.setDescription(roleRequest.getDescription());
        roleService.createRole(role);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Updates an existing role.
     *
     * @param id          the ID of the role to update
     * @param roleRequest the updated role data
     * @return the updated role
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRole(@PathVariable Long id,
                                                  @RequestBody RoleRequest roleRequest) {
        Role role = roleService.getRoleById(id);
        if (roleRequest.getName() == null || roleRequest.getName().isEmpty()) {
            role.setName(roleRequest.getName());
        }
        if (roleRequest.getDescription() == null || roleRequest.getDescription().isEmpty()) {
            role.setDescription(roleRequest.getDescription());
        }
        roleService.updateRole(id, role);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Deletes a role by its ID.
     *
     * @param id the ID of the role to delete
     * @return status response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
