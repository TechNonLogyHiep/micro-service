package vti.microservice.department.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vti.microservice.department.dto.DepartmentDTO;
import vti.microservice.department.service.DepartmentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/departments")
public class DepartmentController {
    private final DepartmentService departmentService;
    @GetMapping
    public List<DepartmentDTO> getAllDepartments() {
        return departmentService.getAllDepartments();
    }
}
