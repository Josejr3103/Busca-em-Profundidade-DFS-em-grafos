# Busca em Profundidade (DFS) em Grafos

Este projeto demonstra a implementação da Busca em Profundidade (DFS) em Java, incluindo versões recursiva e iterativa, análise de conectividade e medição de tempo de execução.

## Estrutura do Projeto

- `DFS.java`
  - Classe que implementa:
    - grafo não direcionado usando lista de adjacência
    - DFS recursiva
    - DFS iterativa usando pilha explícita
    - verificação de conectividade
    - medida de tempo de execução da DFS
  - Possui `main` com exemplos e análise empírica de tempo para grafos lineares.

- `DFS_Demo.java`
  - Versão demonstrativa com saída formatada e colorida no terminal.
  - Implementa:
    - DFS recursiva com estatísticas de visitas e passagens
    - DFS iterativa com pilha
    - verificação de conectividade
    - medição de tempo e apresentação de resultados em tabela

## Requisitos

- Java JDK instalado (versão 8 ou superior)
- `javac` e `java` disponíveis no `PATH`

## Instalação

1. Instale o projeto:
   ```powershell
   git clone https://github.com/Josejr3103/Busca-em-Profundidade-DFS-em-grafos.git
   ```
   
2. Abra a pasta do projeto:
   ```powershell
   cd ..\Busca-em-Profundidade-DFS-em-grafos
   ```

3. Compile os arquivos Java:
   ```powershell
   javac DFS.java DFS_Demo.java
   ```

## Como rodar

- Para executar a demonstração simples da classe `DFS`:
  ```powershell
  java DFS
  ```

- Para executar a demonstração completa e formatada em `DFS_Demo`:
  ```powershell
  java DFS_Demo
  ```

## Como funciona

### Modelo de grafo

O grafo é representado por uma lista de adjacência:
- `List<List<Integer>> adjacencyList`
- cada vértice tem uma lista de vizinhos
- arestas são adicionadas em ambas as direções (`source -> destination` e `destination -> source`), pois o grafo é não direcionado

### DFS recursiva

A DFS recursiva visita um vértice e, em seguida, explora recursivamente cada vizinho não visitado.

Passos principais:
1. marcar o vértice atual como visitado
2. imprimir ou processar o vértice
3. para cada vizinho não visitado, chamar a função recursiva novamente

No código, a função `dfsRecursive` percorre todos os vizinhos não visitados em profundidade.

### DFS iterativa

A DFS iterativa usa uma pilha explícita para simular o comportamento da recursão.

Passos principais:
1. empilhar o vértice inicial
2. enquanto a pilha não estiver vazia:
   - remover o vértice do topo
   - se não foi visitado, marcar como visitado e processar
   - empilhar os vizinhos não visitados em ordem reversa para manter a ordem natural de visita

A versão iterativa é mais segura para grafos grandes porque evita limite de recursão da JVM.

### Verificação de conectividade

Para verificar se o grafo é conectado, o código executa uma DFS a partir do vértice `0` e depois confere se todos os vértices foram visitados.

Se qualquer vértice permanecer não visitado, o grafo não é conectado.

### Medição de tempo

O projeto mede o tempo de execução em nanossegundos usando `System.nanoTime()`.

- `DFS.measureDFSTime(int)` mede a DFS recursiva em `DFS.java`
- `DFS_Demo.medirTempo(int)` mede a DFS recursiva simples em `DFS_Demo.java`

## Resultados esperados

O programa mostra:
- ordem de visita da DFS recursiva e iterativa
- se o grafo é conectado
- comparação de tempos para grafos de tamanhos crescentes (linhares)
- resumo de complexidade teórica: `O(V + E)`

## Observações

- A complexidade de tempo da DFS é `O(V + E)`.
- A complexidade de espaço é `O(V)` devido ao vetor de visitados e à pilha de recursão/estrutural.
- A DFS recursiva pode estourar a pilha em grafos muito grandes ou em cadeias longas.
- A DFS iterativa é a alternativa mais robusta para evitar `StackOverflowError`.
