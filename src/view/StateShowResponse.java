package view;


import Security.Crypt;
import control.Controller;
import control.SingletonUser;
import model.Avaliacao;
import model.Pergunta;
import model.Resposta;
import model.User;

public class StateShowResponse extends StateView {
    private Pergunta question;
    private Resposta response;

    public StateShowResponse(Pergunta p, Resposta r){
        super(false);
        this.question = p;
        this.response = r;
         if(!singlePwd.getPwd().contains("Resposta")){
            singlePwd.push("Resposta");  
        }
        System.out.println(singlePwd.getPwd());
    }

    @Override
    public void draw() {

        System.out.println(Layouts.DesignTop.getLayout());

        try {
            System.out.println("|  PERGUNTA:");
            System.out.println(question.showMode(control.readShowUser(question.getIdUsuario()).split(";")[1]));
            System.out.println("|  RESPOSTA:");
            System.out.println(response.showMode(control.readShowUser(response.getIdUsuario()).split(";")[1]));    
        } catch (Exception e) {
            context.reportFail(e, isOriginState());
        }
        
        System.out.println("\n|  (0) concordar com a resposta");
        System.out.println("|  (1) disconcordar com a resposta\n");

        System.out.println("|  (2) retornar ao menu anterior");
        System.out.println(Layouts.DesignBottom.getLayout());

        System.out.print("\n" + Layouts.LINECOMAND.getLayout());
        byte op =  lerOpcao();

        try {            
            switch (op) {
                case 0:
                    if(!control.existAvalaicaoUserResponse(SingletonUser.getInstanceUser().getId(), response.getIdResposta())){
                        control.addAvaliacao(new Avaliacao(0,SingletonUser.getInstanceUser().getIdUsuario(),response.getId(),true));
                        control.incrementAvaliacao(response.getId());
                        control.incrementNota(response.getId());
                    }else{
                        context.reportFail(new Exception("Resposta já avaliada anteriormente"),isOriginState());
                    }
                    break;
                case 1:
                    if(!control.existAvalaicaoUserResponse(SingletonUser.getInstanceUser().getId(), response.getIdResposta())){
                        control.addAvaliacao(new Avaliacao(0,SingletonUser.getInstanceUser().getIdUsuario(),response.getId(),false));
                        control.incrementAvaliacao(response.getId());
                        control.decrementNota(response.getId());
                    }else{
                        context.reportFail(new Exception("Resposta já avaliada anteriormente"),isOriginState());
                    }
                    break;                
            
                default:                            
                    break;
            }
            singlePwd.pop();
        } catch (Exception e) {
            context.reportFail(e,isOriginState());
        }
        
    }
}
