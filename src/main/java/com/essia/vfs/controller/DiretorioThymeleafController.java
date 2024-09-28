/*package com.essia.vfs.controller;

import com.essia.vfs.model.Diretorio;
import com.essia.vfs.service.DiretorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class DiretorioThymeleafController {

    @Autowired
    private DiretorioService diretorioService;

    @GetMapping("/diretorios")
    public String listarDiretorios(Model model) {
        List<Diretorio> diretorios = diretorioService.listarTodos();
        model.addAttribute("diretorios", diretorios);
        model.addAttribute("novoDiretorio", new Diretorio());
        return "diretorios";
    }

    @PostMapping("/diretorios")
    public String criarDiretorio(@ModelAttribute("novoDiretorio") Diretorio novoDiretorio) {
        diretorioService.salvar(novoDiretorio);
        return "redirect:/diretorios";
    }

    @PostMapping("/diretorios/{id}")
    public String deletarDiretorio(@PathVariable Long id) {
        diretorioService.deletar(id);
        return "redirect:/diretorios";
    }

}*/
