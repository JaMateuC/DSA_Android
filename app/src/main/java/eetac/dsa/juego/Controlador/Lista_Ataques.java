package eetac.dsa.juego.Controlador;

import java.util.Vector;

public class Lista_Ataques
{
    private Vector<Ataque> lista_ataques;
    private int maxAtaques = 4;

    public Lista_Ataques() {
        this.lista_ataques = new Vector<Ataque>();
    }

    public Vector<Ataque> getLista_ataques() {
        return lista_ataques;
    }

    public Boolean añadirAtaque(Ataque ataque)
    {
        if(this.lista_ataques.size()<this.maxAtaques)
        {
            this.lista_ataques.add(ataque);
            return true;
        }
        return false;
    }

    /**
     * devuelve el ataqueBase en la posicion i, si no existe devuelve null;
     * @param i
     * @return
     */
    public Ataque getAtaque(int i)
    {
        if(i<this.lista_ataques.size())
        {
            return this.lista_ataques.get(i);
        }
        return null;
    }

    public Boolean anadirMultiplesAtaques(Lista_Ataques lista){

        if(this.lista_ataques.size() + lista.getNumeroAtaques() <= this.maxAtaques)
        {
            for(Ataque ataque: lista.getLista_ataques()){
                this.lista_ataques.add(ataque);
            }
                return true;
        }
        return false;

    }

    public int getNumeroAtaques()
    {
        return this.lista_ataques.size();
    }
}
