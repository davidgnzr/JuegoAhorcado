import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServidorAhorcado{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerSocket ss = null;	
		ExecutorService pool = null;
		Socket cliente1 = null;
		Socket cliente2 = null;
		int partida=1;
		try {
			pool = Executors.newCachedThreadPool();
			ss= new ServerSocket(7765);
			while(true)
			{
				//Esperamos a que dos clientes se conecten al servidor y empieza la partida.
				cliente1 = ss.accept();
				cliente2= ss.accept();
				PartidaAhorcado sa= new PartidaAhorcado(cliente1,cliente2,partida);
				pool.execute(sa);
				partida++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				cliente1.close();
				cliente2.close();
				pool.shutdown();
				if(ss!=null)
				ss.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
