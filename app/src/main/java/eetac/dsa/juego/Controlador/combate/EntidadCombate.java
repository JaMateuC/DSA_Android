package eetac.dsa.juego.Controlador.combate;


import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.*;

public class EntidadCombate {
    Body body;
    Tipos tipo;

    public enum Tipos{
        Monstruo(0x0002),
        Bala(0x0004),
        Escenario(0x0008),
        SensorMonstruo(0x0010),
        SensorSalto(0x0020);

        private int tag;

        Tipos(int tag)
        {
            this.tag = tag;
        }

        public int getTag()
        {
            return tag;
        }
    }


    public EntidadCombate(Tipos tipo)
    {
        this.tipo = tipo;
    }

    public EntidadCombate(Tipos tipo,float x, float y, float radio, float densidad,float friccion,float restitucion,World world) {
        this.tipo = tipo;

        BodyDef bd = new BodyDef();
        bd.position.set(x, y);
        bd.type = BodyType.DYNAMIC;
        bd.fixedRotation = true;


        CircleShape cs = new CircleShape();
        cs.m_radius = radio;

        FixtureDef fd = new FixtureDef();
        fd.shape = cs;
        fd.density = densidad;
        fd.friction = friccion;
        fd.restitution = restitucion;
        fd.userData = this;
        fd.filter.categoryBits = Tipos.Monstruo.tag;
        fd.filter.maskBits = Tipos.Monstruo.tag|Tipos.Escenario.tag;

        body =  world.createBody(bd);
        body.createFixture(fd);
    }

    public EntidadCombate(Tipos tipo,float x, float y, float radio, float densidad,float friccion,World mundo) {
        this.tipo = tipo;

        BodyDef bd = new BodyDef();
        bd.position.set(x, y);
        bd.type = BodyType.DYNAMIC;
        bd.fixedRotation = true;


        CircleShape cs = new CircleShape();
        cs.m_radius = radio;

        FixtureDef fd = new FixtureDef();
        fd.shape = cs;
        fd.density = densidad;
        fd.friction = friccion;
        fd.restitution = 0.5f;
        fd.userData = this;

        body =  mundo.createBody(bd);
        body.createFixture(fd);
    }

    public EntidadCombate(Tipos tipo, float x, float y, float radio, float densidad, World mundo) {

        this.tipo = tipo;

        BodyDef bd = new BodyDef();
        bd.position.set(x, y);
        bd.type = BodyType.DYNAMIC;
        bd.fixedRotation = true;


        CircleShape cs = new CircleShape();
        cs.m_radius = radio;

        FixtureDef fd = new FixtureDef();
        fd.shape = cs;
        fd.density = densidad;
        fd.friction = 0.3f;
        fd.restitution = 0.5f;
        fd.userData = this;

        body =  mundo.createBody(bd);
        body.createFixture(fd);
    }

    public Tipos getTipo() {
        return tipo;
    }

    public Body getBody() {
        return body;
    }
}
