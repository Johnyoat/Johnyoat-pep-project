package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {
    Connection connection = ConnectionUtil.getConnection();

    public Account getAccountByUserName(String username){


        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from account where username = ?");
            preparedStatement.setString(1, username);

            ResultSet results = preparedStatement.executeQuery();

            while (results.next()) {
               
                return new Account(results.getInt("account_id"), results.getString("username"), results.getString("password"));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Account getAccountByUserId(int userId){


        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from account where account_id = ?");
            preparedStatement.setInt(1, userId);

            ResultSet results = preparedStatement.executeQuery();

            while (results.next()) {
                return new Account(results.getInt("account_id"), results.getString("username"), results.getString("password"));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }





    public Account createAccount(Account account){
        try {
            String sql = "insert into account (username,password) values (?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            

            preparedStatement.executeUpdate();

            ResultSet generatedKey = preparedStatement.getGeneratedKeys();

            if (generatedKey.next()) {
                return new Account((int) generatedKey.getLong(1),account.getUsername(),account.getPassword());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public Account logIn(String username,String password){


        try {
           PreparedStatement preparedStatement = connection.prepareStatement("select * from account where username = ? and password = ?");
           preparedStatement.setString(1, username);
           preparedStatement.setString(2,password);
           
           ResultSet results = preparedStatement.executeQuery();


          while (results.next()) {
            return new Account(results.getInt("account_id"),results.getString("username"),results.getString("password"));
          }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}

