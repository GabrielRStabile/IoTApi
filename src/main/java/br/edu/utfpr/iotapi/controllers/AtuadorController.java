package br.edu.utfpr.iotapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.utfpr.iotapi.dto.atuador.CreateAtuadorDTO;
import br.edu.utfpr.iotapi.dto.atuador.GetAtuadorDTO;
import br.edu.utfpr.iotapi.exceptions.NotFoundException;
import br.edu.utfpr.iotapi.models.Atuador;
import br.edu.utfpr.iotapi.services.AtuadorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/atuador")
public class AtuadorController {
  @Autowired
  private AtuadorService atuadorService;

  @GetMapping("/{id}")
  public ResponseEntity<GetAtuadorDTO> getById(@PathVariable("id") long id) throws NotFoundException {
    var atuador = atuadorService.getById(id);

    return atuador.map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<List<GetAtuadorDTO>> getAll() {
    List<GetAtuadorDTO> atuadores = atuadorService.getAll();
    return ResponseEntity.ok(atuadores);
  }

  @PostMapping
  public ResponseEntity<Atuador> create(@Valid @RequestBody CreateAtuadorDTO dto) {
    var atuador = atuadorService.create(dto);

    return ResponseEntity.ok().body(atuador);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Atuador> update(@PathVariable("id") long id, @Valid @RequestBody CreateAtuadorDTO dto)
      throws NotFoundException {

    var atuador = atuadorService.update(dto, id);
    return ResponseEntity.ok().body(atuador);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteById(@PathVariable("id") long id) throws NotFoundException {
    atuadorService.deleteById(id);
    return ResponseEntity.ok().build();
  }
}
