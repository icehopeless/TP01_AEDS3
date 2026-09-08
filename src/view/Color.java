package view;
public enum Color{
    BLACK("\033[30m"),
    RED("\033[31m"),
    GREEN("\033[38;5;118m"),
    YELLOW("\033[33m"),
    BLUE("\033[38;5;21m"),
    PURPLE("\033[38;5;129m"), // Magenta/Purple
    CYAN("\033[36m"),
    WHITE("\033[37m");
    private String code;
    private Color(String code){
        this.code = code;
    }
    public String getCode() {
        return code;
    }
}
