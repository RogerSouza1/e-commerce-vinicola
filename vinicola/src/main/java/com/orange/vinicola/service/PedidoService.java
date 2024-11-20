package com.orange.vinicola.service;

import com.orange.vinicola.model.Carrinho;
import com.orange.vinicola.model.Endereco;
import com.orange.vinicola.model.ItemPedido;
import com.orange.vinicola.model.Pedido;
import com.orange.vinicola.repository.CarrinhoRepository;
import com.orange.vinicola.repository.ClienteRepository;
import com.orange.vinicola.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public void save(Pedido pedido) {
        pedidoRepository.save(pedido);
    }

    public List<Pedido> buscarPedidosPorClienteId(Long clienteId) {
        return pedidoRepository.findPedidosByClienteId(clienteId);
    }

    public List<Pedido> findAll(){
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> findById(Long produtoId) {
        return pedidoRepository.findById(produtoId);
    }

    public Pedido finalizarCarrinho(Carrinho carrinho, Endereco endereco, String formaPagamento) {
        Pedido pedido = new Pedido();
        pedido.setNumeroPedido(gerarNumeroPedido());
        String status = "Aguardando Pagamento";
        pedido.setStatus(status);
        pedido.setFrete(carrinho.getFrete());
        pedido.setValorTotal(carrinho.getValorTotal());
        pedido.setValorComFrete(carrinho.getValorComFrete());
        pedido.setEndereco(endereco);
        pedido.setEntregue(false);
        pedido.setFormaPagamento(formaPagamento);
        pedido.setCliente(carrinho.getCliente());
        pedido.setItens(copiarItensDoCarrinho(carrinho, pedido));
        pedido.setDataPedido(new Date());

        pedidoRepository.save(pedido);

        limparCarrinho(carrinho);

        return pedido;
    }


    private int gerarNumeroPedido() {
        Integer maxNumeroPedido = pedidoRepository.findMaxNumeroPedido();
        return (maxNumeroPedido != null ? maxNumeroPedido : 0) + 1;
    }

    private List<ItemPedido> copiarItensDoCarrinho(Carrinho carrinho, Pedido pedido) {
        return carrinho.getItens().stream().map(item -> {
            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setProduto(item.getProduto());
            itemPedido.setPedido(pedido);
            itemPedido.setQuantidade(item.getQuantidade());
            itemPedido.setValorItem(item.getValorItem());
            return itemPedido;
        }).collect(Collectors.toList());
    }

    private void limparCarrinho(Carrinho carrinho) {
        carrinho.getItens().clear();
        carrinho.setValorTotal(0);
        carrinho.setFrete(0);
        carrinho.setValorComFrete(0);
        carrinhoRepository.save(carrinho);
    }
}
