package view;

import java.util.Scanner;
import Security.Crypt;
import model.User;

public class StateLogin extends StateView {

    private Scanner scan;
    public StateLogin(){
        scan = StateView.getTheSameScan();
    }   

    @Override
    public void draw() {
        System.out.print("Deseja continuar (S/N) : ");

        if(!scan.nextLine().equalsIgnoreCase("S")){
            context.changeState(new StateInicial());
        }else{
            System.out.println(Color.GREEN.getCode() + " Digite seu email:");
            System.out.print(Layouts.LINECOMAND.getLayout());
            String email = scan.nextLine();

            System.out.println(Color.GREEN.getCode() + " Digite sua senha:");
            System.out.print(Layouts.LINECOMAND.getLayout());
            String senha = Crypt.getSHA256(scan.nextLine().toLowerCase());
            
            try {
                User user = control.searchUser(email, senha);    
            } catch (Exception e) {
                context.reportFail(e);
            }
        }        
    } 
            
}
