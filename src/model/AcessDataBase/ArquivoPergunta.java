package model.AcessDataBase;

import model.Pergunta;
import  java.util.ArrayList;

public class ArquivoPergunta extends Arquivo<Pergunta>{
    ArvoreBMais<ParIdId> relUsuarioPergunta;
    ArvoreBMais<ParDataId> indiceData;

    public ArquivoPergunta () throws Exception{
        super("perguntas", Pergunta.class.getConstructor());
        relUsuarioPergunta = new ArvoreBMais<>(ParIdId.class.getConstructor(),
         5, "./dados/perguntas/relUsuarioPergunta.db");
        
        indiceData = new ArvoreBMais<>(ParDataId.class.getConstructor(),
         5, "./dados/perguntas/indiceData.db");
        if(indiceData.empty()){
            reconstruirIndiceData();
        }
    }

    private void reconstruirIndiceData() throws Exception{
        for(Pergunta p : super.readAll()){
            if(p.isAtiva()){
                indiceData.create(new ParDataId(p.getCriacao(), p.getId()));
            }
        }
    }

    @Override
    public int create(Pergunta p) throws Exception{
        int id = super.create(p);

        relUsuarioPergunta.create(new ParIdId(p.getIdUsuario(), id));

        indiceData.create(new ParDataId(p.getCriacao(), id));

        return id;
    }

    public Pergunta readPergunta(int id) throws Exception{
        Pergunta p = super.read(id);
        return p;
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

    //Pagina vai de 0 ate PorPagina
    public ArrayList<Pergunta> readRecentes(int pagina, int porPagina) throws Exception{
        ArrayList<Pergunta> perguntas = new ArrayList<>();
        ArrayList<ParDataId> pares = indiceData.read(null); //todos crescente

        int inicio = pagina * porPagina;
        int fim = Math.min(inicio + porPagina, pares.size());

        for(int i = inicio; i < fim; i++){
            Pergunta p = super.read(pares.get(i).getId());
            if(p != null){
                perguntas.add(p);
            }
        }
        return perguntas;
    }

    public int contarRecentes() throws Exception{
        return indiceData.read(null).size();
    }


    @Override
    public boolean update(Pergunta p) throws Exception{
        boolean ok = super.update(p);
        if(ok && !p.isAtiva()){
            // pergunta arquivada nao fica publica
            indiceData.delete(new ParDataId(p.getCriacao(), p.getId()));
        }
        return ok;
    }
    
    @Override
    public boolean delete(int id) throws Exception{
        Pergunta p = super.read(id);
        
        if(p == null){
            return false;
        }

        relUsuarioPergunta.delete(new ParIdId(p.getIdUsuario(), id));
        indiceData.delete(new ParDataId(p.getCriacao(), id));

        return super.delete(id);
    }
}
