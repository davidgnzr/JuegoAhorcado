import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
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
			int intentoFoto=0;
			boolean terminado=false;
			while(!terminado) {
				System.out.println("Introduce letra a preguntar");
				String letra = teclado.readLine();
				dos.writeBytes(letra+"\r\n");
			
				String esta=dis.readLine();
				if (esta.equalsIgnoreCase("END")) {
					String fin=dis.readLine();
					System.out.println(fin);
					terminado=true;
				}
				else if (esta.equalsIgnoreCase(palabraAdivinar)){
					System.out.println("INCORRECTO. La letra no esta contenida. ");
					System.out.println(palabraAdivinar);
					String urlFotoG="ahorcado"+intentoFoto+".png";
					System.out.println(urlFotoG);
					String urlFoto="C:\\Users\\David\\Desktop\\Carrera\\4º Carrera\\Sistemas Distribuidos\\Fotos Ahorcado\\fallo"+intentoFoto+".png";
					System.out.println(urlFoto);
					try(FileOutputStream fout =new FileOutputStream(new File(urlFotoG));
							FileInputStream f = new FileInputStream(urlFoto)){
						byte [] buff = new byte[1024*32];
						int leidos = f.read(buff);
						while (leidos != -1) {
							fout.write(buff, 0, leidos);
							leidos = f.read(buff);
						}
						fout.flush();
						intentoFoto++;
					}
					
				}				
				else {
					System.out.println("CORRECTO. La letra esta contenida. ");
					palabraAdivinar=esta;
					System.out.println(palabraAdivinar);
				}
			}
				
			
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
