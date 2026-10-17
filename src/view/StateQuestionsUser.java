package view;

import java.util.ArrayList;

import model.Pergunta;

public class StateQuestionsUser extends StateView {
    
    public StateQuestionsUser(){
        super();
        if(!singlePwd.getPwd().contains("Minhas perguntas")){
            singlePwd.push("Minhas perguntas");
        }

        System.out.println(singlePwd.getPwd());
    }

    private void pausar(){
        System.out.println(Color.GREEN.getCode() + " Pressione Enter para continuar...");
        scan.nextLine();
    }

    private int lerNumero(int max){
        System.out.println(Color.GREEN.getCode() + " Digite o número da pergunta (0 para cancelar):");
        System.out.print(Layouts.LINECOMAND.getLayout());
        int numero;
        try {
            numero = Integer.parseInt(scan.nextLine().strip());
        } catch (NumberFormatException e) {
            numero = -1;
        }

        if(numero < 0 || numero > max){
            throw new IllegalArgumentException("Número inválido!");
        }
        return numero;
    }

    private void mostrarLista(ArrayList<Pergunta> perguntas){
        for(int i = 0; i < perguntas.size(); i++){
            Pergunta p = perguntas.get(i);
            String estado = p.isAtiva() ? "" : " ARQUIVADA";
            System.out.println(Color.PURPLE.getCode() + " (" + (i + 1) + ")" + estado + " " + p.getCriacaoFormatada());
            System.out.println(Color.GREEN.getCode() + " " + p.getPergunta().replace("\n", "\n "));
            System.out.println(" Palavras chave: " + p.getPalavrasChave());
            System.out.println();
        }
    }

    private Pergunta escolherPergunta() throws Exception{
        Pergunta escolhida = null;
        ArrayList<Pergunta> perguntas = control.listarMinhasPerguntas();
        if(perguntas.isEmpty()){
            System.out.println("Você ainda não tem perguntas.");
        }else{
            mostrarLista(perguntas);
            int numero = lerNumero(perguntas.size());
            if(numero == 0){
                System.out.println("Operação cancelada.");
            }else{
                escolhida = perguntas.get(numero - 1);
            }
        }
        return escolhida;
    }

    private void listar() throws Exception{
        ArrayList<Pergunta> perguntas = control.listarMinhasPerguntas();
        if(perguntas.isEmpty()){
            System.out.println("Você ainda não tem perguntas.");
        }else{
            mostrarLista(perguntas);
        }
    }

    private void incluir() throws Exception{
        System.out.println(Color.GREEN.getCode() + " Digite a pergunta (linha vazia para terminar):");
        String texto = lerTexto();

        System.out.println(Color.GREEN.getCode() + " Digite as palavras chave separadas por ; :");
        System.out.print(Layouts.LINECOMAND.getLayout());
        String palavras = scan.nextLine();

        control.addPergunta(texto, palavras);
        System.out.println("Pergunta incluída com sucesso!");
    }

    private void alterar() throws Exception{
        Pergunta p = escolherPergunta();
        if(p != null){
            if(!p.isAtiva()){
                System.out.println("Pergunta arquivada não pode ser alterada!");
            }else{
                System.out.println(Color.GREEN.getCode() + " Texto atual:");
                System.out.println(" " + p.getPergunta().replace("\n", "\n "));
                System.out.println(Color.GREEN.getCode() + " Digite o novo texto (linha vazia para terminar, só Enter mantém o atual):");
                String texto = lerTexto();

                System.out.println(Color.GREEN.getCode() + " Palavras chave atuais: " + p.getPalavrasChave());
                System.out.println(Color.GREEN.getCode() + " Digite as novas palavras chave separadas por ; (só Enter mantém as atuais):");
                System.out.print(Layouts.LINECOMAND.getLayout());
                String palavras = scan.nextLine();

                if(control.updatePergunta(p, texto, palavras)){
                    System.out.println("Pergunta alterada com sucesso!");
                }else{
                    System.out.println("Nada foi alterado.");
                }
            }
        }
    }

    private void arquivar() throws Exception{
        Pergunta p = escolherPergunta();
        if(p != null){
            if(!p.isAtiva()){
                System.out.println("Pergunta já arquivada!");
            }else{
                System.out.println("Arquivar é definitivo, a pergunta não poderá mais ser alterada. Tem certeza (s/n) ? ");
                if(scan.nextLine().strip().equalsIgnoreCase("S")){
                    if(control.arquivarPergunta(p)){
                        System.out.println("Pergunta arquivada com sucesso!");
                    }else{
                        System.out.println("Ocorreu um erro durante o arquivamento!");
                    }
                }else{
                    System.out.println("Operação cancelada.");
                }
            }
        }
    }

    @Override
    public void draw() {
        
        System.out.print(Layouts.UserQuestions.getLayout());
        String option = scan.nextLine().strip();

        if(option.equals("4")){
            singlePwd.pop();
            context.changeState(new StateAreaUser());
        }else{
            try {
                 ArrayList<Pergunta> perguntas = control.listarMinhasPerguntas();
                 System.out.println(perguntas.isEmpty());
                if(option.equals("0")){
                    listar();
                }else if(option.equals("1")){
                    incluir();
                }else if(option.equals("2")){
                    alterar();
                }else if(option.equals("3")){
                    arquivar();
                }else if(option.equals("5")){
                    byte idpergunta = lerOpcao();
                    context.pushState(new StateResponse(perguntas.get(idpergunta - 1)));
                }else if(option.equals("7")){
                    byte idpergunta = lerOpcao();
                    context.pushState(new StateShowResponse(perguntas.get(idpergunta - 1), control.readRespostasByPergunta(perguntas.get(idpergunta - 1).getId()).get(0)));
                }else{
                    System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            pausar();
            context.changeState(new StateQuestionsUser());
        }
    }
}
