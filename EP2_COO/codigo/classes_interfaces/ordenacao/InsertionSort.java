package classes_interfaces.ordenacao;

import java.util.List;

import interfaces.Interface_Ordenacao;
import modificacoes.Produto;
import interfaces.Interface_Criterio;


public class InsertionSort<Criterio> implements Interface_Ordenacao<Criterio> {

    public void ordenar(List<Produto> produtos, Criterio criterio) {
        int number = produtos.size();
        for(int i = 1; i < number; i++){
            Produto prox = produtos.get(i);
            int ant = i - 1;
            while (ant >= 0 && criterio.criterio(prox, produtos.get(ant))) {
                produtos.set(j + 1, produtos.get(ant));
                ant--;
            }

        }
    }
}
