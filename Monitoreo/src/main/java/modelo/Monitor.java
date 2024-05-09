package modelo;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Monitor implements Callable<String>{
	public Monitor() {}
	
	@Override
	public String call() throws Exception {
			Socket socket = new Socket("localhost", 5555);
			//inicializo
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
            
                oos.flush();
    			oos.writeObject(null);
    			System.out.println("se mando el objeto");
    			oos.flush();
    			out.println("hola");
    			oos.flush();
                // Leer respuesta del servidor
    			String respuesta_servidor;
                while ((respuesta_servidor = reader.readLine()) != null) {
                    socket.close();
                    return respuesta_servidor;
                }
                socket.close();
                return null;
	}
	
    @Override
	public String toString() {
		return "Monitor";
	}
}
