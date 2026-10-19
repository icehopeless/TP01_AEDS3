package view;

import model.Avaliacao;
import model.Pergunta;
import model.Resposta;

public class StateShowRating extends StateView {
    private Avaliacao avaliacao;

    public StateShowRating(Avaliacao a){
        super(false);

        this.avaliacao = a;

        if(!singlePwd.ultimaPagina("Avaliação")){
            singlePwd.push("Avaliação");
        }

        System.out.println(singlePwd.getPwd());
    }

    @Override
    public void draw() {

        System.out.println(Layouts.DesignTop.getLayout());
        
        try {
            Resposta r = control.readResposta(avaliacao.getIdResposta());
            Pergunta p = control.readPergunta(r.getIdPergunta());

            System.out.println(p.showMode(control.nomeUsuario(p.getIdUsuario())));
            System.out.println("|");
            System.out.println("|  RESPOSTA");
            System.out.println("|  --------");
            System.out.println(r.showMode(control.nomeUsuario(r.getIdUsuario())));
            System.out.println("|");
            String voto = avaliacao.getItSelfAvalaiacao() ? "POSITIVA (você concordou)" : "NEGATIVA (você discordou)";

            System.out.println("|  Sua avaliação: " + voto);
        } catch (Exception e) {
            System.out.println("|  " + e.getMessage());
        }

        System.out.println("|");
        System.out.println("|  [A] Cancelar avaliação");
        System.out.println("|");
        System.out.println("|  [R] Retornar ao menu anterior");
        System.out.println(Layouts.DesignBottom.getLayout());
        System.out.print(Layouts.LINECOMAND.getLayout());

        String opcao = scan.nextLine().strip().toUpperCase();

        if(opcao.equals("A")){
            try {
                control.cancelarAvaliacao(avaliacao);
                System.out.println("Avaliação cancelada. A nota da resposta foi ajustada.");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            pausar();
        }

        singlePwd.pop();
        context.changeState(new StateMyRatings());
    }
}
