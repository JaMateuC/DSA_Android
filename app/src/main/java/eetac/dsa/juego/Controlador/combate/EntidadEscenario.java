package eetac.dsa.juego.Controlador.combate;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

public class EntidadEscenario {
    float ancho;
    float alto;
    EntidadCombate flag;
    public EntidadEscenario(float ancho, float alto,World mundo) {

        this.flag = new EntidadCombate(EntidadCombate.Tipos.Escenario);

        this.ancho = ancho;
        this.alto = alto;
        BodyDef bodydef = new BodyDef();

        bodydef.type = BodyType.STATIC;
        bodydef.position = new Vec2(0,0);
        bodydef.userData = flag;

        PolygonShape tierra = new PolygonShape();
        tierra.setAsBox(ancho/2,1f,new Vec2(0,-alto/2),0);
        PolygonShape cielo = new PolygonShape();
        cielo.setAsBox(ancho/2,1f,new Vec2(0,alto/2),0);
        PolygonShape derecha = new PolygonShape();
        derecha.setAsBox(1f,alto/2,new Vec2(-ancho/2,0),0);
        PolygonShape izquierda = new PolygonShape();
        izquierda.setAsBox(1f,alto/2,new Vec2(ancho/2,0),0);

        Body cuerpoEsenario = mundo.createBody(bodydef);

        FixtureDef tierradef = new FixtureDef();
        tierradef.shape = tierra;
        tierradef.userData = flag;
        tierradef.friction = 3f;
        tierradef.density = 1;
        tierradef.filter.categoryBits = EntidadCombate.Tipos.Escenario.getTag();
        tierradef.filter.maskBits = EntidadCombate.Tipos.Monstruo.getTag()| EntidadCombate.Tipos.SensorSalto.getTag();

        FixtureDef cielodef = new FixtureDef();
        cielodef.shape = cielo;
        cielodef.userData = flag;
        cielodef.friction = 3f;
        cielodef.density = 1;
        cielodef.filter.categoryBits = EntidadCombate.Tipos.Escenario.getTag();
        cielodef.filter.maskBits = EntidadCombate.Tipos.Monstruo.getTag();

        FixtureDef izquierdadef = new FixtureDef();
        izquierdadef.shape = izquierda;
        izquierdadef.userData = flag;
        izquierdadef.density = 1;
        izquierdadef.friction = 3f;
        izquierdadef.filter.categoryBits = EntidadCombate.Tipos.Escenario.getTag();
        izquierdadef.filter.maskBits = EntidadCombate.Tipos.Monstruo.getTag();

        FixtureDef derechadef = new FixtureDef();
        derechadef.shape = derecha;
        derechadef.userData = flag;
        derechadef.density = 1;
        derechadef.friction = 3f;
        derechadef.filter.categoryBits = EntidadCombate.Tipos.Escenario.getTag();
        derechadef.filter.maskBits = EntidadCombate.Tipos.Monstruo.getTag();


        cuerpoEsenario.createFixture(tierradef);
        cuerpoEsenario.createFixture(cielodef);
        cuerpoEsenario.createFixture( izquierdadef);
        cuerpoEsenario.createFixture(derechadef);

    }
}
