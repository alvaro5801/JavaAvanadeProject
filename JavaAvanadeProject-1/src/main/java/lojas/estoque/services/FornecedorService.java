package lojas.estoque.services;

import lojas.estoque.model.Fornecedor;
import lojas.estoque.repository.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FornecedorService {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    public List<Fornecedor> listarFornecedores() {
        return fornecedorRepository.findAll();
    }

    public Optional<Fornecedor> buscarPorId(Long id) {
        return fornecedorRepository.findById(id);
    }

    public boolean fornecedorExistePorNome(String nome) {
        return fornecedorRepository.existsByNomeIgnoreCase(nome);
    }

    public Fornecedor salvarFornecedor(Fornecedor fornecedor) {
        return fornecedorRepository.save(fornecedor);
    }

    public boolean deletarFornecedorPorId(Long id) {
        if (fornecedorRepository.existsById(id)) {
            fornecedorRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
