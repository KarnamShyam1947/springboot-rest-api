package com.shyam.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shyam.dto.response.DepartmentResponse;
import com.shyam.entities.DepartmentEntity;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Integer> {
    DepartmentEntity findByName(String name);

    @Query(value = """
        SELECT
            new com.shyam.dto.response.DepartmentResponse(d.id, d.name, d.description, count(u.id))
        FROM
            DepartmentEntity d
        LEFT JOIN
            UserEntity u
        ON
            u.department = d.name
        GROUP BY
            d.id, d.name, d.description """)
    public List<DepartmentResponse> getDepartments(); 
}
