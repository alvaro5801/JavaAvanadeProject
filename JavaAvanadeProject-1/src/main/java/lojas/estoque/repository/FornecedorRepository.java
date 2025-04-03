package lojas.estoque.repository;

import lojas.estoque.model.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
    boolean existsByNomeIgnoreCase(String nome);
}
