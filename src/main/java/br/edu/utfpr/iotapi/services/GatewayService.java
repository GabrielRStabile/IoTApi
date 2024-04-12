package br.edu.utfpr.iotapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.utfpr.iotapi.dto.GatewayDTO;
import br.edu.utfpr.iotapi.exceptions.NotFoundException;
import br.edu.utfpr.iotapi.models.Gateway;
import br.edu.utfpr.iotapi.repository.GatewayRepository;

@Service
public class GatewayService {
  @Autowired
  private GatewayRepository gatewayRepository;

  public List<Gateway> getAll() {
    return gatewayRepository.findAll();
  }

  public Optional<Gateway> getById(long id) {
    return gatewayRepository.findById(id);
  }

  public Gateway create(GatewayDTO dto) {
    var gateway = new Gateway();
    BeanUtils.copyProperties(dto, gateway);

    return gatewayRepository.save(gateway);
  }

  public Gateway update(GatewayDTO dto, long id) throws NotFoundException {
    var res = gatewayRepository.findById(id);

    if (res.isEmpty())
      throw new NotFoundException("Gateway " + id + " não existe");

    var gateway = res.get();
    gateway.setNome(dto.nome());
    gateway.setEndereco(dto.endereco());
    gateway.setDescricao(dto.descricao());
    gateway.setPessoa(dto.pessoa());

    return gatewayRepository.save(gateway);
  }

  public void delete(long id) throws NotFoundException {
    var res = gatewayRepository.findById(id);

    if (res.isEmpty())
      throw new NotFoundException("Gateway " + id + " não existe");

    gatewayRepository.deleteById(id);
  }
}
