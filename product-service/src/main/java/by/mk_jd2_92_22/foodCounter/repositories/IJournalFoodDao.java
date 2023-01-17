package by.mk_jd2_92_22.foodCounter.repositories;

import by.mk_jd2_92_22.foodCounter.model.JournalFood;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IJournalFoodDao extends JpaRepository<JournalFood, UUID> {

    Page<JournalFood> findAllByProfile(Pageable pageable, UUID profile);
}
