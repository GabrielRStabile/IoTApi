package br.edu.utfpr.iotapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.iotapi.models.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}
