package view;


import Security.Crypt;
import control.Controller;
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
    }

    @Override
    public void draw() {

        System.out.println(Layouts.DesignTop.getLayout());
        System.out.println("PERGUNTA:");
        System.out.println(p.showMode());
        System.out.println("RESPOSTA:");
        System.out.println(r.showMode());
        System.out.println("(A) concordar com a resposta");
        System.out.println("(B) disconcordar com a resposta\n");

        System.out.println("(R) retornar ao menu anterior");
        System.out.print(Layouts.LINECOMAND.getLayout());
        char op =  lerOpcao();

        switch (op) {
            case 'A':
                if(!control.isExistAvalaiçãoUserQuestion(SingletonUser.getInstanceUser().getId())){
                    control.incrementAvaliacao(response.getId());
                    control.incrementNota(response.getId());
                }else{
                    context.reportFail(new Exception("Resposta já avaliada anteriormente"));
                }
                break;
            case 'B':
                if(!control.isExistAvalaiçãoUserQuestion(SingletonUser.getInstanceUser().getId())){
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
        
    }
}
