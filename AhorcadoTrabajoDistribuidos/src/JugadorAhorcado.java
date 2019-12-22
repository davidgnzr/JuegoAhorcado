
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class JugadorAhorcado {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try (
				Socket s = new Socket("localhost", 7765);
				DataOutputStream dos =  new DataOutputStream(s.getOutputStream());
				DataInputStream dis =  new DataInputStream(s.getInputStream());				
				){
			String rol=dis.readLine();
			if (rol.equalsIgnoreCase("Adivinar")){
				AdivinadorAhorcado aa=new AdivinadorAhorcado(s);
				aa.setVisible(true);
				aa.comenzarJuego();
			}else if(rol.equalsIgnoreCase("Elegir")){
				SeleccionadorAhorcado sa=new SeleccionadorAhorcado(s);
				sa.setVisible(true);
				sa.comenzarJuego();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
