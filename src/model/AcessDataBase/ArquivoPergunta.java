package model.AcessDataBase;
import model.Pergunta;
import  java.util.ArrayList;

public class ArquivoPergunta extends Arquivo<Pergunta>{
    ArvoreBMais<ParIdId> relUsuarioPergunta;

    public ArquivoPergunta () throws Exception{
        super("perguntas", Pergunta.class.getConstructor());
        relUsuarioPergunta = new ArvoreBMais<>(ParIdId.class.getConstructor(),
         5, "./dados/perguntas/relUsuarioPergunta.db");
    }

    @Override
    public int create(Pergunta p) throws Exception{
        int id = super.create(p);
        relUsuarioPergunta.create(new ParIdId(p.getIdUsuario(), id));
        return id;
    }

    public ArrayList<Pergunta> readByUser(int idUsuario) throws Exception{
        ArrayList<Pergunta> perguntas = new ArrayList<>();
        ArrayList<ParIdId> pares = relUsuarioPergunta.read(new ParIdId(idUsuario, -1));
        for(ParIdId par : pares){
            Pergunta p = super.read(par.getId2());
            if(p != null){
                perguntas.add(p);
            }
        }
        return perguntas;
    }

    // Update herdado da classe pai, sem override
    
    @Override
    public boolean delete(int id) throws Exception{
        Pergunta p = super.read(id);
        if(p == null){
            return false;
        }
        relUsuarioPergunta.delete(new ParIdId(p.getIdUsuario(), id));
        return super.delete(id);
    }
}
