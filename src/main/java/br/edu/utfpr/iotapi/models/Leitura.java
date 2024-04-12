package br.edu.utfpr.iotapi.models;

import java.util.Date;

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
@Table(name = "tb_leitura")
public class Leitura {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "leitura_id")
  private long id;

  @Column(nullable = false)
  private double valor;

  @Column(nullable = false)
  private Date data;

  @Column(nullable = false)
  @ManyToOne
  @JoinColumn(name = "sensor_id")
  private Sensor sensor;
}
