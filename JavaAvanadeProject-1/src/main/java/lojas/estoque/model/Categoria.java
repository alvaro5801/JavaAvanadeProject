package lojas.estoque.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome da categoria é obrigatório.")
    @Size(min = 3, message = "O nome da categoria deve ter pelo menos 3 caracteres.")
    @Column(nullable = false)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "fornecedor_id", nullable = false)
    @JsonIgnoreProperties({"categorias", "produtos"})
    private Fornecedor fornecedor;

    @OneToMany(mappedBy = "categoria")
    @JsonIgnoreProperties("categoria") // evita recursão no JSON
    private List<Produto> produto;

    public Categoria() {}

    public Categoria(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public List<Produto> getProduto() {
        return produto;
    }

    public void setProduto(List<Produto> produto) {
        this.produto = produto;
    }
}
