package eetac.dsa.dsa.Servidor.Model.dao;



import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Properties;

public abstract class DAO {

    private Connection con = null;
    private String nombreUsuario;
    private String password;
    private String dbms;
    private String nombreServidor;
    private String puerto;
    private String dbNombre;



    public void initProperties(){

        this.nombreUsuario = PropertiesDB.getInstance().getText("nombreUsuario");
        this.password = PropertiesDB.getInstance().getText("password");
        this.dbms = PropertiesDB.getInstance().getText("dbms");
        this.nombreServidor = PropertiesDB.getInstance().getText("nombreServidor");
        this.puerto = PropertiesDB.getInstance().getText("puerto");
        this.dbNombre = PropertiesDB.getInstance().getText("dbNombre");

    }

    public void getConnection() throws SQLException {

        initProperties();
        Properties connectionProps = new Properties();
        connectionProps.put("user", this.nombreUsuario);
        connectionProps.put("password", this.password);

        String connectionString = "jdbc:" + this.dbms + "://" +
                this.nombreServidor +
                ":" + this.puerto + "/" + dbNombre;


        this.con = DriverManager.getConnection(connectionString,
               connectionProps);



    }

    /*INSERT STATMENTS*/

    public void insertDB() throws SQLException{

        getConnection();

        StringBuffer  buffer = new StringBuffer();

        buffer.append("INSERT INTO ");
        buffer.append(this.getClass().getSimpleName());
        buffer.append(" (");

        Field[] fields = ordenarFields();

        for( Field field : fields){
            buffer.append(field.getName()+",");

        }

        buffer.delete(buffer.length()-1,buffer.length());
        buffer.append(") ");
        buffer.append("Values");
        buffer.append(" (");

        Method[] methods = ordenarGetMethods();

        for(Method method : methods){

            if (method.getName().startsWith("get"))
                try{
                if(method.getReturnType().equals(String.class)) {
                    buffer.append("'" + method.invoke(this, null).toString() + "'" + ",");
                } else {
                    buffer.append(method.invoke(this, null).toString() + ",");
                }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

        }
        buffer.delete(buffer.length()-1,buffer.length());
        buffer.append(") ");



        Statement st = this.con.createStatement();

        st.executeUpdate(buffer.toString());

    }

    /*DELETE STATMENTS*/

    public void deleteDB() throws SQLException{

        getConnection();

        StringBuffer  buffer = new StringBuffer();

        buffer.append("DELETE FROM ");
        buffer.append(this.getClass().getSimpleName());
        buffer.append(" WHERE ");
        for( Field field : this.getClass().getDeclaredFields()){
            if (field.getName().startsWith("id")){
                buffer.append(field.getName());
            }
        }

        buffer.append(" = ");

        buffer.append(getIDObject());



        Statement st = this.con.createStatement();

        st.executeUpdate(buffer.toString());

    }

    /*SELECT STATMENTS*/

    public void selectDB(String id) throws SQLException{

        getConnection();

        StringBuffer  buffer = new StringBuffer();

        buffer.append("SELECT * FROM ");
        buffer.append(this.getClass().getSimpleName());
        buffer.append(" WHERE ");
        for( Field field : this.getClass().getDeclaredFields()){
            if (field.getName().startsWith("id")){
                buffer.append(field.getName());
            }
        }

        buffer.append(" = '" + id + "'");



        Statement st = this.con.createStatement();

        ResultSet rs = st.executeQuery(buffer.toString());

        Method[] methods = ordenarSetMethods();

        while(rs.next()){

            ResultSetMetaData rsmd = rs.getMetaData();
            try{
                for(int i = 1; i <= rsmd.getColumnCount(); i++){

                    int sqlTypes = rsmd.getColumnType(i);

                    switch (sqlTypes) {
                        case Types.VARCHAR:
                            methods[i-1].invoke(this,rs.getString(i));
                            break;
                        case Types.BOOLEAN:
                            methods[i-1].invoke(this,rs.getBoolean(i));
                            break;
                        case Types.INTEGER:
                            methods[i-1].invoke(this,rs.getInt(i));
                            break;

                    }

                }

            }catch (Exception e){
                e.printStackTrace();
            }



        }


    }

    /*UPDATE STATMENTS*/

    public void updateDB() throws SQLException{

        getConnection();

        StringBuffer  buffer = new StringBuffer();

        buffer.append("UPDATE ");
        buffer.append(this.getClass().getSimpleName());
        buffer.append(" SET ");

        Field[] fields = ordenarFields();
        Method[] methods = ordenarGetMethods();

        for( Field field : fields){

            int fieldIndex = 1;

            Annotation aField = field.getAnnotation(OrderFields.class);
            if (aField != null && aField instanceof OrderFields){
                final OrderFields anotacion = (OrderFields) aField;
                fieldIndex = anotacion.indice();
            }

            buffer.append(field.getName() + " = ");

            for(Method method : methods){



                int methodIndex = 0;

                Annotation aMethod = method.getAnnotation(OrderGetMethods.class);
                if (aMethod != null && aMethod instanceof OrderGetMethods){
                    final OrderGetMethods anotacion = (OrderGetMethods) aMethod;
                    methodIndex = anotacion.indice();
                }


                if (fieldIndex == methodIndex)
                    try{
                        if(method.getReturnType().equals(String.class)) {
                            buffer.append("'" + method.invoke(this, null).toString() + "'" + ",");
                        } else {
                            buffer.append(method.invoke(this, null).toString() + ",");
                        }
                        break;
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

            }

        }
        buffer.delete(buffer.length()-1,buffer.length());

        buffer.append(" WHERE ");
        for( Field field : this.getClass().getDeclaredFields()){
            if (field.getName().startsWith("id")){
                buffer.append(field.getName());
                break;
            }
        }

        buffer.append(" = ");

        buffer.append(getIDObject());


        Statement st = this.con.createStatement();

        st.executeUpdate(buffer.toString());

    }

    public String getIDObject(){

        for(Method method : this.getClass().getDeclaredMethods()){
            if (method.getName().startsWith("getId")){
                try{
                    String id = "'" + method.invoke(this,null).toString() + "'";
                    return id;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }

        }

        return "";

    }

    public Field[] ordenarFields(){

        Field[] fields = this.getClass().getDeclaredFields();
        Arrays.sort(fields, new Comparator<Field>() {
            @Override
            public int compare(Field o1, Field o2) {
                OrderFields or1 = o1.getAnnotation(OrderFields.class);
                OrderFields or2 = o2.getAnnotation(OrderFields.class);
                if (or1 != null && or2 != null) {
                    return or1.indice() - or2.indice();
                } else
                if (or1 != null && or2 == null) {
                    return -1;
                } else
                if (or1 == null && or2 != null) {
                    return 1;
                }
                return o1.getName().compareTo(o2.getName());
            }
        });

        return fields;

    }

    public Method[] ordenarGetMethods(){

        Method[] methods = this.getClass().getDeclaredMethods();
        Arrays.sort(methods, new Comparator<Method>() {
            @Override
            public int compare(Method o1, Method o2) {
                OrderGetMethods or1 = o1.getAnnotation(OrderGetMethods.class);
                OrderGetMethods or2 = o2.getAnnotation(OrderGetMethods.class);
                if (or1 != null && or2 != null) {
                    return or1.indice() - or2.indice();
                } else
                if (or1 != null && or2 == null) {
                    return -1;
                } else
                if (or1 == null && or2 != null) {
                    return 1;
                }
                return o1.getName().compareTo(o2.getName());
            }
        });

        return methods;

    }

    public Method[] ordenarSetMethods(){

        Method[] methods = this.getClass().getDeclaredMethods();
        Arrays.sort(methods, new Comparator<Method>() {
            @Override
            public int compare(Method o1, Method o2) {
                OrderSetMethods or1 = o1.getAnnotation(OrderSetMethods.class);
                OrderSetMethods or2 = o2.getAnnotation(OrderSetMethods.class);
                if (or1 != null && or2 != null) {
                    return or1.indice() - or2.indice();
                } else
                if (or1 != null && or2 == null) {
                    return -1;
                } else
                if (or1 == null && or2 != null) {
                    return 1;
                }
                return o1.getName().compareTo(o2.getName());
            }
        });

        return methods;

    }

}
