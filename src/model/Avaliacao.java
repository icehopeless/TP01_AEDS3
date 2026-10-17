public class Avaliacao implements Registro{
    private int idAvaliacao;
    private int idUsuario;
    private int idResposta;
    private boolean itSelfAvaliacao;

    public Avaliacao(int idAv, int idUs, int idRe, boolean iSA){
        this.idAvaliacao = idAv;
        this.idUsuario = idUs;
        this.idResposta = idRe;
        this.itSelfAvaliacao = iSA;
    }


    public int getIdAvaliacao(){
        return idAvaliacao;
    }

    public int getIdUsuario(){
        return idUsuario;
    }

    public int getIdResposta(){
        return idResposta;
    }

    public void setItSelfAvaliacao(boolean itSA){
        this.itSelfAvaliacao = itSA;
    }

    public boolean getItSelfAvalaiacao(){
        return itSelfAvaliacao;
    }

    @Override
    public void setId(int i) {
      this.idAvaliacao = i;
    }

    @Override
    public int getId() {
        return idAvaliacao;
    }

    @Override
    public byte[] serialize() throws IOException{
        ByteArrayOutputStream byteArrayOutputStream  = new ByteArrayOutputStream();
        DataOutputStream dataWrite = new DataOutputStream(byteArrayOutputStream);
        
        dataWrite.writeInt(this.idAvaliacao);
        dataWrite.writeInt(this.idUsuario);
        dataWrite.writeInt(this.idResposta);
        dataWrite.writeBoolean(this.itSelfAvaliacao);

        return byteArrayOutputStream.toByteArray();
    }


    @Override
    public void deserialize(byte[] b) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        DataInputStream dis = new DataInputStream(bais);
        this.idAvaliacao = dis.readInt();
        this.idUsuario = dis.readInt();
        this.idResposta = dis.readInt();
        this.itSelfAvaliacao = dis.readBoolean();
    }
    

}