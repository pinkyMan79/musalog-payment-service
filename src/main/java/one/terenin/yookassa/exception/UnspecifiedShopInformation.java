package one.terenin.yookassa.exception;

import one.terenin.exception.BaseException;
import one.terenin.exception.common.ErrorCode;

public class UnspecifiedShopInformation extends BaseException {
    public UnspecifiedShopInformation(ErrorCode message) {
        super(message);
    }
}
