package one.terenin.yookassa.event;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum YookassaEvent {

    PAYMENT_WAITING_FOR_CAPTURE("Payment waiting for capture"),
    PAYMENT_SUCCESS_PAID("Payment succeeded"),
    PAYMENT_CANCELED("Payment canceled"),

    REFUND_SUCCESS("Refund succeeded"),

    ;

    String eventName;
}
