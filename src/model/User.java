package model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;


//Ws49 was here
public class User implements Registro {
    private int idUsuario;
    private String nome;
    private String email;
    private String hashSenha;
    private String perguntaSecreta;
    private int hashRespostaSecreta;

    public User(int id, String nome, String email, String hashSenha, String perguntaSecreta, int hashRespostaSecreta){
        this.idUsuario = id;
        this.nome = nome;
        this.email = email;
        this.hashSenha = hashSenha;
        this.perguntaSecreta = perguntaSecreta;
        this.hashRespostaSecreta = Math.abs(hashRespostaSecreta);
    }

    public User(){        
        this(-1,  null,null,null,null,-1);
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

    public int getHashRespostaSecreta(){
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
    
    public void setHashRespostaSecreta(int resposta){
        this.hashRespostaSecreta = resposta;
    }

    public byte[] serialize() throws IOException{
        ByteArrayOutputStream byteArrayOutputStream  = new ByteArrayOutputStream();
        DataOutputStream dataWrite = new DataOutputStream(byteArrayOutputStream);
        
        dataWrite.writeInt(this.idUsuario);
        dataWrite.writeUTF(nome);
        dataWrite.writeUTF(email);
        dataWrite.writeUTF(hashSenha);
        dataWrite.writeUTF(perguntaSecreta);
        dataWrite.writeInt(hashRespostaSecreta);
        
        return byteArrayOutputStream.toByteArray();
    }


    @Override
    public void setId(int i) {
      this.idUsuario = i;
    }

    @Override
    public int getId() {
        return idUsuario;
    }

    @Override
    public void deserialize(byte[] b) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        DataInputStream dis = new DataInputStream(bais);
        
        this.idUsuario = dis.readInt();
        this.nome = dis.readUTF();
        this.email = dis.readUTF();
        this.hashSenha = dis.readUTF();
        this.perguntaSecreta = dis.readUTF();
        this.hashRespostaSecreta = dis.readInt();
        
       
    }
}



