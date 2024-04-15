package br.edu.utfpr.iotapi.services;

import br.edu.utfpr.iotapi.dto.dispositivo.CreateDispositivoDTO;
import br.edu.utfpr.iotapi.exceptions.NotFoundException;
import br.edu.utfpr.iotapi.models.Dispositivo;
import br.edu.utfpr.iotapi.models.Sensor;
import br.edu.utfpr.iotapi.models.Atuador;
import br.edu.utfpr.iotapi.repository.DispositivoRepository;
import br.edu.utfpr.iotapi.repository.SensorRepository;
import br.edu.utfpr.iotapi.repository.AtuadorRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DispositivoService {
    @Autowired
    private DispositivoRepository dispositivoRepository;

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private AtuadorRepository atuadorRepository;

    public Dispositivo getById(long id) {
        return dispositivoRepository.findById(id).orElse(null);
    }

    public Dispositivo create(CreateDispositivoDTO dto) {
        Dispositivo dispositivo = new Dispositivo();
        BeanUtils.copyProperties(dto, dispositivo);
        return dispositivoRepository.save(dispositivo);
    }

    public Dispositivo update(CreateDispositivoDTO dto, long id) throws NotFoundException {
        Dispositivo dispositivo = dispositivoRepository.findById(id).orElseThrow(() -> new NotFoundException("Dispositivo " + id + " não existe"));
        BeanUtils.copyProperties(dto, dispositivo, "id");
        return dispositivoRepository.save(dispositivo);
    }

    public void delete(long id) throws NotFoundException {
        if (!dispositivoRepository.existsById(id)) {
            throw new NotFoundException("Dispositivo " + id + " não existe");
        }
        dispositivoRepository.deleteById(id);
    }

    public void addSensors(long id, List<Long> sensorIds) throws NotFoundException {
        Dispositivo dispositivo = dispositivoRepository.findById(id).orElseThrow(() -> new NotFoundException("Dispositivo " + id + " não existe"));
        for (Long sensorId : sensorIds) {
            Sensor sensor = sensorRepository.findById(sensorId).orElseThrow(() -> new NotFoundException("Sensor " + sensorId + " não existe"));
            dispositivo.getSensores().add(sensor);
        }
        dispositivoRepository.save(dispositivo);
    }

    public void removeSensors(long id, List<Long> sensorIds) throws NotFoundException {
        Dispositivo dispositivo = dispositivoRepository.findById(id).orElseThrow(() -> new NotFoundException("Dispositivo " + id + " não existe"));
        for (Long sensorId : sensorIds) {
            Sensor sensor = sensorRepository.findById(sensorId).orElseThrow(() -> new NotFoundException("Sensor " + sensorId + " não existe"));
            dispositivo.getSensores().remove(sensor);
            sensorRepository.delete(sensor);
        }

        dispositivoRepository.save(dispositivo);
    }

    public void addAtuadores(long id, List<Long> atuadorIds) throws NotFoundException {
        Dispositivo dispositivo = dispositivoRepository.findById(id).orElseThrow(() -> new NotFoundException("Dispositivo " + id + " não existe"));
        for (Long atuadorId : atuadorIds) {
            Atuador atuador = atuadorRepository.findById(atuadorId).orElseThrow(() -> new NotFoundException("Atuador " + atuadorId + " não existe"));
            dispositivo.getAtuadores().add(atuador);
        }
        dispositivoRepository.save(dispositivo);
    }

    public void removeAtuadores(long id, List<Long> atuadorIds) throws NotFoundException {
        Dispositivo dispositivo = dispositivoRepository.findById(id).orElseThrow(() -> new NotFoundException("Dispositivo " + id + " não existe"));
        for (Long atuadorId : atuadorIds) {
            Atuador atuador = atuadorRepository.findById(atuadorId).orElseThrow(() -> new NotFoundException("Atuador " + atuadorId + " não existe"));
            dispositivo.getAtuadores().remove(atuador);
            atuadorRepository.delete(atuador);
        }
        dispositivoRepository.save(dispositivo);
    }

    public List<Sensor> getSensorsByDispositivoId(long id) {
        Dispositivo dispositivo = dispositivoRepository.findById(id).orElse(null);
        if (dispositivo != null) {
            return dispositivo.getSensores();
        }
        return null;
    }

    public List<Atuador> getAtuadoresByDispositivoId(long id) {
        Dispositivo dispositivo = dispositivoRepository.findById(id).orElse(null);
        if (dispositivo != null) {
            return dispositivo.getAtuadores();
        }
        return null;
    }
}
