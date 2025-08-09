package vti.dtn.auth_service.repo;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import vti.dtn.auth_service.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByUsername(String username);
}
