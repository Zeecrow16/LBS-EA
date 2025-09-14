package com.example.enterpriselbs.application.controllers.admin;

import com.example.enterpriselbs.infrastructure.entities.Staff;
import com.example.enterpriselbs.infrastructure.repositories.StaffRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final StaffRepository staffRepo;

    public AdminController(StaffRepository staffRepo) {
        this.staffRepo = staffRepo;
    }

    @GetMapping("/staff")
    public List<Staff> getAllStaff() {
        return (List<Staff>) staffRepo.findAll();
    }

    @PostMapping("/staff")
    public Staff addStaff(@RequestBody Staff staff) {
        return staffRepo.save(staff);
    }

    @PatchMapping("/staff/{id}/update-department")
    public Staff updateDepartment(@PathVariable String id, @RequestParam String departmentId) {
        Staff staff = staffRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Staff not found"));
        staff.setDepartmentId(departmentId);
        return staffRepo.save(staff);
    }
}