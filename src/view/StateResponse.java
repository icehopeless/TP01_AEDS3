package view;


import Security.Crypt;
import control.Controller;
import model.Pergunta;
import model.User;

public class StateResponse extends StateView {
    private Pergunta question;
    

    public StateResponse(Pergunta p){
        super(false);
        this.question = p;
        
        if(!singlePwd.getPwd().contains("Responder")){
            singlePwd.push("Responder");  
        }

              System.out.println(singlePwd.getPwd());
    }


    @Override
    public void draw() {
        
        System.out.println(Layouts.DesignTop.getLayout());
        try {
            System.out.println(question.showMode(control.readShowUser(question.getIdUsuario())));
        } catch (Exception e) {
            context.reportFail(e, isOriginState());
        }
        System.out.println(Layouts.DesignBottom.getLayout());

        String textResponse = lerTexto();

        
        try{
            control.addResposta(question.getId(),textResponse);
        }catch(Exception e){
            context.reportFail(e,isOriginState());
        }
        
        singlePwd.pop();
    }
}
