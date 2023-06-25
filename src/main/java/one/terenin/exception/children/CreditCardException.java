package one.terenin.exception.children;

import one.terenin.exception.BaseException;
import one.terenin.exception.common.ErrorCode;

public class CreditCardException extends BaseException {
    public CreditCardException(ErrorCode message) {
        super(message);
    }
}
