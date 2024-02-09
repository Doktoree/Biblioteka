/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lav
 */
public class Config {
    
    private static Config instanca;
    private String username;
    private String dbUrl;
    private String password;
    private int port;
    private Properties prop;
    
    private Config() {
        prop = new Properties();
        prop = ucitajProperti();
        username = prop.getProperty("username");
        dbUrl = prop.getProperty("dbUrl");
        password = prop.getProperty("password");
        port = Integer.parseInt(prop.getProperty("port"));
        
    }

    
    public static Config getInstanca() {
        if(instanca == null){
            instanca = new Config();
                    
        }
        return instanca;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
    
    public Properties ucitajProperti(){
        
        try {
            InputStream in = new FileInputStream("./src/property/prop.properties");
            prop.load(in);
            return prop;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    
         
    
    
}
