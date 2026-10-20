package model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import Formatter.FormatterString;

public class Pergunta implements Registro {

    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private int id;
    private int idUsuario;
    private long criacao;
    private long alteracao;
    private short nota;
    private String pergunta;
    private String palavrasChave;
    private boolean ativa;

    public Pergunta() {
        this.id = -1;
        this.idUsuario = -1;
        this.criacao = 0;
        this.alteracao = 0;
        this.nota = 0;
        this.pergunta = "";
        this.palavrasChave = "";
        this.ativa = true;
    }

    public Pergunta(int idUsuario, String pergunta, String palavrasChave) {
        long agora = System.currentTimeMillis();
        this.id = -1;
        this.idUsuario = idUsuario;
        this.criacao = agora;
        this.alteracao = agora;
        this.nota = 0;
        this.pergunta = pergunta;
        this.palavrasChave = palavrasChave;
        this.ativa = true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public long getCriacao() {
        return criacao;
    }

    public long getAlteracao() {
        return alteracao;
    }

    public void setAlteracao(long alteracao) {
        this.alteracao = alteracao;
    }

    public short getNota() {
        return nota;
    }

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public String getPalavrasChave() {
        return palavrasChave;
    }

    public void setPalavrasChave(String palavrasChave) {
        this.palavrasChave = palavrasChave;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void arquivar() {
        this.ativa = false;
    }

    public String getCriacaoFormatada() {
        return formatarData(criacao);
    }

    public String getAlteracaoFormatada() {
        return formatarData(alteracao);
    }

    private static String formatarData(long milissegundos) {
        return Instant.ofEpochMilli(milissegundos).atZone(ZoneId.systemDefault()).format(FORMATO_DATA);
    }

    @Override
    public String toString() {
        return String.format(
                "ID.............: %d%n"
                        + "ID do usuario..: %d%n"
                        + "Criacao........: %s%n"
                        + "Alteracao......: %s%n"
                        + "Nota...........: %d%n"
                        + "Pergunta.......: %s%n"
                        + "Palavras-chave.: %s%n"
                        + "Ativa..........: %s%n",
                id, idUsuario, getCriacaoFormatada(), getAlteracaoFormatada(),
                nota, pergunta, palavrasChave, ativa ? "sim" : "nao");
    }

    @Override
    public byte[] serialize() throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        DataOutputStream dataStream = new DataOutputStream(byteStream);

        dataStream.writeInt(id);
        dataStream.writeInt(idUsuario);
        dataStream.writeLong(criacao);
        dataStream.writeLong(alteracao);
        dataStream.writeShort(nota);
        dataStream.writeUTF(pergunta);
        dataStream.writeUTF(palavrasChave);
        dataStream.writeBoolean(ativa);

        return byteStream.toByteArray();
    }

    public String showMode(String nomeAutor){
        String flag;
        flag = ativa ? "" : "[ARQUIVADA] ";
        return flag + FormatterString.converteNLines(pergunta) + "|  Pergunta criada por " + nomeAutor + " em " + getCriacaoFormatada();
    }

    @Override
    public void deserialize(byte[] data) throws IOException {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
        DataInputStream dataStream = new DataInputStream(byteStream);

        id = dataStream.readInt();
        idUsuario = dataStream.readInt();
        criacao = dataStream.readLong();
        alteracao = dataStream.readLong();
        nota = dataStream.readShort();
        pergunta = dataStream.readUTF();
        palavrasChave = dataStream.readUTF();
        ativa = dataStream.readBoolean();
    }

}
