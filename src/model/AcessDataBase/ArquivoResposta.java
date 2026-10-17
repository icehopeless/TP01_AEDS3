package model.AcessDataBase;
import model.Resposta;
import java.util.ArrayList;

//ice was here
public class ArquivoResposta extends Arquivo<Resposta>{
    ArvoreBMais<ParIdId> relUsuarioResposta;
    ArvoreBMais<ParIdId> relPerguntaResposta;
    
    public ArquivoResposta () throws Exception{
        super("respostas", Resposta.class.getConstructor());
        relUsuarioResposta = new ArvoreBMais<>(ParIdId.class.getConstructor(), 5, "./dados/respostas/relUsuarioResposta.db");
        relPerguntaResposta = new ArvoreBMais<>(ParIdId.class.getConstructor(), 5, "./dados/respostas/relPerguntaResposta.db");
    }

    @Override
    public int create(Resposta r) throws Exception{
        int id = super.create(r);
        relUsuarioResposta.create(new ParIdId(r.getIdUsuario(), id));
        relPerguntaResposta.create(new ParIdId(r.getIdPergunta(), id));

        return id;
    }

    @Override
    public boolean delete(int id) throws Exception{
        Resposta r = super.read(id);
        if(r == null){
            return false;
        }
        relUsuarioResposta.delete(new ParIdId(r.getIdUsuario(), id));
        relPerguntaResposta.delete(new ParIdId(r.getIdPergunta(), id));

        return super.delete(id);
    }

    public ArrayList<Resposta> readByUser(int idUsuario) throws Exception{
        ArrayList<Resposta> respostas = new ArrayList<>();
        ArrayList<ParIdId> pares = relUsuarioResposta.read(new ParIdId(idUsuario, -1));
        for(ParIdId par : pares){
            Resposta r = super.read(par.getId2());
            if(r != null){
                respostas.add(r);
            }
        }
        return respostas;
    }

    public ArrayList<Resposta> readByPergunta(int idPergunta) throws Exception{
        ArrayList<Resposta> respostas = new ArrayList<>();
        ArrayList<ParIdId> pares = relPerguntaResposta.read(new ParIdId(idPergunta, -1));
        for(ParIdId par : pares){
            Respostas r = super.read(par.getId2());
            if(r != null){
                respotas.add(r);
            }
        }
        return respostas;
    }

    public boolean incrementAvaliacao(int id){
        Resposta r = super.read(id);
        if(r == null || r.isAtiva()){
            return false;
        }
        r.setAvaliacoes(r.getAvaliacoes() + 1);
        return super.update(r);
    }


    public boolean incrementNota(int id){
        Resposta r = super.read(id);
        if(r == null || r.isAtiva()){
            return false;
        }
        r.setNota(r.getNota() + 1);
        return super.update(r);
    }


    public boolean decrementAvaliacao(int id){
        Resposta r = super.read(id);
        if(r == null || r.isAtiva()){
            return false;
        }
        r.setNota(r.getNota() - 1);
        return super.update(r);
    }

}