package lojas.estoque.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lojas.estoque.model.Produto;
import lojas.estoque.repository.ProdutoRepository;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

    public void deletar(Long id) {
        if (produtoRepository.existsById(id)) {
            produtoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Produto não encontrado!");
        }
    } 

    public Produto atualizarQuantidade(Long id, Integer novaQuantidade) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado!"));
        produto.setQuantidade(novaQuantidade);
        return produtoRepository.save(produto);
    }
}
