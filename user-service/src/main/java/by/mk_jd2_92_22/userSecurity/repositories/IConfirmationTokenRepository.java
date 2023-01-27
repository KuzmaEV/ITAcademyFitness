package by.mk_jd2_92_22.userSecurity.repositories;

import by.mk_jd2_92_22.userSecurity.model.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IConfirmationTokenRepository extends JpaRepository<ConfirmationToken, UUID> {


    Optional<ConfirmationToken> findByUser(UUID uuid);



}
