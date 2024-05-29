package br.edu.utfpr.iotapi.controllers;

import br.edu.utfpr.iotapi.dto.sensor.CreateSensorDTO;
import br.edu.utfpr.iotapi.exceptions.NotFoundException;
import br.edu.utfpr.iotapi.models.Leitura;
import br.edu.utfpr.iotapi.models.Sensor;
import br.edu.utfpr.iotapi.services.LeituraService;
import br.edu.utfpr.iotapi.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sensor")
public class SensorController {
    @Autowired
    private SensorService sensorService;

    @Autowired
    private LeituraService leituraService;

    @GetMapping("/{id}")
    public ResponseEntity<Sensor> getById(@PathVariable("id") long id) {
        Sensor sensor = sensorService.getById(id);
        if (sensor == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sensor);
    }

    @GetMapping
    public ResponseEntity<List<Sensor>> getAll() {
        List<Sensor> sensores = sensorService.getAll();
        return ResponseEntity.ok(sensores);
    }

    @GetMapping("/{id}/leitura")
    public ResponseEntity<List<Leitura>> getLeiturasBySensorId(@PathVariable("id") long id) {
        List<Leitura> leituras = leituraService.getBySensorId(id);
        return ResponseEntity.ok(leituras);
    }

    @PostMapping
    public ResponseEntity<Sensor> create(@RequestBody CreateSensorDTO dto) {
        Sensor sensor = sensorService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(sensor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sensor> update(@PathVariable("id") long id, @RequestBody CreateSensorDTO dto)
            throws NotFoundException {
        Sensor sensor = sensorService.update(dto, id);
        return ResponseEntity.ok(sensor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) throws NotFoundException {
        sensorService.delete(id);
        return ResponseEntity.ok().build();
    }
}
