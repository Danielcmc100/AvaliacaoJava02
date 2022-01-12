package classes;

import java.time.LocalDate;

public class Venda {
    LocalDate dataVenda;
    Produto produto;
    int quantidade;
   
    public LocalDate getDataVenda() {
        return dataVenda;
    }
    public void setDataVenda(LocalDate dataVenda) {
        this.dataVenda = dataVenda;
    }
    public Produto getProduto() {
        return produto;
    }
    public void setProduto(Produto produto) {
        this.produto = produto;
    }
    public int getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    public Venda(LocalDate dataVenda, Produto produto, int quantidade) {
        this.dataVenda = dataVenda;
        this.produto = produto;
        this.quantidade = quantidade;
    }
    
}
