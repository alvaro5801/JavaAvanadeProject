package lojas.estoque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import lojas.estoque.model.Fornecedor;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
    boolean existsByNome(String nome);

}
