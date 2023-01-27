package by.mk_jd2_92_22.foodCounter.services;

import by.mk_jd2_92_22.foodCounter.services.exception.NotFoundException;
import by.mk_jd2_92_22.foodCounter.repositories.IRecipeDao;
import by.mk_jd2_92_22.foodCounter.model.Recipe;
import by.mk_jd2_92_22.foodCounter.model.Ingredient;
import by.mk_jd2_92_22.foodCounter.security.customDatail.UserHolder;
import by.mk_jd2_92_22.foodCounter.services.api.IRecipeService;
import by.mk_jd2_92_22.foodCounter.services.api.IIngredientService;
import by.mk_jd2_92_22.foodCounter.services.dto.PageDTO;
import by.mk_jd2_92_22.foodCounter.services.dto.RecipeDTO;
import by.mk_jd2_92_22.foodCounter.services.dto.Type;
import by.mk_jd2_92_22.foodCounter.services.mappers.MapperPageDTO;
import by.mk_jd2_92_22.foodCounter.services.util.AuditProvider;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class RecipeService implements IRecipeService {

    private final IRecipeDao dao;
    private final IIngredientService ingredientService;
    private final MapperPageDTO<Recipe> mapperPageDTO;
    private final AuditProvider creatingAudit;
    private final UserHolder holder;

    public RecipeService(IRecipeDao dao, IIngredientService ingredientService,
                         MapperPageDTO<Recipe> mapperPageDTO, AuditProvider creatingAudit,
                         UserHolder holder) {
        this.dao = dao;
        this.ingredientService = ingredientService;
        this.mapperPageDTO = mapperPageDTO;
        this.creatingAudit = creatingAudit;
        this.holder = holder;
    }

    @Override
    @Transactional
    public Recipe create(RecipeDTO item, HttpHeaders token) {

        final String userId = this.holder.getUser().getUsername();
        final String text = "new Recipe created";

        final UUID uuidDish = UUID.randomUUID();
        final LocalDateTime time = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        final List<Ingredient> ingredients = ingredientService.create(item.getComposition());

        final Recipe dish = new Recipe(uuidDish,
                time,
                time,
                item.getTitle(),
                ingredients,
                UUID.fromString(userId));

        final Recipe recipe = this.dao.save(dish);

        this.creatingAudit.create(UUID.fromString(userId), text, Type.RECIPE, token);

        return recipe;
    }

    @Override
    public Recipe get(UUID uuid) {

        return dao.findById(uuid).orElseThrow(()->
                new NotFoundException("Не удалось найти блюдо "));
    }

    @Override
    public PageDTO<Recipe> get(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return mapperPageDTO.mapper(dao.findAll(pageable));
    }

    @Override
    public PageDTO<Recipe> getMy(int page, int size) {
        final UUID user = UUID.fromString(this.holder.getUser().getUsername());

        Pageable pageable = PageRequest.of(page, size);

        return mapperPageDTO.mapper(dao.findAllByUserId(pageable, user));
    }

    @Override
    @Transactional
    public Recipe update(UUID uuid, LocalDateTime dtUpdate, RecipeDTO item, HttpHeaders token) {

        final String userId = this.holder.getUser().getUsername();
        final String text = "Recipe updated";

        Recipe dish = this.dao.findById(uuid).orElseThrow(()->
                new NotFoundException("Не удалось найти блюдо "));

        if (dish.getDtUpdate().isEqual(dtUpdate)){

            List<UUID> deleteIngredients = dish.getIngredients()
                    .stream().map(Ingredient::getUuid).collect(Collectors.toList());

            this.ingredientService.delete(deleteIngredients);

            List<Ingredient> ingredients = this.ingredientService.create(item.getComposition());

            dish.setDtUpdate(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
            dish.setTitle(item.getTitle());
            dish.setIngredients(ingredients);
        }else {
            throw new IllegalArgumentException("Не удалось обнавить, было кем-то изменино раньше." +
                    " Попробуйте еще раз!");
        }

        final Recipe recipe = this.dao.save(dish);

        this.creatingAudit.create(UUID.fromString(userId), text, Type.RECIPE, token);

        return recipe;
    }

    @Override
    @Transactional
    public void delete(UUID uuid, LocalDateTime dtUpdate, HttpHeaders token) {

        final String userId = this.holder.getUser().getUsername();
        final String text = "new Recipe created";

        Recipe dish = dao.findById(uuid).orElseThrow(()->
                new NotFoundException("Не удалось найти блюдо "));
        if (dish.getDtUpdate().isEqual(dtUpdate)){
            dao.delete(dish);
        }else {
            throw new IllegalArgumentException("Не удалось обнавить, было кемнто изменино раньше." +
                    " Попробуйте еще раз!");
        }

        this.creatingAudit.create(UUID.fromString(userId), text, Type.PRODUCT, token);
    }
}
