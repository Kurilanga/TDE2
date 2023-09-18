import java.util.*;

class Pilha {
    private Node topo;
    private int numero;

    public Pilha(int numero) {
        this.topo = null;
        this.numero = numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getNumero() {
        return numero;
    }

    public boolean estaVazia() {
        return topo == null;
    }

    public void empilhar(int dado) {
        topo = new Node(dado, topo);
    }

    public int desempilhar() {
        if (estaVazia()) {
            return -1;
        }
        int dado = topo.dado;
        topo = topo.proximo;
        return dado;
    }

    public int topo() {
        if (estaVazia()) {
            System.out.println("Erro: A pilha está vazia.");
            return -1;
        }
        return topo.dado;
    }

    public void imprimir() {
        Node atual = topo;
        while (atual != null) {
            if (atual.dado != -1) {
                System.out.print(atual.dado + " ");
            }
            atual = atual.proximo;
        }
        System.out.println();
    }

    public boolean estaOrdenada(boolean ordemCrescente) {
        if (estaVazia() || topo.proximo == null) {
            return true;
        }
        return estaOrdenadaRecursivamente(topo, ordemCrescente);
    }

    private boolean estaOrdenadaRecursivamente(Node atual, boolean ordemCrescente) {
        if (atual.proximo == null) {
            return true;
        }
        int valorAtual = atual.dado;
        int proximoValor = atual.proximo.dado;
        if ((ordemCrescente && proximoValor < valorAtual) || (!ordemCrescente && proximoValor > valorAtual)) {
            return false;
        }
        return estaOrdenadaRecursivamente(atual.proximo, ordemCrescente);
    }
}

class Node {
    int dado;
    Node proximo;

    public Node(int dado, Node proximo) {
        this.dado = dado;
        this.proximo = proximo;
    }
}

public class JogoTDE {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Qual tamanho das pilhas deseja: ");
        int tamanhoPilhas = scanner.nextInt();

        Pilha pilha1 = new Pilha(1);
        Pilha pilha2 = new Pilha(2);
        Pilha pilha3 = new Pilha(3);

        Random random = new Random();
        for (int i = 0; i < tamanhoPilhas; i++) {
            pilha1.empilhar(random.nextInt(100) + 1);
        }

        System.out.println("Pilha 1:");
        pilha1.imprimir();
        System.out.println("Pilha 2:");
        pilha2.imprimir();
        System.out.println("Pilha 3:");
        pilha3.imprimir();

        System.out.print("Ordem Crescente (Tecle 1) / Ordem Descrescente (Tecle 2): ");
        boolean ordemCrescente = scanner.nextInt() == 1;

        int jogadas = 0;
        boolean continuarJogando = true;
        // minha parte da explicacao
        while (continuarJogando) {
            System.out.println("Menu:");
            System.out.println("Encerrar (Tecle 0)");
            System.out.println("Movimentar (Tecle 1)");
            System.out.println("Mostrar soluçao (Tecle 2)");

            int escolha_menu = scanner.nextInt();

            if (escolha_menu == 0) {
                System.out.println("Fim de Jogo");
                break;
            } else if (escolha_menu == 1) {
                System.out.print("De que pilha deseja mover?(1 / 2 / 3): ");
                int origem = scanner.nextInt();
                System.out.print("Para qual pilha deseja mover? (1 / 2 / 3): ");
                int destino = scanner.nextInt();

                Pilha pilhaOrigem, pilhaDestino;

                switch (origem) {
                    case 1:
                        pilhaOrigem = pilha1;
                        break;
                    case 2:
                        pilhaOrigem = pilha2;
                        break;
                    default:
                        pilhaOrigem = pilha3;
                        break;
                }

                switch (destino) {
                    case 1:
                        pilhaDestino = pilha1;
                        break;
                    case 2:
                        pilhaDestino = pilha2;
                        break;
                    default:
                        pilhaDestino = pilha3;
                        break;
                }

                if (!pilhaOrigem.estaVazia() && (pilhaDestino.estaVazia() || pilhaOrigem.topo() < pilhaDestino.topo())) {
                    int valor = pilhaOrigem.desempilhar();
                    pilhaDestino.empilhar(valor);
                    jogadas++;

                    System.out.println("Pilha 1:");
                    pilha1.imprimir();
                    System.out.println("Pilha 2:");
                    pilha2.imprimir();
                    System.out.println("Pilha 3:");
                    pilha3.imprimir();

                    if (pilha1.estaOrdenada(ordemCrescente) || pilha2.estaOrdenada(ordemCrescente) || pilha3.estaOrdenada(ordemCrescente)) {
                        System.out.println("Ordenação concluída em " + jogadas + " jogadas.");

                        System.out.print("Quer continuar ordenando? (Sim(tecle 1) - Nao(tecle 0)) ");
                        int continuar = scanner.nextInt();
                        if (continuar == 0) {
                            System.out.println("Fim de Jogo.");
                            continuarJogando = false;
                        }
                    }
                } else {
                    System.out.println("Movimento incorreto.");
                }
            } else if (escolha_menu == 2) {
                solucaoAutomatica(pilha1, pilha3, pilha2, tamanhoPilhas, ordemCrescente);
                jogadas++;
                System.out.println("Jogo resolvido:");

                System.out.println("Pilha 1:");
                pilha1.imprimir();
                System.out.println("Pilha 2:");
                pilha2.imprimir();
                System.out.println("Pilha 3:");
                pilha3.imprimir();
                System.out.println("Fim de Jogo");
                break;
            } else {
                System.out.println("Opção inválida.");
            }
        }

        scanner.close();
    }

    public static void solucaoAutomatica(Pilha origem, Pilha destino, Pilha intermediaria, int tamanho, boolean ordemCrescente) {
        if (tamanho > 0) {
            solucaoAutomatica(origem, intermediaria, destino, tamanho - 1, ordemCrescente);
            int disco = origem.desempilhar();
            destino.empilhar(disco);
            System.out.println("Pilha de origem:");
            origem.imprimir();
            System.out.println("Pilha de destino:");
            destino.imprimir();
            System.out.println("Pilha intermediária:");
            intermediaria.imprimir();
            System.out.println("Ordenando pilhas...");
            ordenarPilhas(origem, destino, intermediaria, ordemCrescente);
            solucaoAutomatica(intermediaria, destino, origem, tamanho - 1, ordemCrescente);
        }
    }

    public static void ordenarPilhas(Pilha origem, Pilha destino, Pilha intermediaria, boolean ordemCrescente) {
        while (!origem.estaVazia()) {
            int disco = origem.desempilhar();
            if (ordemCrescente) {
                while (!destino.estaVazia() && destino.topo() < disco) {
                    intermediaria.empilhar(destino.desempilhar());
                }
            } else {
                while (!destino.estaVazia() && destino.topo() > disco) {
                    intermediaria.empilhar(destino.desempilhar());
                }
            }
            destino.empilhar(disco);
            while (!intermediaria.estaVazia()) {
                destino.empilhar(intermediaria.desempilhar());
            }
        }
    }
}