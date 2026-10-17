package model.AcessDataBase;
import model.Avaliacao;
import java.util.ArrayList;

public class ArquivoAvaliacao extends Arquivo<Avaliacao>{
    ArvoreBMais<ParIdId> rlUsuarioAvaliacao;
    ArvoreBMais<ParIdId> rlRespostaAvaliacao;
    
    public ArquivoAvaliacao () throws Exception{
        super("Avaliacaos", Avaliacao.class.getConstructor());
        rlUsuarioAvaliacao = new ArvoreBMais<>(ParIdId.class.getConstructor(), 5, "./dados/Avaliacaos/relUsuarioAvaliacao.db");
        rlRespostaAvaliacao = new ArvoreBMais<>(ParIdId.class.getConstructor(), 5, "./dados/Avaliacaos/relPerguntAvaliacao.db");
    }

    @Override
    public int create(Avaliacao r) throws Exception{
        int id = super.create(r);
        rlUsuarioAvaliacao.create(new ParIdId(r.getIdUsuario(), id));
        rlRespostaAvaliacao.create(new ParIdId(r.getIdResposta(), id));

        return id;
    }

    @Override
    public boolean delete(int id) throws Exception{
        Avaliacao r = super.read(id);
        if(r == null){
            return false;
        }
        rlUsuarioAvaliacao.delete(new ParIdId(r.getIdUsuario(), id));
        rlRespostaAvaliacao.delete(new ParIdId(r.getIdResposta(), id));

        return super.delete(id);
    }

    public ArrayList<Avaliacao> readByUser(int idUsuario) throws Exception{
        ArrayList<Avaliacao> avaliacaos = new ArrayList<>();
        ArrayList<ParIdId> pares = rlUsuarioAvaliacao.read(new ParIdId(idUsuario, -1));

        for(ParIdId par : pares){
            Avaliacao a = super.read(par.getId2());
            if(a != null){
                avaliacaos.add(a);
            }
        }
        return Avaliacaos;
    }

    public ArrayList<Avaliacao> readByPergunta(int idPergunta) throws Exception{
        ArrayList<Avaliacao> avaliacaos = new ArrayList<>();
        ArrayList<ParIdId> pares = rlRespostaAvaliacao.read(new ParIdId(idPergunta, -1));
        
        for(ParIdId par : pares){
            Avaliacaos a = super.read(par.getId2());
            if(a != null){
                avaliacaos.add(a);
            }
        }
        return Avaliacaos;
    }

}