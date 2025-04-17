package com.nuka.nuka_pos.api.user;

import com.nuka.nuka_pos.api.user.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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

    public void createUser(User user) {
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
                user.getRole().getId(),
                user.getBranch().getId()
        );
    }
}
