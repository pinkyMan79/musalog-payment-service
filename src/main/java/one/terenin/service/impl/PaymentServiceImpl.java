package one.terenin.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import one.terenin.dto.request.CreditCardRequest;
import one.terenin.dto.response.CreditCardResponse;
import one.terenin.entity.CreditCardEntity;
import one.terenin.exception.children.CreditCardException;
import one.terenin.exception.common.ErrorCode;
import one.terenin.mapper.CreditCardMapper;
import one.terenin.repository.CreditCardRepository;
import one.terenin.service.PaymentService;
import one.terenin.yookassa.Yookassa;
import one.terenin.yookassa.YookassaInitializer;
import one.terenin.yookassa.common.Currency;
import one.terenin.yookassa.model.Amount;
import one.terenin.yookassa.model.Payment;
import one.terenin.yookassa.propertysource.YooPropertySource;
import org.apache.commons.codec.digest.DigestUtils;
import org.mapstruct.ap.internal.model.source.builtin.FinalField;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final RestTemplate restTemplate;
    private final DiscoveryClient client;
    private final CreditCardRepository repository;
    private final YookassaInitializer yookassaInitializer;
    private final CreditCardMapper mapper;
    private final YooPropertySource propertySource;


    @Override
    public CreditCardResponse registerCreditCard(CreditCardRequest request) {
        CreditCardEntity save = repository.save(mapper.map(request));
        return mapper.map(save);
    }

    @Override
    public CreditCardResponse getCardByCardId(UUID creditCardId) {
        CreditCardEntity creditCardEntity = repository.findCreditCardEntityById(creditCardId)
                .orElseThrow(() -> new CreditCardException(ErrorCode.CREDIT_CARD_NOT_FOUND));
        return mapper.map(creditCardEntity);
    }

    @Override
    public CreditCardResponse getCardByOwnerId(UUID ownerId) {
        return null;
    }

    @SneakyThrows
    @Override
    public boolean payForSubscription() {
        Yookassa yookassa = yookassaInitializer.initializeSdk();
        Payment payment = yookassa.createPayment(new Amount(BigDecimal.valueOf(propertySource.getPrice()),
                        Currency.RUB.getInfo()),
                "Pay the subscription and become a god", "http://localhost:8082/");
        String confirmationUrl = payment.getConfirmation().confirmation_url; // call the user service to receive this url
        return payment.isPaid(); // redundant
    }

    private CreditCardRequest encodeRequestByMD5(CreditCardRequest request) {
        String creditCardNumHexed = DigestUtils
                .md5Hex(request.getCardNum()).toUpperCase();
        String creditCardCvv = DigestUtils
                .md5Hex(request.getCvv()).toUpperCase();
        return request.toBuilder()
                .cardNum(creditCardNumHexed)
                .cvv(creditCardCvv)
                .build();
    }

}
