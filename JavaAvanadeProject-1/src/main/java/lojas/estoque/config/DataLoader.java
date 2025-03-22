package lojas.estoque.config;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lojas.estoque.model.Categoria;
import lojas.estoque.model.Fornecedor;
import lojas.estoque.model.Produto;
import lojas.estoque.repository.CategoriaRepository;
import lojas.estoque.repository.FornecedorRepository;
import lojas.estoque.repository.ProdutoRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(CategoriaRepository categoriaRepository, 
                               FornecedorRepository fornecedorRepository, 
                               ProdutoRepository produtoRepository) {
        return args -> {
            // Criando várias Categorias
            Categoria categoriaCafe = categoriaRepository.save(new Categoria("Cafés"));
            Categoria categoriaCha = categoriaRepository.save(new Categoria("Chás"));
            Categoria categoriaAchocolatado = categoriaRepository.save(new Categoria("Achocolatados"));
            Categoria categoriaBatom = categoriaRepository.save(new Categoria("Batons"));
            Categoria categoriaBase = categoriaRepository.save(new Categoria("Bases de Maquiagem"));
            Categoria categoriaMascara = categoriaRepository.save(new Categoria("Máscaras de Cílios"));

            System.out.println("📌 Categoria Café ID: " + categoriaCafe.getId());
            System.out.println("📌 Categoria Chá ID: " + categoriaCha.getId());
            System.out.println("📌 Categoria Achocolatado ID: " + categoriaAchocolatado.getId());
            System.out.println("📌 Categoria Batom ID: " + categoriaBatom.getId());
            System.out.println("📌 Categoria Base ID: " + categoriaBase.getId());
            System.out.println("📌 Categoria Máscara ID: " + categoriaMascara.getId());

            // Criando Fornecedores
            Fornecedor fornecedorBebidas = fornecedorRepository.save(new Fornecedor("Fornecedor XYZ"));
            Fornecedor fornecedorMaquiagem = fornecedorRepository.save(new Fornecedor("Fornecedor Beauty Pro"));

            System.out.println("📌 Fornecedor Bebidas ID: " + fornecedorBebidas.getId());
            System.out.println("📌 Fornecedor Maquiagem ID: " + fornecedorMaquiagem.getId());

            // Criando Produtos com Categorias diferentes
            produtoRepository.save(new Produto("Café Gourmet", BigDecimal.valueOf(25.00), 100, categoriaCafe, fornecedorBebidas));
            produtoRepository.save(new Produto("Chá Preto", BigDecimal.valueOf(12.50), 150, categoriaCha, fornecedorBebidas));
            produtoRepository.save(new Produto("Achocolatado", BigDecimal.valueOf(18.00), 200, categoriaAchocolatado, fornecedorBebidas));

            produtoRepository.save(new Produto("Batom Vermelho", BigDecimal.valueOf(39.90), 50, categoriaBatom, fornecedorMaquiagem));
            produtoRepository.save(new Produto("Base Matte", BigDecimal.valueOf(59.90), 75, categoriaBase, fornecedorMaquiagem));
            produtoRepository.save(new Produto("Máscara de Cílios", BigDecimal.valueOf(29.90), 80, categoriaMascara, fornecedorMaquiagem));

            

            System.out.println("✅ Dados fictícios carregados com categorias diferentes!");
        };
    }
}