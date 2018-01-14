package eetac.dsa.juego.Controlador;

/**
 * Plantilla de un Monstruo, los diferentes tipos de monstruo herederan de esta classe.
 * vida, ataqueBase,defensaBase no son los valores usados en el combate.
 * los valores usados seran del estilo ataqueBase*nivel.
 * para obtener los valores usados en comabte usar los metodos getVidaEfectva, getAtaqueEfectivo etz.
 */
public abstract class Monstruo
{
    private int nivel;
    private int experiencia;

    //parametros base a nivel 1
    private int vidaBase;
    private int ataqueBase;
    private int defensaBase;
    private int experienciaBase;

    private int vidaActual;
    private Lista_Ataques lista_ataques;

    String id;

    private String tipo;

    public Monstruo(int vidaBase, int ataqueBase, int defensaBase, String tipo,int experienciaBase,int nivel)
    {
        this.vidaBase = vidaBase;
        this.ataqueBase = ataqueBase;
        this.defensaBase = defensaBase;
        this.experienciaBase = experienciaBase;
        this.tipo = tipo;
        lista_ataques = new Lista_Ataques();
        this.nivel = nivel;
        this.vidaActual = vidaBase*nivel;
    }

    public int getNivel() {
        return nivel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }

    public int getExperiencia() {
        return experiencia;
    }

    public int getVidaBase() {
        return vidaBase;
    }

    public int getAtaqueBase() {
        return ataqueBase;
    }

    public int getDefensaBase() {
        return defensaBase;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public Lista_Ataques getLista_ataques() {
        return lista_ataques;
    }

    /**
     * obten la vida maxima corregido por el nivel
     * @return vida*nivel
     */
    public int getVidaEfectiva() {
        return vidaBase*nivel;
    }

    /**
     * obten el ataqueBase corregido por el nivel
     * @return ataqueBase*nivel
     */
    public int getAtaqueEfectivo() {
        return ataqueBase *nivel;
    }

    public int getExperienciaBase() {
        return experienciaBase;
    }

    /**
     * devuelve la experiencia total necesaria para alcanzar el proximo nivel
     * @return
     */
    public int getExperienciaEfectiva()
    {
        return experienciaBase*nivel;
    }

    /**
     * obten la defensaBase corregido por el nivel
     * @return defensaBase*nivel
     */
    public int getDefensaEfectiva() {
        return defensaBase*nivel;
    }

    public String getTipo() {
        return tipo;
    }

    /**
     * devuelve la vida que le queda en este momento
     * @return
     */
    public int getVidaActual() {
        return vidaActual;
    }

    public void setVidaActual(int vidaActual) {
        this.vidaActual = vidaActual;
    }

    /**
     * añada vida al monstruo, en caso de añadir mas vida de la vida maxima del monstruo, la vida sobrante no se acumula
     * @param vidaExtra
     */
    public void añadirVida(int vidaExtra)
    {
        if(vidaExtra+vidaActual<getVidaEfectiva())
        {
            vidaActual+=vidaExtra;
        }
        else
        {
            vidaActual= getVidaEfectiva();
        }
    }

    public void añadirExperiencia(int experienciaExtra)
    {
        if(((float)(experienciaExtra+this.experiencia))/((float)getExperienciaEfectiva())<1.f)
        {
            experiencia+=experienciaExtra;
        }
        else
        {
            int experienciaRestante = experiencia+experienciaExtra-getExperienciaEfectiva();
            experiencia = 0;
            nivel++;
            añadirExperiencia(experienciaRestante);
            vidaActual = getVidaEfectiva();
        }
    }

    public void recibirDaño(int daño)
    {
        if(daño-getDefensaEfectiva()>vidaActual)
        {
            vidaActual=0;
        }
        else if(daño<getDefensaEfectiva())
        {

        }
        else
        {
            vidaActual= vidaActual-(daño-getDefensaEfectiva());
        }
    }

    public boolean atacar(Monstruo enemigo, int indiceAtaque)
    {
        if(lista_ataques.getNumeroAtaques()+1<indiceAtaque)
        {
            return false;
        }
        Ataque tmp = this.lista_ataques.getAtaque(indiceAtaque);
        enemigo.recibirDaño((int)(tmp.multiplicador*this.getAtaqueEfectivo()));
        tmp.extraAtacante(this);
        tmp.extraEnemigo(enemigo);
        return true;
    }

    public boolean atacar(Monstruo enemigo, Ataque ataque)
    {
        Ataque tmp = ataque;
        enemigo.recibirDaño((int)(tmp.multiplicador*this.getAtaqueEfectivo()));
        tmp.extraAtacante(this);
        tmp.extraEnemigo(enemigo);
        return true;
    }

    @Override
    public String toString() {
        return "tipo: "+ tipo+ "   nivel: " +nivel;
    }
}
