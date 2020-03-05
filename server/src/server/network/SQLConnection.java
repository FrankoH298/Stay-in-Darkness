package server.network;


import com.mysql.cj.protocol.ResultsetRowsOwner;
import server.clients.Player;

import java.sql.*;

@SuppressWarnings("ALL")
public class SQLConnection {

    // Connection Info
    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC";
    private static final String userName = "root";
    private static final String passWord = "";

    public SQLConnection() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Player loadUser(String name) {
        try {
            // Conectamos a la DB
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, userName, passWord);
            System.out.println("Connection successful");
            Statement stmt = con.createStatement();

            // Cargamos valores.
            ResultSet rs = stmt.executeQuery("SELECT * FROM infoplayer WHERE Username = " + "'" + name + "'");
            int indexPJ;
            float x, y;
            int heading;
            if (rs.next()) {
                indexPJ = rs.getInt("indexPJ");
                rs = stmt.executeQuery("SELECT * FROM position WHERE indexPJ=" + "'" + indexPJ + "'");
                if (rs.next()) {
                    x = rs.getFloat("x");
                    y = rs.getFloat("y");
                    heading = rs.getInt("heading");

                    // Cerramos la conexion.
                    con.close();
                    // Retornamos el jugador cargado.
                    return (new Player(indexPJ, x, y, heading));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean checkPassword(String name, String password) {
        try {
            Class.forName(driver);
            // Conectamos a la DB
            Connection con = DriverManager.getConnection(url, userName, passWord);
            System.out.println("Connection successful");
            Statement stmt = con.createStatement();

            // Checkeamos la contraseña.
            ResultSet rs = stmt.executeQuery("SELECT * FROM infoplayer WHERE Username = " + "'" + name + "'");
            if (rs.next()) {
                boolean checkPassword = rs.getString("Password").equals(password);
                System.out.println(password);
                System.out.println(rs.getString("Password"));
                con.close();
                return checkPassword;
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean saveUser(Player player) {
        try {
            // Conectamos a la DB
            Connection con = DriverManager.getConnection(url, userName, passWord);
            System.out.println("Connection successful");
            Statement stmt = con.createStatement();

            // Guardamos los valores.
            String sql = "INSERT INTO infoplayer (Username, Password) VALUES (?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, player.name);
            ps.setString(2, player.password);
            ps.executeUpdate();
            sql = "INSERT INTO position (x, y, heading) VALUES (?,?)";
            ps = con.prepareStatement(sql);
            ps.executeUpdate();
            con.close();
            return true;
        } catch (SQLException e) {
        }
        return false;
    }
}
