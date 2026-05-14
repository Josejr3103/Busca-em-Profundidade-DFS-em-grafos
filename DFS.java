import java.util.*;

public class DFS {

    private int vertices;
    private List<List<Integer>> adjacencyList;

    public DFS(int vertices) {
        this.vertices = vertices;
        adjacencyList = new ArrayList<>();
        for (int i = 0; i < vertices; i++) {
            adjacencyList.add(new ArrayList<>());
        }
    }

    public void addEdge(int source, int destination) {
        adjacencyList.get(source).add(destination);
        adjacencyList.get(destination).add(source); // grafo não direcionado
    }

    // DFS recursiva a partir de um vértice
    public void dfs(int startVertex) {
        boolean[] visited = new boolean[vertices];
        System.out.print("Ordem de visita DFS a partir do vértice " + startVertex + ": ");
        dfsRecursive(startVertex, visited);
        System.out.println();
    }

    private void dfsRecursive(int vertex, boolean[] visited) {
        visited[vertex] = true;
        System.out.print(vertex + " ");

        for (int neighbor : adjacencyList.get(vertex)) {
            if (!visited[neighbor]) {
                dfsRecursive(neighbor, visited);
            }
        }
    }

    // DFS iterativa (usando pilha explícita)
    public void dfsIterative(int startVertex) {
        boolean[] visited = new boolean[vertices];
        Stack<Integer> stack = new Stack<>();

        stack.push(startVertex);
        System.out.print("DFS iterativa a partir do vértice " + startVertex + ": ");

        while (!stack.isEmpty()) {
            int vertex = stack.pop();

            if (!visited[vertex]) {
                visited[vertex] = true;
                System.out.print(vertex + " ");

                // Adiciona vizinhos na pilha em ordem reversa para manter a ordem
                List<Integer> neighbors = adjacencyList.get(vertex);
                for (int i = neighbors.size() - 1; i >= 0; i--) {
                    if (!visited[neighbors.get(i)]) {
                        stack.push(neighbors.get(i));
                    }
                }
            }
        }
        System.out.println();
    }

    // Verifica conectividade do grafo
    public boolean isConnected() {
        boolean[] visited = new boolean[vertices];
        dfsRecursive(0, visited);
        for (boolean v : visited) {
            if (!v) return false;
        }
        return true;
    }

    // Mede o tempo de execução da DFS
    public long measureDFSTime(int startVertex) {
        boolean[] visited = new boolean[vertices];
        long start = System.nanoTime();
        dfsRecursive(startVertex, visited);
        long end = System.nanoTime();
        return end - start;
    }

    public static void main(String[] args) {
        System.out.println("=== Demonstração da Busca em Profundidade (DFS) ===\n");

        // Exemplo 1: Grafo simples com 6 vértices
        System.out.println("--- Exemplo 1: Grafo com 6 vértices ---");
        DFS grafo1 = new DFS(6);
        grafo1.addEdge(0, 1);
        grafo1.addEdge(0, 2);
        grafo1.addEdge(1, 3);
        grafo1.addEdge(2, 4);
        grafo1.addEdge(3, 5);
        grafo1.addEdge(4, 5);

        grafo1.dfs(0);
        grafo1.dfsIterative(0);
        System.out.println("Grafo conectado? " + grafo1.isConnected());

        // Exemplo 2: Grafo com 8 vértices
        System.out.println("\n--- Exemplo 2: Grafo com 8 vértices ---");
        DFS grafo2 = new DFS(8);
        grafo2.addEdge(0, 1);
        grafo2.addEdge(0, 2);
        grafo2.addEdge(1, 3);
        grafo2.addEdge(1, 4);
        grafo2.addEdge(2, 5);
        grafo2.addEdge(2, 6);
        grafo2.addEdge(3, 7);

        grafo2.dfs(0);
        grafo2.dfsIterative(0);

        // Análise empírica: medição de tempo
        System.out.println("\n--- Análise Empírica: Tempo de Execução ---");
        int[] sizes = {10, 100, 500, 1000, 2000, 5000};

        System.out.printf("%-15s %-20s%n", "Vértices", "Tempo (ns)");
        System.out.println("-".repeat(35));

        for (int size : sizes) {
            DFS grafoTeste = new DFS(size);
            // Cria grafo linear (pior caso para recursão)
            for (int i = 0; i < size - 1; i++) {
                grafoTeste.addEdge(i, i + 1);
            }

            // Média de 5 execuções
            long totalTime = 0;
            for (int run = 0; run < 5; run++) {
                totalTime += grafoTeste.measureDFSTime(0);
            }
            long avgTime = totalTime / 5;
            System.out.printf("%-15d %-20d%n", size, avgTime);
        }
    }
}
