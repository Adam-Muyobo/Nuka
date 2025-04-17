package com.nuka.nuka_pos.api.branch;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/branches")
public class BranchController {

    private final BranchService branchService;

    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @GetMapping
    public ResponseEntity<List<BranchResponse>> getAllBranches() {
        return ResponseEntity.ok(branchService.getAllBranches());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchResponse> getBranchById(@PathVariable Long id) {
        return ResponseEntity.ok(branchService.getBranchById(id));
    }

    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<List<BranchResponse>> getBranchesByOrganizationId(@PathVariable Long organizationId) {
        return ResponseEntity.ok(branchService.getBranchesByOrganizationId(organizationId));
    }

    @PostMapping
    public ResponseEntity<Void> createBranch(@RequestBody Branch branch) {
        branchService.createBranch(branch);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBranch(@PathVariable Long id, @RequestBody Branch updatedData) {
        branchService.updateBranch(id, updatedData);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBranch(@PathVariable Long id) {
        branchService.deleteBranch(id);
        return ResponseEntity.ok().build();
    }
}
