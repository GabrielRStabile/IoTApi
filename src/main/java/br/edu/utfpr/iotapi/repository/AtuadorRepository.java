package br.edu.utfpr.iotapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.iotapi.models.Atuador;

public interface AtuadorRepository extends JpaRepository<Atuador, Long> {

}
