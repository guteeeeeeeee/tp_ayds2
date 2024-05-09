/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servidor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mycompany.televisor.Televisor;

//import modelo.Administrador;
import modelo.Constantes;
import modelo.Empleado;
import modelo.Monitor;
import modelo.Totem;

/**
 *
 * @author ignacio
 */
public class Servidor extends Thread implements Serializable{
    private LocalDateTime tiempo = LocalDateTime.now();
    private DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private static final long serialVersionUID = 4209360273818925922L;
    private ColasManager manager = new ColasManager();
    private PrintWriter out;
    private ServerSocket serverSocket;
    
    public Servidor() throws IOException{
    	 this.serverSocket = new ServerSocket(5555);
         System.out.println("Servidor TCP iniciado. Esperando conexiones...");
    }
    
    public void startServer() {
    	try {
			while(true) {
				Socket clientSocket = this.serverSocket.accept();
				Thread thread = new Thread(new ClientHandler(clientSocket,manager));
				thread.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    private static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private ColasManager manager;

        public ClientHandler(Socket clientSocket,ColasManager manager) {
            this.clientSocket = clientSocket;
            this.manager = manager;
        }

        @Override
        public void run() {
            try {
                /*BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);*/
                //ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());  
                
                // Comunicación bidireccional
                DatosConexion datos = new DatosConexion(this.clientSocket);
                
                //Object obj = datos.ois.readObject();
                Object obj = datos.ois.readObject();
                if(obj instanceof Totem){
                	System.out.println("entra totem");
                    Totem totem=(Totem)obj;
                    System.out.println("dni que entra: " + totem.getDni());
                    manager.newCliente(totem.getDni());
                    System.out.println("documentos: " + manager.dnis.toString());
                }else if(obj instanceof Televisor){
                	System.out.println("entra televisor");
                	System.out.println("datos: " + datos);
                    manager.creaTele(datos);
                }else if(obj instanceof Empleado){
                	System.out.println("entra empleado");
                    Empleado empleado = (Empleado) obj;
                    String msg=datos.in.readLine();
                    if(msg.equalsIgnoreCase("nuevo")) {
                    	manager.newBox(datos);
                    }else 
                    	manager.llamaCliente(String.valueOf(empleado.getPuesto()));    	        	
                }else if (obj==null){
                	System.out.println("entra ping al servidor");
                	MemoryMXBean mbean = ManagementFactory.getMemoryMXBean();
                	MemoryUsage cantMemoriaUsada = mbean.getHeapMemoryUsage();
                	datos.out.flush();
                	datos.out.println(cantMemoriaUsada.toString());
                	datos.out.flush();
                }
            } catch (IOException e) {
                System.err.println("Error al manejar la conexión con el cliente: " + e.getMessage());
            } catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
    /*
    
    private static void handleClient(Socket clientSocket) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);

            // Comunicación bidireccional
            writer.println("¡Conexión establecida con el servidor!");

            String clientMessage;
            while ((clientMessage = reader.readLine()) != null) {
                System.out.println("Mensaje recibido del cliente: " + clientMessage);

                // Procesar la solicitud del cliente (aquí puedes implementar tu lógica)
                String response = "Respuesta del servidor";

                // Enviar la respuesta al cliente
                writer.println(response);
                System.out.println("Respuesta enviada al cliente: " + response);
            }

            // Cerrar la conexión con el cliente
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Error al manejar la conexión con el cliente: " + e.getMessage());
        }
    }*/

	private String calculaTiempo() {
    	int horas = LocalDateTime.now().getHour() - this.tiempo.getHour();
    	int minutos = LocalDateTime.now().getMinute() - this.tiempo.getMinute();
    	int segundos = LocalDateTime.now().getSecond() - this.tiempo.getSecond();
    	String tiempoActual;
    	if (minutos < 0) {
			minutos = 60-minutos;
		}
    	if (minutos > 60) {
    		horas++;
			minutos = minutos-60;
		}
    	if (segundos < 0) {
			segundos = 60-segundos;
		}
    	if (segundos > 60) {
    		minutos++;
			segundos = segundos-60;
		}
    	if (minutos < 10 && segundos < 10) {
    		tiempoActual = horas + ":0" + minutos + ":0" + segundos;
		} else if (minutos < 10) {
			tiempoActual = horas + ":0" + minutos + ":" + segundos;
		} else if (segundos < 10) {
    		tiempoActual = horas + ":" + minutos + ":0" + segundos;
    	}else {
    		tiempoActual = horas + ":" + minutos + ":" + segundos;
		}
    	return tiempoActual;
	}
	
    private String calculaTiempoPromedio(int personas) {
    	int horas = LocalDateTime.now().getHour() - this.tiempo.getHour();
    	int minutos = LocalDateTime.now().getMinute() - this.tiempo.getMinute();
    	int segundos = LocalDateTime.now().getSecond() - this.tiempo.getSecond();
    	String tiempoActual;
    	if (personas == 0) {
			tiempoActual = "0:00:00";
		}
    	else{
    		if (minutos < 0) {
    			minutos = 60-minutos;
    		}
        	if (minutos > 60) {
        		horas++;
    			minutos = minutos-60;
    		}
        	if (segundos < 0) {
    			segundos = 60-segundos;
    		}
        	if (segundos > 60) {
        		minutos++;
    			segundos = segundos-60;
    		}
    		if (minutos < 10 && segundos < 10) {
        		tiempoActual = horas/personas + ":0" + minutos/personas + ":0" + segundos/personas;
    		} else if (minutos < 10) {
    			tiempoActual = horas/personas + ":0" + minutos/personas + ":" + segundos/personas;
    		} else if (segundos < 10) {
        		tiempoActual = horas/personas + ":" + minutos/personas + ":0" + segundos/personas;
        	}else {
        		tiempoActual = horas/personas + ":" + minutos/personas + ":" + segundos/personas;
    		}
    	}
		return tiempoActual;
	}
    public void cerrarsocket(){
    
    }
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
}