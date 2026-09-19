package view;

public class StateMain extends StateView{
    public StateMain(){
        super();
        
        if(!singlePwd.getPwd().contains("Início")){
            singlePwd.push("Início");  
        }        
        System.out.println(singlePwd.getPwd());
    }
    @Override
    public void draw() {
        System.out.print(Layouts.UserMain.getLayout());
        byte option = lerOpcao();

        if(option == 0){
            context.changeState(new StateAreaUser());
        }else if(option == 1){
            //context.changeState(StateQuestions);
            avisarEmConstrucao();
            context.changeState(new StateMain());
        }else if(option == 2){
            context.changeState(new StateInicial());
        }else{
            context.changeState(new StateMain());
        }
        
    }
    
}
