package one.terenin.api;

import jakarta.annotation.security.PermitAll;
import one.terenin.dto.request.CreditCardRequest;
import one.terenin.dto.response.CreditCardResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.UUID;

@RequestMapping("/api/v1/payment")
@CrossOrigin(maxAge = 3600)
public interface PaymentApi {

    // authorize by user service sending request from this, no open endpoint
    @PostMapping("/card/register")
    @PermitAll
    ResponseEntity<CreditCardResponse> registerCard(@RequestBody CreditCardRequest request);

    @GetMapping("/card/get/{cardId}")
    @PreAuthorize("hasRole('USER') or hasRole('SUBSCRIBER')")
    ResponseEntity<CreditCardResponse> getCardByCardId(@PathVariable("cardId") UUID cardId);

    @GetMapping("/card/get/{ownerId}")
    @PreAuthorize("hasRole('USER') or hasRole('SUBSCRIBER')")
    ResponseEntity<CreditCardResponse> getCardByOwnerId(@PathVariable("ownerId") UUID ownerId);

    // take info from token in security context, authorize by user service
    // create from yookassa
    @GetMapping("/pay/for/subscription")
    ResponseEntity<String> payForSubscription();

    // pay for order api // need order + servcie

}
