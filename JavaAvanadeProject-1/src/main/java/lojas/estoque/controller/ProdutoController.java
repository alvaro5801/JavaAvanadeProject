package lojas.estoque.controller;

import lojas.estoque.model.Produto;
import lojas.estoque.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository repository;

    @GetMapping
    public ResponseEntity<List<Produto>> listar() {
        List<Produto> produtos = repository.findAll();
        return ResponseEntity.ok(produtos);
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Produto produto) {
        try {
            Produto salvo = repository.save(produto);
            return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: Produto já existente ou dados inválidos.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Produto produto) {
        Optional<Produto> existente = repository.findById(id);
        if (existente.isPresent()) {
            try {
                Produto atualizado = existente.get();
                atualizado.setNome(produto.getNome());
                atualizado.setPreco(produto.getPreco());
                // Atualize outros campos conforme necessário
                repository.save(atualizado);
                return ResponseEntity.ok(atualizado);
            } catch (DataIntegrityViolationException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: Dados inválidos ou conflito de integridade.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: Produto não encontrado.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remover(@PathVariable Long id) {
        Optional<Produto> existente = repository.findById(id);
        if (existente.isPresent()) {
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: Produto não encontrado.");
        }
    }
}
