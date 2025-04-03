package lojas.estoque.controller;

import lojas.estoque.model.Fornecedor;
import lojas.estoque.services.FornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {

    @Autowired
    private FornecedorService fornecedorService;

    @GetMapping
    public ResponseEntity<List<Fornecedor>> listarFornecedores() {
        return ResponseEntity.ok(fornecedorService.listarFornecedores());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarFornecedorPorId(@PathVariable Long id) {
        Optional<Fornecedor> fornecedor = fornecedorService.buscarPorId(id);
        if (fornecedor.isPresent()) {
            return ResponseEntity.ok(fornecedor.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fornecedor não encontrado.");
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> cadastrarFornecedor(@RequestBody Fornecedor fornecedor) {
        if (fornecedorService.fornecedorExistePorNome(fornecedor.getNome())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Já existe um fornecedor com esse nome.");
        }
        Fornecedor novo = fornecedorService.salvarFornecedor(fornecedor);
        return ResponseEntity.status(HttpStatus.CREATED).body(novo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarFornecedor(@PathVariable Long id, @RequestBody Fornecedor atualizado) {
        Optional<Fornecedor> existente = fornecedorService.buscarPorId(id);
        if (existente.isPresent()) {
            Fornecedor fornecedor = existente.get();
            fornecedor.setNome(atualizado.getNome());
            fornecedorService.salvarFornecedor(fornecedor);
            return ResponseEntity.ok(fornecedor);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fornecedor não encontrado.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarFornecedor(@PathVariable Long id) {
        boolean deletado = fornecedorService.deletarFornecedorPorId(id);
        if (deletado) {
            return ResponseEntity.ok("Fornecedor deletado com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fornecedor não encontrado.");
        }
    }
}
