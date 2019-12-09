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
				String letra;
				int letrasRestantes=numLetras;
				String antigPalabraEncrip=palabraEncrip;
				while(letrasRestantes>0) {
					palabraEncrip="";
					letra=dis.readLine();		
					System.out.println("¿La palabra contiene la letra: "+letra+" ? [SI/NO]");
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
					
					}else if (sn.equalsIgnoreCase("NO")) {
						dos.writeBytes("NO"+"\r\n");
						dos.writeBytes(antigPalabraEncrip+"\r\n");
					}else {
						System.out.println("Lo respondido no coincide.");
					}
				}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
