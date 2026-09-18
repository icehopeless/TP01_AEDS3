package view;

import Security.Crypt;
import control.SingletonUser;
import model.User;

public class StateDataUser extends StateView {
    private User user;
    public StateDataUser(){
        super();
        if(!singlePwd.getPwd().contains("Meus dados")){
            singlePwd.push("Meus dados");  
        }

        System.out.println(singlePwd.getPwd()); 
        try {
             user = SingletonUser.getInstanceUser();
        } catch (Exception e) {
          context.reportFail(e);
        }    
    }

    public String getDataUser(){
        String nameData = " "+ Color.PURPLE.getCode() + "Nome : " + user.getNome() + Color.GREEN.getCode() +" \r\n";
        String emailData = " "+ Color.PURPLE.getCode() + "Email : " + user.getEmail() + Color.GREEN.getCode() +" \r\n";
        String perguntaData = " "+ Color.PURPLE.getCode() + "Pergunta : " + user.getPerguntaSecreta() + Color.GREEN.getCode() +" \r\n";

        int maior = nameData.length() > emailData.length()? nameData.length() : emailData.length();
        maior = perguntaData.length() > maior ? perguntaData.length() : maior;
        
        String top = Layouts.DesignTop.getLayout();
        String bottom = Layouts.DesignBottom.getLayout();

        if(maior > top.length()){
            top = top.substring(0,2) + "─".repeat( maior - top.length()) + top.substring(2,top.length() - 1) + "\r\n";
            bottom = bottom.substring(0,2) + "─".repeat( maior - bottom.length()) + bottom.substring(2,bottom.length() - 1) + "\r\n";
        }
        
        return top + nameData + emailData + perguntaData + bottom;
    }

    @Override
    public void draw() {
        System.out.println(getDataUser());
        System.out.print(Layouts.UserData.getLayout());
        byte option = scan.nextByte();
        scan.nextLine();

        if(option == 0){ 
            System.out.println("Digite o novo nome: ");
            String novonome = scan.nextLine();
            
            user.setNome(novonome);
            if(control.updateUser(user)){
                context.changeState(new StateDataUser());
            }else{
                context.reportFail(new Exception("Ocorreu um erro durante a atualização!"));
            }
        }else if(option == 1){
            System.out.println("Digite o novo email: ");
            String email = scan.nextLine();
            
            user.setEmail(email);
            if(control.updateUser(user)){
                context.changeState(new StateDataUser());
            }else{
                context.reportFail(new Exception("Ocorreu um erro durante a atualização!"));
            }
        }else if(option == 2){
            System.out.println("Digite a nova senha: ");
            String novasenha = Crypt.getSHA256(scan.nextLine().toLowerCase());
            
            user.setHashSenha(novasenha);
            if(control.updateUser(user)){
                context.changeState(new StateDataUser());
            }else{
                context.reportFail(new Exception("Ocorreu um erro durante a atualização!"));
            }
        }else if(option == 3){
            System.out.println("Digite a nova pergunta secreta: ");
            String perguntaSecreta = scan.nextLine();
            
            System.out.println("Digite a nova resposta secreta: ");
            String respostaSecreta = scan.nextLine();
            user.setPerguntaSecreta(perguntaSecreta);
            user.setHashRespostaSecreta(Math.abs(respostaSecreta.hashCode()));

            if(control.updateUser(user)){
                context.changeState(new StateDataUser());
            }else{
                context.reportFail(new Exception("Ocorreu um erro durante a atualização!"));
            }
        }else if(option == 4){
            System.out.println("Tem certeza que deseja apagar sua conta (s/n) ? ");

            if(scan.nextLine().equalsIgnoreCase("S")){
                System.out.println("Tem certeza mesmo que deseja apagar sua conta (s/n) ? ");
                if(scan.nextLine().equalsIgnoreCase("S")){
                    System.out.println("Não tem volta heim! Tem certeza mesmo (s/n) ? ");
                    if(scan.nextLine().equalsIgnoreCase("S")){
                        try {
                            if(control.deleteUser(user.getId())){
                                context.changeState(new StateInicial());
                            }else{
                                throw new Exception("Ocorreu um erro durante a exclusão");
                            }
                        } catch (Exception e) {
                            context.reportFail(e);
                        }
                    }
                }
            }
        }else if(option == 5){
            singlePwd.pop();
            context.changeState(new StateAreaUser());            
        }
    }
}
