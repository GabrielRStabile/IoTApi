package br.edu.utfpr.iotapi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "tb_atuador")
public class Atuador {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "sensor_id")
  private long id;

  @Column(nullable = false)
  private String nome;

  @ManyToOne
  @Column(nullable = false)
  @JoinColumn(name = "dispositivo_id")
  private Dispositivo dispositivo;
}
