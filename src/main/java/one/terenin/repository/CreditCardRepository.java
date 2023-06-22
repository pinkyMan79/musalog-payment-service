package one.terenin.repository;

import one.terenin.entity.CreditCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CreditCardRepository extends JpaRepository<CreditCardEntity, UUID> {

    Optional<CreditCardEntity> findCreditCardEntityById(UUID id);
    Optional<CreditCardEntity> findCreditCardEntityByOwnerId(UUID ownerId);
    boolean existsCreditCardEntityByCardNum(String cardNum);

}
