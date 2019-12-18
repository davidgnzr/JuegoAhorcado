
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
				DataInputStream dis =  new DataInputStream(s.getInputStream());
				DataOutputStream dos =  new DataOutputStream(s.getOutputStream());
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

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
