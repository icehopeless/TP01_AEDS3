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

public class Resposta implements Registro{
    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm");
    private int idResposta;
    private int idPergunta;
    private int idUsuario;
    private long criacao;
    private String resposta;
    private short nota;
    private short avaliacoes;
    private boolean ativa;


    public Resposta() {
        this(-1,-1,-1,(long)-1,null,(short)-1,(short)-1,false);
    }

    public Resposta(int idResposta, int idPergunta, int idUsuario, long criacao, String resposta, short nota, short avaliacoes, boolean ativa) {
        this.idResposta = idResposta;
        this.idPergunta = idPergunta;
        this.idUsuario = idUsuario;
        this.criacao = criacao;
        this.resposta = resposta;
        this.nota = nota;
        this.avaliacoes = avaliacoes;
        this.ativa = ativa;
    }


    public int getIdResposta() {
        return idResposta;
    }

    public void setIdResposta(int idResposta) {
        this.idResposta = idResposta;
    }

    public int getIdPergunta() {
        return idPergunta;
    }

    public void setIdPergunta(int idPergunta) {
        this.idPergunta = idPergunta;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public long getCriacao() {
        return criacao;
    }

    public void setCriacao(long criacao) {
        this.criacao = criacao;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public short getNota() {
        return nota;
    }

    public void setNota(short nota) {
        this.nota = nota;
    }

    public short getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(short avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    public void arquivar() {
        this.ativa = false;
    }

    @Override
    public void setId(int i) {
      this.idResposta = i;
    }

    @Override
    public int getId() {
        return idResposta;
    }

    public String getCriacaoFormatada() {
    return Instant.ofEpochMilli(criacao)
                  .atZone(ZoneId.systemDefault())
                  .format(FORMATO_DATA);
    }

    //Respondido por Antônio Carlos Machado em 16/09/2026 às 13:20
    public String showMode(String nomeAutor){
        String flag;
        flag = ativa ? "" : "|  [ARQUIVADA] ";
        return flag + FormatterString.converteNLines(resposta) + "|  Respondido por " + nomeAutor + " em " + getCriacaoFormatada() + 
        "\n|  Avalicoes: "  + avaliacoes + "  Nota: " + nota;
    }
    
    //int idResposta, int idPergunta, int idUsuario, long criacao, String resposta, short nota, short avaliacoes, boolean ativa
    @Override
    public byte[] serialize() throws IOException{
        ByteArrayOutputStream byteArrayOutputStream  = new ByteArrayOutputStream();
        DataOutputStream dataWrite = new DataOutputStream(byteArrayOutputStream);
        
        dataWrite.writeInt(this.idResposta);
        dataWrite.writeInt(this.idPergunta);
        dataWrite.writeInt(this.idUsuario);
        dataWrite.writeLong(criacao);
        dataWrite.writeUTF(resposta);
        dataWrite.writeShort(nota);
        dataWrite.writeShort(avaliacoes);
        dataWrite.writeBoolean(ativa);

        return byteArrayOutputStream.toByteArray();
    }


    @Override
    public void deserialize(byte[] b) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        DataInputStream dis = new DataInputStream(bais);

        this.idResposta = dis.readInt();
        this.idPergunta = dis.readInt();
        this.idUsuario = dis.readInt();
        this.criacao = dis.readLong();
        this.resposta = dis.readUTF();
        this.nota = dis.readShort();
        this.avaliacoes = dis.readShort();
        this.ativa = dis.readBoolean();
        
    }
}