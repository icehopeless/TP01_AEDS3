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
        System.out.println("PERGUNTA:");
        System.out.println(question.showMode());
        System.out.println("RESPOSTA:");
        System.out.println(response.showMode());
        System.out.println("(A) concordar com a resposta");
        System.out.println("(B) disconcordar com a resposta\n");

        System.out.println("(R) retornar ao menu anterior");
        System.out.print(Layouts.LINECOMAND.getLayout());
        byte op =  lerOpcao();

        try {            
            switch (op) {
                case 0:
                    if(!control.isExistAvalaicaoUserQuestion(SingletonUser.getInstanceUser().getId())){
                        control.addAvaliacao(new Avaliacao(0,SingletonUser.getInstanceUser().getIdUsuario(),response.getId(),true));
                        control.incrementAvaliacao(response.getId());
                        control.incrementNota(response.getId());
                        System.out.println("ew");
                    }else{
                        context.reportFail(new Exception("Resposta já avaliada anteriormente"));
                    }
                    break;
                case 1:
                    if(!control.isExistAvalaicaoUserQuestion(SingletonUser.getInstanceUser().getId())){
                        control.addAvaliacao(new Avaliacao(0,SingletonUser.getInstanceUser().getIdUsuario(),response.getId(),false));
                        control.incrementAvaliacao(response.getId());
                        control.decrementNota(response.getId());
                    }else{
                        context.reportFail(new Exception("Resposta já avaliada anteriormente"));
                    }
                    break;                
            
                default:                
                    singlePwd.pop();
                    break;
            }
        } catch (Exception e) {
            context.reportFail(e);
        }
        
    }
}
