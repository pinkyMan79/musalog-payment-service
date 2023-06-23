package one.terenin.yookassa;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import one.terenin.yookassa.implementation.RealYookassa;
import one.terenin.yookassa.propertysource.YooPropertySource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class YookassaInitializer {

    private final YooPropertySource propertySource;

    public Yookassa initializeSdk() {
        return new RealYookassa(Integer.parseInt(propertySource.getShopId()), propertySource.getSecret());
    }
}
