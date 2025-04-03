package lojas.estoque.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

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

    @NotBlank(message = "O nome fantasia é obrigatório.")
    private String nomeFantasia;

    @Pattern(regexp = "\\d{14}", message = "CNPJ deve conter 14 dígitos numéricos.")
    private String cnpj;

    @Email(message = "E-mail inválido.")
    private String email;

    @NotBlank(message = "O telefone é obrigatório.")
    private String telefone;

    @NotBlank(message = "O endereço é obrigatório.")
    private String endereco;

    @OneToMany(mappedBy = "fornecedor")
    @JsonIgnore
    private List<Produto> produtos;

    @OneToMany(mappedBy = "fornecedor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Categoria> categorias;

    public Fornecedor() {}

    public Fornecedor(String nome) {
        this.nome = nome;
    }

    // Getters e Setters

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

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
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
