/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pack1;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author akroma
 */
@WebService(serviceName = "MyWebService")
public class MyWebService {

    @WebMethod(operationName = "getOp")
    public float getOp(@WebParam(name = "value") float value) {
        //TODO write your implementation code here:
        return value = value * 2;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getDiscos")
    public String getDiscos(@WebParam(name = "zonas") String zonas) {
        String result = null;
        try {
            Demo d = new Demo();
            result = d.getDiscos(zonas);
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "insertComment")
    public String insertComment(@WebParam(name = "cadena") String cadena) {
        String part[] = cadena.split(";");
        String text = part[0];
        float rank = Float.parseFloat(part[1]);
        int idDisco = Integer.parseInt(part[2]);
        String idFbUser = part[3];
        Demo d = new Demo();
        String resultado = null;
        try {
            resultado = d.insertComment(text, rank, idFbUser, idDisco);
        } catch (Exception e) {
            resultado = e.toString();
        }
        return resultado;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getComments")
    public String getComments(@WebParam(name = "idDisc") int idDisc) {
        String result = null;
        try {
            Demo d = new Demo();
            result = d.getCommentarios(idDisc);
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "NewUser")
    public String NewUser(@WebParam(name = "cadena") String cadena) {
        String[] usr = cadena.split(";");
        String idFb = usr[0];
        String name = usr[1];
        String gender = usr[2];
        String birthday = usr[3];
        String urlPic = usr[4];

        Demo d = new Demo();
        String resultado = null;
        try {
            resultado = d.insertNewUser(idFb, name, gender, birthday, urlPic);
        } catch (SQLException ex) {
            Logger.getLogger(MyWebService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }
}
