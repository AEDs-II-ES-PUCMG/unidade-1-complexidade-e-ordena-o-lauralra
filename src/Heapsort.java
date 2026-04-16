
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;

public class Heapsort<T extends Comparable<T>> implements IOrdenador<T> {

    private long comparacoes;
    private long movimentacoes;
    private LocalDateTime inicio;
    private LocalDateTime termino;

    void sort(int[] array) {

        // Criando outro vetor, com todos os elementos do vetor anterior reposicionados (uma posição a frente)
        // de forma a ignorar a posição zero	    
        int[] tmp = new int[array.length + 1];
        for (int i = 0; i < array.length; i++) {
            tmp[i + 1] = array[i];
        }

        // Construção do heap
        for (int tamHeap = (tmp.length - 1) / 2; tamHeap >= 1; tamHeap--) {
            restaura(tmp, tamHeap, tmp.length - 1);
        }

        //Ordenação propriamente dita
        int tamHeap = tmp.length - 1;

        troca(tmp,
                1, tamHeap--);
        while (tamHeap > 1) {
            restaura(tmp, 1, tamHeap);
            troca(tmp, 1, tamHeap--);
        }

        //Alterar o vetor para voltar à posição zero
        for (int i = 0; i < array.length; i++) {
            array[i] = tmp[i + 1];
        }
    }

    void restaura(int[] array, int i, int tamHeap) {

        int maior = i;
        int filho = getMaiorFilho(array, i, tamHeap);

        if (array[i] < array[filho]) {
            maior = filho;
        }
        if (maior != i) {
            troca(array, i, maior);
            if (maior <= tamHeap / 2) {
                restaura(array, maior, tamHeap);
            }
        }
    }

    int getMaiorFilho(int[] array, int i, int tamHeap) {

        int filho;

        if (2 * i == tamHeap || array[2 * i] > array[2 * i + 1]) {
            filho = 2 * i;
        } else {
            filho = 2 * i + 1;
        }
        return filho;
    }

    void troca(int[] array, int i, int j) {

        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    @Override
    public T[] ordenar(T[] dados) {
        return ordenar(dados, T::compareTo);
    }

    @Override
    public T[] ordenar(T[] dados, Comparator<T> comparador) {
        T[] dadosOrdenados = Arrays.copyOf(dados, dados.length);
        int tamanho = dadosOrdenados.length;

        inicio = LocalDateTime.now();

        for (int posReferencia = 1; posReferencia <= tamanho - 1; posReferencia++) {
            T valor = dadosOrdenados[posReferencia];
            int j = posReferencia - 1;
            comparacoes++;
            while (j >= 0 && comparador.compare(valor, dadosOrdenados[j]) < 0) {
                j--;
                comparacoes++;
            }

            copiarDados(j + 1, posReferencia, dadosOrdenados);
            dadosOrdenados[j + 1] = valor;

        }
        termino = LocalDateTime.now();

        return dadosOrdenados;
    }

    private void copiarDados(int inicio, int fim, T[] vet) {
        for (int i = fim; i > inicio; i--) {
            movimentacoes++;
            vet[i] = vet[i - 1];
        }
    }

    public long getComparacoes() {
        return comparacoes;
    }

    public long getMovimentacoes() {
        return movimentacoes;
    }

    public double getTempoOrdenacao() {
        return Duration.between(inicio, termino).toMillis();
    }

}
