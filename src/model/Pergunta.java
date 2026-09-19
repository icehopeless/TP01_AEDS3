package model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Pergunta implements Registro{
    

    private int id;
    private String conteudo;
    private int dataCriacao;
    private short horaCriacao;
    private int dataModificacao;
    private short horaModificacao;

    public Pergunta() {
        this.id = -1;
        this.conteudo = "";
        this.dataCriacao = 0;
        this.horaCriacao = 0;
        this.dataModificacao = 0;
        this.horaModificacao = 0;
    }


    public Pergunta (int id, String conteudo, int dataCriacao, short horaCriacao, int dataModificacao, short horaModificacao) {
        this.id = id;
        this.conteudo = conteudo;
        this.dataCriacao = dataCriacao;
        this.horaCriacao = horaCriacao;
        this.dataModificacao = dataModificacao;
        this.horaModificacao = horaModificacao;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public int getDataCriacao() {
        return dataCriacao;
    }


    public void setDataCriacao(int dataCriacao) {
        this.dataCriacao = dataCriacao;
    }


    public short getHoraCriacao() {
        return horaCriacao;
    }

    public void setHoraCriacao(short horaCriacao) {
        this.horaCriacao = horaCriacao;
    }

    public int getDataModificacao() {
        return dataModificacao;
    }


    public void setDataModificacao(int dataModificacao) {
        this.dataModificacao = dataModificacao;
    }

    public short getHoraModificacao() {
        return horaModificacao;
    }

    public void setHoraModificacao(short horaModificacao) {
        this.horaModificacao = horaModificacao;
    }


    @Override
    public String toString() {
        return String.format(
            "ID..: %d%n"
            + "Conteudo: %s%n"
            + "Criacao: %d %d%n"
            + "Modificacao: %d %d%n"
        , id, conteudo, dataCriacao, 
        horaCriacao, dataModificacao, horaModificacao);
    }

    @Override
    public byte[] serialize() throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        DataOutputStream dataStream = new DataOutputStream(byteStream);

        dataStream.writeInt(id);
        dataStream.writeUTF(conteudo);
        dataStream.writeInt(dataCriacao);
        dataStream.writeShort(horaCriacao);
        dataStream.writeInt(dataModificacao);
        dataStream.writeShort(horaModificacao);

        return byteStream.toByteArray();
    }

    @Override 
    public void deserialize(byte[] data) throws IOException {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
        DataInputStream dataStream = new DataInputStream(byteStream);

        id = dataStream.readInt();
        conteudo = dataStream.readUTF();
        dataCriacao = dataStream.readInt();
        horaCriacao = dataStream.readShort();
        dataModificacao = dataStream.readInt();
        horaModificacao = dataStream.readShort();
    }

}
