package lojas.estoque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lojas.estoque.model.Produto;


@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    @Transactional
    @Modifying
    @Query("UPDATE Produto p SET p.quantidade = :quantidade WHERE p.id = :id")
    boolean existsByNome(String nome);
    void atualizarQuantidade(Long id, Integer quantidade);
}
