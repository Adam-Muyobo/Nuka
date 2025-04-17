package com.nuka.nuka_pos.api.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user) {
        userService.createUser(user);
        return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User user) {
        userService.updateUser(id, user);
        return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/organization/{organizationId}")
    public List<UserResponse> getUsersByOrganization(@PathVariable Long organizationId) {
        return userService.getUsersByOrganization(organizationId);
    }

    @GetMapping("/branch/{branchId}")
    public List<UserResponse> getUsersByBranch(@PathVariable Long branchId) {
        return userService.getUsersByBranch(branchId);
    }
}
