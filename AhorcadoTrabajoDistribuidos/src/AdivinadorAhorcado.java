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
			boolean completado=false;
			boolean ahorcado=false;
			while(!completado || !ahorcado) {
				System.out.println("Introduce letra a preguntar");
				String letra = teclado.readLine();
				dos.writeBytes(letra+"\r\n");
			
				String esta=dis.readLine();
				if (esta.equalsIgnoreCase(palabraAdivinar)){
					System.out.println("INCORRECTO. La letra no esta contenida. ");
					System.out.println(palabraAdivinar);
				}
				else {
					System.out.println("CORRECTO. La letra esta contenida. ");
					palabraAdivinar=esta;
					System.out.println(palabraAdivinar);
				}
				int lRestantes= palabraAdivinar.indexOf("_");
				if (lRestantes==-1) {
					completado=true;
				}
			}
			
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
