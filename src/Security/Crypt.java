package Security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Crypt {
    //Gerado por Google em Modo AI(Gemini)
    //Optamos por uma criptografia mais segura que MD5 devido a facilidade de bruteforce
    public static String getSHA256(String entrada) {
        try {
            // Instancia o algoritmo SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            
            // Converte a string de entrada em bytes e gera o hash
            byte[] hashBytes = digest.digest(entrada.getBytes(StandardCharsets.UTF_8));
            
            // Converte os bytes do hash para uma representação hexadecimal (String)
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            
            return hexString.toString();
            
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Algoritmo de hash não encontrado", e);
        }
    }
}
