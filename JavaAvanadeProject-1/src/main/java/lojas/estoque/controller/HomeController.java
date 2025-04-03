package lojas.estoque.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "🚀 API JavaAvanadeProject está no ar e funcionando! 🎉";
    }
}
