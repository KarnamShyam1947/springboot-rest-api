package com.shyam.services;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.shyam.dto.request.MedicineDTO;
import com.shyam.entities.MedicineEntity;
import com.shyam.repositories.MedicineRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicineService {
    
    private final MedicineRepository medicineRepository;
    private final ModelMapper mapper;

    public Optional<MedicineEntity> getById(int id) {
        return medicineRepository.findById(id);
    }

    public List<MedicineEntity> getAll() {
        return medicineRepository.findAll();
    }

    public MedicineEntity save(MedicineDTO dto) {
        MedicineEntity check = medicineRepository.findByName(dto.getName());
        if (check != null) {
            return null;
        }
        
        MedicineEntity medicine = mapper.map(dto, MedicineEntity.class);
        medicine.setId(0);
        return medicineRepository.save(medicine);
    }

    public void delete(MedicineEntity medicineEntity) {
        medicineRepository.delete(medicineEntity);
    }

    public MedicineEntity update(int medicineId, MedicineDTO dto) {
        MedicineEntity medicine = mapper.map(dto, MedicineEntity.class);
        medicine.setId(medicineId);
        return medicineRepository.save(medicine);
    }
}
