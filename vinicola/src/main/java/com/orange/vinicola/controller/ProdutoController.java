package com.orange.vinicola.controller;

import com.orange.vinicola.model.Imagem;
import com.orange.vinicola.model.Produto;
import com.orange.vinicola.service.ImagemService;
import com.orange.vinicola.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Controller
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;
    @Autowired
    private ImagemService imagemService;

    @GetMapping("/cadastro-produto")
    public String showFormProdutos(Model model) {
        model.addAttribute("produto", new Produto());
        return "registrar-produto";
    }

    @PostMapping("/cadastro-produto")
    public String registerProduct(Produto produto, @RequestParam("imageUpload") MultipartFile[] files, @RequestParam("principalImage") Integer principalImageIndex,
                                  Model model) {
        try {
            if (produtoService.findByNome(produto.getNome()) != null) {
                model.addAttribute("mensagem", "Produto já registrado!");
                return "registrar-produto";
            }

            produto.setAtivado(true);
            Produto produtoSalvo = produtoService.save(produto);

            // Verifica se as imagens foram carregadas
            if (files == null || files.length == 0) {
                model.addAttribute("mensagem", "Por favor, anexe pelo menos uma imagem.");
                return "registrar-produto";
            }

            // Verifica se o índice da imagem principal foi enviado
            if (principalImageIndex == null || principalImageIndex < 0 || principalImageIndex >= files.length) {
                model.addAttribute("mensagem", "Selecione uma imagem principal válida.");
                return "registrar-produto";
            }

            // Salva as imagens no banco
            for (int i = 0; i < files.length; i++) {
                MultipartFile file = files[i];
                try {
                    Imagem imagem = new Imagem();
                    imagem.setProduto(produtoSalvo);
                    imagem.setDados(file.getBytes());
                    imagem.setUrl(file.getOriginalFilename());
                    imagem.setPrincipal(i == principalImageIndex); // Define a imagem principal com base no índice
                    imagemService.save(imagem);
                } catch (IOException e) {
                    e.printStackTrace();
                    // Adicione tratamento de erro conforme necessário
                }
            }

            model.addAttribute("mensagem", "Produto registrado com sucesso!");
            return "redirect:/lista-produtos";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("mensagem", "Erro: Produto já registrado!");
        }
        return "registrar-produto";
    }

    @GetMapping("/lista-produtos")
    public String listarProduto(Model model) {
        List<Produto> produtos = produtoService.findAll();
        model.addAttribute("produtos", produtos);
        return "lista-produtos";
    }

    @GetMapping("/buscar-produto")
    public String buscarProduto(@RequestParam("nome") String nome, Model model) {
        List<Produto> produtos = produtoService.findByNome(nome);
        model.addAttribute("produtos", produtos);
        return "fragments/tabela-produtos :: tabela-produtos";
    }

    @GetMapping("/editar-produto")
    public String editarProduto(@RequestParam("id") Long id, Model model) {
        Optional<Produto> produto = produtoService.findById(id);
        if (produto.isPresent()) {
            DecimalFormat df = new DecimalFormat("R$ #,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
            String precoFormatado = df.format(produto.get().getPreco());
            model.addAttribute("produto", produto.get());
            model.addAttribute("precoFormatado", precoFormatado);
        }
        return "editar-produto";
    }

    @PostMapping("/editar-produto")
    public String updateProduct(@RequestParam("id") Long id, Produto produto, Model model) {
        try {
            Optional<Produto> produtoExistente = produtoService.findById(id);

            if (produtoExistente.isEmpty()) {
                model.addAttribute("mensagem", "Produto não encontrado!");
                return "erro";
            }

            Produto produtoNovo = produtoExistente.get();
            produtoNovo.setNome(produto.getNome());
            produtoNovo.setAvaliacao(produto.getAvaliacao());
            produtoNovo.setDescricao(produto.getDescricao());
            System.out.println(produto.getPreco());
            produtoNovo.setPreco(produto.getPreco());
            produtoNovo.setQtdEstoque(produto.getQtdEstoque());
            produtoService.save(produtoNovo);

            model.addAttribute("mensagem", "Produto atualizado com sucesso!");
            return "redirect:/lista-produtos";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("mensagem", "Erro: Produto já registrado!");
            return "redirect:/lista-produtos";
        }
    }

}

