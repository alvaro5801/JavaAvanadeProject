package lojas.estoque.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "fornecedores")
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do fornecedor é obrigatório.")
    @Size(min = 3, message = "O nome do fornecedor deve ter pelo menos 3 caracteres.")
    @Column(nullable = false)
    private String nome;

    @OneToMany(mappedBy = "fornecedor")
    @JsonIgnore
    private List<Produto> produtos;

    // ✅ Adicionado: categorias pertencentes a este fornecedor
    @OneToMany(mappedBy = "fornecedor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Categoria> categorias;

    public Fornecedor() {}

    public Fornecedor(String nome) {
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

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }
}
