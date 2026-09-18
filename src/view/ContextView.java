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

    public void start(){
        render();
    }
    public void render(){
        do{
            hasNextState = false;
            currentState.draw();            
            this.currentState = this.nextState;
        }while(hasNextState);
    }

    public void reportFail(Exception e){
        System.out.println(e.getMessage());
        render();
    }
}
