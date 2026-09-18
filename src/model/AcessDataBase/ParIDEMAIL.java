package model.AcessDataBase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;


public class ParIDEMAIL implements RegistroHashExtensivel<ParIDEMAIL> {
    
    private String email; // chave
    private int id;     // valor
    private final short TAMANHO = 64;  // tamanho em bytes

    public ParIDEMAIL() {
        this.email = "*".repeat(64);
        this.id = -1;
    }

    public ParIDEMAIL(String email, int id) throws Exception {
        if(email.getBytes().length + 4 > TAMANHO){
            throw new Exception("ERROR: EMAIL EXCEDE O TAMANHO!");
        }
        this.email = email;
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    
    public static int hash(String email){
        return Math.abs(email.hashCode());
    }

    @Override
    public int hashCode() {
        return hash(this.email);
    }

    public short size() {
        return this.TAMANHO;
    }

    public String toString() {
        return "("+this.email + ";" + this.id+")";
    }

    public byte[] serialize() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        byte emailBytes[] = this.email.getBytes();
        dos.write(emailBytes);
        for(int i = emailBytes.length; i < TAMANHO - 4; i++){
            dos.write(' ');
        }
        dos.writeInt(this.id);
        return baos.toByteArray();
    }

    public void deserialize(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        byte emailBytes [] = new byte[TAMANHO - 4];
        dis.read(emailBytes);
        this.email = new String(emailBytes).strip();
        this.id = dis.readInt();
    }


}