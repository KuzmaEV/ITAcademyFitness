package by.mk_jd2_92_22.foodCounter.services;

import by.mk_jd2_92_22.foodCounter.model.JournalFoodList;
import by.mk_jd2_92_22.foodCounter.services.exception.NotFoundException;
import by.mk_jd2_92_22.foodCounter.repositories.IJournalFoodDao;
import by.mk_jd2_92_22.foodCounter.model.Recipe;
import by.mk_jd2_92_22.foodCounter.model.JournalFood;
import by.mk_jd2_92_22.foodCounter.model.Product;
import by.mk_jd2_92_22.foodCounter.security.customDatail.UserHolder;
import by.mk_jd2_92_22.foodCounter.services.api.IProfileService;
import by.mk_jd2_92_22.foodCounter.services.api.IRecipeService;
import by.mk_jd2_92_22.foodCounter.services.api.IJournalFoodService;
import by.mk_jd2_92_22.foodCounter.services.api.IProductService;
import by.mk_jd2_92_22.foodCounter.services.dto.JournalFoodDTO;
import by.mk_jd2_92_22.foodCounter.services.dto.PageDTO;
import by.mk_jd2_92_22.foodCounter.services.dto.Type;
import by.mk_jd2_92_22.foodCounter.services.mappers.MapperPageDTO;
import by.mk_jd2_92_22.foodCounter.services.util.AuditProvider;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class JournalFoodService implements IJournalFoodService {

    private final IJournalFoodDao dao;
    private final IProductService productService;
    private final IRecipeService dishService;
    private final MapperPageDTO<JournalFood> mapperPageDTO;
    private final IProfileService profileService;
    private final UserHolder holder;
    private final AuditProvider creatingAudit;

    public JournalFoodService(IJournalFoodDao dao, IProductService productService,
                              IRecipeService dishService, MapperPageDTO<JournalFood> mapperPageDTO,
                              IProfileService profileService, UserHolder holder, AuditProvider creatingAudit) {
        this.dao = dao;
        this.productService = productService;
        this.dishService = dishService;
        this.mapperPageDTO = mapperPageDTO;
        this.profileService = profileService;
        this.holder = holder;
        this.creatingAudit = creatingAudit;
    }

    @Override @Transactional
    public JournalFood create(JournalFoodDTO item, UUID profile, HttpHeaders token) {

        final UUID uuid = UUID.randomUUID();
        final LocalDateTime time = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        final String user = this.holder.getUser().getUsername();

        Product product = null;
        Recipe dish = null;

        this.profileService.get(profile);

        if (item.getProduct() != null) {
            product = this.productService.get(item.getProduct().getUuid());
        } else if (item.getRecipe() != null) {
            dish = this.dishService.get(item.getRecipe().getUuid());
        } else {throw new IllegalStateException("Запрос некорректен. Сервер не может обработать запрос");}

        JournalFood journalFood = new JournalFood(uuid,
                time,
                time,
                item.getDtSupply(),
                product,
                dish,
                item.getWeight(),
                profile);

        final JournalFood save = this.dao.save(journalFood);

        this.creatingAudit.create(UUID.fromString(user), "new JournalFood entry created", Type.JOURNAL_FOOD, token);
        return save;
    }

    @Override
    public JournalFood get(UUID uuid, UUID profile) {

        //проверят- есть ли такой профиль и соответствует ли он пользователю
        this.profileService.get(profile);

        return this.dao.findById(uuid).orElseThrow(() ->
                new NotFoundException("Не удалось найти запись дневника питания "));


    }


    @Override
    public PageDTO<JournalFood> get(int page, int size, UUID profile) {

        this.profileService.get(profile);

        Pageable pageable = PageRequest.of(page, size, Sort.by("dtSupply").descending());

        return mapperPageDTO.mapper(dao.findAllByProfile(pageable, profile));
    }

    @Override
    public JournalFoodList get(LocalDateTime from, LocalDateTime to, UUID profileId) {
        if (from.isAfter(to)){
            throw new IllegalStateException("некорректные данные при указании интервала даты");
        }

        final List<JournalFood> journalFoods = this.dao.findAllByProfileAndDtSupplyBetween(profileId, from, to);
        return new JournalFoodList(journalFoods);
    }

    @Override @Transactional
    public JournalFood update(UUID uuid, LocalDateTime dtUpdate, JournalFoodDTO item, UUID profile, HttpHeaders token) {

        this.profileService.get(profile);
        final String user = this.holder.getUser().getUsername();

        JournalFood diary = dao.findById(uuid).orElseThrow(()->
                new NotFoundException("Не удалось найти запись дневника питания "));

        if (diary.getDtUpdate().isEqual(dtUpdate)){

            Product product = null;
            Recipe dish = null;

            if (item.getProduct() != null) {
                product = this.productService.get(item.getProduct().getUuid());
            }
            if (item.getRecipe() != null) {
                dish = this.dishService.get(item.getRecipe().getUuid());
            }

            diary.setDtUpdate(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
            diary.setProduct(product);
            diary.setDish(dish);
            diary.setWeight(item.getWeight());

        }else {
            throw new IllegalArgumentException("Не удалось обнавить, было кем-то изменино раньше." +
                    " Попробуйте еще раз!");
        }

        final JournalFood save = this.dao.save(diary);
        this.creatingAudit.create(UUID.fromString(user), "JournalFood entry update", Type.JOURNAL_FOOD, token);

        return save;
    }

    @Override @Transactional
    public void delete(UUID uuid, LocalDateTime dtUpdate, UUID profile, HttpHeaders token) {

        this.profileService.get(profile);
        final String user = this.holder.getUser().getUsername();

        JournalFood diary = dao.findById(uuid).orElseThrow(()->
                new NotFoundException("Не удалось найти запись дневника питания "));

        if (diary.getDtUpdate().isEqual(dtUpdate)){
            dao.delete(diary);
        }else {
            throw new IllegalArgumentException("Не удалось обнавить, было кемнто изменино раньше." +
                    " Попробуйте еще раз!");
        }
        this.creatingAudit.create(UUID.fromString(user), "JournalFood entry update", Type.JOURNAL_FOOD, token);

    }
}
