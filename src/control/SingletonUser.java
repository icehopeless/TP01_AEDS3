package control;

import model.User;

public class SingletonUser {
    private static User user;
    
    public static void setUser(User user) {
        SingletonUser.user = user;
    }
    public static User getInstanceUser()  throws Exception{
        if(user != null){
            return user;
        }
        throw new Error("Ocorreu um erro! Usuário nulo ou não setado anteriormente!");
    }

}
