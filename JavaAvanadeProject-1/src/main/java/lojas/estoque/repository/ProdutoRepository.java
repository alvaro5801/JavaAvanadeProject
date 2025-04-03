package lojas.estoque.repository;

import lojas.estoque.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    boolean existsByNomeIgnoreCase(String nome);
}
