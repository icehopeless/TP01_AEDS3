package view;

import java.util.ArrayList;
import java.util.function.Supplier;

import model.Pergunta;
import model.Resposta;

// Pergunta completa com as respostas ativas, em ordem decrescente de nota.
public class StateQuestion extends StateView {

    private int idPergunta;
    private Supplier<StateView> voltar;

    public StateQuestion(int idPergunta, Supplier<StateView> voltar){
        super(false);

        this.idPergunta = idPergunta;
        this.voltar = voltar;

        if(!singlePwd.ultimaPagina("Pergunta")){
            singlePwd.push("Pergunta");
        }

        System.out.println(singlePwd.getPwd());
    }

    @Override
    public void draw() {

        Pergunta p = null;

        ArrayList<Resposta> respostas = new ArrayList<>();

        System.out.println(Layouts.DesignTop.getLayout());

        try {
            p = control.readPergunta(idPergunta);

            System.out.println(p.showMode(control.nomeUsuario(p.getIdUsuario())));
            System.out.println("|  Palavras chave: " + p.getPalavrasChave());

            System.out.println("|");
            System.out.println("|  RESPOSTAS");
            System.out.println("|  ---------");

            respostas = control.readRespostasByPergunta(idPergunta); 

            if(respostas.isEmpty()){
                System.out.println("|  Ainda não há respostas.");
            }

            for(int i = 0; i < respostas.size(); i++){

                Resposta r = respostas.get(i);

                System.out.println("|  (" + (i + 1) + ")");
                System.out.println(r.showMode(control.nomeUsuario(r.getIdUsuario())));
                System.out.println("|");
            }

        } catch (Exception e) {
            System.out.println("|  " + e.getMessage());
        }

        System.out.println("|");

        if(p != null && p.isAtiva()){
            System.out.println("|  [A] Responder à pergunta");
        }

        System.out.println("|  [R] Retornar");
        System.out.println("|  Ou digite o número da resposta para abri-la");
        
        System.out.println(Layouts.DesignBottom.getLayout());
        System.out.print(Layouts.LINECOMAND.getLayout());

        String opcao = scan.nextLine().strip().toUpperCase();
        int numero = paraNumero(opcao);

        if(opcao.equals("A") && p != null && p.isAtiva()){
            context.changeState(new StateResponse(p, () -> new StateQuestion(idPergunta, voltar)));
        }else if(opcao.equals("R")){
            singlePwd.pop();

            context.changeState(voltar.get());
        }else if(numero >= 1 && numero <= respostas.size()){
            context.changeState(new StateShowResponse(p, respostas.get(numero - 1), () -> new StateQuestion(idPergunta, voltar)));
        }else{
            context.changeState(new StateQuestion(idPergunta, voltar));
        }
    }
}
