package by.mk_jd2_92_22.foodCounter.services;

import by.mk_jd2_92_22.foodCounter.core.exception.NotFoundException;
import by.mk_jd2_92_22.foodCounter.repositories.IJournalFoodDao;
import by.mk_jd2_92_22.foodCounter.model.Recipe;
import by.mk_jd2_92_22.foodCounter.model.JournalFood;
import by.mk_jd2_92_22.foodCounter.model.Product;
import by.mk_jd2_92_22.foodCounter.services.api.IRecipeService;
import by.mk_jd2_92_22.foodCounter.services.api.IJournalFoodService;
import by.mk_jd2_92_22.foodCounter.services.api.IProductService;
import by.mk_jd2_92_22.foodCounter.services.dto.JournalFoodDTO;
import by.mk_jd2_92_22.foodCounter.services.dto.PageDTO;
import by.mk_jd2_92_22.foodCounter.services.mappers.MapperPageDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class JournalFoodService implements IJournalFoodService {

    private final IJournalFoodDao dao;
    private final IProductService productService;
    private final IRecipeService dishService;
    private final MapperPageDTO<JournalFood> mapperPageDTO;

    public JournalFoodService(IJournalFoodDao dao,
                              IProductService productService,
                              IRecipeService dishService,
                              MapperPageDTO<JournalFood> mapperPageDTO) {
        this.dao = dao;
        this.productService = productService;
        this.dishService = dishService;
        this.mapperPageDTO = mapperPageDTO;
    }

    @Override @Transactional
    public JournalFood create(JournalFoodDTO item, HttpHeaders token) {

        UUID uuid = UUID.randomUUID();
        LocalDateTime time = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);

        Product product = null;
        Recipe dish = null;

        if (item.getProduct() != null) {
            product = this.productService.get(item.getProduct().getUuid());
        } else if (item.getDish() != null) {
            dish = this.dishService.get(item.getDish().getUuid());
        } else {throw new IllegalStateException("Запрос некорректен. Сервер не может обработать запрос");}

        JournalFood diary = new JournalFood(uuid,
                time,
                time,
                item.getDtSupply(),
                product,
                dish,
                item.getWeight());

        return this.dao.save(diary);
    }

    @Override
    public JournalFood get(UUID uuid) {
        return  this.dao.findById(uuid).orElseThrow(()->
                new NotFoundException("Не удалось найти запись дневника питания "));
    }

    @Override
    public PageDTO<JournalFood> get(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return mapperPageDTO.mapper(dao.findAll(pageable));
    }

    @Override @Transactional
    public JournalFood update(UUID uuid, LocalDateTime dtUpdate, JournalFoodDTO item, HttpHeaders token) {

        JournalFood diary = dao.findById(uuid).orElseThrow(()->
                new NotFoundException("Не удалось найти запись дневника питания "));

        if (diary.getDtUpdate().isEqual(dtUpdate)){

            Product product = null;
            Recipe dish = null;

            if (item.getProduct() != null) {
                product = this.productService.get(item.getProduct().getUuid());
            }
            if (item.getDish() != null) {
                dish = this.dishService.get(item.getDish().getUuid());
            }

            diary.setDtUpdate(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
            diary.setProduct(product);
            diary.setDish(dish);
            diary.setWeight(item.getWeight());

        }else {
            throw new IllegalArgumentException("Не удалось обнавить, было кем-то изменино раньше." +
                    " Попробуйте еще раз!");
        }
        return this.dao.save(diary);
    }

    @Override @Transactional
    public void delete(UUID uuid, LocalDateTime dtUpdate, HttpHeaders token) {

        JournalFood diary = dao.findById(uuid).orElseThrow(()->
                new NotFoundException("Не удалось найти запись дневника питания "));

        if (diary.getDtUpdate().isEqual(dtUpdate)){
            dao.delete(diary);
        }else {
            throw new IllegalArgumentException("Не удалось обнавить, было кемнто изменино раньше." +
                    " Попробуйте еще раз!");
        }

    }
}
