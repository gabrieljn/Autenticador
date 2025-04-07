package com.auth.service;

import com.auth.domain.Usuario;
import com.auth.dto.LoginDto;

public interface LoginService {
	
	public Usuario buscarUsuario(LoginDto login) throws NullPointerException;

}