/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myjava.maumanya;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author Lenovo
 */
public class koneksi {
    private static final String URL = "jdbc:mysql://localhost:3306/kasir_ayamgebug";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    
    public static Connection getkoneksi() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Koneksi Database Berhasil!");
            return conn;
        }catch (SQLException e) {
            System.err.println("Koneksi Database Gagal!");
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }
}
