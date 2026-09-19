package view;

import java.util.Scanner;

import control.Controller;

public abstract class StateView {
    protected  ContextView context;
    protected  Controller control;
    protected static Scanner scan;
    protected static SingletonPWD singlePwd;

    protected static Scanner getTheSameScan(){
        
        if(scan == null){
            //aqui eu peguei a codificação e padronizei utf8 por cauda dos acentos 
            String codificacao = System.getProperty("stdin.encoding", System.getProperty("native.encoding", "UTF-8"));
            scan = new Scanner(System.in, codificacao);
        }
        
        return scan;
    }

    public StateView(){
        clearScreen();
        singlePwd = SingletonPWD.getPwdObject();
        this.context = null;        
        this.control = null;   
        scan = StateView.getTheSameScan(); 
    }

    public void clearScreen(){
        System.out.print("\033[2J\033[H");
        System.out.flush();
    }

    protected byte lerOpcao(){
        byte option;
        try {
            option = Byte.parseByte(scan.nextLine().strip());
        } catch (NumberFormatException e) {
            option = -1;
        }
        return option;
    }

    protected void avisarEmConstrucao(){
        System.out.println("Opção em construção!");
        System.out.println("Pressione Enter para continuar...");
        scan.nextLine();
    }
    public void setContext(ContextView context) {
        this.context = context;
    }
    public void setControl(Controller control) {
        this.control = control;
    }
    public ContextView getContext() {
        return context;
    }
    public Controller getControl() {
        return control;
    }
    
    public abstract void draw();
    
}
