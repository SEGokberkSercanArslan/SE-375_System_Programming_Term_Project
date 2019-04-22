package Server;

import java.sql.*;

public class CFSDatabase {

    private String url = "jdbc:sqlite:src/Server/Database/ClientDatabase.db";

    public CFSDatabase(){

    }

    protected void add_new_user(String username,String password){
        String sql = "INSERT INTO ClientInfo(Username,Password) VALUES(?,?)";
        try (Connection connection = this.connect();
             PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,username);
            statement.setString(2,password);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected boolean is_user_exist(String username){
        String sql = "SELECT Username FROM ClientInfo";
        try (Connection connection = this.connect();
            PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                if (resultSet.getString("Username").equals(username)){
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    protected String get_user_password(String username){
        String sql = "SELECT * FROM ClientInfo";
        try (Connection connection = this.connect();
            PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                if (resultSet.getString("Username").equals(username)){
                    return resultSet.getString("Password");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Not Found";
    }

    private Connection connect(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(this.url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

}
