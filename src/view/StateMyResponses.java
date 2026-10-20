package view;

import java.util.ArrayList;

import control.SingletonUser;
import model.Pergunta;
import model.Resposta;

public class StateMyResponses extends StateView{

    public StateMyResponses(){

        super(false);

        if(!singlePwd.ultimaPagina("Minhas Respostas")){
            singlePwd.push("Minhas Respostas");
        }
        
        System.out.println(singlePwd.getPwd());
    }

    @Override
    public void draw() {
        ArrayList<Resposta> respostas = new ArrayList<>();

        System.out.println(Layouts.DesignTop.getLayout());
        try {
            respostas = control.readRespostasByUser(SingletonUser.getInstanceUser().getId());
            if(respostas.isEmpty()){
                System.out.println("|  Você ainda não respondeu nenhuma pergunta.");
            }
            for(int i = 0; i < respostas.size(); i++){
                Resposta r = respostas.get(i);
                Pergunta p = control.readPergunta(r.getIdPergunta());

                String estado = r.isAtiva() ? "" : " [ARQUIVADA]";
                System.out.println("|  (" + (i + 1) + ")" + estado + " Pergunta: " + p.getPergunta().split("\n")[0]);

                System.out.println("|      Sua resposta: " + r.getResposta().split("\n")[0]);

                System.out.println("|");
            }
        } catch (Exception e) {
            System.out.println("|  " + e.getMessage());
        }

        System.out.println("|  Digite o número da resposta para abri-la, ou R para retornar");
        System.out.println(Layouts.DesignBottom.getLayout());
        System.out.print(Layouts.LINECOMAND.getLayout());

        String opcao = scan.nextLine().strip().toUpperCase();
        int numero = paraNumero(opcao);

        if(opcao.equals("R")){
            singlePwd.pop();
            context.changeState(new StateAreaUser());
        }else if(numero >= 1 && numero <= respostas.size()){
            abrir(respostas.get(numero - 1));
        }else{
            context.changeState(new StateMyResponses());
        }
    }

    // Mostra pergunta + resposta
    // como a resposta é do próprio usuário, a única opção lá é arquivar
    private void abrir(Resposta r){
        try {
            Pergunta p = control.readPergunta(r.getIdPergunta());
            context.changeState(new StateShowResponse(p, r, () -> new StateMyResponses()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            pausar();
            context.changeState(new StateMyResponses());
        }
    }
}
