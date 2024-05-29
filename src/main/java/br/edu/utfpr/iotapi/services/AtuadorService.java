package br.edu.utfpr.iotapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.utfpr.iotapi.dto.atuador.CreateAtuadorDTO;
import br.edu.utfpr.iotapi.dto.atuador.GetAtuadorDTO;
import br.edu.utfpr.iotapi.exceptions.NotFoundException;
import br.edu.utfpr.iotapi.models.Atuador;
import br.edu.utfpr.iotapi.repository.AtuadorRepository;
import br.edu.utfpr.iotapi.repository.DispositivoRepository;

@Service
public class AtuadorService {
  @Autowired
  private AtuadorRepository atuadorRepository;

  @Autowired
  private DispositivoRepository dispositivoRepository;

  public Optional<GetAtuadorDTO> getById(long id) throws NotFoundException {
    var res = atuadorRepository.findById(id);
    if (res.isPresent()) {
      var atuador = res.get();

      GetAtuadorDTO dto = new GetAtuadorDTO(atuador.getId(), atuador.getNome());
      return Optional.of(dto);
    } else {
      throw new NotFoundException("Atuador " + id + " não existe");
    }
  }

  public List<Atuador> getAll() {
    return atuadorRepository.findAll();
  }

  public Atuador create(CreateAtuadorDTO dto) {
    var atuador = new Atuador();
    BeanUtils.copyProperties(dto, atuador);

    return atuadorRepository.save(atuador);
  }

  public Atuador update(CreateAtuadorDTO dto, long id) throws NotFoundException {
    var res = atuadorRepository.findById(id);

    if (res.isEmpty()) {
      throw new NotFoundException("Atuador " + id + " não existe");
    }
    var atuador = res.get();

    atuador.setNome(dto.nome());

    return atuadorRepository.save(atuador);
  }

  public void deleteById(long id) throws NotFoundException {
    var res = atuadorRepository.findById(id);

    if (res.isEmpty()) {
      throw new NotFoundException("Atuador " + id + " não existe");
    }
    var atuador = res.get();

    var dispositivo = atuador.getDispositivo();
    if (dispositivo != null) {
      dispositivo.getAtuadores().remove(atuador);
      dispositivoRepository.save(dispositivo);
    }

    atuadorRepository.delete(atuador);
  }
}
