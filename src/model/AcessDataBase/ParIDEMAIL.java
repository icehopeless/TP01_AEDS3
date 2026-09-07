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
        this.email = email;
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
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
        dos.write(this.email.getBytes());
        dos.writeInt(this.id);
        return baos.toByteArray();
    }

    public void deserialize(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        byte[] b = new byte[11];
        dis.read(b);
        this.email = new String(b);
        this.id = dis.readInt();
    }

    public static int hash(String email){
        long emailLong = Long.parseLong(email);
        int hashValue = (int) (emailLong % (int)(1e9 + 7));
        return Math.abs(hashValue);
    }
}