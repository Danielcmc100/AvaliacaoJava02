package comparadores;

import java.util.Comparator;

import classes.Produto;

public class ComparadorProduto implements Comparator<Produto> {

    @Override
    public int compare(Produto o1, Produto o2) {
        return Float.compare(o1.getValor(), o2.getValor());
    }
    
}
