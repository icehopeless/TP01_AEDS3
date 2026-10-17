package view;

import control.Controller;

public class ContextView {
    private StateView currentState;
    private StateView nextState;
    private Controller control;
    private boolean hasNextState;

    public ContextView(StateView initialState){
        this.currentState = initialState;
        this.nextState = null;
        this.control = null;
        this.hasNextState = false;
        currentState.setContext(this);
        currentState.setControl(control);
    }
    
    public void setControl(Controller control) {
        this.control = control;
        currentState.setControl(control);
    }

    public void changeState(StateView stView){
        stView.setContext(this);        
        this.nextState = stView;
        stView.setControl(control);
        hasNextState = true;
    }

    public Controller getControl() {
        return control;
    }

    public void render(){
        do{
            hasNextState = false;
            currentState.draw();            
            this.currentState = this.nextState;
        }while(hasNextState);
    }

    public void pushState(StateView state){
        stView.setContext(this);   
        stView.setControl(control);
        state.draw();     
    }

    public void start(){
        render();
    }

    public void reportFail(Exception e){
        currentState.clearScreen();
        System.out.println(e.getMessage());
        try {
            Thread.sleep(1000);
        } catch (Exception ex) {
            System.out.println("Erro ao tentar lag!");
        }
        
        render();
    }
}
