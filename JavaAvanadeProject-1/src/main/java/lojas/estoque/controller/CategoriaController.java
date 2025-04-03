package lojas.estoque.controller;

import lojas.estoque.model.Categoria;
import lojas.estoque.model.Fornecedor;
import lojas.estoque.services.CategoriaService;
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
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @GetMapping
    public ResponseEntity<List<Categoria>> listarCategorias() {
        return ResponseEntity.ok(categoriaService.listarCategorias());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarCategoriaPorId(@PathVariable Long id) {
        Optional<Categoria> categoria = categoriaService.buscarPorId(id);
        if (categoria.isPresent()) {
            return ResponseEntity.ok(categoria.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria não encontrada.");
        }
    }

    @Operation(
        summary = "Cadastrar nova categoria",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Exemplo de categoria com ID do fornecedor",
            content = @Content(examples = @ExampleObject(value = """
                {
                  "nome": "Categoria Infantil",
                  "fornecedor": { "id": 2 }
                }
            """))
        )
    )
    @PostMapping("/")
    public ResponseEntity<?> cadastrarCategoria(@RequestBody Categoria categoria) {
        if (categoriaService.categoriaExistePorNome(categoria.getNome())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Já existe uma categoria com esse nome.");
        }

        Long fornecedorId = categoria.getFornecedor().getId();
        Fornecedor fornecedor = fornecedorRepository.findById(fornecedorId)
            .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado."));

        categoria.setFornecedor(fornecedor);
        Categoria novaCategoria = categoriaService.salvarCategoria(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaCategoria);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarCategoria(@PathVariable Long id, @RequestBody Categoria categoriaAtualizada) {
        Optional<Categoria> existente = categoriaService.buscarPorId(id);
        if (existente.isPresent()) {
            Categoria categoria = existente.get();
            categoria.setNome(categoriaAtualizada.getNome());

            Long fornecedorId = categoriaAtualizada.getFornecedor().getId();
            Fornecedor fornecedor = fornecedorRepository.findById(fornecedorId)
                .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado."));
            categoria.setFornecedor(fornecedor);

            categoriaService.salvarCategoria(categoria);
            return ResponseEntity.ok(categoria);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria não encontrada.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarCategoria(@PathVariable Long id) {
        boolean deletado = categoriaService.deletarCategoriaPorId(id);
        if (deletado) {
            return ResponseEntity.ok("Categoria deletada com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria não encontrada.");
        }
    }
}
