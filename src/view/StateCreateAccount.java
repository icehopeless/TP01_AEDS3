package view;


import java.util.Scanner;

import Security.Crypt;
import model.User;

public class StateCreateAccount extends StateView {

    Scanner scan;
    public StateCreateAccount(){
        scan = StateView.getTheSameScan();
    }

    @Override
    public void draw() {
        System.out.print("Deseja continuar (S/N) : ");

        if(!scan.nextLine().equalsIgnoreCase("S")){
            context.changeState(new StateInicial());
        }else{
            System.out.println(Color.GREEN.getCode() + " Digite seu nome:");
            System.out.print(Layouts.LINECOMAND.getLayout());
            String name = scan.nextLine();
            
    
            System.out.println(Color.GREEN.getCode() + " Digite seu email:");
            System.out.print(Layouts.LINECOMAND.getLayout());
            String email = scan.nextLine();

    
            System.out.println(Color.GREEN.getCode() + " Digite sua senha:");
            System.out.print(Layouts.LINECOMAND.getLayout());
            String hashPass = Crypt.getSHA256(scan.nextLine().toLowerCase());

    
            System.out.println(Color.GREEN.getCode() + " Digite sua pergunta secreta:");
            System.out.print(Layouts.LINECOMAND.getLayout());
            String pergunta = scan.nextLine();
        
    
            System.out.println(Color.GREEN.getCode() + " Digite sua resposta secreta");
            System.out.print(Layouts.LINECOMAND.getLayout());
            String resposta = scan.nextLine();
            
            User user = new User(0, name, email, hashPass, pergunta, Math.abs(resposta.hashCode()));
            try {
                control.addUser(user);  
                context.changeState(new StateInicial());
            } catch (Exception e) {
                context.reportFail(e);
            }
        }
    }
    
}
