package classes_interfaces.criterios;

import interfaces.Interface_Criterio;
import modificacoes.Produto;

public class estoque_decre implements Interface_Criterio{

    @Override
    public boolean criterio(Produto p1, Produto p2) {
        return p1.getQtdEstoque() > p2.getQtdEstoque();
    }
    
}
