package com.orange.vinicola.service;

import com.orange.vinicola.model.Carrinho;
import com.orange.vinicola.model.Item;
import com.orange.vinicola.model.Produto;
import com.orange.vinicola.model.Usuario;
import com.orange.vinicola.repository.CarrinhoRepository;
import com.orange.vinicola.repository.ItemRepository;
import com.orange.vinicola.repository.ProdutoRepository;
import com.orange.vinicola.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarrinhoService {

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ItemRepository itemRepository;

    public void deleteByUsuarioId(Long usuarioId) {
        Carrinho carrinho = carrinhoRepository.findByUsuarioId(usuarioId);
        if (carrinho != null) {
            carrinhoRepository.delete(carrinho);
        }
    }

    public Carrinho findByUsuarioId(Long usuarioId) {
        return carrinhoRepository.findByUsuarioId(usuarioId);
    }

    public Carrinho save(Carrinho carrinho) {
        return carrinhoRepository.save(carrinho);
    }

    @Transactional
    public void adicionarProduto(Long usuarioId, Long produtoId, int quantidade) {
        Carrinho carrinho = getOrCreateCarrinho(usuarioId);

        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

        Item itemExistente = carrinho.getItens().stream()
                .filter(item -> item.getProduto().getId().equals(produtoId))
                .findFirst()
                .orElse(null);

        if (itemExistente != null) {
            itemExistente.setQuantidade(itemExistente.getQuantidade() + quantidade);
            itemExistente.setValorProduto(itemExistente.getQuantidade() * produto.getPreco());
        } else {
            Item novoItem = new Item();
            novoItem.setProduto(produto);
            novoItem.setQuantidade(quantidade);
            novoItem.setValorProduto(quantidade * produto.getPreco());
            novoItem.setCarrinho(carrinho);
            carrinho.getItens().add(novoItem);
        }

        salvarCarrinhoAtualizado(carrinho);
    }

    @Transactional
    public void removerProduto(Long usuarioId, Long produtoId, int quantidade) {
        Carrinho carrinho = carrinhoRepository.findByUsuarioId(usuarioId);

        if (carrinho == null) {
            throw new IllegalArgumentException("Carrinho não encontrado");
        }

        Item itemExistente = carrinho.getItens().stream()
                .filter(item -> item.getProduto().getId().equals(produtoId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado no carrinho"));

        if (itemExistente.getQuantidade() > quantidade) {
            itemExistente.setQuantidade(itemExistente.getQuantidade() - quantidade);
            itemExistente.setValorProduto(itemExistente.getQuantidade() * itemExistente.getProduto().getPreco());
        } else {
            carrinho.getItens().remove(itemExistente);
            itemRepository.delete(itemExistente);
        }

        salvarCarrinhoAtualizado(carrinho);
    }

    @Transactional
    public void limparCarrinho(Long usuarioId) {
        Carrinho carrinho = carrinhoRepository.findByUsuarioId(usuarioId);

        if (carrinho == null) {
            throw new IllegalArgumentException("Carrinho não encontrado");
        }

        carrinho.getItens().clear();

        salvarCarrinhoAtualizado(carrinho);
    }

    @Transactional
    public void calcularFrete(Long usuarioId, double frete) {
        Carrinho carrinho = carrinhoRepository.findByUsuarioId(usuarioId);
        if (carrinho == null) {
            throw new IllegalArgumentException("Carrinho não encontrado para o usuário com id: " + usuarioId);
        }
        carrinho.setFrete(frete);
        salvarCarrinhoAtualizado(carrinho);
    }

    private Carrinho getOrCreateCarrinho(Long usuarioId) {
        Carrinho carrinho = carrinhoRepository.findByUsuarioId(usuarioId);
        if (carrinho == null) {
            Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
            if (usuarioOpt.isEmpty()) {
                throw new IllegalArgumentException("Usuário não encontrado");
            }
            carrinho = criarCarrinho(usuarioOpt.get());
        }
        return carrinho;
    }

    private Carrinho criarCarrinho(Usuario usuario) {
        Carrinho novoCarrinho = new Carrinho();
        novoCarrinho.setUsuario(usuario);
        novoCarrinho.setFrete(0.0);
        novoCarrinho.setValorTotal(0.0);
        novoCarrinho.setFinalizado(false);
        return carrinhoRepository.save(novoCarrinho);
    }

    private void salvarCarrinhoAtualizado(Carrinho carrinho) {
        atualizarValorTotalCarrinho(carrinho);
        carrinhoRepository.save(carrinho);
    }

    private void atualizarValorTotalCarrinho(Carrinho carrinho) {
        double valorTotal = carrinho.getItens().stream()
                .mapToDouble(Item::getValorProduto)
                .sum();
        carrinho.setValorTotal(valorTotal + carrinho.getFrete());
    }
}
