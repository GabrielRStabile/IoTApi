package br.edu.utfpr.iotapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.iotapi.models.Sensor;

public interface SensorRepository extends JpaRepository<Sensor, Long> {

}
