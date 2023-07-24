package interfaces;

import java.util.List;
import modificacoes.Produto;

public interface Interface_Ordenacao<Criterio> {
    void ordenar(List<Produto> produtos, Criterio criterio);

}
