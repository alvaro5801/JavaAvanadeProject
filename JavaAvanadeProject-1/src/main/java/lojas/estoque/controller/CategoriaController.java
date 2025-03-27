package lojas.estoque.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lojas.estoque.model.Categoria;
import lojas.estoque.repository.CategoriaRepository;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaRepository categoriaRepository;

    public CategoriaController(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    
    @GetMapping
    public List<Categoria> listarCategorias() {
        return categoriaRepository.findAll();
    }

   
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarCategoriaPorId(@PathVariable Long id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        if (categoria.isPresent()) {
            return ResponseEntity.ok(categoria.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("{\"erro\": \"Categoria com ID " + id + " não encontrada!\"}");
        }
    }

    
    @PostMapping
    public ResponseEntity<?> criarCategoria(@Valid @RequestBody Categoria categoria) {
    String nomeLimpo = categoria.getNome().trim(); 

    if (categoriaRepository.existsByNome(nomeLimpo)) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                             .body("{\"erro\": \"A categoria já existe!\"}");
    }

    categoria.setNome(nomeLimpo); 
    Categoria novaCategoria = categoriaRepository.save(categoria);
    return ResponseEntity.status(HttpStatus.CREATED).body(novaCategoria);
}

    
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarCategoria(@PathVariable Long id, @RequestBody Categoria categoriaAtualizada) {
        Optional<Categoria> optionalCategoria = categoriaRepository.findById(id);

        if (optionalCategoria.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("{\"erro\": \"Categoria com ID " + id + " não encontrada!\"}");
        }

        Categoria categoria = optionalCategoria.get();
        categoria.setNome(categoriaAtualizada.getNome());
        categoriaRepository.save(categoria);

        return ResponseEntity.ok(categoria);
    }

   
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarCategoria(@PathVariable Long id) {
        if (!categoriaRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("{\"erro\": \"Categoria com ID " + id + " não encontrada para deletar!\"}");
        }
        categoriaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
