package com.nuka.nuka_pos.api.user;

import com.nuka.nuka_pos.api.user.dto.LoginRequest;
import com.nuka.nuka_pos.api.user.dto.LoginResponse;
import com.nuka.nuka_pos.api.user.enums.Role;
import com.nuka.nuka_pos.api.user.exceptions.UserNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        return mapToResponse(user);
    }

    public UserResponse getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
        return mapToResponse(user);
    }


    public void createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    public void updateUser(Long id, User updatedData) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        if (updatedData.getUsername() != null) existingUser.setUsername(updatedData.getUsername());
        if (updatedData.getForenames() != null) existingUser.setForenames(updatedData.getForenames());
        if (updatedData.getSurname() != null) existingUser.setSurname(updatedData.getSurname());
        if (updatedData.getEmail() != null) existingUser.setEmail(updatedData.getEmail());
        if (updatedData.getPhone() != null) existingUser.setPhone(updatedData.getPhone());
        if (updatedData.getIsActive() != null) existingUser.setIsActive(updatedData.getIsActive());

        //Encode password if provided
        if (updatedData.getPassword() != null && !updatedData.getPassword().isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(updatedData.getPassword()));
        }

        if (updatedData.getRole() != null) existingUser.setRole(updatedData.getRole());// Handling role update

        if (updatedData.getBranch() != null) existingUser.setBranch(updatedData.getBranch());
        userRepository.save(existingUser);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    public List<UserResponse> getUsersByOrganization(Long organizationId) {
        return userRepository.findByOrganizationId(organizationId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<UserResponse> getUsersByBranch(Long branchId) {
        return userRepository.findByBranchId(branchId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // New method to get users by role and organization
    public List<UserResponse> getUsersByRoleAndOrganization(Role role, Long organizationId) {
        return userRepository.findByRoleAndOrganizationId(role, organizationId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UserNotFoundException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UserNotFoundException("Invalid username or password");
        }

        return new LoginResponse(
                user.getId(),
                user.getUsername(),
                user.getRole().name(),
                "Login successful"
        );
    }

    private UserResponse mapToResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getForenames(),
                user.getSurname(),
                user.getEmail(),
                user.getPhone(),
                user.getIsActive(),
                user.getCreatedAt().toString(),
                user.getOrganization().getId(),
                user.getRole().name(),  // Return the role as a string
                user.getBranch() != null ? user.getBranch().getId() : null
        );
    }

    public User fetchUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
    }
}
