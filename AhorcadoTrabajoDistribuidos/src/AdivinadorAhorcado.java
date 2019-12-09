import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class AdivinadorAhorcado {
	private Socket s=null;;
	
	public AdivinadorAhorcado(Socket s) {
		this.s=s;
	}
	
	public void comenzarJuego() {
		try (	BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));					
				DataInputStream dis =  new DataInputStream(s.getInputStream());
				DataOutputStream dos =  new DataOutputStream(s.getOutputStream());
				){
			// TODO Auto-generated method stub
			System.out.println("El otro jugador esta eligiendo la palabra...");
			String palabraAdivinar=dis.readLine();
			System.out.println("Tu palabra a adivinar es: ");
			System.out.println(palabraAdivinar);
			
			System.out.println("Introduce letra a preguntar");
			String letra = teclado.readLine();
			dos.writeBytes(letra+"\r\n");
			
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
