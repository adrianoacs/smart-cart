package br.com.smartcart.api.convert;

import br.com.smartcart.api.rest.dto.ShoppingItemsRq;
import br.com.smartcart.domain.valueobjects.ShoppingItemsVO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConvertRqVO {

    ShoppingItemsVO shoppingItemsConvert(ShoppingItemsRq shoppingItemsRq);
}
