package br.com.smartcart.api.rest;

import br.com.smartcart.application.service.SmartCartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/receipt")
public class SmartCartController {

    private final SmartCartService smartCartService;

    public SmartCartController(SmartCartService smartCartService)
    {
        this.smartCartService = smartCartService;
    }

    @PostMapping("/import")
    public ResponseEntity<String> importRecept(@RequestParam String url) {
        try {
            smartCartService.importRecept(url);
            return ResponseEntity.ok("Nota processada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao processar nota: " + e.getMessage());
        }
    }
}
