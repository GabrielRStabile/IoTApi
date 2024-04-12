package br.edu.utfpr.iotapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.iotapi.models.Gateway;

public interface GatewayRepository extends JpaRepository<Gateway, Long> {

}
