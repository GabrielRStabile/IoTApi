package br.edu.utfpr.iotapi.controllers;

import br.edu.utfpr.iotapi.dto.CreatePessoaDTO;
import br.edu.utfpr.iotapi.dto.DeletePessoaDTO;
import br.edu.utfpr.iotapi.dto.GetGatewaysByPessoaIdDTO;
import br.edu.utfpr.iotapi.dto.UpdatePessoaDTO;
import br.edu.utfpr.iotapi.exceptions.NotFoundException;
import br.edu.utfpr.iotapi.exceptions.WrongPasswordException;
import br.edu.utfpr.iotapi.models.Gateway;
import br.edu.utfpr.iotapi.models.Pessoa;
import br.edu.utfpr.iotapi.services.PessoaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {
    @Autowired
    private PessoaService pessoaService;

    @GetMapping
    public List<Pessoa> getAll() {
        return pessoaService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> getById(@PathVariable("id") long id) {
        var person = pessoaService.getById(id);

        return person.map(pessoa -> ResponseEntity.ok().body(pessoa))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/gateway")
    public List<GetGatewaysByPessoaIdDTO> getGatewaysByPessoaId(@PathVariable("id") long id) {
        return pessoaService.getGatewaysByPessoaId(id);
    }

    @PostMapping
    public ResponseEntity<Pessoa> create(@Valid @RequestBody CreatePessoaDTO dto) {
        var res = pessoaService.create(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> update(@PathVariable("id") long id, @Valid @RequestBody UpdatePessoaDTO dto)
            throws NotFoundException, WrongPasswordException {
        var res = pessoaService.update(id, dto);
        return ResponseEntity.ok().body(res);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Pessoa> delete(@PathVariable("id") long id, @Valid @RequestBody DeletePessoaDTO dto)
            throws NotFoundException, WrongPasswordException {
        pessoaService.checkPassword(id, dto.senha());

        pessoaService.delete(id);
        return ResponseEntity.ok().build();
    }
}
