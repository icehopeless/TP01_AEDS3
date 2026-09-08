package view;

import java.util.Scanner;



public class StateInicial extends StateView {
    Scanner scan;

    public StateInicial(){
        clearScreen();
        scan = StateView.getTheSameScan();
    }

    @Override
    public void draw() {
        System.out.print(Layouts.MainMenu.getLayout());    
        short op = scan.nextShort();
        scan.nextLine();
        
        if(op == 0){
            context.changeState(new StateLogin());
        }
        if(op == 1){
            context.changeState(new StateCreateAccount());
        }
    }
    
}
