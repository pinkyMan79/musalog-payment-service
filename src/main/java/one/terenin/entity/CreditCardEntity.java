package one.terenin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import one.terenin.entity.parent.AbstractEntity;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "credit_card_table")
public class CreditCardEntity extends AbstractEntity {

    @Column(name = "card_number", nullable = false)
    private String cardNum;
    @Column(name = "cvv", nullable = false)
    private String cvv;
    @Column(name = "expiration", nullable = false)
    private Date expiration;
    @Column(name = "owner_name", nullable = true)
    private String ownerName;
    @Column(name = "owner_id", nullable = false)
    private UUID ownerId;

}
