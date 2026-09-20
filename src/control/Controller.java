package control;

import model.ManagerEntities;
import model.User;
import model.AcessDataBase.ArquivoPergunta;
import view.ContextView;
import java.text.Normalizer;
import model.Pergunta;
import  java.util.ArrayList;



public class Controller {
    private ContextView context;
    private ManagerEntities managerModel;    

    public Controller(ContextView context, ManagerEntities managerEntities){
        this.context = context;
        this.managerModel = managerEntities;
        this.context.setControl(this);
        this.managerModel.setControl(this);
        context.start();
    }

    private boolean checkValidEmail(String email){
        boolean result = false;
        email = email.strip();
        if(email.length() > 0){
            result = email.contains("@") && email.contains(".");
        }

        return result;
    }

    public static String normalizeResponse(String resposta){
        return Normalizer.normalize(resposta.strip(), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase();
    }

    public static int hashRespostaSecreta(String resposta){
        return Math.abs(normalizeResponse(resposta).hashCode());
    }

    public void addUser(User user) throws Exception{
        if(checkValidEmail(user.getEmail())){
            //user.setPerguntaSecreta(normalizeResponse(user.getPerguntaSecreta()));
            //user.setHashRespostaSecreta(user.getHashRespostaSecreta());
            managerModel.getArqUsers().create(user);
        }else{
            throw new IllegalArgumentException("Erro : Email Inválido!");
        }
    }

    public boolean updateUser(User newUser){
        if(newUser == null){
            return false;
        }

        try {
            if(!checkValidEmail(newUser.getEmail())){
                throw new IllegalArgumentException("Erro: Email invalido");
            }

          //  newUser.setPerguntaSecreta(newUser.getPerguntaSecreta());
            return managerModel.getArqUsers().update(newUser);
        } catch (Exception e) {
           context.reportFail(e);
           return false; 
        }
    }

    public boolean updateSenhaUser(String email, String newHashSenha){
        try {
            User user = managerModel.getArqUsers().read(email);
            user.setHashSenha(newHashSenha); 
            return managerModel.getArqUsers().update(user);
        } catch (Exception e) {
           context.reportFail(e);
           return false; 
        }
    }

    public boolean deleteUser(int id) throws Exception{
        // Cascata: apaga as perguntas antes do usuário, para não sobrar pergunta órfã
        ArrayList<Pergunta> perguntas = managerModel.getArqPergunta().readByUser(id);
        for(Pergunta p : perguntas){
            managerModel.getArqPergunta().delete(p.getId());
        }
        return managerModel.getArqUsers().delete(id);
    }
    
    public boolean findUser(String email) {
        try {
            return managerModel.getArqUsers().read(email) != null ? true : false;
        } catch (Exception e) {
            context.reportFail(e);
            return false;
        }
    }

    public User readUser(String email, String hashPassword) throws Exception{
        User user = managerModel.getArqUsers().read(email);
        if(user != null){
            if(!hashPassword.equalsIgnoreCase(user.getHashSenha())){
                user = null;
            }
        }else{
            throw new IllegalAccessException("Error Senha ou Email inválidos!");
        }
      
        return user;

    }

    public String [] recoveryQuestion(String email) throws Exception{
        User user = managerModel.getArqUsers().read(email);
        if(user != null){
            String vals [] = new String [2];
            vals[0] = user.getPerguntaSecreta();
            vals[1] = String.valueOf(user.getHashRespostaSecreta());
            return vals;
        }else{
            throw new IllegalAccessException("Ocorreu um erro durante a recuperação!");
        }
    }

    private int idUserLogado() throws Exception{
        return  SingletonUser.getInstanceUser().getId();
    }

    public ArrayList<Pergunta> listarMinhasPerguntas() throws Exception{
        return   managerModel.getArqPergunta().readByUser(idUserLogado());
    }

    public int addPergunta(String text, String keyWord) throws Exception{
        int idUsuario = idUserLogado();
        text = text.strip();
        keyWord = keyWord.strip();
        if(text.isEmpty() ||   keyWord.isEmpty()){
            throw new IllegalArgumentException("Conteudo da Pergunta ou das Palavras Chave vazio");
        }else if(text.length() > 4000){
            throw new  IllegalArgumentException("Pergunta excede o numero de caracteres");
        }else if(keyWord.length() > 1000){
            throw new  IllegalArgumentException("Palavras Chave excedem o numero de caracteres");
        }else if(managerModel.getArqUsers().read(idUsuario) == null){
            throw new IllegalArgumentException("Usuario não encontrado");
        }else{
            Pergunta p = new Pergunta(idUsuario, text, keyWord);
            return  managerModel.getArqPergunta().create(p);
        }
    }

    public boolean updatePergunta(Pergunta p, String newText, String newWords) throws Exception{
        boolean resp;
        if(p.getIdUsuario() != idUserLogado()){
            throw new IllegalAccessException ("Usuario não correspondente");
        }else if(!p.isAtiva()){
            throw new IllegalAccessException ("Pergunta arquivada não pode ser alterada");
        }

        newText = newText.strip();
        newWords = newWords.strip();
        if(newText.isEmpty() && newWords.isEmpty()){
            resp =  false;
        }else if(newText.length() > 4000){
            throw new IllegalArgumentException("Pergunta excede o numero de caracteres");
        }else if(newWords.length() > 1000){
            throw new IllegalArgumentException("Palavras Chave excedem o numero de caracteres");
        }else{

            if(!newText.isEmpty()){
                p.setPergunta(newText);
            }
            if(!newWords.isEmpty()){
                p.setPalavrasChave(newWords);
            }
            p.setAlteracao(System.currentTimeMillis());
            resp = managerModel.getArqPergunta().update(p);
        }
        return resp;
    }

    public boolean arquivarPergunta(Pergunta p) throws Exception{
        if(p.getIdUsuario() != idUserLogado()){
            throw new IllegalAccessException ("Usuario não correspondente");
        }else if(!p.isAtiva()){
            throw new IllegalAccessException ("Pergunta já arquivada");
        }
        p.setAlteracao(System.currentTimeMillis());
        p.arquivar();
        return managerModel.getArqPergunta().update(p);
    }


}
