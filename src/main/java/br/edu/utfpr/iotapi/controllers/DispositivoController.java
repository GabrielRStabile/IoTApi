package br.edu.utfpr.iotapi.controllers;

import br.edu.utfpr.iotapi.dto.dispositivo.CreateDispositivoDTO;
import br.edu.utfpr.iotapi.dto.dispositivo.GetDispositivoDTO;
import br.edu.utfpr.iotapi.exceptions.NotFoundException;
import br.edu.utfpr.iotapi.models.Atuador;
import br.edu.utfpr.iotapi.models.Dispositivo;
import br.edu.utfpr.iotapi.models.Sensor;
import br.edu.utfpr.iotapi.services.DispositivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/dispositivo")
public class DispositivoController {
    @Autowired
    private DispositivoService dispositivoService;

    @PostMapping
    public ResponseEntity<Dispositivo> create(@RequestBody CreateDispositivoDTO dto) {
        Dispositivo dispositivo = dispositivoService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dispositivo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Dispositivo> update(@PathVariable("id") long id, @RequestBody CreateDispositivoDTO dto)
            throws NotFoundException {
        Dispositivo dispositivo = dispositivoService.update(dto, id);
        return ResponseEntity.ok(dispositivo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) throws NotFoundException {
        dispositivoService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/sensor")
    public ResponseEntity<Void> addSensors(@PathVariable("id") long id, @RequestBody List<Long> sensorIds)
            throws NotFoundException {
        dispositivoService.addSensors(id, sensorIds);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/sensor")
    public ResponseEntity<Void> removeSensors(@PathVariable("id") long id, @RequestBody List<Long> sensorIds)
            throws NotFoundException {
        dispositivoService.removeSensors(id, sensorIds);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/atuador")
    public ResponseEntity<Void> addAtuadores(@PathVariable("id") long id, @RequestBody List<Long> atuadorIds)
            throws NotFoundException {
        dispositivoService.addAtuadores(id, atuadorIds);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/atuador")
    public ResponseEntity<Void> removeAtuadores(@PathVariable("id") long id, @RequestBody List<Long> atuadorIds)
            throws NotFoundException {
        dispositivoService.removeAtuadores(id, atuadorIds);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<GetDispositivoDTO>> getAll() {
        List<GetDispositivoDTO> dispositivos = dispositivoService.getAll();
        return ResponseEntity.ok(dispositivos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetDispositivoDTO> getById(@PathVariable("id") long id) {
        // Dispositivo dispositivo = dispositivoService.getById(id);
        GetDispositivoDTO dispositivo = dispositivoService.getById(id);
        if (dispositivo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dispositivo);
    }

    @GetMapping("/{id}/sensor")
    public ResponseEntity<List<Sensor>> getSensorsByDispositivoId(@PathVariable("id") long id) {
        List<Sensor> sensores = dispositivoService.getSensorsByDispositivoId(id);
        return ResponseEntity.ok(sensores);
    }

    @GetMapping("/{id}/atuador")
    public ResponseEntity<List<Atuador>> getAtuadoresByDispositivoId(@PathVariable("id") long id) {
        List<Atuador> atuadores = dispositivoService.getAtuadoresByDispositivoId(id);
        return ResponseEntity.ok(atuadores);
    }
}
