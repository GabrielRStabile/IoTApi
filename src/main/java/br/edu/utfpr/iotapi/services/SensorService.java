package br.edu.utfpr.iotapi.services;

import br.edu.utfpr.iotapi.dto.sensor.CreateSensorDTO;
import br.edu.utfpr.iotapi.dto.sensor.GetSensorDTO;
import br.edu.utfpr.iotapi.exceptions.NotFoundException;
import br.edu.utfpr.iotapi.models.Dispositivo;
import br.edu.utfpr.iotapi.models.Sensor;
import br.edu.utfpr.iotapi.repository.SensorRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SensorService {
    @Autowired
    private SensorRepository sensorRepository;

    public Sensor getById(long id) {
        return sensorRepository.findById(id).orElse(null);
    }

    public List<GetSensorDTO> getAll() {
        List<Sensor> sensores = sensorRepository.findAll();
        return sensores.stream().map(this::convertToGetSensorDTO).collect(Collectors.toList());
    }

    public Sensor create(CreateSensorDTO dto) {
        Sensor sensor = new Sensor();
        BeanUtils.copyProperties(dto, sensor);
        return sensorRepository.save(sensor);
    }

    public Sensor update(CreateSensorDTO dto, long id) throws NotFoundException {
        Sensor sensor = sensorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Sensor " + id + " não existe"));
        BeanUtils.copyProperties(dto, sensor, "id");
        return sensorRepository.save(sensor);
    }

    public void removeDispositivo(long id) throws NotFoundException {
        Sensor sensor = sensorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Sensor " + id + " não existe"));
        sensor.setDispositivo(null);
        sensorRepository.save(sensor);
    }

    public void addDispositivo(long id, long dispositivoId) throws NotFoundException {
        Sensor sensor = sensorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Sensor " + id + " não existe"));
        Dispositivo dispositivo = new Dispositivo();
        dispositivo.setId(dispositivoId);
        sensor.setDispositivo(dispositivo);
        sensorRepository.save(sensor);
    }

    public void delete(long id) throws NotFoundException {
        if (!sensorRepository.existsById(id)) {
            throw new NotFoundException("Sensor " + id + " não existe");
        }
        sensorRepository.deleteById(id);
    }

    public GetSensorDTO convertToGetSensorDTO(Sensor sensor) {
        Long dispositivoId = (sensor.getDispositivo() != null) ? sensor.getDispositivo().getId() : null;

        GetSensorDTO dto = new GetSensorDTO(
                sensor.getId(), sensor.getNome(), sensor.getTipo(), dispositivoId);
        return dto;
    }
}
