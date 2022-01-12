import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;

import classes.*;
import comparadores.ComparadorProduto;


public class Programa{
    public static List<Produto> produtos = new ArrayList<>();
    public static List<Venda> vendas = new ArrayList<>();
    public static void main(String[] args) throws InterruptedException, IOException {
        Scanner sc = new Scanner(System.in);
        int opcao = 1;
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
         
        do {
            LimparTela(sc);
            System.out.println("===========================");
            System.out.println("     Venda de produtos     ");
            System.out.println("===========================");
            System.out.println("1 – Incluir produto");
            System.out.println("2 – Consultar produto");
            System.out.println("3 – Listagem de produtos");
            System.out.println("4 – Vendas por período – detalhado");
            System.out.println("5 – Realizar venda\n0 – Sair");
            System.out.print("Opção: ");
            opcao = sc.nextInt();
            sc.nextLine();
            LimparTela(sc);
            
            switch(opcao) {
                case 0:

                //Sair

                System.out.println("Programa encerrado");
                break;

                case 1:

                //Incluir produto

                Incluirproduto(sc);

                sc.nextLine();
                break;
                case 2:

                //Consultar produto
                
                ConsultarProduto(sc);
                
                break;
                case 3:
                
                //Listagem de produtos

                System.out.println("===========================");
                System.out.println("     Lista de produtos     ");
                System.out.println("===========================");
                //Verifica se há produtos
                if(produtos.isEmpty()) {
                    System.out.println("Não há produtos");
                    sc.nextLine();
                    break;
                }
                float valorTotalProdutos = 0;
                for (Produto p : produtos) {
                    System.out.printf("Nome: %s Códgo: %.0f Quantidade: %d Valor: %.2f\n",p.getNome(),p.getCodgo(),p.getQuantidade(),p.getValor());
                    /* System.out.print("Nome: " + p.getNome());
                    System.out.print(" Códgo: " + p.getCodgo());
                    System.out.print(" Quantidade: " + p.getQuantidade());
                    System.out.println(" Valor: " + p.getValor()); */
                    valorTotalProdutos += p.getValor();
                }
                //Valor médio, máximo e mínimo dos produtos
                Float media = valorTotalProdutos/produtos.size();
                Float maximo = Collections.max(produtos,new ComparadorProduto()).getValor();
                Float minimo = Collections.min(produtos,new ComparadorProduto()).getValor();
                System.out.println("===========================");
                System.out.printf("Valor dos produtos médio: %.2f máximo: %.2f mínimo: %.2f\n", media,maximo,minimo);
                sc.nextLine();
                
                break;
                case 4:

                //Relatório de vendas

                //Verifica se há vendas
                if(vendas.isEmpty()) {
                    System.out.println("Não há vendas");
                    sc.nextLine();
                    break;
                }

                //Data da venda 

                String dataVenda2;
                do {
                    System.out.print("Data da venda(0:Data atual):");
                    dataVenda2 = sc.nextLine();
                    if(dataVenda2.hashCode() == String.valueOf(0).hashCode()) {
                        dataVenda2 = LocalDate.now().format(formato);
                        break;
                    }
                }while(!Validardata(dataVenda2));
                LimparTela(sc);

                System.out.println("===========================");
                System.out.println("    Relatório de vendas    ");
                System.out.printf("   depois de: %s   \n",dataVenda2);
                System.out.println("===========================");
                
                LocalDate date = LocalDate.parse(dataVenda2,formato);

                // quantidade, valor unitário e valor total.
                for(int i = 0; i < vendas.size();i++) {
                    if(vendas.get(i).getDataVenda().isAfter(date)) { 
                        System.out.printf("Nome: %s Data da venda: %s Quantidade: %d Valor unitário: R$%.2f Valor total: R$%.2f\n",
                        vendas.get(i).getProduto().getNome(),vendas.get(i).getDataVenda(),vendas.get(i).getQuantidade(),
                        vendas.get(i).getProduto().getValor(),vendas.get(i).getProduto().getValor()*vendas.get(i).getQuantidade());
                    }
                }
                sc.nextLine();
                break;
                case 5:
                    
                //Realizar venda

                System.out.println("===========================");
                System.out.println("       Realizar venda      ");
                System.out.println("===========================");

                //Busca o produto fornecido pelo usuario

                Produto produtoTemp;
                Double codgoVenda;
                do {
                    System.out.print("Códgo do produto(0: Sair): ");
                    codgoVenda = sc.nextDouble();
                    sc.nextLine();
                    produtoTemp = ProcurarProduto(codgoVenda);
                    if(produtoTemp != null) {
                        System.out.printf("Produto: %s encontrado\n",produtoTemp.getNome());
                    }else {
                        System.out.println("Produto não encontrado");
                    }
                }while(produtoTemp == null && codgoVenda.intValue() != 0);
                if(codgoVenda.intValue() == 0) break;

                //Verifica se possui a quantidade

                int qtdVenda = 0;
                boolean temEstoque;
                do {
                    System.out.print("Quantidade(0: Sair): ");
                    qtdVenda = sc.nextInt();
                    sc.nextLine();
                    temEstoque = produtoTemp.getQuantidade() >= qtdVenda && qtdVenda > 0;
                    if(temEstoque) {
                        System.out.printf("Quantidade aceita, quantidade restante: %d\n", produtoTemp.getQuantidade() - qtdVenda);
                    } else {
                        System.out.printf("Quantidade invalida, quantidade disponivel: %d\n", produtoTemp.getQuantidade());
                    } 
                }while(!temEstoque && qtdVenda != 0);
                if(qtdVenda == 0) break;

                //Data da venda 

                String dataVenda;
                do {
                    System.out.print("Data da venda(0:Data atual):");
                    dataVenda = sc.nextLine();
                    if(dataVenda.hashCode() == String.valueOf(0).hashCode()) {
                        dataVenda = LocalDate.now().format(formato);
                        break;
                    }
                }while(!Validardata(dataVenda));
                
                //Concluir venda
                
                Venda novaVenda = new Venda(LocalDate.parse(dataVenda,formato), produtoTemp, qtdVenda);
                produtoTemp.setQuantidade(produtoTemp.getQuantidade() - qtdVenda);
                vendas.add(novaVenda);
                System.out.print("Venda bem sucedida!");
                sc.nextLine();
                break;
                default:
                    System.out.println("Opção invalida");
                    sc.nextLine();
                break;
            }
        }while(opcao != 0);
        sc.close();
    }

    private static void LimparTela(Scanner in) throws InterruptedException, IOException {
        
        // Limpa toda a tela
        if (System.getProperty("os.name").contains("Windows"))
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        else
            System.out.print("\033[H\033[2J");
        
        System.out.flush();
    }

    private static boolean Validardata(String data){
        return data.matches("^[0-9]{2}[/][0-9]{2}[/][0-9]{4}$");
    }

    private static void Incluirproduto(Scanner sc) {
        Double codgo;
        System.out.print("Códgo do produto: ");
        codgo = sc.nextDouble();
        sc.nextLine();

        String nome;
        System.out.print("Nome do produto: ");
        nome = sc.nextLine();

        float valor;
        System.out.print("Valor do produto: ");
        valor = sc.nextFloat();
        sc.nextLine();

        int quantidade;
        System.out.print("Quantidade do produto: ");
        quantidade = sc.nextInt();
        sc.nextLine();

        Produto produto = new Produto(codgo,nome,valor,quantidade);
        produtos.add(produto);

        System.out.println("Produto adicionado com sucesso");
    }

    private static void ConsultarProduto(Scanner sc) {
        System.out.println("===========================");
        System.out.println("      ConsultarProdut      ");
        System.out.println("===========================");
        System.out.print("Códgo do produto: ");
        Double codgo = sc.nextDouble();
        sc.nextLine();
        System.out.println("===========================");
        Produto produto = ProcurarProduto(codgo);
        if(produto != null) {
            System.out.printf("Produto [%s] encontrado\n",produto.getNome());
            System.out.printf("Nome: %s Quantidade: %d Valor unitário: R$%.2f",produto.getNome(),produto.getQuantidade(),produto.getValor());
        } else {
            System.out.print("Produto não encontrado");
        }
        
        sc.nextLine();
    }

    private static Produto ProcurarProduto(Double codgo) {
        
        for (Produto p : produtos) {
            if(p.getCodgo().hashCode() == codgo.hashCode())
                return p;
        }
        return null;
    }  
}