package com.orange.vinicola.service;

import com.orange.vinicola.model.Carrinho;
import com.orange.vinicola.model.Cliente;
import com.orange.vinicola.model.Item;
import com.orange.vinicola.model.Produto;
import com.orange.vinicola.repository.CarrinhoRepository;
import com.orange.vinicola.repository.ItemRepository;
import com.orange.vinicola.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CarrinhoService {

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ItemRepository itemRepository;

    public Carrinho adicionarProduto(Carrinho carrinho, Long produtoId, int quantidade, Cliente cliente) {

        System.out.println("Antes de adicionar o produto: " + carrinho.getItens());


        if (carrinho == null) {
            carrinho = new Carrinho();
            carrinho.setItens(new ArrayList<>());
            carrinho.setValorTotal(0);
            carrinho.setFrete(0);
            carrinho.setValorComFrete(0);
            if (cliente != null) {
                carrinho.setCliente(cliente);
            }
        }

        Optional<Produto> optionalProduto = produtoRepository.findById(produtoId);

        if (optionalProduto.isEmpty()) {
            throw new RuntimeException("Produto não encontrado");
        }

        Produto produto = optionalProduto.get();

        if (produto.getQtdEstoque() < quantidade) {
            throw new RuntimeException("Quantidade indisponível em estoque");
        }

        boolean itemJaExiste = false;
        if (carrinho.getItens() == null) {
            adicionarItem(carrinho, produto, quantidade);
        } else {
            for (Item item : carrinho.getItens()) {
                if (item.getProduto().getId().equals(produtoId)) {
                    item.setQuantidade(item.getQuantidade() + quantidade);
                    double valorAnteriorItem = item.getValorItem();
                    item.setValorItem(produto.getPreco() * item.getQuantidade());

                    carrinho.setValorTotal(carrinho.getValorTotal() + (item.getValorItem() - valorAnteriorItem));
                    carrinho.setValorComFrete(carrinho.getValorTotal() + carrinho.getFrete());
                    itemJaExiste = true;
                    break;
                }
            }

            if (!itemJaExiste) {
                adicionarItem(carrinho, produto, quantidade);
            }
        }


        if (cliente != null) {
            carrinhoRepository.save(carrinho);
        }

        return carrinho;
    }

    public Carrinho incrementarQuantidade(Carrinho carrinho, Long produtoId, Cliente cliente) {
        if (carrinho == null) {
            throw new RuntimeException("Carrinho não encontrado");
        }

        Optional<Produto> optionalProduto = produtoRepository.findById(produtoId);

        if (optionalProduto.isEmpty()) {
            throw new RuntimeException("Produto não encontrado");
        }

        Produto produto = optionalProduto.get();

        for (Item item : carrinho.getItens()) {
            if (item.getProduto().getId().equals(produtoId)) {
                if (produto.getQtdEstoque() < item.getQuantidade() + 1) {
                }

                item.setQuantidade(item.getQuantidade() + 1);

                double valorAnteriorItem = item.getValorItem();
                item.setValorItem(produto.getPreco() * item.getQuantidade());

                carrinho.setValorTotal(carrinho.getValorTotal() + (item.getValorItem() - valorAnteriorItem));
                carrinho.setValorComFrete(carrinho.getValorTotal() + carrinho.getFrete());
                break;
            }
        }

        if (cliente != null) {
            carrinhoRepository.save(carrinho);
        }

        return carrinho;
    }

    public Carrinho decrementarQuantidade(Carrinho carrinho, Long produtoId, Cliente cliente) {
        if (carrinho == null) {
            throw new RuntimeException("Carrinho não encontrado");
        }

        Optional<Produto> optionalProduto = produtoRepository.findById(produtoId);

        if (optionalProduto.isEmpty()) {
            throw new RuntimeException("Produto não encontrado");
        }

        Produto produto = optionalProduto.get();

        for (Item item : carrinho.getItens()) {
            if (item.getProduto().getId().equals(produtoId)) {
                if (item.getQuantidade() > 1) {
                    carrinho.setValorTotal(carrinho.getValorTotal() - item.getValorItem());
                    item.setQuantidade(item.getQuantidade() - 1);
                    item.setValorItem(produto.getPreco() * item.getQuantidade());
                    carrinho.setValorTotal(carrinho.getValorTotal() + item.getValorItem());
                    carrinho.setValorComFrete(carrinho.getValorTotal() + carrinho.getFrete());
                } else if (item.getQuantidade() == 1) {
                    this.removerProduto(carrinho, produtoId, cliente);
                }
                break;
            }
        }

        if (cliente != null) {
            carrinhoRepository.save(carrinho);
        }

        return carrinho;
    }

    public Carrinho atualizarFrete(Carrinho carrinho, double frete, Cliente cliente) {

        if (carrinho == null) {
            throw new RuntimeException("Carrinho não encontrado");
        }

        carrinho.setValorComFrete(carrinho.getValorComFrete() - carrinho.getFrete());
        carrinho.setFrete(frete);
        carrinho.setValorComFrete(carrinho.getValorTotal() + carrinho.getFrete());

        if (cliente != null) {
            carrinhoRepository.save(carrinho);
        }

        return carrinho;
    }

    public Carrinho removerProduto(Carrinho carrinho, Long produtoId, Cliente cliente) {
        if (carrinho == null) {
            throw new RuntimeException("Carrinho não encontrado");
        }

        Item itemRemovido = null;

        for (Item item : carrinho.getItens()) {
            if (item.getProduto().getId().equals(produtoId)) {
                itemRemovido = item;
                break;
            }
        }

        assert itemRemovido != null;

        carrinho.setValorTotal(carrinho.getValorTotal() - itemRemovido.getValorItem());
        carrinho.setValorComFrete(carrinho.getValorTotal() + carrinho.getFrete());
        carrinho.getItens().remove(itemRemovido);

        if (cliente != null) {
            carrinhoRepository.save(carrinho);
        }

        return carrinho;
    }

    public Carrinho buscarCarrinhoPorClienteId(Long clienteId) {
        return carrinhoRepository.findCarrinhoByClienteId(clienteId);
    }

    private void adicionarItem(Carrinho carrinho, Produto produto, int quantidade) {

        Item item = new Item();
        item.setProduto(produto);
        item.setQuantidade(quantidade);
        item.setValorItem(produto.getPreco() * quantidade);
        item.setCarrinho(carrinho);

        if (carrinho.getItens() == null) {
            carrinho.setItens(new ArrayList<>());
        }

        carrinho.getItens().add(item);
        carrinho.setValorTotal(carrinho.getValorTotal() + item.getValorItem());
        carrinho.setValorComFrete(carrinho.getValorTotal() + carrinho.getFrete());
    }


    public void save(Carrinho carrinho) {
        carrinhoRepository.save(carrinho);
    }
}
