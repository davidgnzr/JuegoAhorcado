import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServidorAhorcado{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerSocket ss =null;	
		ExecutorService pool =null;
		try {
			pool = Executors.newCachedThreadPool();
			ss= new ServerSocket(7769);
			while(true)
			{
				Socket cliente1 = ss.accept();
				Socket cliente2= ss.accept();
				PartidaAhorcado sa= new PartidaAhorcado(cliente1,cliente2);
				pool.execute(sa);
			}	
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				pool.shutdown();
				if(ss!=null)
				ss.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
