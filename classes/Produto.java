package classes;
public class Produto {
    private Double codgo;
    private String nome;
    private float valor;
    private int quantidade;

    public Produto(Double codgo, String nome, float valor, int quantidade) {
        this.codgo = codgo;
        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public float getValor() {
        return valor;
    }
    public void setValor(float valor) {
        this.valor = valor;
    }
    public int getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    public Double getCodgo() {
        return codgo;
    }
    public void setCodgo(Double codgo) {
        this.codgo = codgo;
    }
    
}
