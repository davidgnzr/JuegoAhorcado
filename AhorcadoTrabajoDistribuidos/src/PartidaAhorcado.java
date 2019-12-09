import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PartidaAhorcado implements Runnable {

	private Socket sjug1=null;
	private Socket sjug2=null;
		
		
	public PartidaAhorcado(Socket sjug1,Socket sjug2) {
			this.sjug1 = sjug1;
			this.sjug2 = sjug2;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		DataOutputStream dos1;
		DataOutputStream dos2;
		DataInputStream dis1;
		DataInputStream dis2;

		try {
			dos1= new DataOutputStream(this.sjug1.getOutputStream());
			dos2= new DataOutputStream(this.sjug2.getOutputStream());
			dis1 =  new DataInputStream(this.sjug1.getInputStream());
			dis2 =  new DataInputStream(this.sjug2.getInputStream());
			
			String bienvenida= "Bienvenidos al juego del Ahorcado \r\n";
			String roladivinar="Tú vas a adivinar. \r\n";
			String rolelegir="Tú vas a decidir la palabra. \r\n";
			String adivinar="Adivinar\r\n";
			String elegir="Elegir\r\n";
			dos1.writeBytes(bienvenida);
			dos2.writeBytes(bienvenida);
			dos1.writeBytes(roladivinar);
			dos2.writeBytes(rolelegir);
			dos1.writeBytes(adivinar);
			dos2.writeBytes(elegir);
			
			String palabraEncrip=dis2.readLine();
			dos1.writeBytes(palabraEncrip+"\r\n");
			
			String letra=dis1.readLine();
			dos2.writeBytes(letra+"\r\n");
			
		}catch (IOException e) {
			e.printStackTrace();
		}

	}

}
