package lojas.estoque.controller;

import lojas.estoque.model.Fornecedor;
import lojas.estoque.repository.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {

    @Autowired
    private FornecedorRepository repository;

    @GetMapping
    public ResponseEntity<List<Fornecedor>> listar() {
        List<Fornecedor> fornecedores = repository.findAll();
        return ResponseEntity.ok(fornecedores);
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Fornecedor fornecedor) {
        try {
            Fornecedor salvo = repository.save(fornecedor);
            return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: Fornecedor já existente ou dados inválidos.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Fornecedor fornecedor) {
        Optional<Fornecedor> existente = repository.findById(id);
        if (existente.isPresent()) {
            try {
                Fornecedor atualizado = existente.get();
                atualizado.setNome(fornecedor.getNome());
                atualizado.setContato(fornecedor.getContato());
               
                repository.save(atualizado);
                return ResponseEntity.ok(atualizado);
            } catch (DataIntegrityViolationException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: Dados inválidos ou conflito de integridade.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: Fornecedor não encontrado.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remover(@PathVariable Long id) {
        Optional<Fornecedor> existente = repository.findById(id);
        if (existente.isPresent()) {
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body
::contentReference[oaicite:0]{index=0}
 
