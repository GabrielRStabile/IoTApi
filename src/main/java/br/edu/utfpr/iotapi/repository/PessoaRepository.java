package br.edu.utfpr.iotapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.iotapi.models.Pessoa;
import org.springframework.security.core.userdetails.UserDetails;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    UserDetails findByEmail(String email);
}
