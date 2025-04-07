package com.auth.controller;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.domain.Usuario;
import com.auth.dto.LoginDto;
import com.auth.service.LoginService;

@RestController
@CrossOrigin("*")
@RequestMapping("login")
public class LoginController {

	@Autowired
	private JwtEncoder jwtEncoder;

	@Autowired
	private LoginService loginService;

	@PostMapping
	public ResponseEntity<?> login(@RequestBody LoginDto login) {

		try {

			if (login.getUsuario().isBlank() || login.getSenha().isBlank()) {
				throw new NullPointerException("Usuário ou senha não informados");
			} else {
				Usuario usuario = loginService.buscarUsuario(login);

				Instant agora = Instant.now();

				Long expiracao = 400L;

				JwtClaimsSet claims = JwtClaimsSet.builder().issuer("http://autenticador").subject(usuario.getUsuario())
						.issuedAt(agora).expiresAt(agora.plusSeconds(expiracao)).build();

				Jwt jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims));

				return ResponseEntity.ok(jwtValue.getTokenValue());
			}

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}

}