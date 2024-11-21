package com.orange.vinicola.controller;

import com.orange.vinicola.model.Imagem;
import com.orange.vinicola.model.Produto;
import com.orange.vinicola.service.ImagemService;
import com.orange.vinicola.service.ProdutoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
            if (!produtoService.findByNome(produto.getNome()).isEmpty()) {
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
            if (principalImageIndex == null || principalImageIndex < 0) {
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
        produtos.sort((p1, p2) -> p2.getId().compareTo(p1.getId()));
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
    public String editarProduto(@RequestParam("id") Long id, Authentication authentication, Model model) {
        Optional<Produto> produto = produtoService.findById(id);
        if (produto.isPresent()) {
            DecimalFormat df = new DecimalFormat("R$ #,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
            String precoFormatado = df.format(produto.get().getPreco());
            List<Imagem> imagens = imagemService.findByProdutoId(id);
            String userRole = authentication.getAuthorities().stream().findFirst().get().getAuthority();
            model.addAttribute("userRole", userRole);
            System.out.println(userRole);
            model.addAttribute("produto", produto.get());
            model.addAttribute("precoFormatado", precoFormatado);
            model.addAttribute("imagens", imagens);
        }
        return "editar-produto";
    }

    @PostMapping("/editar-produto")
    public String updateProduct(
            @RequestParam("id") Long id,
            Produto produto,
            @RequestParam("imageUpload") MultipartFile[] files,
            @RequestParam(value = "principalImage", required = false) Integer principalImageIndex,
            @RequestParam(value = "substituteImages", required = false) String substituteImages,
            Model model) {
        try {
            Optional<Produto> produtoExistente = produtoService.findById(id);

            if (produtoExistente.isEmpty()) {
                model.addAttribute("mensagem", "Produto não encontrado!");
                return "erro";
            }

            Produto produtoEditado = produtoExistente.get();
            produtoEditado.setId(produto.getId());
            produtoEditado.setNome(produto.getNome());
            produtoEditado.setAvaliacao(produto.getAvaliacao());
            produtoEditado.setDescricao(produto.getDescricao());
            produtoEditado.setPreco(produto.getPreco());
            produtoEditado.setQtdEstoque(produto.getQtdEstoque());
            produtoService.save(produtoEditado);

            if (files != null) {
                List<Imagem> imagensAntigas = imagemService.findByProdutoId(produtoEditado.getId());

                if ("true".equals(substituteImages)) {
                    // Exclui todas as imagens anteriores do produto
                    for (Imagem imagem : imagensAntigas) {
                        imagemService.deleteById(imagem.getId());
                    }
                }

                // Verifica e redefine a imagem principal existente
                if (principalImageIndex != null) {
                    Imagem imagemPrincipalExistente = imagemService.findPrincipalByProdutoId(produtoEditado.getId());
                    if (imagemPrincipalExistente != null) {
                        imagemPrincipalExistente.setPrincipal(false);
                        imagemService.save(imagemPrincipalExistente);
                    }
                }

                // Salva as novas imagens
                for (int i = 0; i < files.length; i++) {
                    MultipartFile file = files[i];
                    try {
                        Imagem imagem = new Imagem();
                        imagem.setProduto(produtoEditado);
                        imagem.setDados(file.getBytes());
                        imagem.setUrl(file.getOriginalFilename());

                        // Define a imagem principal
                        imagem.setPrincipal(principalImageIndex != null && i == principalImageIndex);

                        imagemService.save(imagem);
                    } catch (IOException e) {
                        e.printStackTrace();
                        // Adicione tratamento de erro conforme necessário
                    }
                }
            }

            model.addAttribute("mensagem", "Produto atualizado com sucesso!");
            return "redirect:/lista-produtos";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("mensagem", "Erro: Produto já registrado!");
            return "redirect:/lista-produtos";
        }
    }

    @GetMapping("/alterar-estado-produto")
    public String alterarEstadoProduto(@RequestParam("id") Long id, Model model) {
        Optional<Produto> produtoOpt = produtoService.findById(id);
        if (produtoOpt.isPresent()) {
            Produto produto = produtoOpt.get();
            produtoService.alterar_estado_produto(produto);
            model.addAttribute("mensagem", "Estado do produto alterado com sucesso!");
        } else {
            model.addAttribute("mensagem", "Produto não encontrado!");
        }
        List<Produto> todosProdutos = produtoService.findAll();
        model.addAttribute("produtos", todosProdutos);
        return "lista-produtos";
    }

    @GetMapping("/detalhes-produto")
    public String detalhesProduto(@RequestParam("id") Long id, Model model, HttpServletRequest request) {
        Optional<Produto> produtoOpt = produtoService.findById(id);
        if (produtoOpt.isPresent()) {
            Produto produto = produtoOpt.get();

            Imagem imagemPrincipal = imagemService.findPrincipalByProdutoId(id);
            List<Imagem> imagensProduto = imagemService.findByProduto(id);
            imagensProduto.removeIf(imagem -> imagem.getId().equals(imagemPrincipal.getId()));
            imagensProduto.add(0, imagemPrincipal);

            model.addAttribute("carrinho", request.getSession().getAttribute("carrinho"));
            model.addAttribute("produtoImagens", imagensProduto);
            model.addAttribute("produto", produto);
            return "detalhes-produto";
        } else {
            model.addAttribute("mensagem", "Produto não encontrado!");
            return "redirect:/lista-produtos";
        }
    }

    @GetMapping("/detalhes-produto-cliente")
    public String detalhesProdutoCliente(@RequestParam("id") Long id, Model model) {
        Optional<Produto> produtoOpt = produtoService.findById(id);
        if (produtoOpt.isPresent()) {
            Produto produto = produtoOpt.get();

            Imagem imagemPrincipal = imagemService.findPrincipalByProdutoId(id);
            List<Imagem> imagensProduto = imagemService.findByProduto(id);
            imagensProduto.removeIf(imagem -> imagem.getId().equals(imagemPrincipal.getId()));
            imagensProduto.add(0, imagemPrincipal);

            model.addAttribute("produtoImagens", imagensProduto);
            model.addAttribute("produto", produto);
            return "detalhe-produto-cliente";
        } else {
            model.addAttribute("mensagem", "Produto não encontrado!");
            return "redirect:/index";
        }
    }

        @PostMapping("/pesquisa")
        public String pesquisarProdutos(@RequestParam(value = "nome", required = false, defaultValue = "") String nome, Model model) {

            List<Produto> produtos = null;

            if (nome.isEmpty()){
                produtos = produtoService.findAllAtivado();
            } else {
                produtos = produtoService.pesquisarPorNome(nome);
            }

            model.addAttribute("produtos", produtos);
            return "index";
        }

}

