package model.AcessDataBase;


import control.Controller;
import model.User;

public class ArquivoUser extends Arquivo<User>{
    //Ws49 was here
    Arquivo<User> arqUsers;
    HashExtensivel<ParIDEMAIL> indiceIndiretoEMAIL;
    private Controller controll;
    public ArquivoUser() throws Exception{
        super("users",User.class.getConstructor());
        indiceIndiretoEMAIL = new HashExtensivel<ParIDEMAIL>(
            ParIDEMAIL.class.getConstructor(), 
            4, 
            ".\\dados\\users\\indiceEmail.d.db",   
            ".\\dados\\users\\indiceEmail.c.db"    
        );
        
    }

    public void setControll(Controller controll) {
        this.controll = controll;
    }

    @Override
    public int create(User user) throws Exception{
        
        if(read(user.getEmail()) == null){    
            new ParIDEMAIL(user.getEmail(),-1);        
            int id = super.create(user);
            indiceIndiretoEMAIL.create(new ParIDEMAIL(user.getEmail(),id));    
            return id;
        }

        throw new IllegalArgumentException("Email já existente!");
    }
    
    public User read(String email) throws Exception{
        ParIDEMAIL par = indiceIndiretoEMAIL.read(ParIDEMAIL.hash(email));
        User user = null;
        if(par != null){
            user = read(par.getId());
        }

        return user;
    }


    
    @Override
    public boolean delete(int id) throws Exception{
        User user = super.read(id);
        boolean rtn = false;

        if(user != null){
            if(super.delete(id)){
                rtn = indiceIndiretoEMAIL.delete(ParIDEMAIL.hash(user.getEmail()));
            }
        }
        return rtn;   
    }

    public boolean delete(String email) throws Exception{
        ParIDEMAIL pIdemail = indiceIndiretoEMAIL.read(ParIDEMAIL.hash(email));
        boolean rtn = false;

        if(pIdemail != null){
            if(delete(pIdemail.getId())){
                rtn = indiceIndiretoEMAIL.delete(ParIDEMAIL.hash(email));
            }
        }

        return rtn;   
        
    }
    
    

    @Override 
    public boolean update(User newUser) throws Exception{
        User oldUser = read(newUser.getIdUsuario());
        boolean rtn = false;
        
        if(!newUser.getEmail().equals(oldUser.getEmail())){
                if(indiceIndiretoEMAIL.read(ParIDEMAIL.hash(newUser.getEmail())) == null){
                    indiceIndiretoEMAIL.create(new ParIDEMAIL(newUser.getEmail(),newUser.getIdUsuario()));  
                    indiceIndiretoEMAIL.delete(ParIDEMAIL.hash(oldUser.getEmail()));              
                }else{
                    throw new Exception("Não é possível alocar este email: Email já cadastrado!");
                }
        }
            
        rtn = super.update(newUser);                             

        return rtn;
    }
}