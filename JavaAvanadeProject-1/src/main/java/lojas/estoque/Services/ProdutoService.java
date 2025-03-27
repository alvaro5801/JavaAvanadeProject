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
        // Normaliza o nome removendo espaços extras e colocando tudo em minúsculo
        String nomeNormalizado = produto.getNome().trim().toLowerCase();

        // Verifica se já existe um produto com o nome normalizado
        boolean existe = produtoRepository.findAll().stream()
            .map(p -> p.getNome().trim().toLowerCase())
            .anyMatch(nome -> nome.equals(nomeNormalizado));

        if (existe) {
            throw new IllegalArgumentException("Já existe um produto com o nome: " + produto.getNome());
        }

        // Padroniza o nome com espaços ajustados antes de salvar
        produto.setNome(produto.getNome().trim());
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
