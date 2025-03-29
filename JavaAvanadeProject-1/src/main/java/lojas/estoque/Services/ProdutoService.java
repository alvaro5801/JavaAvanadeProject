package lojas.estoque.Services;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import lojas.estoque.model.Produto;
import lojas.estoque.repository.ProdutoRepository;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto salvarProduto(Produto produto) {
        if (produtoRepository.existsByNome(produto.getNome())) {
            throw new IllegalArgumentException("Já existe um produto com o nome: " + produto.getNome());
        }
        return produtoRepository.save(produto);
    }

    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }
    
    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }


    public void deletar(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado!"));

        Categoria categoria = produto.getCategoria();
        Fornecedor fornecedor = produto.getFornecedor();

        produtoRepository.delete(produto);

        // Se não restaram produtos na categoria, deletar a categoria
        if (categoria != null && !produtoRepository.existsByCategoria(categoria)) {
            categoriaRepository.deleteById(categoria.getId());

            // Se não restaram categorias no fornecedor, deletar o fornecedor
            if (fornecedor != null && !categoriaRepository.existsByFornecedor(fornecedor)) {
                fornecedorRepository.deleteById(fornecedor.getId());
            }
        }
    }

    public Produto atualizarQuantidade(Long id, Integer novaQuantidade) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado!"));
        produto.setQuantidade(novaQuantidade);
        return produtoRepository.save(produto);
    }
}
