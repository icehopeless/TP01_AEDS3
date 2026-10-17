

import control.Controller;
import model.ManagerEntities;
import model.AcessDataBase.ArquivoUser;
import model.AcessDataBase.ArquivoPergunta;
import model.AcessDataBase.ArquivoResposta;
import view.ContextView;
import view.StateInicial;

public class Main{
  
    public static void main(String[] args) {
        try {
           new Controller(new ContextView(new StateInicial()),
            new ManagerEntities(new ArquivoUser(), new ArquivoPergunta(), new ArquivoResposta()));    
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}