package view;

import java.util.Scanner;

import control.Controller;

public abstract class StateView {
    protected  ContextView context;
    protected  Controller control;
    private static Scanner scan;

    public static Scanner getTheSameScan(){
        if(scan == null){
            scan = new Scanner(System.in);
        }
        
        return scan;
    }

    public StateView(){
        this.context = null;        
        this.control = null;    
    }

    public void clearScreen(){
        System.out.print("\033[2J\033[H");
        System.out.flush();
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
