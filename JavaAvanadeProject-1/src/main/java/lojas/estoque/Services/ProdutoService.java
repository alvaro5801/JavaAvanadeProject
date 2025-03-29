package lojas.estoque.Services;

import lojas.estoque.model.Categoria;
import lojas.estoque.model.Fornecedor;
import lojas.estoque.model.Produto;
import lojas.estoque.repository.CategoriaRepository;
import lojas.estoque.repository.FornecedorRepository;
import lojas.estoque.repository.ProdutoRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final FornecedorRepository fornecedorRepository;

    public ProdutoService(ProdutoRepository produtoRepository,
                          CategoriaRepository categoriaRepository,
                          FornecedorRepository fornecedorRepository) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
        this.fornecedorRepository = fornecedorRepository;
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

        if (categoria != null && !produtoRepository.existsByCategoria(categoria)) {
            categoriaRepository.deleteById(categoria.getId());

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
