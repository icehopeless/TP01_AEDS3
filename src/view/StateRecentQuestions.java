package view;

import java.util.ArrayList;

import model.Pergunta;

public class StateRecentQuestions extends StateView {
    private int pagina; 

    public StateRecentQuestions(int pagina){

        super(false);

        this.pagina = pagina;

        if(!singlePwd.ultimaPagina("Perguntas mais recentes")){
            singlePwd.push("Perguntas mais recentes");
        }

        System.out.println(singlePwd.getPwd());
    }

    @Override
    public void draw() {
        ArrayList<Pergunta> perguntas = new ArrayList<>();
        int totalPaginas = 0;

        System.out.println(Layouts.DesignTop.getLayout());
        try {
            totalPaginas = control.totalPaginasRecentes();
            perguntas = control.listarPerguntasRecentes(pagina);

            if(perguntas.isEmpty()){
                System.out.println("|  Nenhuma pergunta publicada ainda.");
            }
            for(int i = 0; i < perguntas.size(); i++){

                Pergunta p = perguntas.get(i);

                String primeiraLinha = p.getPergunta().split("\n")[0];
                System.out.println("|  (" + (i + 1) + ") " + primeiraLinha);
                System.out.println("|      Criada por " + control.nomeUsuario(p.getIdUsuario()) + " em " + p.getCriacaoFormatada());
                System.out.println("|");
            }

            if(totalPaginas > 0){
                System.out.println("|  Página " + (pagina + 1) + " de " + totalPaginas);
            }

        } catch (Exception e) {
            System.out.println("|  " + e.getMessage());
        }

        System.out.println("|");
        System.out.println("|  [A] Página anterior    [B] Próxima página    [R] Retornar");
        System.out.println("|  Ou digite o número da pergunta para abri-la");
        System.out.println(Layouts.DesignBottom.getLayout());
        System.out.print(Layouts.LINECOMAND.getLayout());

        String opcao = scan.nextLine().strip().toUpperCase();
        int numero = paraNumero(opcao);

        if(opcao.equals("A") && pagina > 0){
            context.changeState(new StateRecentQuestions(pagina - 1));
        }else if(opcao.equals("B") && pagina + 1 < totalPaginas){
            context.changeState(new StateRecentQuestions(pagina + 1));
        }else if(opcao.equals("R")){
            singlePwd.pop();
            context.changeState(new StateSearchQuestions());
        }else if(numero >= 1 && numero <= perguntas.size()){
            // ao voltar da pergunta reabre a página da listagem
            context.changeState(new StateQuestion(perguntas.get(numero - 1).getId(), () -> new StateRecentQuestions(pagina)));
        }else{
            context.changeState(new StateRecentQuestions(pagina));
        }
    }
}
