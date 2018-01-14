package eetac.dsa.juego.Controlador.combate;




public class Proba /*extends TestbedTest*/{
/*    EntidadEscenario escenario;
    MonstruoEntidad monstruo;


    public void initTest(boolean b) {
        setTitle("pruebaMonstruo");
        getWorld().setGravity(new Vec2(0.f,-9.8f));
        getWorld().setContactListener(new CombatCollisionListener());
        escenario = new EntidadEscenario(30,30,getWorld());
        monstruo = new MonstruoEntidad(0,0,0.75f,0.5f,new MonstruoEjemplo(1,1),getWorld());
        MonstruoEntidad monstruo2 = new MonstruoEntidad(-10,0,0.75f,0.5f,new MonstruoEjemplo(1,1),getWorld());
    }


    @Override
    public void keyPressed(char argKeyChar, int argKeyCode) {
        switch (argKeyChar) {
            case 'w':
                monstruo.saltar();
                break;

            case 'a':
                monstruo.andarIzquierda();
                break;

            case 'd':
                monstruo.andarDerecha();
                break;

            case '1':
                monstruo.setAtaqueSeleccionado1(0);
                break;

            case '2':
                monstruo.setAtaqueSeleccionado1(1);
                break;

            case '3':
                monstruo.setAtaqueSeleccionado1(2);
                break;

            case '4':
                monstruo.setAtaqueSeleccionado1(3);
                break;


            case '0':
                monstruo.ataque1();
                break;
        }
    }



    @Override
    public synchronized void step(TestbedSettings settings) {
        super.step(settings);
        monstruo.step(1f/60);
    }

    public String getTestName() {
        return "prueba monstruo";
    }




    public static void main( String[] args )
    {
        TestbedModel model = new TestbedModel();         // create our model

// add tests
        TestList.populateModel(model);                   // populate the provided testbed tests
        model.addCategory("My Super Tests");             // add a category
        model.addTest(new Proba());                // add our test

// add our custom setting "My Range Setting", with a default value of 10, between 0 and 20
        model.getSettings().addSetting(new TestbedSetting("My Range Setting", TestbedSetting.SettingType.ENGINE, 10, 0, 20));

        TestbedPanel panel = new TestPanelJ2D(model);    // create our testbed panel


        JFrame testbed = new TestbedFrame(model, panel, TestbedController.UpdateBehavior.UPDATE_CALLED); // put both into our testbed frame
// etc
        testbed.setVisible(true);
        testbed.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }*/
}
