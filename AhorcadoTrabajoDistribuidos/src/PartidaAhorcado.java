import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
			boolean finalizado=false;
			int intentos=7;
			String acertado="";
			while(!finalizado) {
				
				String letra=dis1.readLine();
				dos2.writeBytes(letra+"\r\n");
				acertado=dis2.readLine();
				if(acertado.equalsIgnoreCase("NO")) {
					intentos--;
					System.out.println("Intentos restantes: "+intentos);
				}
			
				if(intentos==0) {
					finalizado=true;
					dos2.writeBytes("END"+"\r\n");
					dos1.writeBytes("END"+"\r\n");
					dos2.writeBytes("Enhorabuena!! Has ganado."+"\r\n");
					dos1.writeBytes("Has perdido, no has conseguido adivinar la palabra."+"\r\n");
				}
				else {
					palabraEncrip=dis2.readLine();
					int lRestantes= palabraEncrip.indexOf("_");
					if (lRestantes==-1) {
						finalizado=true;
						dos2.writeBytes("END"+"\r\n");
						dos1.writeBytes("END"+"\r\n");
						dos2.writeBytes("Has perdido, el otro jugador ha acertado la palabra."+"\r\n");
						dos1.writeBytes("Enhorabuena!! Has ganado."+"\r\n");
					}else {
						dos1.writeBytes(palabraEncrip+"\r\n");
						dos2.writeBytes("OK"+"\r\n");
						if(acertado.equalsIgnoreCase("NO")) {
							//File f=new File("C:\\Users\\David\\Desktop\\Carrera\\4º Carrera\\Sistemas Distribuidos\\Fotos Ahorcado\\fallo0.png");
							
							//dos1.writeBytes(f.toString());
							
						}
					}
				}
			}
		}catch (IOException e) {
			e.printStackTrace();
		}

	}

}
