package com.orange.vinicola.service;

import com.orange.vinicola.model.Carrinho;
import com.orange.vinicola.model.Item;
import com.orange.vinicola.model.Produto;
import com.orange.vinicola.repository.CarrinhoRepository;
import com.orange.vinicola.repository.ItemRepository;
import com.orange.vinicola.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CarrinhoService {

    //- clicando em comprar o produto é colocado no carrinho.
    //- O carrinho acumula os produtos selecionados
    //- Posso comprar 2 vezes o mesmo produto e irá adicionar mais 1 item a quantidade.
    //- o item deve ser armazenado na seção e no banco de dados, pois o usuário ainda não estará logado, e quando ele logar os itens ainda devem permanecer no carrinho.
    //- Posso aumentar os itens  de um produto no carrinho.
    //- O subtotal deve ser recalculado
    //- O subtotal deve levar em consideração o frete calculado
    //- Posso diminuir os itens  de um produto no carrinho.
    //- O subtotal deve ser recalculado
    //- O subtotal deve levar em consideração o frete calculado
    //- Posso remover o item de um produto no carrinho.
    //- O subtotal deve ser recalculado
    //- O subtotal deve levar em consideração o frete calculado
    //- O frete para cliente não logado é de livre escolha
    //- Escolher entre 3 valores de frete.

    //Ordem de prioridade:
    //Adicionar produto ao carrinho
    //Acumular produtos no carrinho
    //Adicionar múltiplas quantidades do mesmo produto
    //Armazenamento dos itens
    //Aumentar a quantidade de itens
    //Diminuir a quantidade de itens
    //Remover um item do carrinho
    //Cálculo do subtotal
    //Cálculo do frete
    //Escolha de frete para clientes não logados

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ItemRepository itemRepository;

    public Carrinho adicionarProduto(@SessionAttribute("carrinho") Carrinho carrinho, Long produtoId, int quantidade) {
        if (carrinho == null) {
            carrinho = new Carrinho();
            carrinho.setItens(new ArrayList<>());
            carrinho.setValorTotal(0);
            carrinho.setFrete(0);
            carrinho.setValorComFrete(0);
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
                    return carrinho;
                }
            }

            if (!itemJaExiste) {
                adicionarItem(carrinho, produto, quantidade);
            }
        }

        return carrinho;
    }

    public Carrinho incrementarQuantidade(@SessionAttribute("carrinho") Carrinho carrinho, Long produtoId) {

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
                    //TODO: Realizar tratamento caso passe do estoque
                }

                item.setQuantidade(item.getQuantidade() + 1);

                double valorAnteriorItem = item.getValorItem();
                item.setValorItem(produto.getPreco() * item.getQuantidade());

                carrinho.setValorTotal(carrinho.getValorTotal() + (item.getValorItem() - valorAnteriorItem));
                carrinho.setValorComFrete(carrinho.getValorTotal() + carrinho.getFrete());
                break;
            }
        }

        return carrinho;
    }

    public Carrinho decrementarQuantidade(@SessionAttribute("carrinho") Carrinho carrinho, Long produtoId) {

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
                if (item.getQuantidade() < item.getQuantidade() - 1) {
                    //TODO
                }

                if (item.getQuantidade() == 1) {
                    this.removerProduto(carrinho, produtoId);
                    break;
                }

                item.setQuantidade(item.getQuantidade() - 1);
                item.setValorItem(produto.getPreco() * item.getQuantidade());
                carrinho.setValorTotal(carrinho.getValorTotal() - item.getValorItem());
                carrinho.setValorComFrete(carrinho.getValorTotal() + carrinho.getFrete());
                break;
            }
        }

        return carrinho;
    }

    public Carrinho atualizarFrete(@SessionAttribute("carrinho") Carrinho carrinho, double frete) {
        if (carrinho == null) {
            throw new RuntimeException("Carrinho não encontrado");
        }

        carrinho.setValorComFrete(carrinho.getValorComFrete() - carrinho.getFrete());
        carrinho.setFrete(frete);
        carrinho.setValorComFrete(carrinho.getValorTotal() + carrinho.getFrete());
        return carrinho;
    }

    public Carrinho removerProduto(@SessionAttribute("carrinho") Carrinho carrinho, Long produtoId) {
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

        return carrinho;
    }

    public Carrinho buscarCarrinhoPorUsuarioId(Long usuarioId) {
        return carrinhoRepository.findByUsuarioId(usuarioId);
    }

    private Carrinho adicionarItem (Carrinho carrinho, Produto produto, int quantidade) {
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
        return carrinho;
    }

    public void save(Carrinho carrinho) {
        carrinhoRepository.save(carrinho);
    }


}
