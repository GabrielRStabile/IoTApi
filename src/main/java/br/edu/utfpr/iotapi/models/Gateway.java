package br.edu.utfpr.iotapi.models;

import java.util.List;

import jakarta.persistence.CascadeType;
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
@Table(name = "tb_gateway")
public class Gateway {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "gateway_id")
  private long id;

  @Column(nullable = false)
  private String nome;

  @Column(nullable = false)
  private String descricao;

  @Column(nullable = false, unique = true)
  private String endereco;

  @ManyToOne
  @JoinColumn(name = "pessoa_id")
  private Pessoa pessoa;

  @Column(nullable = false)
  @OneToMany(mappedBy = "gateway", cascade = CascadeType.ALL)
  private List<Dispositivo> dispositivos;
}
