package model;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

//Ws49 was here
public class User {
    private int idUsuario;
    private String nome;
    private String email;
    private String hashSenha;
    private String perguntaSecreta;
    private String hashRespostaSecreta;

    public User(int id, String nome, String email, String hashSenha, String perguntaSecreta, String hashRespostaSecreta){
        this.idUsuario = id;
        this.nome = nome;
        this.email = email;
        this.hashSenha = hashSenha;
        this.perguntaSecreta = perguntaSecreta;
        this.hashRespostaSecreta = hashRespostaSecreta;
    }

    public User(){        
         User(-1,null,null,null,null,null);
    }

    public String getEmail(){
        return email;
    }
    public int getIdUsuario(){
        return idUsuario;
    }

    public String getNome(){
        return nome;
    }

    public String getHashSenha(){
        return hashSenha;
    }

    public String getPerguntaSecreta(){
        return perguntaSecreta;
    }

    public String getHashRespostaSecreta(){
        return hashRespostaSecreta;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setHashSenha(String hash){
        this.hashSenha = hash;
    }

    public void setPerguntaSecreta(String pergunta){
        this.perguntaSecreta = pergunta;

    }
    
    public void hashRespostaSecreta(String resposta){
        this.hashRespostaSecreta = resposta;
    }

    public byte[] serialize(){
        ByteArrayOutputStream byteArrayOutputStream  = new ByteArrayOutputStream();
        DataOutput dataWrite = new DataOutput(byteArrayOutputStream);
        
        dataWrite.write(id);
        dataWrite.writeUTF(nome);
        dataWrite.writeUTF(email);
        dataWrite.writeUTF(hashSenha);
        dataWrite.writeUTF(perguntaSecreta);
        dataWrite.writeUTF(hashRespostaSecreta);
        
        return byteArrayOutputStream.toByteArray();
    }

    public static User deserialize(byte[] b){
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        DataInputStream dis = new DataInputStream(bais);
        
        int id = dis.readInt();
        String nome = dis.readUTF();
        String email = dis.readUTF();
        String hashSenha = dis.readUTF();
        String perguntaSecreta = dis.readUTF();
        String hashRespostaSecreta = dis.readUTF();
        
        return new User(id, nome, email, hashSenha, perguntaSecreta, hashRespostaSecreta);
    }
}



