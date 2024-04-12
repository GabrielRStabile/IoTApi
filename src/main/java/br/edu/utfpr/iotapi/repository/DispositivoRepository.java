package br.edu.utfpr.iotapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.iotapi.models.Dispositivo;

public interface DispositivoRepository extends JpaRepository<Dispositivo, Long> {

}
