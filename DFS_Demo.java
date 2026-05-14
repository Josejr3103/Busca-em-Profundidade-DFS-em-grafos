import java.util.*;

public class DFS_Demo {

    // ── Cores ANSI para o terminal ──────────────────────────────────────────
    static final String RESET   = "\u001B[0m";
    static final String BOLD    = "\u001B[1m";
    static final String CYAN    = "\u001B[36m";
    static final String GREEN   = "\u001B[32m";
    static final String YELLOW  = "\u001B[33m";
    static final String BLUE    = "\u001B[34m";
    static final String MAGENTA = "\u001B[35m";
    static final String RED     = "\u001B[31m";
    static final String WHITE   = "\u001B[37m";
    static final String DIM     = "\u001B[2m";

    private int vertices;
    private List<List<Integer>> adjacencyList;

    // Contadores para estatísticas
    private int totalVisitados;
    private int totalPassagens;

    public DFS_Demo(int vertices) {
        this.vertices = vertices;
        adjacencyList = new ArrayList<>();
        for (int i = 0; i < vertices; i++) {
            adjacencyList.add(new ArrayList<>());
        }
    }

    public void addEdge(int source, int destination) {
        adjacencyList.get(source).add(destination);
        adjacencyList.get(destination).add(source);
    }

    // ── DFS Recursiva ────────────────────────────────────────────────────────
    public void dfs(int startVertex) {
        boolean[] visited = new boolean[vertices];
        totalVisitados = 0;
        totalPassagens = 0;

        System.out.println(CYAN + "  Percurso: " + RESET);
        System.out.print("  ");
        dfsRecursive(startVertex, visited, 0);
        System.out.println();
    }

    private void dfsRecursive(int vertex, boolean[] visited, int profundidade) {
        visited[vertex] = true;
        totalVisitados++;
        totalPassagens += adjacencyList.get(vertex).size();
        
        String cor = GREEN;
        System.out.print(cor + BOLD + "[" + vertex + "]" + RESET + " ");

        for (int vizinho : adjacencyList.get(vertex)) {
            if (!visited[vizinho]) {
                dfsRecursive(vizinho, visited, profundidade + 1);
            }
        }
    }

    // ── DFS Iterativa ────────────────────────────────────────────────────────
    public void dfsIterativa(int startVertex) {
        boolean[] visited = new boolean[vertices];
        Stack<Integer> pilha = new Stack<>();
        totalVisitados = 0;
        totalPassagens = 0;

        pilha.push(startVertex);
        System.out.println(CYAN + "  Percurso: " + RESET);
        System.out.print("  ");

        while (!pilha.isEmpty()) {
            int vertex = pilha.pop();
            if (!visited[vertex]) {
                visited[vertex] = true;
                totalVisitados++;
                System.out.print(MAGENTA + BOLD + "[" + vertex + "]" + RESET + " ");

                List<Integer> vizinhos = adjacencyList.get(vertex);
                totalPassagens += vizinhos.size();
                for (int i = vizinhos.size() - 1; i >= 0; i--) {
                    if (!visited[vizinhos.get(i)]) {
                        pilha.push(vizinhos.get(i));
                    }
                }
            }
        }
        System.out.println();
    }

    public boolean isConnected() {
        boolean[] visited = new boolean[vertices];
        dfsRecursiveSimples(0, visited);
        for (boolean v : visited) if (!v) return false;
        return true;
    }

    private void dfsRecursiveSimples(int vertex, boolean[] visited) {
        visited[vertex] = true;
        for (int vizinho : adjacencyList.get(vertex)) {
            if (!visited[vizinho]) dfsRecursiveSimples(vizinho, visited);
        }
    }

    public long medirTempo(int startVertex) {
        boolean[] visited = new boolean[vertices];
        long inicio = System.nanoTime();
        dfsRecursiveSimples(startVertex, visited);
        return System.nanoTime() - inicio;
    }

    // ── Utilitários de exibição ──────────────────────────────────────────────
    static void linha(char c, int tam) {
        System.out.println(DIM + String.valueOf(c).repeat(tam) + RESET);
    }

    static void cabecalho(String texto) {
        System.out.println();
        linha('=', 55);
        System.out.println(BOLD + WHITE + "  " + texto + RESET);
        linha('=', 55);
    }

    static void secao(String texto) {
        System.out.println();
        System.out.println(BLUE + BOLD + ">> " + texto + RESET);
        linha('-', 45);
    }

    static void info(String chave, String valor) {
        System.out.printf("  " + CYAN + "%-22s" + RESET + "%s%n", chave + ":", valor);
    }

    static void detalhe(String texto) {
        System.out.println("  " + DIM + texto + RESET);
    }

    // ── Main ─────────────────────────────────────────────────────────────────
    public static void main(String[] args) throws InterruptedException {

        cabecalho("BUSCA EM PROFUNDIDADE (DFS) - Demonstração");
        System.out.println(DIM + "  Depth-First Search · Análise e Projeto de Algoritmos" + RESET);

        // ════════════════════════════════════════════════════════════════════
        // EXEMPLO 1: Grafo pequeno com 6 vértices
        // ════════════════════════════════════════════════════════════════════
        secao("Exemplo 1 - Grafo com 6 vértices");

        DFS_Demo g1 = new DFS_Demo(6);
        g1.addEdge(0, 1); g1.addEdge(0, 2);
        g1.addEdge(1, 3); g1.addEdge(2, 4);
        g1.addEdge(3, 5); g1.addEdge(4, 5);

        System.out.println(DIM + "  Arestas: 0-1, 0-2, 1-3, 2-4, 3-5, 4-5" + RESET);
        System.out.println(DIM + "  Estrutura: dois caminhos paralelos do 0 ao 5" + RESET);
        System.out.println();

        System.out.println(BOLD + "  [DFS Recursiva] " + RESET + "a partir do vértice 0:");
        long t1 = System.nanoTime();
        g1.dfs(0);
        long t1f = System.nanoTime() - t1;

        System.out.println();
        System.out.println(BOLD + "  [DFS Iterativa] " + RESET + "a partir do vértice 0:");
        long t2 = System.nanoTime();
        g1.dfsIterativa(0);
        long t2f = System.nanoTime() - t2;

        System.out.println();
        System.out.println(BLUE + "  ── Detalhes do Exemplo 1 " + RESET);
        info("Vértices totais", "6");
        info("Arestas totais", "6");
        info("Grafo conectado", g1.isConnected() ? GREEN + "Sim" + RESET : RED + "Não ✗" + RESET);
        info("Tempo recursivo", String.format("%,d ns", t1f));
        info("Tempo iterativo", String.format("%,d ns", t2f));
        detalhe("Ambas as versões visitam os mesmos vértices, mas pela ordem da pilha.");

        // ════════════════════════════════════════════════════════════════════
        // EXEMPLO 2: Grafo em árvore com 8 vértices
        // ════════════════════════════════════════════════════════════════════
        secao("Exemplo 2 - Grafo em árvore com 8 vértices");

        DFS_Demo g2 = new DFS_Demo(8);
        g2.addEdge(0, 1); g2.addEdge(0, 2);
        g2.addEdge(1, 3); g2.addEdge(1, 4);
        g2.addEdge(2, 5); g2.addEdge(2, 6);
        g2.addEdge(3, 7);

        System.out.println(DIM + "  Formato: árvore binária com raiz no 0" + RESET);
        System.out.println(DIM + "  Arestas: 0-1, 0-2, 1-3, 1-4, 2-5, 2-6, 3-7" + RESET);
        System.out.println();

        System.out.println(BOLD + "  [DFS Recursiva] " + RESET + "a partir do vértice 0:");
        long t3 = System.nanoTime();
        g2.dfs(0);
        long t3f = System.nanoTime() - t3;

        System.out.println();
        System.out.println(BOLD + "  [DFS Iterativa] " + RESET + "a partir do vértice 0:");
        long t4 = System.nanoTime();
        g2.dfsIterativa(0);
        long t4f = System.nanoTime() - t4;

        System.out.println();
        System.out.println(BLUE + "  ── Detalhes do Exemplo 2 " + RESET);
        info("Vértices totais", "8");
        info("Arestas totais", "7");
        info("Grafo conectado", g2.isConnected() ? GREEN + "Sim" + RESET : RED + "Não ✗" + RESET);
        info("Tempo recursivo", String.format("%,d ns", t3f));
        info("Tempo iterativo", String.format("%,d ns", t4f));
        detalhe("A DFS explora subárvore esquerda completamente antes da direita.");

        // ════════════════════════════════════════════════════════════════════
        // ANÁLISE EMPÍRICA
        // ════════════════════════════════════════════════════════════════════
        secao("Análise Empírica - Tempo de Execução por Tamanho");
        System.out.println(DIM + "  Grafo linear (cadeia): 0─1─2─...─n  (pior caso para recursão)" + RESET);
        System.out.println(DIM + "  Cada medição é a média de 5 execuções" + RESET);
        System.out.println();

        System.out.printf("  " + BOLD + "%-12s  %-14s  %-12s  %s%n" + RESET,
                "Vértices", "Tempo (ns)", "Tempo (µs)", "Escala visual");
        linha('─', 55);

        int[] tamanhos = {10, 100, 500, 1000, 2000};

        long[] tempos = new long[tamanhos.length];
        for (int i = 0; i < tamanhos.length; i++) {
            DFS_Demo g = new DFS_Demo(tamanhos[i]);
            for (int j = 0; j < tamanhos[i] - 1; j++) g.addEdge(j, j + 1);

            long soma = 0;
            for (int r = 0; r < 5; r++) soma += g.medirTempo(0);
            tempos[i] = soma / 5;
        }

        long maxTempo = Arrays.stream(tempos).max().getAsLong();
        int barraMax = 20;

        for (int i = 0; i < tamanhos.length; i++) {
            int barras = maxTempo > 0 ? (int) (tempos[i] * barraMax / maxTempo) : 0;
            String barra = GREEN + "█".repeat(Math.max(1, barras)) + RESET;
            System.out.printf("  %-12d  %-14s  %-12s  %s%n",
                    tamanhos[i],
                    String.format("%,d", tempos[i]),
                    String.format("%.2f", tempos[i] / 1000.0),
                    barra);
        }

        // ════════════════════════════════════════════════════════════════════
        // RESUMO FINAL
        // ════════════════════════════════════════════════════════════════════
        cabecalho("RESUMO FINAL");

        System.out.println(BOLD + "  Complexidade Teórica" + RESET);
        info("Tempo", "O(V + E)  ->  visita cada vértice e aresta uma vez");
        info("Espaço", "O(V)      ->  vetor de visitados + pilha de recursão");
        System.out.println();

        System.out.println(BOLD + "  Comparativo das Versões" + RESET);
        System.out.printf("  " + CYAN + "%-22s  %-15s  %-15s%n" + RESET,
                "Característica", "Recursiva", "Iterativa");
        linha('─', 55);
        System.out.printf("  %-22s  " + GREEN + "%-15s" + RESET + "  " + GREEN + "%-15s%n" + RESET,
                "Complexidade", "O(V + E)", "O(V + E)");
        System.out.printf("  %-22s  " + RED + "%-15s" + RESET + "  " + GREEN + "%-15s%n" + RESET,
                "Grafos grandes", "Risco de overflow", "Seguro");
        System.out.printf("  %-22s  " + YELLOW + "%-15s" + RESET + "  " + GREEN + "%-15s%n" + RESET,
                "Robustez", "Limitada", "Alta");

        System.out.println();
        System.out.println(BOLD + "  Aplicações Reais" + RESET);
        System.out.println("  " + GREEN + "OK" + RESET + " Detecção de ciclos em grafos");
        System.out.println("  " + GREEN + "OK" + RESET + " Ordenação topológica (dependências)");
        System.out.println("  " + GREEN + "OK" + RESET + " Resolução de labirintos e puzzles");
        System.out.println("  " + GREEN + "OK" + RESET + " Análise de redes e compiladores");

        System.out.println();
        linha('═', 55);
        System.out.println(DIM + "  Análise e Projeto de Algoritmos · DFS em Java" + RESET);
        linha('═', 55);
        System.out.println();
    }
}