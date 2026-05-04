package br.com.smartcart.api.rest;

import br.com.smartcart.api.convert.ConvertRqVO;
import br.com.smartcart.api.rest.dto.ShoppingItemsRq;
import br.com.smartcart.domain.valueobjects.response.ShoppingItemsRsVO;
import br.com.smartcart.application.service.ShoppingItemsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shopping-items")
@Tag(name = "Shopping Items", description = "Gerenciamento de listas de compras")
@SecurityRequirement(name = "bearerAuth")
public class ShoppingItemsController {

    private final ShoppingItemsService shoppingItemsService;
    private final ConvertRqVO convert;

    public ShoppingItemsController(ShoppingItemsService shoppingItemsService, ConvertRqVO convert) {
        this.shoppingItemsService = shoppingItemsService;
        this.convert = convert;
    }

    @Operation(summary = "Criar lista de compras", description = "Cria uma nova lista de compras para o usuário autenticado")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista criada com sucesso",
                content = @Content(schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "403", description = "customerId não corresponde ao usuário autenticado",
                content = @Content(schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "500", description = "Erro interno ao processar a requisição",
                content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping()
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> save(
            @RequestBody ShoppingItemsRq shoppingItemsRq,
            @AuthenticationPrincipal Jwt jwt,
            @Parameter(description = "ID do cliente (deve corresponder ao usuário autenticado)")
            @RequestHeader(required = false) Long customerId) {
        try {
            Long authenticatedUserId = extractUserIdFromJwt(jwt);

            if (customerId != null && !customerId.equals(authenticatedUserId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Acesso negado: customerId não corresponde ao usuário autenticado");
            }

            shoppingItemsService.save(convert.shoppingItemsConvert(shoppingItemsRq), authenticatedUserId);
            return ResponseEntity.ok("Lista de compras salva com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao processar nota: " + e.getMessage());
        }
    }

    @Operation(summary = "Excluir lista de compras", description = "Exclui uma lista de compras pelo ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista excluída com sucesso",
                content = @Content(schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "500", description = "Erro interno ao processar a requisição",
                content = @Content(schema = @Schema(implementation = String.class)))
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> delete(
            @Parameter(description = "ID da lista de compras", required = true)
            @PathVariable(name = "id") Long id,
            @AuthenticationPrincipal Jwt jwt) {
        try {
            shoppingItemsService.delete(id);
            return ResponseEntity.ok("Lista de compras excluída com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao processar nota: " + e.getMessage());
        }
    }

    @Operation(summary = "Buscar lista de compras", description = "Retorna uma lista de compras pelo ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista encontrada",
                content = @Content(schema = @Schema(implementation = ShoppingItemsRsVO.class))),
        @ApiResponse(responseCode = "404", description = "Lista não encontrada",
                content = @Content)
    })
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ShoppingItemsRsVO> find(
            @Parameter(description = "ID da lista de compras", required = true)
            @PathVariable(name = "id") Long id,
            @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(shoppingItemsService.find(id));
    }

    @Operation(summary = "Listar listas de compras", description = "Retorna todas as listas de compras do usuário autenticado")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Listas retornadas com sucesso",
                content = @Content(schema = @Schema(implementation = ShoppingItemsRsVO.class))),
        @ApiResponse(responseCode = "403", description = "customerId não corresponde ao usuário autenticado",
                content = @Content(schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "500", description = "Erro interno ao processar a requisição",
                content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping()
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> list(
            @AuthenticationPrincipal Jwt jwt,
            @Parameter(description = "ID do cliente (deve corresponder ao usuário autenticado)")
            @RequestHeader(required = false) Long customerId) {
        try {
            Long authenticatedUserId = extractUserIdFromJwt(jwt);

            if (customerId != null && !customerId.equals(authenticatedUserId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Acesso negado: customerId não corresponde ao usuário autenticado");
            }

            return ResponseEntity.ok(shoppingItemsService.List(authenticatedUserId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao processar requisição: " + e.getMessage());
        }
    }

    @Operation(summary = "Atualizar lista de compras", description = "Atualiza uma lista de compras existente do usuário autenticado")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista atualizada com sucesso",
                content = @Content(schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "403", description = "customerId não corresponde ao usuário autenticado",
                content = @Content(schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "500", description = "Erro interno ao processar a requisição",
                content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PutMapping()
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> update(
            @RequestBody ShoppingItemsRq shoppingItemsRq,
            @AuthenticationPrincipal Jwt jwt,
            @Parameter(description = "ID do cliente (deve corresponder ao usuário autenticado)")
            @RequestHeader(required = false) Long customerId) {
        try {
            Long authenticatedUserId = extractUserIdFromJwt(jwt);

            if (customerId != null && !customerId.equals(authenticatedUserId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Acesso negado: customerId não corresponde ao usuário autenticado");
            }

            shoppingItemsService.update(convert.shoppingItemsConvert(shoppingItemsRq), authenticatedUserId);
            return ResponseEntity.ok("Lista de compras atualizada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao processar nota: " + e.getMessage());
        }
    }

    private Long extractUserIdFromJwt(Jwt jwt) {
        if (jwt == null) {
            throw new IllegalArgumentException("JWT token não pode ser nulo");
        }

        String subject = jwt.getSubject();

        try {
            return Long.parseLong(subject);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Não foi possível extrair o ID do usuário do token JWT: " + subject);
        }
    }
}
