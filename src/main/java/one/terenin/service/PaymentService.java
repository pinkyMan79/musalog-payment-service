package one.terenin.service;

import one.terenin.dto.request.CreditCardRequest;
import one.terenin.dto.response.CreditCardResponse;

import java.util.UUID;

public interface PaymentService {

    CreditCardResponse registerCreditCard(CreditCardRequest request);
    CreditCardResponse getCardByCardId(UUID creditCardId);
    CreditCardResponse getCardByOwnerId(UUID ownerId);
    boolean payForSubscription();

}
