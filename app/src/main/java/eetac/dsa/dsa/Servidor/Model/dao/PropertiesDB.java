package eetac.dsa.dsa.Servidor.Model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PropertiesDB {

    private static PropertiesDB instance = null;
    private List<String> properties = new ArrayList<>();

    private PropertiesDB() {

        // Exists only to defeat instantiation.
    }
    public static PropertiesDB getInstance() {
        if(instance == null) {
            instance = new PropertiesDB();
        }
        return instance;
    }

    public String getText(String propertyName){

        if(false == properties.contains(propertyName)){

            properties.add(propertyName);

        }

        ResourceBundle label1 = ResourceBundle.getBundle("database");
        return label1.getString(propertyName);

    }

}
