package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {
    
    public Account insertAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "insert into account(username, password) values (?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_author_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_author_id, account.getUsername(), account.getPassword());
            }
        }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        return null;
    }

    public Account loginAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "select * from account where username = ? AND password = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            ResultSet pkeyResultSet = preparedStatement.executeQuery(sql);
            if(pkeyResultSet.first()){
                return new Account(pkeyResultSet.getInt("account_id"), pkeyResultSet.getString("username"), pkeyResultSet.getString("password"));
            }
        }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        return null;
    }
    
}