package com.shyam.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shyam.dto.request.MedicineDTO;
import com.shyam.entities.MedicineEntity;
import com.shyam.repositories.MedicineRepository;
import com.shyam.repositories.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicineService {
    
    private final MedicineRepository medicineRepository;
    private final CloudinaryService cloudinaryService;
    private final OrderRepository orderRepository;
    private final ModelMapper mapper;

    public Optional<MedicineEntity> getById(int id) {
        return medicineRepository.findById(id);
    }

    public List<MedicineEntity> getAll() {
        return medicineRepository.findAll();
    }

    @SuppressWarnings("rawtypes")
    public MedicineEntity save(MedicineDTO dto) {
        MedicineEntity check = medicineRepository.findByName(dto.getName());
        if (check != null) {
            return null;
        }
        
        Map res = cloudinaryService.uploadToCloud(dto.getFile(), "medicines");
        MedicineEntity medicine = mapper.map(dto, MedicineEntity.class);
        medicine.setImageUrl((String) (res.get("secure_url")));
        medicine.setPublicId((String) (res.get("public_id")));
        medicine.setId(0);

        return medicineRepository.save(medicine);
    }

    @Transactional
    public void delete(MedicineEntity medicineEntity) {
        cloudinaryService.deleteFromCloud(medicineEntity.getPublicId());
        orderRepository.deleteByMedicineId(medicineEntity.getId());
        medicineRepository.delete(medicineEntity);
    }

    public MedicineEntity update(int medicineId, MedicineDTO dto) {
        MedicineEntity medicine = mapper.map(dto, MedicineEntity.class);
        medicine.setId(medicineId);
        return medicineRepository.save(medicine);
    }
}
