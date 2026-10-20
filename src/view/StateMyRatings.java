package view;

import java.util.ArrayList;

import model.Avaliacao;
import model.Resposta;

public class StateMyRatings extends StateView {

    public StateMyRatings(){
        super(false);

        if(!singlePwd.ultimaPagina("Minhas avaliações")){
            singlePwd.push("Minhas avaliações");
        }

        System.out.println(singlePwd.getPwd());
    }

    @Override
    public void draw() {
        ArrayList<Avaliacao> avaliacoes = new ArrayList<>();

        System.out.println(Layouts.DesignTop.getLayout());
        try {
            avaliacoes = control.listarMinhasAvaliacoes();

            if(avaliacoes.isEmpty()){
                System.out.println("|  Você ainda não avaliou nenhuma resposta.");
            }

            for(int i = 0; i < avaliacoes.size(); i++){
                Avaliacao a = avaliacoes.get(i);
                Resposta r = control.readResposta(a.getIdResposta());

                String voto = a.getItSelfAvalaiacao() ? "POSITIVA" : "NEGATIVA";
                
                System.out.println("|  (" + (i + 1) + ") " + voto + " - " + r.getResposta().split("\n")[0]);
            }
        } catch (Exception e) {
            System.out.println("|  " + e.getMessage());
        }

        System.out.println("|");
        System.out.println("|  Digite o número da avaliação para abri-la, ou R para retornar");
        System.out.println(Layouts.DesignBottom.getLayout());
        System.out.print(Layouts.LINECOMAND.getLayout());

        String opcao = scan.nextLine().strip().toUpperCase();
        int numero = paraNumero(opcao);

        if(opcao.equals("R")){
            singlePwd.pop();
            context.changeState(new StateAreaUser());
        }else if(numero >= 1 && numero <= avaliacoes.size()){
            context.changeState(new StateShowRating(avaliacoes.get(numero - 1)));
        }else{
            context.changeState(new StateMyRatings());
        }
    }
}
