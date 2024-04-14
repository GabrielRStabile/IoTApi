package br.edu.utfpr.iotapi.controllers;

import br.edu.utfpr.iotapi.dto.CreatePessoaDTO;
import br.edu.utfpr.iotapi.dto.DeletePessoaDTO;
import br.edu.utfpr.iotapi.exceptions.NotFoundException;
import br.edu.utfpr.iotapi.exceptions.WrongPasswordException;
import br.edu.utfpr.iotapi.models.Pessoa;
import br.edu.utfpr.iotapi.services.PessoaService;
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

        return person.map(pessoa -> ResponseEntity.ok().body(pessoa)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Pessoa> create(@RequestBody CreatePessoaDTO dto) {
        var res = pessoaService.create(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> update(@PathVariable("id") long id, @RequestBody CreatePessoaDTO dto) throws NotFoundException {
        var res = pessoaService.update(dto, id);
        return ResponseEntity.ok().body(res);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Pessoa> delete(@PathVariable("id") long id, @RequestBody DeletePessoaDTO dto) throws NotFoundException, WrongPasswordException {
        pessoaService.checkPassword(id, dto.senha());

        pessoaService.delete(id);
        return ResponseEntity.ok().build();
    }
}
