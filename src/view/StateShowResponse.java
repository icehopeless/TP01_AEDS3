package view;

import java.util.function.Supplier;

import control.SingletonUser;
import model.Pergunta;
import model.Resposta;

public class StateShowResponse extends StateView {
    private Pergunta question;
    private Resposta response;

    private Supplier<StateView> voltar;

    public StateShowResponse(Pergunta p, Resposta r, Supplier<StateView> voltar){
        super(false);

        this.question = p;
        this.response = r;
        this.voltar = voltar;

        if(!singlePwd.ultimaPagina("Resposta")){
            singlePwd.push("Resposta");
        }

        System.out.println(singlePwd.getPwd());
    }

    @Override
    public void draw() {
        boolean respostaMinha = false;
        boolean perguntaMinha = false;

        System.out.println(Layouts.DesignTop.getLayout());

        try {
            int idLogado = SingletonUser.getInstanceUser().getId();

            respostaMinha = response.getIdUsuario() == idLogado;
            perguntaMinha = question.getIdUsuario() == idLogado;

            System.out.println(question.showMode(control.nomeUsuario(question.getIdUsuario())));
            System.out.println("|");
            System.out.println("|  RESPOSTA");
            System.out.println("|  --------");

            String autor = respostaMinha ? "você" : control.nomeUsuario(response.getIdUsuario());

            System.out.println(response.showMode(autor));

        } catch (Exception e) {
            System.out.println("|  " + e.getMessage());
        }

        System.out.println("|");

        if(respostaMinha){
            System.out.println("|  [A] Arquivar resposta");
        }else{
            System.out.println("|  [A] Concordar com a resposta");
            System.out.println("|  [B] Discordar da resposta");

            if(perguntaMinha){
                System.out.println("|  [C] Arquivar resposta (você é o autor da pergunta)");
            }
        }
        System.out.println("|");
        System.out.println("|  [R] Retornar ao menu anterior");

        System.out.println(Layouts.DesignBottom.getLayout());
        System.out.print(Layouts.LINECOMAND.getLayout());

        String opcao = scan.nextLine().strip().toUpperCase();

        if(!opcao.equals("R")){
            try {
                if(opcao.equals("A") && respostaMinha){
                    arquivar();
                }else if(opcao.equals("A")){
                    control.avaliarResposta(response, true);
                    System.out.println("Você concordou com a resposta.");
                }else if(opcao.equals("B") && !respostaMinha){
                    control.avaliarResposta(response, false);
                    System.out.println("Você discordou da resposta.");
                }else if(opcao.equals("C") && !respostaMinha && perguntaMinha){
                    arquivar();
                }else{
                    System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            pausar();
        }

        // Volta para a tela anterior e já mostra a nota nova
        singlePwd.pop();
        context.changeState(voltar.get());
    }

    private void arquivar() throws Exception{
        System.out.println("Arquivar é definitivo, a resposta sai das listagens. Tem certeza (s/n) ?");

        if(scan.nextLine().strip().equalsIgnoreCase("S")){
            control.arquivarResposta(response);
            
            System.out.println("Resposta arquivada com sucesso!");
        }else{
            System.out.println("Operação cancelada.");
        }
    }
}
