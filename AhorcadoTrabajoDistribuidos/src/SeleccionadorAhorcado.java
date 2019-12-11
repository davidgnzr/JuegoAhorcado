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
				dos.writeBytes(palabra+"\r\n");
				dos.writeBytes(palabraEncrip+"\r\n");
				String letra;
				int letrasRestantes=numLetras;
				String antigPalabraEncrip=palabraEncrip;
				boolean terminado =false;
				while(!terminado) {
					palabraEncrip="";
					letra=dis.readLine();
					System.out.println(letra);
					System.out.println("¿La palabra contiene la letra: "+letra+" ? [SI/NO]");
					boolean nocoincide=true;
					while(nocoincide) {
						String sn = teclado.readLine();
						if (sn.equalsIgnoreCase("SI")) {
							int i=0;
							int indexl=0;			
							while(i<numLetras) {
								indexl=palabra.indexOf(letra, i);
								if (indexl==i) {
									palabraEncrip=palabraEncrip+letra+" ";
									letrasRestantes--;
								}else {
									String letraAntigua= Character.toString(antigPalabraEncrip.charAt(i*2));
									palabraEncrip=palabraEncrip+letraAntigua+" ";
								}
								i++;										
							}
							dos.writeBytes("SI"+"\r\n");
							dos.writeBytes(palabraEncrip+"\r\n");
							antigPalabraEncrip=palabraEncrip;
							nocoincide=false;
					
						}else if (sn.equalsIgnoreCase("NO")) {
							dos.writeBytes("NO"+"\r\n");
							dos.writeBytes(antigPalabraEncrip+"\r\n");
							nocoincide=false;
						}else {
							System.out.println("Lo respondido no coincide. Vuelve a introducir");
							nocoincide=true;
						}
					}
					String end=dis.readLine();
					if (end.equalsIgnoreCase("END")) {
						String fin=dis.readLine();
						System.out.println(fin);
						terminado=true;
					}
				}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
