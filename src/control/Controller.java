package control;

import model.ManagerEntities;
import model.User;
import view.ContextView;
import java.text.Normalizer;


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
        return Normalizer.normalize(resposta, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase();
    }

    public void addUser(User user) throws Exception{
        if(checkValidEmail(user.getEmail())){
            user.setPerguntaSecreta(normalizeResponse(user.getPerguntaSecreta()));
            user.setHashRespostaSecreta(user.getHashRespostaSecreta());
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

            newUser.setPerguntaSecreta(newUser.getPerguntaSecreta());
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
}
