package br.edu.utfpr.iotapi.services;

import br.edu.utfpr.iotapi.dto.sensor.CreateSensorDTO;
import br.edu.utfpr.iotapi.exceptions.NotFoundException;
import br.edu.utfpr.iotapi.models.Sensor;
import br.edu.utfpr.iotapi.repository.SensorRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SensorService {
    @Autowired
    private SensorRepository sensorRepository;

    public Sensor getById(long id) {
        return sensorRepository.findById(id).orElse(null);
    }

    public List<Sensor> getAll() {
        return sensorRepository.findAll();
    }

    public Sensor create(CreateSensorDTO dto) {
        Sensor sensor = new Sensor();
        BeanUtils.copyProperties(dto, sensor);
        return sensorRepository.save(sensor);
    }

    public Sensor update(CreateSensorDTO dto, long id) throws NotFoundException {
        Sensor sensor = sensorRepository.findById(id).orElseThrow(() -> new NotFoundException("Sensor " + id + " não existe"));
        BeanUtils.copyProperties(dto, sensor, "id");
        return sensorRepository.save(sensor);
    }

    // TODO: Remover o vinculo com dispositivo
    public void delete(long id) throws NotFoundException {
        if (!sensorRepository.existsById(id)) {
            throw new NotFoundException("Sensor " + id + " não existe");
        }
        sensorRepository.deleteById(id);
    }
}
