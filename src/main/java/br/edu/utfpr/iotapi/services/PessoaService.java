package br.edu.utfpr.iotapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.utfpr.iotapi.dto.PessoaDTO;
import br.edu.utfpr.iotapi.exceptions.NotFoundException;
import br.edu.utfpr.iotapi.models.Pessoa;
import br.edu.utfpr.iotapi.repository.PessoaRepository;

@Service
public class PessoaService {
  @Autowired
  private PessoaRepository pessoaRepository;

  public List<Pessoa> getAll() {
    return pessoaRepository.findAll();
  }

  public Optional<Pessoa> getById(long id) {
    return pessoaRepository.findById(id);
  }

  // Inserir pessoa no db
  public Pessoa create(PessoaDTO dto) {
    var pessoa = new Pessoa();
    BeanUtils.copyProperties(dto, pessoa);

    // Persistir no db
    return pessoaRepository.save(pessoa);
  }

  public Pessoa update(PessoaDTO dto, long id) throws NotFoundException {
    var res = pessoaRepository.findById(id);

    if (res.isEmpty()) {
      throw new NotFoundException("Pessoa " + id + " não existe");
    }

    var pessoa = res.get();
    pessoa.setNome(dto.nome());
    pessoa.setEmail(dto.email());

    return pessoaRepository.save(pessoa);
  }

  public void delete(long id) throws NotFoundException {
    var res = pessoaRepository.findById(id);

    if (res.isEmpty()) {
      throw new NotFoundException("Pessoa " + id + " não existe");
    }

    pessoaRepository.deleteById(id);
  }
}
