package br.com.zambotti.customer.service;

import br.com.zambotti.customer.dto.AuthDTO;
import br.com.zambotti.customer.dto.JwtDTO;

public interface UserService {
    JwtDTO login(AuthDTO authDTO);
}
