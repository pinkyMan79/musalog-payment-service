package one.terenin.yookassa.model.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import one.terenin.yookassa.model.Amount;

import java.util.UUID;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC)
public class RefundRequest {

    Amount amount;
    UUID payment_id;
}
