package lojas.estoque.controller;

import lojas.estoque.model.Fornecedor;
import lojas.estoque.repository.FornecedorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/fornecedores")
public class FornecedorController {

    private final FornecedorRepository fornecedorRepository;

    public FornecedorController(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

   
    @GetMapping
    public List<Fornecedor> listarFornecedores() {
        return fornecedorRepository.findAll();
    }

   
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarFornecedorPorId(@PathVariable Long id) {
        Optional<Fornecedor> fornecedor = fornecedorRepository.findById(id);
        if (fornecedor.isPresent()) {
            return ResponseEntity.ok(fornecedor.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("{\"erro\": \"Fornecedor com ID " + id + " não encontrado!\"}");
        }
    }
    
    
    @PostMapping
    public ResponseEntity<?> criarFornecedor(@Valid @RequestBody Fornecedor fornecedor) {
    String nomeLimpo = fornecedor.getNome().trim(); 

    if (fornecedorRepository.existsByNome(nomeLimpo)) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                             .body("{\"erro\": \"O fornecedor já existe!\"}");
    }

    fornecedor.setNome(nomeLimpo); 
    Fornecedor novoFornecedor = fornecedorRepository.save(fornecedor);
    return ResponseEntity.status(HttpStatus.CREATED).body(novoFornecedor);
}


    // 🔁 Atualizar fornecedor
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarFornecedor(@PathVariable Long id, @RequestBody Fornecedor fornecedorAtualizado) {
        Optional<Fornecedor> optionalFornecedor = fornecedorRepository.findById(id);
    
        if (optionalFornecedor.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("{\"erro\": \"Fornecedor com ID " + id + " não encontrado!\"}");
        }
    
        Fornecedor fornecedor = optionalFornecedor.get();
        fornecedor.setNome(fornecedorAtualizado.getNome());
        fornecedorRepository.save(fornecedor);
    
        return ResponseEntity.ok(fornecedor);
    }
    

    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarFornecedor(@PathVariable Long id) {
        if (!fornecedorRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"erro\": \"Fornecedor com ID " + id + " não encontrado para deletar!\"}");
        }

        fornecedorRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
