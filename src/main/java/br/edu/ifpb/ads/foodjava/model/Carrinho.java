package br.edu.ifpb.ads.foodjava.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Collections;

public class Carrinho {

    private final List<PedidoPreMoldado> itens;

    public Carrinho(){
        itens = new ArrayList<>();
    }

    public void adicionarItem(ItemCardapio refeicao, int quantidade){
        if (refeicao == null || quantidade <=0 ){
            return;
        }
        Optional<PedidoPreMoldado> seJaExiste = itens.stream().filter(x -> x.getItem().getNome().equalsIgnoreCase(refeicao.getNome())).findFirst();
        // isPresente retorna um "sim" ou "não". Caso seja encontrado no findFirst(), ele retorna o objeto procurado(SIM), caso contrário fica vazio e retorna ;
        if(seJaExiste.isPresent()){
            PedidoPreMoldado itemExistente = seJaExiste.get();
            itemExistente.setQtda(itemExistente.getQtda() + quantidade);
        }else{
            itens.add(new PedidoPreMoldado(refeicao,quantidade));
        }
    }

    public void removerItem(PedidoPreMoldado item){
        itens.remove(item);
    }

    public void limpar(){
        itens.clear();
    }

    public double getValorTotal(){
        return itens.stream().mapToDouble(PedidoPreMoldado::getSubTotal).sum();
    }

    public List<PedidoPreMoldado> getItens(){
        return Collections.unmodifiableList(itens);
    }
}
