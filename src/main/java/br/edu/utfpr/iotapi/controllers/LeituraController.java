package br.edu.utfpr.iotapi.controllers;

import br.edu.utfpr.iotapi.dto.leitura.CreateLeituraDTO;
import br.edu.utfpr.iotapi.exceptions.NotFoundException;
import br.edu.utfpr.iotapi.models.Leitura;
import br.edu.utfpr.iotapi.services.LeituraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/leitura")
public class LeituraController {
    @Autowired
    private LeituraService leituraService;

    @GetMapping("/{id}")
    public ResponseEntity<Leitura> getById(@PathVariable("id") long id) {
        Leitura leitura = leituraService.getById(id);

        if (leitura == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(leitura);
    }

    @PostMapping
    public ResponseEntity<Leitura> create(@RequestBody CreateLeituraDTO dto) throws NotFoundException {
        var leitura = leituraService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(leitura);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) throws NotFoundException {
        leituraService.delete(id);
        return ResponseEntity.ok().build();
    }
}
