package view;

import Security.Crypt;
import control.Controller;
import control.SingletonUser;
import model.User;

public class StateLogin extends StateView {

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
                User user = control.readUser(email, senha);  
                if(user != null){
                    SingletonUser.setUser(user);
                    context.changeState(new StateMain());
                }else{
                    System.out.println("Email ou senha incorretos!");
                    System.out.println("   [0]  Tentar novamente");
                    System.out.println("   [1]  Recuperar senha");
                    System.out.println("   [2]  Retornar ao menu inicial");
                    byte opcao = lerOpcao();

                    if(opcao == 0){
                        context.changeState(new StateLogin());
                    }else if(opcao == 1 && control.findUser(email)){

                        int count = 3;                        
                        String vals [] = control.recoveryQuestion(email);
                        int hash = Integer.parseInt(vals[1]); 
                        System.out.println(vals[0] + " ?");
                        int hashDigitado = 0;
                        
                        System.out.println("Número de tentativas " + count);
                        while(count > 0){
                            hashDigitado = Controller.hashRespostaSecreta(scan.nextLine());
                            if(hash == hashDigitado){
                                count = -1;
                            }else{                            
                                System.out.println("Reposta incorreta! Número de tentativas " + --count);
                            }
                        }

                        if(count != 0 && hash == hashDigitado){
                            System.out.println("Digite a nova senha: ");
                            String novasenha = Crypt.getSHA256(scan.nextLine().toLowerCase()); 
                            
                            if(control.updateSenhaUser(email,novasenha)){
                                context.changeState(new StateInicial());
                            }else{
                                context.reportFail(new Exception("Ocorreu um erro durante a atualização!"));
                            }
                        }else{
                            context.changeState(new StateInicial());
                        }
                    }else{
                        context.changeState(new StateInicial());
                    }
                }
            
            } catch (Exception e) {                
                context.reportFail(e);
            }
        }        
    } 
            
}
