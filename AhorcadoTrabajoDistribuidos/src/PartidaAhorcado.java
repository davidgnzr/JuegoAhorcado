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


	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
