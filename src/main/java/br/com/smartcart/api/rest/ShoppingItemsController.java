package br.com.smartcart.api.rest;

import br.com.smartcart.api.convert.ConvertRqVO;
import br.com.smartcart.api.rest.dto.ShoppingItemsRq;
import br.com.smartcart.application.service.ShoppingItemsService;
import org.hibernate.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shopping-items")
public class ShoppingItemsController {

    private final ShoppingItemsService shoppingItemsService;
    private final ConvertRqVO convert;

    public ShoppingItemsController(ShoppingItemsService shoppingItemsService, ConvertRqVO convert)
    {
        this.shoppingItemsService = shoppingItemsService;
        this.convert = convert;
    }

    @PostMapping()
    public ResponseEntity<String> save(@RequestBody ShoppingItemsRq shoppingItemsRq,
                                       @RequestHeader Long customerId) {
        try {
            shoppingItemsService.save(convert.shoppingItemsConvert(shoppingItemsRq), customerId);
            return ResponseEntity.ok("Lista de compras salva com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao processar nota: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id")  Long id) {
        try {
            shoppingItemsService.delete(id);
            return ResponseEntity.ok("Lista de compras excluída com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao processar nota: " + e.getMessage());
        }
    }
}
