package view;

import control.SingletonUser;

public class StateInicial extends StateView {

    public StateInicial(){
        SingletonUser.setUser(null);
    }
    @Override
    public void draw() {
        System.out.print(Layouts.MainMenu.getLayout());    
        byte option = scan.nextByte();
        scan.nextLine();
        
        if(option == 0){
            context.changeState(new StateLogin());
        }
        if(option == 1){
            context.changeState(new StateCreateAccount());
        }

    }


    
}
