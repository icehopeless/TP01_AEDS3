package aed3;


import java.io.IOException;

public interface Registro {
    public void setId(int i);
    public int getId();
    public byte[] serialize() throws IOException;
    public void deserialize(byte[] b) throws IOException;
}