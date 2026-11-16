package ma.uir.awd.service.impl;

import ma.uir.awd.dao.CategoryRepository;
import ma.uir.awd.dao.EquipmentRepository;
import ma.uir.awd.entity.Category;
import ma.uir.awd.entity.Equipment;
import ma.uir.awd.service.facade.CatalogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CatalogServiceImpl implements CatalogService {
    private final CategoryRepository categories;
    private final EquipmentRepository equipment;

    public CatalogServiceImpl(CategoryRepository categories, EquipmentRepository equipment) {
        this.categories = categories;
        this.equipment = equipment;
    }

    @Override public List<Category> categories() { return categories.findAll(); }

    @Override @Transactional
    public Category saveCategory(Category category) {
        if (category == null || category.getName() == null || category.getName().isBlank()) {
            throw new IllegalArgumentException("Nom de categorie obligatoire");
        }
        return categories.save(category);
    }

    @Override public List<Equipment> equipment() { return equipment.findAll(); }

    @Override @Transactional
    public Equipment saveEquipment(Equipment item) {
        if (item == null || item.getName() == null || item.getName().isBlank()
                || !Double.isFinite(item.getDailyPrice()) || item.getDailyPrice() < 0) {
            throw new IllegalArgumentException("Equipement ou tarif invalide");
        }
        return equipment.save(item);
    }
}
