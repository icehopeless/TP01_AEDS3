package view;

import control.Controller;

public class ContextView {
    private StateView state;
    private Controller control;
    
    public ContextView(StateView initialState){
        this.state = initialState;
        this.control = null;
        state.setContext(this);
        state.setControl(control);
    }
    
    public void setControl(Controller control) {
        this.control = control;
        state.setControl(control);
        render();
    }

    public void changeState(StateView stView){
        stView.setContext(this);
        stView.setControl(control);
        this.state = stView;
        render();
    }

    public Controller getControl() {
        return control;
    }

    public void render(){
        state.draw();
    }

    public void reportFail(Exception e){
        System.out.println(e.getMessage());
        render();
    }
}
