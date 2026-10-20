package view;

public class StateAreaUser extends StateView {
    public StateAreaUser(){
        super(true);
        if(!singlePwd.getPwd().contains("Minha Área")){
            singlePwd.push("Minha Área");  
        }
        System.out.println(singlePwd.getPwd());      
    }

    @Override
    public void draw() {
        System.out.print(Layouts.UserArea.getLayout());
        byte option = lerOpcao();

        if(option == 0){
            context.changeState(new StateDataUser());
        }else if(option == 1){
            context.changeState(new StateQuestionsUser());
        }else if(option == 2){
            context.changeState(new StateMyResponses());
        } else if(option == 3){
            context.changeState(new StateMyRatings());
        }else if(option == 4){
            singlePwd.pop();
            context.changeState(new StateMain());
        }else{
            context.changeState(new StateAreaUser());
        }
    }
    
}
