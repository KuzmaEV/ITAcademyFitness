package by.mk_jd2_92_22.foodCounter.services;

import by.mk_jd2_92_22.foodCounter.repositories.IIngredientDao;
import by.mk_jd2_92_22.foodCounter.model.Ingredient;
import by.mk_jd2_92_22.foodCounter.services.api.IIngredientService;
import by.mk_jd2_92_22.foodCounter.services.api.IProductService;
import by.mk_jd2_92_22.foodCounter.services.dto.IngredientDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class IngredientService implements IIngredientService {

    private final IIngredientDao dao;
    private final IProductService productService;

    public IngredientService(IIngredientDao dao, ProductService productService) {
        this.dao = dao;
        this.productService = productService;
    }

    @Override @Transactional
    public List<Ingredient> create(List<IngredientDTO> items) {

        Iterable<Ingredient> iterable = items.stream().map(i ->
                        new Ingredient(UUID.randomUUID(), this.productService.get(i.getProduct().getUuid()),
                                i.getWeight()))
                .collect(Collectors.toList());


        return this.dao.saveAll(iterable);
    }

    @Override @Transactional
    public void delete(List<UUID> items){
        try {
            dao.deleteAllById(items);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
