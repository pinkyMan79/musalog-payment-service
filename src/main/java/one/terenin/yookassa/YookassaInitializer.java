package one.terenin.yookassa;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import one.terenin.yookassa.implementation.RealYookassa;

@UtilityClass
public class YookassaInitializer {

    public Yookassa initializeSdk(int shopIdentifier, @NonNull String shopToken) {
        return new RealYookassa(shopIdentifier, shopToken);
    }
}
