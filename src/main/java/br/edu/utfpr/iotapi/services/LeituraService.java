package br.edu.utfpr.iotapi.services;

import br.edu.utfpr.iotapi.dto.leitura.CreateLeituraDTO;
import br.edu.utfpr.iotapi.exceptions.NotFoundException;
import br.edu.utfpr.iotapi.models.Leitura;
import br.edu.utfpr.iotapi.repository.LeituraRepository;
import br.edu.utfpr.iotapi.repository.SensorRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeituraService {
    @Autowired
    private LeituraRepository leituraRepository;

    @Autowired
    private SensorRepository sensorRepository;

    public List<Leitura> getAll() {
        return leituraRepository.findAll();
    }

    public Leitura create(CreateLeituraDTO dto) throws NotFoundException {
        Leitura leitura = new Leitura();
        BeanUtils.copyProperties(dto, leitura);

        var sensor = sensorRepository.findById(dto.sensorId()).orElseThrow(
                () -> new NotFoundException("Sensor " + dto.sensorId() + " não existe")
        );

        leitura.setSensor(sensor);
        return leituraRepository.save(leitura);
    }

    public void delete(long id) throws NotFoundException {
        if (!leituraRepository.existsById(id)) {
            throw new NotFoundException("Leitura " + id + " não existe");
        }

        leituraRepository.deleteById(id);
    }

    public Leitura update(Leitura leitura, long id) {
        leitura.setId(id);
        return leituraRepository.save(leitura);
    }

    public Leitura getById(long id) {
        return leituraRepository.findById(id).orElse(null);
    }

    public List<Leitura> getBySensorId(long sensorId) {
        return leituraRepository.findBySensorId(sensorId);
    }
}
