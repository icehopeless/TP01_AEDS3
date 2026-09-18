package view;

public class StateAreaUser extends StateView {
    public StateAreaUser(){
        super();
        if(!singlePwd.getPwd().contains("Minha Área")){
            singlePwd.push("Minha Área");  
        }
        System.out.println(singlePwd.getPwd());      
    }

    @Override
    public void draw() {
        System.out.print(Layouts.UserArea.getLayout());
        byte option = scan.nextByte();
        scan.nextLine();

        if(option == 0){
            context.changeState(new StateDataUser());   
        }else if(option == 1){
        }else if(option == 2){
        }else if(option == 3){
        }else if(option == 4){
            singlePwd.pop();
            context.changeState(new StateMain());            
        }
    }
    
}
