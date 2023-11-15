package com.example.demo.repo;

import com.example.demo.model.Role;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface RoleRepository extends JpaRepository<Role, Long> {

    List<Role> findAllById(Long id);
}
