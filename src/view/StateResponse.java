package view;


import Security.Crypt;
import control.Controller;
import model.User;

public class StateResponse extends StateView {
    private Pergunta question;
    

    public StateResponse(Pergunta p){
        this.question = p;
        
        if(!singlePwd.getPwd().contains("Responder")){
            singlePwd.push("Responder");  
        }
    }


    @Override
    public void draw() {
        System.out.println(Layouts.DesignTop.getLayout());
        System.out.println(question.showMode());
        System.out.println(Layouts.DesignBottom.getLayout());
        System.out.println(Layouts.LINECOMAND.getLayout());
        String textResponse = lerTexto();

        control.addResposta(question.getId(),textResponse);
    }
}
