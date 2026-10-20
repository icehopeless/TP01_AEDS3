package Formatter;

public class FormatterString {
    public static String converteNLines(String line){
        String newLine = "";

        if(line.length() > 70){
            while(line.length() > 70){
                newLine += "|  " + line.substring(0,69) + "\n";
                line = line.substring(69);
            }
        }
        
        String partsLines [] = line.split("\n");

        for(int i = 0 ; i < partsLines.length; i++){
            newLine += "|  " + partsLines[i] + "\n";
        }
        
        return newLine;
    }
}


/*

  *      ____     _____    ___  
  |     /        |        |   \
  |    /         |_____   |    \
  |    \         |        |    /
  |     \____    |_____   |___/ 

               *                                
            \  |  /
             \ | /
         ----- ❄ -----
             / | \
            /  |  \
               *

*/