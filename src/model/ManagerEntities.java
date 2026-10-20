package model;

import control.Controller;
import model.AcessDataBase.ArquivoUser;
import model.AcessDataBase.ArquivoPergunta;
import model.AcessDataBase.ArquivoResposta;
import model.AcessDataBase.ArquivoAvaliacao;

public class ManagerEntities{
    private ArquivoUser arqUsers;
    private ArquivoPergunta arqPergunta;
    private ArquivoResposta arqResposta;
    private ArquivoAvaliacao arqAvaliacao;
    private Controller control;
    public ManagerEntities(ArquivoUser arqUsers, ArquivoPergunta arqPergunta, ArquivoResposta arqResposta, ArquivoAvaliacao arqAva) throws Exception{
        this.arqUsers = arqUsers;
        this.arqPergunta = arqPergunta;
        this.arqResposta = arqResposta;
        this.arqAvaliacao = arqAva;
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

    public ArquivoResposta getArqResposta(){
        return arqResposta;
    }

    public ArquivoAvaliacao getArqAvalaiacao(){
        return arqAvaliacao;
    }
}