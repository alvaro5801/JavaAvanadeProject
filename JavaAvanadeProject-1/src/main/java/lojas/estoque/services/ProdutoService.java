package lojas.estoque.services;

import lojas.estoque.model.Categoria;
import lojas.estoque.model.Fornecedor;
import lojas.estoque.model.Produto;
import lojas.estoque.repository.CategoriaRepository;
import lojas.estoque.repository.FornecedorRepository;
import lojas.estoque.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    public boolean produtoExistePorNome(String nome) {
        return produtoRepository.existsByNomeIgnoreCase(nome);
    }

    public Produto salvarProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    public boolean deletarProdutoPorId(Long id) {
        if (produtoRepository.existsById(id)) {
            produtoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Categoria buscarCategoriaPorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada."));
    }

    public Fornecedor buscarFornecedorPorId(Long id) {
        return fornecedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado."));
    }
}
