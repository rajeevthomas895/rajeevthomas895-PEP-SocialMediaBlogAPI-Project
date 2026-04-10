package DAO;
import Model.Account;
import Util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class AccountDAO {
  public Account getAccountByUsername(String username){
    try{
      Connection conn=ConnectionUtil.getConnection();
      String sql="select * from account where username=?;";
      PreparedStatement ps= conn.prepareStatement(sql);
      ps.setString(1,username);
      ResultSet rs=ps.executeQuery();
      if(rs.next()){
        return new Account(
          rs.getInt("account_id"),
          rs.getString("username"),
          rs.getString("password")
        );
      }
    }
    catch(Exception e){
      e.printStackTrace();
    }
    return null;
  }
  public Account insertAccount(Account account){
    try {
      Connection conn=ConnectionUtil.getConnection();
      String sql="insert into account (username,password) values (?,?);";
      PreparedStatement ps= conn.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
      ps.setString(1,account.getUsername());
      ps.setString(2,account.getPassword());
      ps.executeUpdate();
      ResultSet rs= ps.getGeneratedKeys();
      if(rs.next()){
        int id=rs.getInt(1);
        return new Account(id,account.getUsername(),account.getPassword());
      }
    } 
    catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
  public Account getAccountById(int id){
    try{
      Connection conn=ConnectionUtil.getConnection();
      String sql="select * from account where account_id=?;";
      PreparedStatement ps=conn.prepareStatement(sql);
      ps.setInt(1,id);
      ResultSet rs=ps.executeQuery();
      if(rs.next()){
        return new Account(
          rs.getInt("account_id"),
          rs.getString("username"),
          rs.getString("password")
        );
      }
    }
    catch(Exception e){
      e.printStackTrace();
    }
    return null;
  }
}
