package model;

import control.Controller;
import model.AcessDataBase.ArquivoUser;

public class ManagerEntities{
    ArquivoUser arqUsers;
    private Controller control;
    public ManagerEntities(ArquivoUser arqUsers) throws Exception{
        this.arqUsers = arqUsers;
    }

    public void setControl(Controller control) {
        this.control = control;
        arqUsers.setControll(control);
    }

    public ArquivoUser getArqUsers() {
        return arqUsers;
    }
}