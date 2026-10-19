package view;

import java.util.function.Supplier;

import model.Pergunta;

public class StateResponse extends StateView {
    private Pergunta question;
    private Supplier<StateView> voltar;

    public StateResponse(Pergunta p, Supplier<StateView> voltar){
        super(false);

        this.question = p;
        this.voltar = voltar;

        if(!singlePwd.ultimaPagina("Responder")){
            singlePwd.push("Responder");
        }

        System.out.println(singlePwd.getPwd());
    }

    @Override
    public void draw() {
        System.out.println(Layouts.DesignTop.getLayout());
        try {
            System.out.println(question.showMode(control.nomeUsuario(question.getIdUsuario())));
        } catch (Exception e) {
            System.out.println("|  " + e.getMessage());
        }

        System.out.println(Layouts.DesignBottom.getLayout());

        System.out.println(Color.GREEN.getCode() + " Digite a resposta (linha vazia para terminar):");

        String texto = lerTexto();

        try {
            control.addResposta(question.getId(), texto);

            System.out.println("Resposta publicada com sucesso!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        pausar();

        singlePwd.pop();
        context.changeState(voltar.get()); 
    }
}
