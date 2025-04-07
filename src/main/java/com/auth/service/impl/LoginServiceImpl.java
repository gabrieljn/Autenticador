package com.auth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth.domain.Usuario;
import com.auth.dto.LoginDto;
import com.auth.repository.UsuarioRepository;
import com.auth.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	BCryptPasswordEncoder bcryptPasswordEncoder;

	public Usuario buscarUsuario(LoginDto login) throws NullPointerException {

		Usuario usuario = usuarioRepository.findByUsuario(login.getUsuario());

		if (usuario == null || verificarSenha(login.getSenha(), usuario.getSenha()) == false) {
			throw new NullPointerException("Usuário ou senha inválidos");
		} else {
			return usuario;
		}

	}
	
	private boolean verificarSenha(String senhaLogin, String senhaUsuario) {
		return bcryptPasswordEncoder.matches(senhaLogin, senhaUsuario);
	}

}