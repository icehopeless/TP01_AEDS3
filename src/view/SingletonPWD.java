package view;

public class SingletonPWD {
    private String pwd;
    private String way;
    private static SingletonPWD pwdObject;
    

    private SingletonPWD(){
        pwd = way = "";
    }

    public static SingletonPWD getPwdObject() {
        if(pwdObject == null){
            pwdObject = new SingletonPWD();
        }
     
        return pwdObject;
    }

    private void updatePWD(){
        if(way.isEmpty()){
            pwd = "";
        }else{
            pwd = Layouts.PWD.getLayout();
            pwd = pwd.substring(0,14) + "─".repeat(way.length() - 1) +pwd.replace("{}", way).substring(14);
            pwd = pwd.substring(0,pwd.length() - 3) + "─".repeat(way.length() - 1) + "┘";
        }
    }

    public void push(String page){
        if(way.length() == 0){
            way = page;
        }else{
            way += " > " + page;
        }
        updatePWD();
    }


    public void pop(){
        if(way.contains(" > ")){
            way = way.substring(0,way.lastIndexOf(" > "));            
        }else{
            way = "";
        }
        updatePWD();
    }

    public String getPwd() {
        return pwd;
    }

    public boolean ultimaPagina(String page){
        return way.equals(page) || way.endsWith(" > " + page);
    }

}

