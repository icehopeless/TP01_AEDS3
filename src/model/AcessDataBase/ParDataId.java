package model.AcessDataBase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ParDataId implements InterfaceArvoreBMais<ParDataId> {

    private long data;
    private int id;
    private final short TAMANHO = 12; // pra arvore nao calcular o tamanho pagina errado

    public ParDataId() {
        this(-1, -1);
    }

    public ParDataId(long data, int id) {
        this.data = data;
        this.id = id;
    }

    public long getData() {
        return data;
    }

    public int getId() {
        return id;
    }

    @Override
    public ParDataId clone() {
        return new ParDataId(this.data, this.id);
    }

    @Override
    public short size() {
        return TAMANHO;
    }

    @Override
    public int compareTo(ParDataId outro) {
        if (this.data != outro.data) {
            //troquei aqui o this data - outro.data pois estava estourando em alguns testes que fiz 
            return Long.compare(outro.data, this.data); 
        }
        // se a data é a mesma compara pelo ID
        return Integer.compare(outro.id, this.id);
    }

    @Override
    public byte[] serialize() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeLong(this.data);
        dos.writeInt(this.id);
        return baos.toByteArray();
    }

    @Override
    public void deserialize(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        this.data = dis.readLong();
        this.id = dis.readInt();
    }
}
