package eetac.dsa.juego.root;

import eetac.dsa.model.EscenarioJSON;
import eetac.dsa.model.resultsserver.ResultLoginArgs;

/**
 * Created by oscar on 23/12/2017.
 */

public interface ResponseRest {
    void onGetLogingArgs(ResultLoginArgs resultLoginArgs);
    void onCambiarMapa(EscenarioJSON escenarioJSON, int x, int y);
}
