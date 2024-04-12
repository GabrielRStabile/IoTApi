package br.edu.utfpr.iotapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.iotapi.models.Gateway;
import java.util.List;

public interface GatewayRepository extends JpaRepository<Gateway, Long> {

  List<Gateway> findByPessoaId(long id);

}
