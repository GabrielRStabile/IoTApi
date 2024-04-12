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
@Table(name = "tb_dispositivo")
public class Dispositivo {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "dispositivo_id")
  private long id;

  @Column(nullable = false)
  private String nome;

  @Column(nullable = false)
  private String descricao;

  private String local;

  @Column(nullable = false)
  private String endereco;

  @ManyToOne
  @Column(nullable = false)
  @JoinColumn(name = "gateway_id")
  private Gateway gateway;
}
