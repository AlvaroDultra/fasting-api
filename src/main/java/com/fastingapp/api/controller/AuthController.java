package com.fastingapp.api.controller;

import com.fastingapp.api.dto.AuthResponseDTO;
import com.fastingapp.api.dto.LoginRequestDTO;
import com.fastingapp.api.dto.UsuarioRequestDTO;
import com.fastingapp.api.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Endpoints para registro e login de usuários")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/registrar")
    @Operation(
            summary = "Registrar novo usuário",
            description = "Cria uma nova conta de usuário e retorna um token JWT para autenticação"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário registrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou email já cadastrado")
    })
    public ResponseEntity<AuthResponseDTO> registrar(@Valid @RequestBody UsuarioRequestDTO dto) {
        AuthResponseDTO response = authService.registrar(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    @Operation(
            summary = "Fazer login",
            description = "Autentica um usuário existente e retorna um token JWT válido por 24 horas"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Email ou senha inválidos")
    })
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        AuthResponseDTO response = authService.login(dto);
        return ResponseEntity.ok(response);
    }
}