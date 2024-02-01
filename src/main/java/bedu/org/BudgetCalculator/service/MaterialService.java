package bedu.org.BudgetCalculator.service;

import bedu.org.BudgetCalculator.dto.material.CreateMaterialDTO;
import bedu.org.BudgetCalculator.dto.material.MaterialDTO;
import bedu.org.BudgetCalculator.dto.material.UpdateMaterialDTO;
import bedu.org.BudgetCalculator.exception.material.MaterialNotFoundException;
import bedu.org.BudgetCalculator.mapper.MaterialMapper;
import bedu.org.BudgetCalculator.model.Material;
import bedu.org.BudgetCalculator.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private MaterialMapper materialMapper;

    public List<MaterialDTO> findAll() {
        List<Material> data = materialRepository.findAll();
        return data.stream().map(materialMapper::toDTO).toList();
    }

    public MaterialDTO findById(Long id) throws MaterialNotFoundException {
        Optional<Material> result = materialRepository.findById(id);

        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new MaterialNotFoundException(id));

        return materialMapper.toDTO(result.get());
    }

    public MaterialDTO save(CreateMaterialDTO data) {
        Material entity = materialRepository.save(materialMapper.toModel(data));
        return materialMapper.toDTO(entity);
    }

    public MaterialDTO update(Long id, UpdateMaterialDTO data) throws MaterialNotFoundException {
        Material materialExists = materialRepository.findById(id)
                .orElseThrow(() -> new MaterialNotFoundException(id));

        materialExists.setName(data.getName());
        materialExists.setQuantity(data.getQuantity());
        materialExists.setPrice(data.getPrice());

        Material materialUpdated = materialRepository.save(materialExists);

        return materialMapper.toDTO(materialUpdated);
    }

    public void deleteById(Long id) throws MaterialNotFoundException {
        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new MaterialNotFoundException(id));

        materialRepository.deleteById(id);
    }
}
