package com.nuka.nuka_pos.api.branch;

import com.nuka.nuka_pos.api.branch.exceptions.BranchNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BranchService {

    private final BranchRepository branchRepository;

    public BranchService(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    public List<BranchResponse> getAllBranches() {
        return branchRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public BranchResponse getBranchById(Long id) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new BranchNotFoundException("Branch not found with id: " + id));
        return mapToResponse(branch);
    }

    public void createBranch(Branch branch) {
        branchRepository.save(branch);
    }

    public void updateBranch(Long id, Branch updatedData) {
        Branch existing = branchRepository.findById(id)
                .orElseThrow(() -> new BranchNotFoundException("Branch not found with id: " + id));

        if (updatedData.getName() != null) existing.setName(updatedData.getName());
        if (updatedData.getLocation() != null) existing.setLocation(updatedData.getLocation());
        if (updatedData.getPhone() != null) existing.setPhone(updatedData.getPhone());
        if (updatedData.getEmail() != null) existing.setEmail(updatedData.getEmail());
        if (updatedData.getIsHeadOffice() != null) existing.setIsHeadOffice(updatedData.getIsHeadOffice());
        if (updatedData.getOrganization() != null) existing.setOrganization(updatedData.getOrganization());
        if (updatedData.getCurrency() != null) existing.setCurrency(updatedData.getCurrency());

        branchRepository.save(existing);
    }

    public void deleteBranch(Long id) {
        if (!branchRepository.existsById(id)) {
            throw new BranchNotFoundException("Branch not found with id: " + id);
        }
        branchRepository.deleteById(id);
    }

    public List<BranchResponse> getBranchesByOrganizationId(Long organizationId) {
        return branchRepository.findByOrganizationId(organizationId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private BranchResponse mapToResponse(Branch branch) {
        return new BranchResponse(
                branch.getId(),
                branch.getName(),
                branch.getLocation(),
                branch.getPhone(),
                branch.getEmail(),
                branch.getIsHeadOffice(),
                branch.getOrganization().getId(),
                branch.getCurrency().getId()
        );
    }
}
