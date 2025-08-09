package vti.microservice.department.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vti.microservice.department.entity.DepartmentEntity;

public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Integer> {
    // JpaRepository provides basic CRUD operations, no additional methods needed for now
}
