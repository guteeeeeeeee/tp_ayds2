/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.servidor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import servidor.Servidor;

/**
 *
 * @author ignacio
 */
public class Main {
    
    public static void main(String[] args) throws Exception {
    	Servidor servidor = new Servidor();
    	servidor.startServer();
    }
    
}
