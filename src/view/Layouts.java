package view;
public enum Layouts {
    MainMenu(   Color.PURPLE.getCode() +
                "  ████████╗███████╗██████╗ ███╗   ███╗██╗███╗   ██╗ █████╗ ██╗     ███╗   ███╗███████╗\r\n" + //
                "  ╚══██╔══╝██╔════╝██╔══██╗████╗ ████║██║████╗  ██║██╔══██╗██║     ████╗ ████║██╔════╝\r\n" + //
                "     ██║   █████╗  ██████╔╝██╔████╔██║██║██╔██╗ ██║███████║██║     ██╔████╔██║█████╗\r\n" + //
                "     ██║   ██╔══╝  ██╔══██╗██║╚██╔╝██║██║██║╚██╗██║██╔══██║██║     ██║╚██╔╝██║██╔══╝\r\n" + //
                "     ██║   ███████╗██║  ██║██║ ╚═╝ ██║██║██║ ╚████║██║  ██║███████╗██║ ╚═╝ ██║███████╗\r\n" + //
                "     ╚═╝   ╚══════╝╚═╝  ╚═╝╚═╝     ╚═╝╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝╚══════╝╚═╝     ╚═╝╚══════╝\r\n" + //
                "\r\n" + //
                Color.GREEN.getCode() + 
                "                         Terminal Question & Answer System\r\n" + //
                 Color.PURPLE.getCode() + 
                 "                                Version 1.0.0\r\n" + //
                "\r\n" + //
                Color.GREEN.getCode() +
                "\t┌──────────────────────────────────────────────────────────────┐\r\n" + //
                "\t│                        MAIN MENU                             │\r\n" + //
                "\t├──────────────────────────────────────────────────────────────┤\r\n" + //
                "\t│                                                              │\r\n" + //
                "\t│   [0]  LOGIN                                                 │\r\n" + //
                "\t│   [1]  CREATE ACCOUNT                                        │\r\n" + //
                "\t│   [2]  EXIT                                                  │\r\n" + //
                "\t│                                                              │\r\n" + //
                "\t└──────────────────────────────────────────────────────────────┘\r\n" + //
                "\r\n" + Color.GREEN.getCode() +" TerminalMe "+ Color.PURPLE.getCode()+"> "),

    LINECOMAND(Color.GREEN.getCode() +" TerminalMe "+ Color.PURPLE.getCode()+"> ");

    
    private String layout;
    Layouts(String lay){
        this.layout = lay;
    }

    public String getLayout() {
        return layout;
    }
}
