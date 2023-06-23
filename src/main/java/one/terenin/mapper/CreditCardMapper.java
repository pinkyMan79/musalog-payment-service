package one.terenin.mapper;

import one.terenin.dto.request.CreditCardRequest;
import one.terenin.dto.response.CreditCardResponse;
import one.terenin.entity.CreditCardEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreditCardMapper {

    CreditCardResponse map(CreditCardEntity entity);
    @InheritInverseConfiguration
    CreditCardEntity map(CreditCardRequest request);

}
