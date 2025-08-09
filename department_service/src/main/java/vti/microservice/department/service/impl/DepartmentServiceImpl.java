package vti.microservice.department.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vti.microservice.department.dto.DepartmentDTO;
import vti.microservice.department.entity.DepartmentEntity;
import vti.microservice.department.repository.DepartmentRepository;
import vti.microservice.department.service.DepartmentService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Override
    public List<DepartmentDTO> getAllDepartments() {
        List<DepartmentEntity> departmentEntities = departmentRepository.findAll();
        return departmentEntities.stream()
                .map(departmentEntity -> DepartmentDTO.builder()
                        .name(departmentEntity.getName())
                        .type(departmentEntity.getType().toString())
                        .createdDate(departmentEntity.getCreatedAt())
                        .build())
                .toList();
    }
}
