package one.terenin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CreditCardResponse {
    private UUID creditCardId;
    private String cardNum;
    private String cvv;
    private Date expiration;
    private String ownerName;
}
