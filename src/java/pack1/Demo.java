package pack1;

import java.io.UnsupportedEncodingException;
import java.sql.*;

/**
 *
 * @author www.luv2code.com
 */
public class Demo {

    Statement myStmt = null;
    ResultSet myRs = null;
    Connection c = null;

    public String getDiscos(String zonas) throws SQLException, UnsupportedEncodingException, Exception {

        c = DBClass.getConnection();
        myStmt = c.createStatement();
        String sql = "";
        if (zonas.equals("todos")) {
            sql = "select * from Discoteca";
        } else {
            sql = "select * from Discoteca where Discoteca.zona = \"" + zonas + "\"";
        }

        myRs = myStmt.executeQuery(sql);
        int cont = 0;
        String json = "";
        json = "[\n";
        while (myRs.next()) {
            json = json + "{\n";
            json = json + "\"id\":\"" + myRs.getString("idDiscoteca") + "\",\n";
            json = json + "\"nombre\":\"" + myRs.getString("nombre") + "\",\n";
            json = json + "\"direccion\":\"" + myRs.getString("direccion") + "\",\n";
            json = json + "\"telefono\":\"" + myRs.getString("telefono") + "\",\n";
            json = json + "\"rt\":\"" + myRs.getString("rank") + "\"\n";
            json = json + "}\n";
            if (cont == 0) {
                json = json + ",";
            }
            cont++;
        }
        json = json + "]\n";
        return json;
    }

    public String insertComment(String text, float rank, String idFbUser, int idDisc) throws SQLException, Exception {
        System.err.println("////** " + idFbUser);
        c = DBClass.getConnection();
        myStmt = c.createStatement();
        String sql = "SELECT idUsuario FROM guateDiscoBD.Usuario where idFb = \"" + idFbUser + "\"";
        myRs = myStmt.executeQuery(sql);
        String id = "";
        while (myRs.next()) {
            id = myRs.getString("idUsuario");
        }

        System.err.println("/////////////" + id);

        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String currentTime = sdf.format(dt);

        String resultadoInser = "error";
        String insert = "INSERT INTO `guateDiscoBD`.`Comentario`\n"
                + "(`texto`,`fecha`,`estado`,\n"
                + "`rank`,`Discoteca_idDiscoteca`,`Usuario_idUsuario`)\n"
                + "VALUES\n"
                + "(\"" + text + "\",'" + currentTime + "',1," + rank + "," + idDisc + "," + id + ");";
        System.err.println("" + insert);

        try {
            c = DBClass.getConnection();
            Statement stmt = c.createStatement();
            stmt.executeUpdate(insert);
            resultadoInser = "exitoso";
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultadoInser;
    }

    String getCommentarios(int id) throws SQLException, UnsupportedEncodingException, Exception {

        c = DBClass.getConnection();
        myStmt = c.createStatement();
        myRs = myStmt.executeQuery(""
                + "SELECT c.texto,c.rank,c.fecha, u.usuario, u.urlPic FROM `guateDiscoBD`.`Comentario` c, `guateDiscoBD`.`Usuario` u\n"
                + "where c.Discoteca_idDiscoteca = " + id + " and\n"
                + "u.idUsuario  = c.Usuario_idUsuario and\n"
                + "	c.estado = 1  order by fecha desc, rank desc");
        int cont = 0;
        String json = "";
        json = "[\n";
        while (myRs.next()) {
            json = json + "{\n";
            json = json + "\"texto\":\"" + myRs.getString("texto") + "\",\n";
            json = json + "\"fecha\":\"" + myRs.getString("fecha") + "\",\n";
            json = json + "\"rt\":\"" + myRs.getString("rank") + "\"\n";
            json = json + "\"user\":\"" + myRs.getString("usuario") + "\",\n";
            json = json + "\"urlPic\":\"" + myRs.getString("urlPic") + "\",\n";
            json = json + "}\n";
            if (cont == 0) {
                json = json + ",";
            }
            cont++;
        }
        json = json + "]\n";
        return json;
    }

    String insertNewUser(String idFb, String name, String gender, String birthday, String urlPic) throws SQLException {

        String resultadoInser = "error";
//        String insert = "INSERT INTO `guateDiscoBD`.`Usuario`\n"
//                + "(`idFb`,`usuario`,`gender`,`birthday`,`urlPic`)\n"
//                + "VALUES\n"
//                + "(\"" + idFb + "\",\"" + name + "\",\"" + gender + "\",\"" + birthday + "\",\"" + urlPic + "\");";
        
        
        
        String insert ="INSERT INTO `guateDiscoBD`.`Usuario` (idFb, usuario, gender, birthday, urlPic)\n" +
                    "SELECT * FROM (SELECT \"" + idFb + "\",\"" + name + "\",\"" + gender + "\",\"" + birthday + "\",\"" + urlPic + "\") AS tmp\n" +
                    "WHERE NOT EXISTS (\n" +
                    "    SELECT idFb FROM `guateDiscoBD`.`Usuario` WHERE idFb = \"" + idFb + "\"\n" +
                    ") LIMIT 1;";
        
        
        System.err.println("" + insert);
        try {
            c = DBClass.getConnection();
            Statement stmt = c.createStatement();
            stmt.executeUpdate(insert);
            resultadoInser = "exitoso";
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultadoInser;
    }

    String getDisco(String id) throws SQLException, UnsupportedEncodingException, Exception {
        c = DBClass.getConnection();
        myStmt = c.createStatement();
        myRs = myStmt.executeQuery("SELECT d.latitud, d.altitud,d.paginaWeb,d.facebook,d.twitter,d.descripcion "
                + "FROM guateDiscoBD.Discoteca d "
                + "where d.idDiscoteca =" + id + ";");
        String json = "";
        json = "[\n";
        while (myRs.next()) {
            json = json + "{\n";
            json = json + "\"latitud\":\"" + myRs.getString("latitud") + "\",\n";
            json = json + "\"altitud\":\"" + myRs.getString("altitud") + "\",\n";
            json = json + "\"webSite\":\"" + myRs.getString("paginaWeb") + "\"\n";
            json = json + "\"facebook\":\"" + myRs.getString("facebook") + "\"\n";
            json = json + "\"twitter\":\"" + myRs.getString("twitter") + "\"\n";
            json = json + "\"descripcion\":\"" + myRs.getString("descripcion") + "\"\n";
            json = json + "}\n";
        }
        json = json + "]\n";
        return json;
    }

}
