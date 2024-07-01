package com.shyam.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shyam.dto.request.DepartmentRequest;
import com.shyam.dto.response.DepartmentResponse;
import com.shyam.entities.DepartmentEntity;
import com.shyam.exceptions.EntityAlreadyExistsException;
import com.shyam.repositories.DepartmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    
    private final DepartmentRepository departmentRepository;
    private final ModelMapper mapper;

    public DepartmentEntity addDepartment(DepartmentRequest request) {
        DepartmentEntity check = departmentRepository.findByName(request.getName());
        if (check != null) 
            return null;
        
        DepartmentEntity department = mapper.map(request, DepartmentEntity.class);
        department.setId(0);

        return departmentRepository.save(department);
    }

    public List<DepartmentResponse> getAllDepartments() {
        return departmentRepository.getDepartments();
    }

    @Transactional
    public void deleteDepartment(int departmentId) throws EntityAlreadyExistsException {
        DepartmentEntity department = departmentRepository
                                        .findById(departmentId)
                                        .orElseThrow(() -> new EntityAlreadyExistsException("Department Not Found with id : " + departmentId));
                            

        departmentRepository.deleteByDepartment(department.getName());

        departmentRepository.delete(department);
    }

}
