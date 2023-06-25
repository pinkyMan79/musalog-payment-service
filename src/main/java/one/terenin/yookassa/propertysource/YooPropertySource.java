package one.terenin.yookassa.propertysource;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@PropertySource("classpath:application.yaml")
public class YooPropertySource {

    private String secret;
    private String shopId;
    private Integer price;

    public YooPropertySource(@Value("${yoo.secret}") String secret,
                             @Value("${yoo.id}") String shopId,
                             @Value("${yoo.price}") String price) {
        this.secret = secret;
        this.shopId = shopId;
        this.price = Integer.parseInt(price);
    }
}
