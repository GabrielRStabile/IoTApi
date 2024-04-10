package br.edu.utfpr.iotapi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
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

  @Column(nullable = false)
  private String endereco;

  @ManyToOne
  @JoinColumn(name = "pessoa_id")
  private Pessoa pessoa;
}
