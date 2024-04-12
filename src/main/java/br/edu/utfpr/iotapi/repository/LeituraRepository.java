package br.edu.utfpr.iotapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.iotapi.models.Leitura;

public interface LeituraRepository extends JpaRepository<Leitura, Long> {

}
