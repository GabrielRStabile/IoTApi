package br.edu.utfpr.iotapi.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_sensor")
public class Sensor {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "sensor_id")
  private long id;

  @Column(nullable = false)
  private String nome;

  @Column(nullable = false)
  private String tipo;

  @ManyToOne
  @JoinColumn(name = "dispositivo_id", nullable = false)
  private Dispositivo dispositivo;

  @Column(nullable = false)
  @OneToMany(mappedBy = "sensor")
  private List<Leitura> leituras;
}
