package view;


import Security.Crypt;
import control.Controller;
import model.User;

public class StateCreateAccount extends StateView {

    @Override
    public void draw() {
        System.out.print("Deseja continuar (S/N) : ");

        if (!scan.nextLine().equalsIgnoreCase("S")) {
            context.changeState(new StateInicial());
        } else {
            System.out.println(Color.GREEN.getCode() + " Digite seu email:");
            System.out.print(Layouts.LINECOMAND.getLayout());
            String email = scan.nextLine();

            if (!control.findUser(email)) {

                System.out.println(Color.GREEN.getCode() + " Digite seu nome:");
                System.out.print(Layouts.LINECOMAND.getLayout());
                String name = scan.nextLine();

                System.out.println(Color.GREEN.getCode() + " Digite sua senha:");
                System.out.print(Layouts.LINECOMAND.getLayout());
                String hashPass = Crypt.getSHA256(scan.nextLine().toLowerCase());

                System.out.println(Color.GREEN.getCode() + " Digite sua pergunta secreta:");
                System.out.print(Layouts.LINECOMAND.getLayout());
                String pergunta = scan.nextLine();

                System.out.println(Color.GREEN.getCode() + " Digite sua resposta secreta");
                System.out.print(Layouts.LINECOMAND.getLayout());
                String resposta = scan.nextLine();

                User user = new User(0, name, email, hashPass, pergunta, Controller.hashRespostaSecreta(resposta));
                try {
                    control.addUser(user);
                    context.changeState(new StateInicial());
                } catch (Exception e) {
                    context.reportFail(e);
                }
            }else{
                System.out.println("Email já cadastrado tente novamente!");
                System.out.println("Precione qualer tecla para retornar...");
                scan.nextLine();
                context.changeState(new StateInicial());
            }
        }
    }

}
