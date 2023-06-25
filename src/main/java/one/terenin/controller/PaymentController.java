package one.terenin.controller;

import lombok.RequiredArgsConstructor;
import one.terenin.api.PaymentApi;
import one.terenin.dto.request.CreditCardRequest;
import one.terenin.dto.response.CreditCardResponse;
import one.terenin.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PaymentController implements PaymentApi {

    private final PaymentService service;

    @Override
    public ResponseEntity<CreditCardResponse> registerCard(CreditCardRequest request) {
        return ResponseEntity.ok(service.registerCreditCard(request));
    }

    @Override
    public ResponseEntity<CreditCardResponse> getCardByCardId(UUID cardId) {
        return ResponseEntity.ok(service.getCardByCardId(cardId));
    }

    @Override
    public ResponseEntity<CreditCardResponse> getCardByOwnerId(UUID ownerId) {
        return ResponseEntity.ok(service.getCardByOwnerId(ownerId));
    }

    @Override
    public ResponseEntity<String> payForSubscription() {
        return ResponseEntity.ok(service.payForSubscription());
    }
}
