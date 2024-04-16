package br.edu.utfpr.iotapi.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.utfpr.iotapi.dto.gateway.CreateGatewayDTO;
import br.edu.utfpr.iotapi.dto.gateway.GetGatewayDTO;
import br.edu.utfpr.iotapi.exceptions.NotFoundException;
import br.edu.utfpr.iotapi.models.Dispositivo;
import br.edu.utfpr.iotapi.models.Gateway;
import br.edu.utfpr.iotapi.repository.DispositivoRepository;
import br.edu.utfpr.iotapi.repository.GatewayRepository;
import br.edu.utfpr.iotapi.repository.PessoaRepository;

@Service
public class GatewayService {
  @Autowired
  private GatewayRepository gatewayRepository;

  @Autowired
  private PessoaRepository pessoaRepository;

  @Autowired
  private DispositivoRepository dispositivoRepository;
  // public List<Gateway> getAll() {
  // return gatewayRepository.findAll();
  // }

  public Optional<GetGatewayDTO> getById(long id) throws NotFoundException {
    var res = gatewayRepository.findById(id);
    if (res.isPresent()) {
      return Optional.of(convertToDTO(res.get()));
    } else {
      throw new NotFoundException("Gateway " + id + " não existe");
    }
  }

  public Gateway create(CreateGatewayDTO dto) {
    var gateway = new Gateway();
    BeanUtils.copyProperties(dto, gateway);

    var pessoa = pessoaRepository.findById(dto.pessoaId());
    gateway.setPessoa(pessoa.get());

    return gatewayRepository.save(gateway);
  }

  public Gateway update(CreateGatewayDTO dto, long id) throws NotFoundException {
    var res = gatewayRepository.findById(id);

    if (res.isEmpty())
      throw new NotFoundException("Gateway " + id + " não existe");

    var gateway = res.get();

    BeanUtils.copyProperties(dto, gateway);

    var pessoa = pessoaRepository.findById(dto.pessoaId());
    if (!pessoa.isEmpty()) {
      gateway.setPessoa(pessoa.get());
    }
    gateway.setNome(dto.nome());
    gateway.setEndereco(dto.endereco());
    gateway.setDescricao(dto.descricao());

    return gatewayRepository.save(gateway);
  }

  public void addDispositivos(long gatewayId, List<Long> dispositivosIds) throws NotFoundException {
    var gateway = gatewayRepository.findById(gatewayId);

    if (gateway.isEmpty())
      throw new NotFoundException("Gateway " + gatewayId + " não existe");

    List<Dispositivo> dispositivos = dispositivoRepository.findAllById(dispositivosIds);

    for (Dispositivo dispositivo : dispositivos) {
      dispositivo.setGateway(gateway.get());
    }

    dispositivoRepository.saveAll(dispositivos);
  }

  public void delete(long id) throws NotFoundException {
    if (!gatewayRepository.existsById(id)) {
      throw new NotFoundException("Gateway " + id + " não existe");
    }

    gatewayRepository.deleteById(id);
  }

  private GetGatewayDTO convertToDTO(Gateway gateway) {
    return new GetGatewayDTO(gateway.getId(), gateway.getNome(), gateway.getDescricao(),
        gateway.getEndereco());
  }
}
