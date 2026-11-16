package ma.uir.awd.service.facade;
import ma.uir.awd.entity.Category;
import ma.uir.awd.entity.Equipment;
import java.util.List;

public interface CatalogService {
    List<Category> categories();
    Category saveCategory(Category category);
    List<Equipment> equipment();
    Equipment saveEquipment(Equipment item);
}
