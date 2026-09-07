public class ArquivoUser extends Arquivo<User>{
    //Ws49 was here
    Arquivo<User> arqUsers;
    HashExtensivel<ParIDEMAIL> indiceIndiretoEMAIL;

    public ArquivoUser() throws Exception{
        super("users",User.class.getConstructor());
        indiceIndiretoEMAIL = new HashExtensivel<ParIDEMAIL>(
            ParCPFID.class.getConstructor(), 
            4, 
            ".\\dados\\users\\indiceEmail.d.db",   
            ".\\dados\\users\\indiceEmail.c.db"    
        );
    }

    @Override
    public int create(User user){
        int id = super.create(user);
        indiceIndiretoEMAIL.create(new ParIDEMAIL(u.getEmail(),id));    
        return id;
    }
    
    public User read(String email){
        ParIDEMAIL par = indiceIndiretoEMAIL.read(ParIDEMAIL.hash(email));
        if(par == null){
            return null;
        }

        return read(par.getId());
    }


    
    @Override
    public boolean delete(int id){
        User user = super.read(id);
        boolean rtn = false;

        if(user != null){
            if(super.delete(id)){
                rtn = indiceIndiretoEMAIL.delete(ParIDEMAIL.hash(user.getEmail()));
            }
        }
        return rtn;   
    }

    public boolean delete(String email){
        ParIDEMAIL pIdemail = idindiceIndiretoEMAIL.read(ParIDEMAIL.hash(email));
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

        if(super.update(newUser)){
            indiceIndiretoEMAIL.delete(ParIDEMAIL.hash(oldUser.getEmail()));
            rtn = indiceIndiretoEMAIL.create(new ParIDEMAIL(newUser.getEmail(),newUser.getIdUsuario()));           
        }

        return rtn;
    }
}