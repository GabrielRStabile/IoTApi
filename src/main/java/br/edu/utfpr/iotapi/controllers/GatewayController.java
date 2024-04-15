package br.edu.utfpr.iotapi.controllers;

import br.edu.utfpr.iotapi.dto.CreateGatewayDTO;
import br.edu.utfpr.iotapi.exceptions.NotFoundException;
import br.edu.utfpr.iotapi.models.Gateway;
import br.edu.utfpr.iotapi.services.GatewayService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gateway")
public class GatewayController {
    @Autowired
    private GatewayService gatewayService;

    // @GetMapping
    // public ResponseEntity<List<Gateway>> getAll() {
    // return ResponseEntity.ok().body(gatewayService.getAll());
    // }

    @GetMapping("/{id}")
    public ResponseEntity<Gateway> getById(@PathVariable("id") long id) {
        var gateway = gatewayService.getById(id);

        return gateway.map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Gateway> create(@Valid @RequestBody CreateGatewayDTO dto) {
        var res = gatewayService.create(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Gateway> update(@PathVariable("id") long id, @Valid @RequestBody CreateGatewayDTO dto)
            throws NotFoundException {
        var res = gatewayService.update(dto, id);

        return ResponseEntity.ok().body(res);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Gateway> delete(@PathVariable("id") long id) throws NotFoundException {
        gatewayService.delete(id);
        return ResponseEntity.ok().build();
    }
}
