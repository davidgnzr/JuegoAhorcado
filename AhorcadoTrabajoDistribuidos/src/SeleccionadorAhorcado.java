import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class SeleccionadorAhorcado {
	private Socket s=null;;
	
	public SeleccionadorAhorcado(Socket s) {
		this.s=s;
	}
	
	public void comenzarJuego() {
		try (BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));			
				DataInputStream dis =  new DataInputStream(s.getInputStream());
				DataOutputStream dos =  new DataOutputStream(s.getOutputStream());
					){
				System.out.println("Introduce la palabra deseada en mayusculas y sin acento:");
				String palabra = teclado.readLine();
				int numLetras=palabra.length();
				String palabraEncrip="";
				for(int i=0;i<numLetras;i++) {
					palabraEncrip=palabraEncrip+"_ ";
				}
				System.out.println(palabraEncrip);
				dos.writeBytes(palabraEncrip+"\r\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
