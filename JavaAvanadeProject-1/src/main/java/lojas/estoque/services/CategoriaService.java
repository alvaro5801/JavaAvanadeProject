package lojas.estoque.services;

import lojas.estoque.model.Categoria;
import lojas.estoque.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> listarCategorias() {
        return categoriaRepository.findAll();
    }

    public Optional<Categoria> buscarPorId(Long id) {
        return categoriaRepository.findById(id);
    }

    public boolean categoriaExistePorNome(String nome) {
        return categoriaRepository.existsByNomeIgnoreCase(nome);
    }

    public Categoria salvarCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public boolean deletarCategoriaPorId(Long id) {
        if (categoriaRepository.existsById(id)) {
            categoriaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
