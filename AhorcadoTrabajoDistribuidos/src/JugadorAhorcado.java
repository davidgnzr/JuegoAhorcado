
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class JugadorAhorcado {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try (
				Socket s = new Socket("localhost", 7300);
				//BufferedReader inSocket = new BufferedReader
						 //(new InputStreamReader(s.getInputStream()));
				DataInputStream dis =  new DataInputStream(s.getInputStream());
				DataOutputStream dos =  new DataOutputStream(s.getOutputStream());
				){
			String leidos=dis.readLine();
			System.out.println(leidos);
			System.out.println(dis.readLine());
			String rol=dis.readLine();
			if (rol.equalsIgnoreCase("Adivinar")){
				AdivinadorAhorcado aa=new AdivinadorAhorcado(s);
				aa.comenzarJuego();
			}else if(rol.equalsIgnoreCase("Elegir")){
				SeleccionadorAhorcado sa=new SeleccionadorAhorcado(s);
				sa.comenzarJuego();
			}

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
