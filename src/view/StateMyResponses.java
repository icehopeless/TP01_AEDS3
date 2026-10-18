package view;

import java.util.ArrayList;

import control.SingletonUser;
import model.Pergunta;
import model.Resposta;

public class StateMyResponses extends StateView{

    public StateMyResponses(){
        super(false);
        if(!singlePwd.getPwd().contains("Minhas Respostas")){
            singlePwd.push("Minhas Respostas");  
        }        
        System.out.println(singlePwd.getPwd());
    }

    @Override
    public void draw() {
        System.out.println(Layouts.DesignTop.getLayout());
        ArrayList<Resposta> respostas = new ArrayList<>();

        try {
            respostas = control.readRespostasByUser(SingletonUser.getInstanceUser().getIdUsuario());

            for(int i = 0; i < respostas.size(); i++){
                Resposta r = respostas.get(i);
                System.out.println("\n|  (" + (i + 1) + ") PERGUNTA: ");
                Pergunta pergunta = control.readPergunta(r.getIdPergunta());

                System.out.println(pergunta.showMode(control.readShowUser(pergunta.getIdUsuario()).split(";")[1]));
                System.out.println(r.showMode(SingletonUser.getInstanceUser().getNome()));
            }
        } catch (Exception e) {
            context.reportFail(e, isOriginState());
        }

        System.out.println(Layouts.DesignBottom.getLayout());
        System.out.println("Digite 1 para arquivar uma resposta, ou qualquer outra tecla para retornar   ...");

        if(lerOpcao() == 1){
            arquivar(respostas);
            System.out.println("Pressione Enter para continuar...");
            scan.nextLine();
            context.changeState(new StateMyResponses());
        }
            singlePwd.pop();
            context.changeState(new StateAreaUser());
        
    }


    private void arquivar(ArrayList<Resposta> respostas){
    try {
        System.out.println("Digite o numero da resposta (0 para cancelar):");
        int numero = lerOpcao();
        if(numero == 0){
            System.out.println("Operacao cancelada");
            return;
        }
        if(numero < 0 || numero > respostas.size()){
            System.out.println("num invalido");
            return;
        }

        Resposta r = respostas.get(numero - 1);
        System.out.println("Tem certeza que deseja arquivar (s/n) ? ");
        if(scan.nextLine().strip().equalsIgnoreCase("S")){
            if(control.arquivarResposta(r)){
                System.out.println("Resposta arquivada com sucesso!");
            }else{
                System.out.println("Ocorreu um erro durante o arquivamento!");
            }
        }else{
            System.out.println("Operacao cancelada.");
        }
    } catch (Exception e) {
        System.out.println(e.getMessage());
    }
}




}

