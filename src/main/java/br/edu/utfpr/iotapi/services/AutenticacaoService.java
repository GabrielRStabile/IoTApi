package br.edu.utfpr.iotapi.services;

import br.edu.utfpr.iotapi.dto.autenticacao.CreatePessoaDTO;
import br.edu.utfpr.iotapi.exceptions.EmailAlreadyRegistered;
import br.edu.utfpr.iotapi.models.FuncaoPessoa;
import br.edu.utfpr.iotapi.models.Pessoa;
import br.edu.utfpr.iotapi.repository.PessoaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {
    @Autowired
    private PessoaRepository pessoaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return pessoaRepository.findByEmail(username);
    }

    public void registrar(CreatePessoaDTO dto) throws EmailAlreadyRegistered {
        var pessoa = new Pessoa();
        BeanUtils.copyProperties(dto, pessoa);

        if (pessoaRepository.findByEmail(pessoa.getEmail()) != null) {
            throw new EmailAlreadyRegistered();
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(pessoa.getPassword());

        pessoa.setSenha(encryptedPassword);

        pessoa.setFuncao(FuncaoPessoa.USUARIO);

        pessoaRepository.save(pessoa);
    }
}
