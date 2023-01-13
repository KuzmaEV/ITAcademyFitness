package by.mk_jd2_92_22.foodCounter.services;

import by.mk_jd2_92_22.foodCounter.core.builder.ProductBuilder;
import by.mk_jd2_92_22.foodCounter.core.exception.NotFoundException;
import by.mk_jd2_92_22.foodCounter.repositories.IProductDao;
import by.mk_jd2_92_22.foodCounter.model.Product;
import by.mk_jd2_92_22.foodCounter.services.api.IProductService;
import by.mk_jd2_92_22.foodCounter.services.dto.*;
import by.mk_jd2_92_22.foodCounter.services.mappers.MapperPageDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class ProductService implements IProductService {

    private final IProductDao dao;
    private final MapperPageDTO<Product> mapperPageDTO;

    public ProductService(IProductDao dao, MapperPageDTO<Product> mapperPageDTO) {
        this.dao = dao;
        this.mapperPageDTO = mapperPageDTO;
    }

    @Override
    @Transactional
    public Product create(ProductDTO item) {

        UUID uuid = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);

        final Product product = dao.save(ProductBuilder.create()
                .setUuid(uuid)
                .setDtCreate(now)
                .setDtUpdate(now)
                .setName(item.getTitle())
                .setKcal(item.getCalories())
                .setProteins(item.getProteins())
                .setFats(item.getFats())
                .setCarbohydrates(item.getCarbohydrates())
                .setWeight(item.getWeight())
                .build());

        //TODO
//                RestTemplate restTemplate = new RestTemplate();
//         restTemplate
//                .postForEntity("http://user-service:8080/api/v1/audit", new AuditDTO(product.getUuid(),
//                        "create product", Type.PRODUCT), String.class);

        return product;
    }

    @Override
    public PageDTO<Product> get(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return mapperPageDTO.mapper(dao.findAll(pageable));
    }

    @Override
    public Product get(UUID uuid) {
        return dao.findById(uuid).orElseThrow(()->
                new NotFoundException("Не удалось найти продукт "));
    }

    @Override
    @Transactional
    public Product update(UUID uuid, LocalDateTime dtUpdate, ProductDTO item) {

        Product product = dao.findById(uuid).orElseThrow(()->
                new NotFoundException("Не удалось найти продукт "));

        if (product.getDtUpdate().isEqual(dtUpdate)){
            product.setDtUpdate(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
            product.setTitle(item.getTitle());
            product.setCalories(item.getCalories());
            product.setProteins(item.getProteins());
            product.setCarbohydrates(item.getCarbohydrates());
            product.setFats(item.getFats());
            product.setWeight(item.getWeight());
        }else {
            throw new IllegalArgumentException("Не удалось обнавить, было кем-то изменино раньше." +
                    " Попробуйте еще раз!");
        }

        return dao.save(product);
    }

    @Override
    @Transactional
    public void delete(UUID uuid, LocalDateTime dtUpdate) {

        Product product = dao.findById(uuid).orElseThrow(()->
                new NotFoundException("Не удалось найти продукт "));

        if (product.getDtUpdate().isEqual(dtUpdate)){
            dao.delete(product);
        }else {
            throw new IllegalArgumentException("Не удалось обнавить, было кемнто изменино раньше." +
                    " Попробуйте еще раз!");
        }

    }
}
