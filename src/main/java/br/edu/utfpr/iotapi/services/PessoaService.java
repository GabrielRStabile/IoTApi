package br.edu.utfpr.iotapi.services;

import br.edu.utfpr.iotapi.dto.CreatePessoaDTO;
import br.edu.utfpr.iotapi.dto.UpdatePessoaDTO;
import br.edu.utfpr.iotapi.exceptions.NotFoundException;
import br.edu.utfpr.iotapi.exceptions.WrongPasswordException;
import br.edu.utfpr.iotapi.models.Pessoa;
import br.edu.utfpr.iotapi.repository.PessoaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {
    @Autowired
    private PessoaRepository pessoaRepository;

    public List<Pessoa> getAll() {
        return pessoaRepository.findAll();
    }

    public void checkPassword(long id, String password) throws NotFoundException, WrongPasswordException {
        var res = pessoaRepository.findById(id);

        if (res.isEmpty()) {
            throw new NotFoundException("Pessoa " + id + " não existe");
        }

        var pessoa = res.get();

        if (!pessoa.getSenha().equals(password)) {
            throw new WrongPasswordException();
        }

    }

    public Optional<Pessoa> getById(long id) {
        return pessoaRepository.findById(id);
    }

    public Pessoa create(CreatePessoaDTO dto) {
        var pessoa = new Pessoa();
        BeanUtils.copyProperties(dto, pessoa);

        return pessoaRepository.save(pessoa);
    }

    public Pessoa update(long id, UpdatePessoaDTO dto) throws NotFoundException, WrongPasswordException {
        var res = pessoaRepository.findById(id);

        if (res.isEmpty()) {
            throw new NotFoundException("Pessoa " + id + " não existe");
        }
        var pessoa = res.get();

        if (dto.senhaNova() != null && !dto.senhaAntiga().isEmpty()) {
            if (!pessoa.getSenha().equals(dto.senhaAntiga())) {
                throw new WrongPasswordException();
            }
            pessoa.setSenha(dto.senhaNova());
        }

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
