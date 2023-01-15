package by.mk_jd2_92_22.userSecurity.repositories;

import by.mk_jd2_92_22.userSecurity.model.UserFull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserFullRepository extends JpaRepository<UserFull, UUID> {
    Optional<UserFull> findByMail(String mail);

    boolean existsByEmail(String email);
}
