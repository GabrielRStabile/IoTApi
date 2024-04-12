package br.edu.utfpr.iotapi.services;

import java.util.List;
import java.util.Optional;

import org.checkerframework.checker.units.qual.t;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.utfpr.iotapi.dto.CreateGatewayDTO;
import br.edu.utfpr.iotapi.exceptions.NotFoundException;
import br.edu.utfpr.iotapi.models.Gateway;
import br.edu.utfpr.iotapi.repository.GatewayRepository;
import br.edu.utfpr.iotapi.repository.PessoaRepository;

@Service
public class GatewayService {
  @Autowired
  private GatewayRepository gatewayRepository;

  @Autowired
  private PessoaRepository pessoaRepository;

  public List<Gateway> getAll() {
    return gatewayRepository.findAll();
  }

  public Optional<Gateway> getById(long id) {
    return gatewayRepository.findById(id);
  }

  public Gateway create(CreateGatewayDTO dto) {
    var gateway = new Gateway();
    BeanUtils.copyProperties(dto, gateway);

    var pessoa = pessoaRepository.findById(dto.pessoaId()).get();
    gateway.setPessoa(pessoa);

    return gatewayRepository.save(gateway);
  }

  public Gateway update(CreateGatewayDTO dto, long id) throws NotFoundException {
    var res = gatewayRepository.findById(id);

    if (res.isEmpty())
      throw new NotFoundException("Gateway " + id + " não existe");

    var gateway = res.get();

    var pessoa = pessoaRepository.findById(dto.pessoaId());
    if (pessoa.isEmpty())
      throw new NotFoundException("Pessoa " + dto.pessoaId() + " não existe");

    gateway.setNome(dto.nome());
    gateway.setEndereco(dto.endereco());
    gateway.setDescricao(dto.descricao());
    gateway.setPessoa(pessoa.get());

    return gatewayRepository.save(gateway);
  }

  public void delete(long id) throws NotFoundException {
    var res = gatewayRepository.findById(id);

    if (res.isEmpty())
      throw new NotFoundException("Gateway " + id + " não existe");

    gatewayRepository.deleteById(id);
  }
}
