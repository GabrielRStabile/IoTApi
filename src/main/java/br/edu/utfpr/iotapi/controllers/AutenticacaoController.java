package br.edu.utfpr.iotapi.controllers;

import br.edu.utfpr.iotapi.dto.autenticacao.CreatePessoaDTO;
import br.edu.utfpr.iotapi.dto.autenticacao.LoginDTO;
import br.edu.utfpr.iotapi.exceptions.EmailAlreadyRegistered;
import br.edu.utfpr.iotapi.models.Pessoa;
import br.edu.utfpr.iotapi.services.AutenticacaoService;
import br.edu.utfpr.iotapi.services.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {
    @Autowired
    private AutenticacaoService autenticacaoService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping
    public ResponseEntity<String> autenticar(@Valid @RequestBody LoginDTO loginDTO) throws UsernameNotFoundException {
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.senha());
        var auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((Pessoa) auth.getPrincipal());

        return ResponseEntity.ok().body(token);
    }

    @PostMapping
    public ResponseEntity registrar(@Valid @RequestBody CreatePessoaDTO createPessoaDTO) throws EmailAlreadyRegistered {
        autenticacaoService.registrar(createPessoaDTO);
        return ResponseEntity.ok().build();
    }
}
