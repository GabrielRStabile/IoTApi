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
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
  @JoinColumn(name = "gateway_id", nullable = false)
  private Gateway gateway;

  @Column(nullable = false)
  @OneToMany(mappedBy = "dispositivo")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private List<Sensor> sensores;

  @Column(nullable = false)
  @OneToMany(mappedBy = "dispositivo")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private List<Atuador> atuadores;
}
