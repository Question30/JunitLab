package org.example;

import java.sql.*;

public class PasswordComply {

    private String passwordString;
    private final int minPasswordLength = 8;
    private final int maxPasswordLength = 12;

    static final String DB_URL = "jdbc:mysql://localhost:3306/PERSCHOLAS";
    static final String USER ="root";
    static final String PASS ="password";
    static final String QUERY = "{call getEmpName(? ,?)}";

    public PasswordComply(String verifyPassword){
        passwordString = verifyPassword;
    }

    private boolean verifyPasswordLength(){
        if(!passwordString.isEmpty()){
            if(passwordString.length() >= minPasswordLength && passwordString.length() <= maxPasswordLength){
                return true;
            }
        }
        return false;
    }

    private boolean verifyAlphaNumeric(){
        return true;
    }

    private boolean hasAllowespecialCharacters(){
        return true;
    }

    public boolean doesNotAlreadyExist() throws SQLException{

        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        CallableStatement stmt = conn.prepareCall(QUERY);
        stmt.setString(1, passwordString);
        stmt.registerOutParameter(2, Types.VARCHAR);
        System.out.println("Executing stored procedure...");
        stmt.execute();

        String existingPassword = stmt.getString(2);
        System.out.println("Password already exist" + existingPassword);
        return true;
    }

    private boolean hasNoSpecialCharacters(){
        return true;
    }

    public void setPassword(String givenPassword){
        passwordString = givenPassword;
    }

    public boolean doesPasswordComply(){
        return verifyPasswordLength() && verifyAlphaNumeric() && hasAllowespecialCharacters() && hasNoSpecialCharacters();
    }
}
