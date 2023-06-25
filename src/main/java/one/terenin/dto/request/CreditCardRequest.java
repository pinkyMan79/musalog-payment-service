package one.terenin.dto.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.CreditCardNumber;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CreditCardRequest {
    // Luhn algorithm here
    @CreditCardNumber(message="Not a valid credit card number")
    private String cardNum;
    @Digits(integer=3, fraction=0, message="Invalid CVV")
    private String cvv;
    @Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([2-9][0-9])$",
            message="Must be formatted MM/YY")
    private Date expiration;
    private String ownerName;
    private UUID ownerId;
}
