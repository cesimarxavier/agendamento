package br.edu.ifrn.agendamento.controller; 

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/professor")
    public String professorView() {
        return "forward:/professor.html";
    }

    @GetMapping("/")
    public String indexView() {
        return "forward:/index.html";
    }
}