package lojas.estoque.controller;

import lojas.estoque.model.Produto;
import lojas.estoque.model.Categoria;
import lojas.estoque.model.Fornecedor;
import lojas.estoque.services.ProdutoService;
import lojas.estoque.repository.CategoriaRepository;
import lojas.estoque.repository.FornecedorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @GetMapping
    public ResponseEntity<List<Produto>> listarProdutos() {
        return ResponseEntity.ok(produtoService.listarProdutos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarProdutoPorId(@PathVariable Long id) {
        Optional<Produto> produto = produtoService.buscarPorId(id);
        if (produto.isPresent()) {
            return ResponseEntity.ok(produto.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado.");
        }
    }

    @Operation(
        summary = "Cadastrar novo produto",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Exemplo com ID de categoria e fornecedor",
            content = @Content(examples = @ExampleObject(value = """
                {
                  "nome": "Tinta Azul",
                  "preco": 19.99,
                  "quantidade": 100,
                  "categoria": { "id": 1 },
                  "fornecedor": { "id": 2 }
                }
            """))
        )
    )
    @PostMapping("/")
    public ResponseEntity<?> cadastrarProduto(@RequestBody Produto produto) {
        if (produtoService.produtoExistePorNome(produto.getNome())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Já existe um produto com esse nome.");
        }

        Categoria categoria = categoriaRepository.findById(produto.getCategoria().getId())
            .orElseThrow(() -> new RuntimeException("Categoria não encontrada."));
        Fornecedor fornecedor = fornecedorRepository.findById(produto.getFornecedor().getId())
            .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado."));

        produto.setCategoria(categoria);
        produto.setFornecedor(fornecedor);

        Produto novoProduto = produtoService.salvarProduto(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProduto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarProduto(@PathVariable Long id, @RequestBody Produto atualizado) {
        Optional<Produto> existente = produtoService.buscarPorId(id);
        if (existente.isPresent()) {
            Produto produto = existente.get();
            produto.setNome(atualizado.getNome());
            produto.setPreco(atualizado.getPreco());
            produto.setQuantidade(atualizado.getQuantidade());

            Categoria categoria = categoriaRepository.findById(atualizado.getCategoria().getId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada."));
            Fornecedor fornecedor = fornecedorRepository.findById(atualizado.getFornecedor().getId())
                .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado."));

            produto.setCategoria(categoria);
            produto.setFornecedor(fornecedor);

            produtoService.salvarProduto(produto);
            return ResponseEntity.ok(produto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarProduto(@PathVariable Long id) {
        boolean deletado = produtoService.deletarProdutoPorId(id);
        if (deletado) {
            return ResponseEntity.ok("Produto deletado com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado.");
        }
    }
}
