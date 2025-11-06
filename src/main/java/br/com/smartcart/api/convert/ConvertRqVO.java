package br.com.smartcart.api.convert;

import br.com.smartcart.api.rest.dto.ShoppingItemsRq;
import br.com.smartcart.domain.valueobjects.request.ShoppingItemsRqVO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConvertRqVO {

    ShoppingItemsRqVO shoppingItemsConvert(ShoppingItemsRq shoppingItemsRq);
}
