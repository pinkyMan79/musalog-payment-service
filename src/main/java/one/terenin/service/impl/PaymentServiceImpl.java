package one.terenin.service.impl;

import lombok.RequiredArgsConstructor;
import one.terenin.dto.request.CreditCardRequest;
import one.terenin.dto.response.CreditCardResponse;
import one.terenin.entity.CreditCardEntity;
import one.terenin.exception.children.CreditCardException;
import one.terenin.exception.common.ErrorCode;
import one.terenin.mapper.CreditCardMapper;
import one.terenin.repository.CreditCardRepository;
import one.terenin.service.PaymentService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final RestTemplate restTemplate;
    private final DiscoveryClient client;
    private final CreditCardRepository repository;
    private final CreditCardMapper mapper;


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

    @Override
    public boolean payForSubscription() {
        return false;
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
