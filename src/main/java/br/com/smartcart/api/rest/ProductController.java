package br.com.smartcart.api.rest;

import br.com.smartcart.application.service.ReceptService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ReceptService receptService;

    public ProductController(ReceptService receptService)
    {
        this.receptService = receptService;
    }

    @PostMapping("/import")
    public ResponseEntity<String> search(@RequestParam String url,
                                               @RequestHeader Long customerId) {
        try {
            receptService.importRecept(url, customerId);
            return ResponseEntity.ok("Nota processada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao processar nota: " + e.getMessage());
        }
    }
}
