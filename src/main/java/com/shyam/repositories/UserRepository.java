package com.shyam.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shyam.entities.UserEntity;
import com.shyam.enums.Role;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    public UserEntity findById(int id);
    public UserEntity findByEmail(String email);
    public List<UserEntity> findByRole(Role role);
    public UserEntity findByUniqueToken(String token);
    public List<UserEntity> findByDepartment(String department);
}
