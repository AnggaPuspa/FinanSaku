/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package finansaku2;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author Angga Puspa
 */
public class koneksi {
    
    public static Connection koneksi(){
        try{
           Class.forName("com.mysql.jdbc.Driver"); 
           Connection koneksi = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/db_finansaku","root","");
           System.out.println("koneksi berhasil");
           return koneksi;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
            System.err.println("koneksi gagal");
            return null;
            
        }
    }
    public static void main(String[] args){
       koneksi objek = new koneksi();
        objek.koneksi();
    }
     
}
