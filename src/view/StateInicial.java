package view;

import control.SingletonUser;

public class StateInicial extends StateView {

    public StateInicial(){
        SingletonUser.setUser(null);
    }
    @Override
    public void draw() {
        System.out.print(Layouts.MainMenu.getLayout());    
        byte option = lerOpcao();

        if(option == 0){
            context.changeState(new StateLogin());
        }else if(option == 1){
            context.changeState(new StateCreateAccount());
        }else if(option != 2){
            context.changeState(new StateInicial());
        }

    }


    
}
