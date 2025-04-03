package lojas.estoque.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    @Column(nullable = false)
    private Integer quantidade; 

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    @JsonIgnoreProperties("produto") // evita recursão infinita
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "fornecedor_id", nullable = false)
    @JsonIgnoreProperties({"fornecedor"}) // evita expansão no Swagger
    private Fornecedor fornecedor;

    // Construtores
    public Produto() {}

    public Produto(String nome, BigDecimal preco, Integer quantidade, Categoria categoria, Fornecedor fornecedor) {
        this(null, nome, preco, quantidade, categoria, fornecedor);
    }

    public Produto(Long id, String nome, BigDecimal preco, Integer quantidade, Categoria categoria, Fornecedor fornecedor) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        this.categoria = categoria;
        this.fornecedor = fornecedor;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }

    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    public Fornecedor getFornecedor() { return fornecedor; }
    public void setFornecedor(Fornecedor fornecedor) { this.fornecedor = fornecedor; }
}
