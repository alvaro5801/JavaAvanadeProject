package lojas.estoque.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Server server = new Server();
        server.setUrl("https://javaavanadeproject-production.up.railway.app");
        server.setDescription("Servidor de produção");

        return new OpenAPI()
                .info(new Info()
                    .title("Java Avanade Project API")
                    .version("1.0")
                    .description("Documentação da API de Produtos, Fornecedores e Categorias."))
                .servers(List.of(server));
    }
}
