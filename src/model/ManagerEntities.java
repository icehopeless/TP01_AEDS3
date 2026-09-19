package model;

import control.Controller;
import model.AcessDataBase.ArquivoUser;
import model.AcessDataBase.ArquivoPergunta;

public class ManagerEntities{
    ArquivoUser arqUsers;
    ArquivoPergunta arqPergunta;
    private Controller control;
    public ManagerEntities(ArquivoUser arqUsers, ArquivoPergunta arqPergunta) throws Exception{
        this.arqUsers = arqUsers;
        this.arqPergunta = arqPergunta;
    }

    public void setControl(Controller control) {
        this.control = control;
        arqUsers.setControll(control);
    }

    public ArquivoUser getArqUsers() {
        return arqUsers;
    }

    public ArquivoPergunta getArqPergunta(){
        return arqPergunta;
    }
}