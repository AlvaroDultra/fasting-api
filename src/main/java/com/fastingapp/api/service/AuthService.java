package com.fastingapp.api.service;

import com.fastingapp.api.dto.AuthResponseDTO;
import com.fastingapp.api.dto.LoginRequestDTO;
import com.fastingapp.api.dto.UsuarioRequestDTO;
import com.fastingapp.api.dto.UsuarioResponseDTO;
import com.fastingapp.api.model.entity.Usuario;
import com.fastingapp.api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UsuarioService usuarioService;

    @Transactional
    public AuthResponseDTO registrar(UsuarioRequestDTO dto) {
        // Criar usuário usando o serviço existente
        UsuarioResponseDTO usuarioDTO = usuarioService.criar(dto);

        // Gerar token
        String token = jwtService.gerarToken(usuarioDTO.getEmail(), usuarioDTO.getId());

        return new AuthResponseDTO(token, usuarioDTO);
    }

    public AuthResponseDTO login(LoginRequestDTO dto) {
        // Autenticar
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha())
        );

        // Buscar usuário
        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Gerar token
        String token = jwtService.gerarToken(usuario.getEmail(), usuario.getId());

        // Converter para DTO
        UsuarioResponseDTO usuarioDTO = usuarioService.buscarPorEmail(usuario.getEmail());

        return new AuthResponseDTO(token, usuarioDTO);
    }
}