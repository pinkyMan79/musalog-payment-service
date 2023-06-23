package one.terenin.yookassa;

import lombok.NonNull;
import one.terenin.yookassa.event.YookassaEvent;
import one.terenin.yookassa.exception.BadRequestException;
import one.terenin.yookassa.exception.UnspecifiedShopInformation;
import one.terenin.yookassa.model.Amount;
import one.terenin.yookassa.model.Payment;
import one.terenin.yookassa.model.Refund;
import one.terenin.yookassa.model.Webhook;
import one.terenin.yookassa.model.collecting.PaymentList;
import one.terenin.yookassa.model.collecting.RefundList;
import one.terenin.yookassa.model.collecting.WebhookList;


import java.io.IOException;
import java.util.UUID;

public interface Yookassa {

    Payment createPayment(@NonNull Amount amount,
                          @NonNull String description,
                          @NonNull String redirectUrl)
            throws UnspecifiedShopInformation, BadRequestException, IOException;
    Payment getPayment(@NonNull UUID paymentIdentifier)
            throws UnspecifiedShopInformation, BadRequestException, IOException;
    PaymentList getPayments()
            throws UnspecifiedShopInformation, BadRequestException, IOException;
    Refund createRefund(@NonNull UUID paymentIdentifier, @NonNull Amount amount)
            throws UnspecifiedShopInformation, BadRequestException, IOException;
    Refund getRefund(@NonNull UUID refundIdentifier)
            throws UnspecifiedShopInformation, BadRequestException, IOException;
    RefundList getRefunds()
            throws UnspecifiedShopInformation, BadRequestException, IOException;
    Webhook createWebhook(@NonNull YookassaEvent event,
                          @NonNull String url)
            throws UnspecifiedShopInformation, BadRequestException, IOException;
    void deleteWebhook(@NonNull UUID webhookIdentifier)
            throws UnspecifiedShopInformation, BadRequestException, IOException;
    WebhookList getWebhooks()
            throws UnspecifiedShopInformation, BadRequestException, IOException;

}
