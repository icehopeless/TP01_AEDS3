package view;


import Security.Crypt;
import control.Controller;
import model.Pergunta;
import model.User;

public class StateResponse extends StateView {
    private Pergunta question;
    

    public StateResponse(Pergunta p){
        this.question = p;
        
        if(!singlePwd.getPwd().contains("Responder")){
            singlePwd.push("Responder");  
        }

              System.out.println(singlePwd.getPwd());
    }


    @Override
    public void draw() {
        System.out.println(Layouts.DesignTop.getLayout());
        System.out.println(question.showMode());

        String textResponse = lerTexto();

        
        try{
            control.addResposta(question.getId(),textResponse);
        }catch(Exception e){
            context.reportFail(e);
        }
    }
}
