package control;

import model.ManagerEntities;
import model.User;
import view.ContextView;
import java.text.Normalizer;

import Security.Crypt;

public class Controller {
    private ContextView context;
    private ManagerEntities managerModel;    

    public Controller(ContextView context, ManagerEntities managerEntities){
        this.context = context;
        this.managerModel = managerEntities;
        this.context.setControl(this);
        this.managerModel.setControl(this);
    }

    private boolean checkEmail(String email){
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
        if(checkEmail(user.getEmail())){
            user.setPerguntaSecreta(normalizeResponse(user.getPerguntaSecreta()));
            user.setHashRespostaSecreta(user.getHashRespostaSecreta());
            managerModel.getArqUsers().create(user);
        }else{
            throw new IllegalArgumentException("Erro : Email Inválido!");
        }
    }

    public boolean updateUser(User newUser){return false;}

    public boolean deleteUser(){return false;}
    
    public User searchUser(String email, String hashPassword) throws Exception{
        User user = managerModel.getArqUsers().read(email);
        if(user != null){
            if(!hashPassword.equalsIgnoreCase(user.getHashSenha())){
                throw new IllegalAccessException("Error Senha ou Email inválidos!");
            }
        }else{
            throw new IllegalAccessException("Error Senha ou Email inválidos!");
        }
      
        return user;

    }
}
