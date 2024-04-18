package br.edu.utfpr.iotapi.services;

import br.edu.utfpr.iotapi.dto.GetPessoaDTO;
import br.edu.utfpr.iotapi.dto.UpdatePessoaDTO;
import br.edu.utfpr.iotapi.dto.gateway.GetGatewayDTO;
import br.edu.utfpr.iotapi.exceptions.NotFoundException;
import br.edu.utfpr.iotapi.models.Gateway;
import br.edu.utfpr.iotapi.models.Pessoa;
import br.edu.utfpr.iotapi.repository.GatewayRepository;
import br.edu.utfpr.iotapi.repository.PessoaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PessoaService {
    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private GatewayRepository gatewayRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    public void checkPassword(long id, String password) throws NotFoundException, AuthenticationException {
        var res = pessoaRepository.findById(id);

        if (res.isEmpty()) {
            throw new NotFoundException("Pessoa " + id + " não existe");
        }

        var pessoa = res.get();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(pessoa.getEmail(), password));
    }

    public GetPessoaDTO getByEmail(String email) {
        var pessoa = pessoaRepository.findByEmail(email);

        var res = new GetPessoaDTO();

        BeanUtils.copyProperties(pessoa, res);

        return res;
    }

    public List<GetGatewayDTO> getGatewaysByPessoaId(long id) {
        var res = pessoaRepository.findById(id);

        if (res.isPresent()) {
            List<Gateway> gateways = gatewayRepository.findByPessoaId(id);
            return gateways.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    public Pessoa update(long id, UpdatePessoaDTO dto) throws NotFoundException {
        var res = pessoaRepository.findById(id);

        if (res.isEmpty()) {
            throw new NotFoundException("Pessoa " + id + " não existe");
        }
        var pessoa = res.get();

        if (dto.senhaNova() != null && !dto.senhaAntiga().isEmpty()) {
            checkPassword(id, dto.senhaAntiga());
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

    private GetGatewayDTO convertToDTO(Gateway gateway) {
        return new GetGatewayDTO(gateway.getId(), gateway.getNome(), gateway.getDescricao(), gateway.getEndereco());
    }
}
