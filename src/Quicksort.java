import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;

public class Quicksort<T extends Comparable<T>> implements IOrdenador<T> {

    private long comparacoes;
    private long movimentacoes;
    private LocalDateTime inicio;
    private LocalDateTime termino;

    public void sort(int[] array) {
        quicksort(array, 0, array.length - 1);
    }

    /**
     * Algoritmo de ordenação Quicksort.
     *
     * @param int esq: início da partição a ser ordenada
     * @param int dir: fim da partição a ser ordenada
     */
    private void quicksort(int[] array, int esq, int dir) {

        int part;
        if (esq < dir) {
            part = particao(array, esq, dir);
            quicksort(array, esq, part - 1);
            quicksort(array, part + 1, dir);
        }
    }

    private int particao(int[] array, int inicio, int fim) {

        int pivot = array[fim];
        int part = inicio - 1;
        for (int i = inicio; i < fim; i++) {
            if (array[i] < pivot) {
                part++;
                swap(array, part, i);
            }
        }
        part++;
        swap(array, part, fim);
        return (part);
    }

    private void swap(int[] array, int i, int j) {

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
