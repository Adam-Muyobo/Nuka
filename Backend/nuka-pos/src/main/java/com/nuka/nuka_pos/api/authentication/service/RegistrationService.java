package com.nuka.nuka_pos.api.authentication.service;

import com.nuka.nuka_pos.api.authentication.dto.RegistrationRequest;
import com.nuka.nuka_pos.api.authentication.dto.RegistrationResponse;
import com.nuka.nuka_pos.api.organization.Organization;
import com.nuka.nuka_pos.api.organization.OrganizationRepository;
import com.nuka.nuka_pos.api.role.Role;
import com.nuka.nuka_pos.api.role.RoleRepository;
import com.nuka.nuka_pos.api.user.User;
import com.nuka.nuka_pos.api.user.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final OrganizationRepository organizationRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(UserRepository userRepository, RoleRepository roleRepository,
                               OrganizationRepository organizationRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.organizationRepository = organizationRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public RegistrationResponse register(RegistrationRequest registrationRequest) {
        // Check if the organization code, username, or email already exists
        if (organizationRepository.existsByEmail(registrationRequest.getOrganizationEmail())) {
            throw new IllegalArgumentException("Organization email already exists");
        }
        if (userRepository.existsByUsername(registrationRequest.getAdminUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.existsByEmail(registrationRequest.getAdminEmail())) {
            throw new IllegalArgumentException("Admin email already exists");
        }

        // Create new Organization
        Organization organization = new Organization();
        organization.setName(registrationRequest.getOrganizationName());
        organization.setCode(registrationRequest.getOrganizationCode());
        organization.setEmail(registrationRequest.getOrganizationEmail());
        organization.setPhone(registrationRequest.getOrganizationPhone());
        organization.setCountry(registrationRequest.getCountry());
        organization.setBusinessType(registrationRequest.getBusinessType());
        organization.setSmartInvoicingEnabled(false); // Assuming false initially, can be set based on the request
        organization.setBarcodeEnabled(false); // Similarly, based on the request
        organization.setReceiptMode(Organization.ReceiptMode.BOTH); // Default, can be updated
        organization.setCreatedAt(java.time.LocalDateTime.now());

        organizationRepository.save(organization);

        // Create the root admin User
        Role role = roleRepository.findByName("ROOT_ADMIN").orElseThrow(() -> new IllegalArgumentException("Role not found"));
        String encodedPassword = passwordEncoder.encode(registrationRequest.getAdminPassword());

        User user = new User();
        user.setUsername(registrationRequest.getAdminUsername());
        user.setPassword(encodedPassword);
        user.setForenames(registrationRequest.getAdminForenames());
        user.setSurname(registrationRequest.getAdminSurname());
        user.setEmail(registrationRequest.getAdminEmail());
        user.setPhone(registrationRequest.getAdminPhone());
        user.setRole(role);
        user.setOrganization(organization);
        user.setIsActive(true);
        user.setCreatedAt(java.time.LocalDateTime.now());

        userRepository.save(user);

        // Return response with the organization and admin details
        return new RegistrationResponse(
                organization.getId(),
                organization.getName(),
                user.getId(),
                user.getUsername(),
                "Registration successful"
        );
    }
}
