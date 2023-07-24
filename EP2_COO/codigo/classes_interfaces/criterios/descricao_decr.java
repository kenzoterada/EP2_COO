package classes_interfaces.criterios;

import interfaces.Interface_Criterio;
import modificacoes.Produto;

public class descricao_decr implements Interface_Criterio{

    @Override
    public boolean criterio(Produto p1, Produto p2) {
        return p1.getDescricao().compareToIgnoreCase(p2.getDescricao()) > 0;
    }

    
}
    
