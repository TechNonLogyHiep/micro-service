package vti.microservice.department.service;

import org.springframework.stereotype.Service;
import vti.microservice.department.dto.DepartmentDTO;

import java.util.List;

@Service
public interface DepartmentService {
    List<DepartmentDTO> getAllDepartments();
}
