package lojas.estoque.repository;

import lojas.estoque.model.Produto;
import lojas.estoque.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;



@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    boolean existsByNome(String nome);
    boolean existsByCategoria(Categoria categoria);
    Optional<Produto> findByNome(String nome);

    @Transactional
    @Modifying
    @Query("UPDATE Produto p SET p.quantidade = :quantidade WHERE p.id = :id")
    void atualizarQuantidade(Long id, Integer quantidade);
}
