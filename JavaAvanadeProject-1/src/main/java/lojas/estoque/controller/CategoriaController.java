package lojas.estoque.controller;

import lojas.estoque.model.Categoria;
import lojas.estoque.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaRepository repository;

    @GetMapping
    public ResponseEntity<List<Categoria>> listar() {
        List<Categoria> categorias = repository.findAll();
        return ResponseEntity.ok(categorias);
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Categoria categoria) {
        try {
            Categoria salva = repository.save(categoria);
            return ResponseEntity.status(HttpStatus.CREATED).body(salva);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: Categoria já existente ou dados inválidos.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Categoria categoria) {
        Optional<Categoria> existente = repository.findById(id);
        if (existente.isPresent()) {
            try {
                Categoria atualizada = existente.get();
                atualizada.setNome(categoria.getNome());
                // Atualize outros campos conforme necessário
                repository.save(atualizada);
                return ResponseEntity.ok(atualizada);
            } catch (DataIntegrityViolationException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: Dados inválidos ou conflito de integridade.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: Categoria não encontrada.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remover(@PathVariable Long id) {
        Optional<Categoria> existente = repository.findById(id);
        if (existente.isPresent()) {
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: Categoria não encontrada.");
        }
    }
}
