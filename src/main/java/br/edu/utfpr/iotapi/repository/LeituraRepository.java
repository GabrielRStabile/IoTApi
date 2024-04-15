package br.edu.utfpr.iotapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.iotapi.models.Leitura;

import java.util.List;

public interface LeituraRepository extends JpaRepository<Leitura, Long> {
    List<Leitura> findBySensorId(long sensorId);
}
