package one.terenin.yookassa.exception;

import one.terenin.exception.BaseException;
import one.terenin.exception.common.ErrorCode;

public class BadRequestException extends BaseException {

    public BadRequestException(ErrorCode message) {
        super(message);
    }
}
