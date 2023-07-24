package classes_interfaces.criterios;

import interfaces.Interface_Criterio;
import modificacoes.Produto;

public class preco_cre implements Interface_Criterio {

    @Override
    public boolean criterio(Produto p1, Produto p2) {
        return p1.getPreco() < p2.getPreco();
    }
    
}
