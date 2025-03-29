package lojas.estoque.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import lojas.estoque.model.Categoria;
import lojas.estoque.model.Fornecedor;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Optional<Categoria> findByNome(String nome);
    //boolean existsByNome(String nome);
    boolean existsByFornecedor(Fornecedor fornecedor);
}
