package br.edu.utfpr.iotapi.services;

import br.edu.utfpr.iotapi.dto.atuador.GetAtuadorDTO;
import br.edu.utfpr.iotapi.dto.dispositivo.CreateDispositivoDTO;
import br.edu.utfpr.iotapi.dto.dispositivo.GetDispositivoDTO;
import br.edu.utfpr.iotapi.dto.sensor.GetSensorDTO;
import br.edu.utfpr.iotapi.exceptions.NotFoundException;
import br.edu.utfpr.iotapi.models.Dispositivo;
import br.edu.utfpr.iotapi.models.Sensor;
import br.edu.utfpr.iotapi.models.Atuador;
import br.edu.utfpr.iotapi.repository.DispositivoRepository;
import br.edu.utfpr.iotapi.repository.SensorRepository;

import br.edu.utfpr.iotapi.repository.AtuadorRepository;

import org.hibernate.Hibernate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DispositivoService {
    @Autowired
    private DispositivoRepository dispositivoRepository;

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private SensorService sensorService;

    @Autowired
    private AtuadorService atuadorService;

    @Autowired
    private AtuadorRepository atuadorRepository;

    public GetDispositivoDTO getById(long id) {
        Dispositivo dispositivo = dispositivoRepository.findById(id).orElse(null);
        return convertToGetDispositivoDTO(dispositivo);
    }

    public List<GetDispositivoDTO> getAll() {
        List<Dispositivo> dispositivos = dispositivoRepository.findAll();
        return dispositivos.stream().map(this::convertToGetDispositivoDTO).collect(Collectors.toList());
    }

    public Dispositivo create(CreateDispositivoDTO dto) {
        Dispositivo dispositivo = new Dispositivo();
        BeanUtils.copyProperties(dto, dispositivo);
        return dispositivoRepository.save(dispositivo);
    }

    public Dispositivo update(CreateDispositivoDTO dto, long id) throws NotFoundException {
        Dispositivo dispositivo = dispositivoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Dispositivo " + id + " não existe"));
        BeanUtils.copyProperties(dto, dispositivo, "id");
        return dispositivoRepository.save(dispositivo);
    }

    public void delete(long id) throws NotFoundException {
        if (!dispositivoRepository.existsById(id)) {
            throw new NotFoundException("Dispositivo " + id + " não existe");
        }
        dispositivoRepository.deleteById(id);
    }

    @Transactional
    public void addSensors(long id, List<Long> sensorIds) throws NotFoundException {
        Dispositivo dispositivo = dispositivoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Dispositivo " + id + " não existe"));

        List<Sensor> sensores = sensorRepository.findAllById(sensorIds);

        for (Sensor sensor : sensores) {
            if (sensor.getDispositivo() == null) {
                sensor.setDispositivo(dispositivo);
                sensorService.addDispositivo(sensor.getId(), dispositivo.getId());
            }
        }
        dispositivo.setSensores(sensores);
        dispositivoRepository.save(dispositivo);
    }

    @Transactional
    public void removeSensors(long id, List<Long> sensorIds) throws NotFoundException {
        Dispositivo dispositivo = dispositivoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Dispositivo " + id + " não existe"));
        for (Long sensorId : sensorIds) {
            Sensor sensor = sensorRepository.findById(sensorId)
                    .orElseThrow(() -> new NotFoundException("Sensor " + sensorId + " não existe"));
            dispositivo.getSensores().remove(sensor);

            sensorService.removeDispositivo(sensorId);
        }
        dispositivoRepository.save(dispositivo);
    }

    @Transactional
    public void addAtuadores(long id, List<Long> atuadorIds) throws NotFoundException {
        Dispositivo dispositivo = dispositivoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Dispositivo " + id + " não existe"));
        for (Long atuadorId : atuadorIds) {
            Atuador atuador = atuadorRepository.findById(atuadorId)
                    .orElseThrow(() -> new NotFoundException("Atuador " + atuadorId + " não existe"));
            atuador.setDispositivo(dispositivo);
            dispositivo.getAtuadores().add(atuador);
        }
        dispositivoRepository.save(dispositivo);
    }

    @Transactional
    public void removeAtuadores(long id, List<Long> atuadorIds) throws NotFoundException {
        Dispositivo dispositivo = dispositivoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Dispositivo " + id + " não existe"));
        for (Long atuadorId : atuadorIds) {
            Atuador atuador = atuadorRepository.findById(atuadorId)
                    .orElseThrow(() -> new NotFoundException("Atuador " + atuadorId + " não existe"));
            dispositivo.getAtuadores().remove(atuador);
        }
        dispositivoRepository.save(dispositivo);
    }

    @Transactional
    public List<GetSensorDTO> getSensorsByDispositivoId(long id) {
        Dispositivo dispositivo = dispositivoRepository.findById(id).orElse(null);
        if (dispositivo != null) {
            return dispositivo.getSensores().stream().map(sensor -> sensorService.convertToGetSensorDTO(sensor))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Transactional
    public List<GetAtuadorDTO> getAtuadoresByDispositivoId(long id) {
        Dispositivo dispositivo = dispositivoRepository.findById(id).orElse(null);
        if (dispositivo != null) {
            return dispositivo.getAtuadores().stream().map(atuador -> atuadorService.convertToGetAtuadorDTO(atuador))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private GetDispositivoDTO convertToGetDispositivoDTO(Dispositivo dispositivo) {
        Long gatewayId = (dispositivo.getGateway() != null) ? dispositivo.getGateway().getId() : null;

        GetDispositivoDTO dto = new GetDispositivoDTO(
                dispositivo.getId(), dispositivo.getNome(), dispositivo.getDescricao(), dispositivo.getLocal(),
                dispositivo.getEndereco(), gatewayId);
        return dto;
    }
}
