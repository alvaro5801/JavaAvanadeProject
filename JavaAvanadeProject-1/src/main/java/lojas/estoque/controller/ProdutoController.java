package lojas.estoque.controller;

import lojas.estoque.model.Produto;
import lojas.estoque.Services.ProdutoService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PutMapping("/{id}/quantidade")
    public ResponseEntity<Produto> atualizarQuantidade(@PathVariable Long id, @RequestParam Integer quantidade) {
    Produto produtoAtualizado = produtoService.atualizarQuantidade(id, quantidade);
        return ResponseEntity.ok(produtoAtualizado);
}


    @GetMapping
    public List<Produto> listarTodos() {
        return produtoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
    Optional<Produto> produto = produtoService.buscarPorId(id);
    if (produto.isPresent()) {
        return ResponseEntity.ok(produto.get());
    } else {
        return ResponseEntity.notFound().build();
    }
}

    @PostMapping
    public ResponseEntity<Produto> salvar(@RequestBody Produto produto) {
    Produto novoProduto = produtoService.salvar(produto);
    return ResponseEntity.status(201).body(novoProduto);
}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
    produtoService.deletar(id);
    return ResponseEntity.noContent().build();
}
}
