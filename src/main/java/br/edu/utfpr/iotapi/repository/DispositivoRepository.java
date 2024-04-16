package br.edu.utfpr.iotapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.iotapi.models.Dispositivo;
import java.util.List;

public interface DispositivoRepository extends JpaRepository<Dispositivo, Long> {
  List<Dispositivo> findByGatewayId(long id);
}
