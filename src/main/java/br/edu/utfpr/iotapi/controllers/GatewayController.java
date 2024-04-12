package br.edu.utfpr.iotapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.utfpr.iotapi.dto.GatewayDTO;
import br.edu.utfpr.iotapi.exceptions.NotFoundException;
import br.edu.utfpr.iotapi.models.Gateway;
import br.edu.utfpr.iotapi.services.GatewayService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/gateway")
public class GatewayController {
  @Autowired
  private GatewayService gatewayService;

  @GetMapping
  public ResponseEntity<List<Gateway>> getAll() {
    return ResponseEntity.ok().body(gatewayService.getAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Object> getById(@PathVariable("id") long id) {
    var gateway = gatewayService.getById(id);

    return gateway.isPresent()
        ? ResponseEntity.ok().body(gateway.get())
        : ResponseEntity.notFound().build();
  }

  @PostMapping
  public ResponseEntity<Object> create(@RequestBody GatewayDTO dto) {
    try {
      var res = gatewayService.create(dto);

      return ResponseEntity.status(HttpStatus.CREATED).body(res);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> delete(@PathVariable("id") long id) {
    try {
      gatewayService.delete(id);
      return ResponseEntity.ok().build();
    } catch (NotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }
}
