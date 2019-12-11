import java.awt.Font;
import java.awt.Image;
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

import javax.swing.*;
import javax.swing.border.EmptyBorder;
public class AdivinadorAhorcado extends JFrame{
	private Socket s=null;
	private JPanel contentPane;
	private JTextField textField;
	JPanel panel;
	
	public AdivinadorAhorcado(Socket s) {
		this.s=s;
		
		//InterfazGráfica
		interfazGraficaAdivinador();
		

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
						JPanel p = new JPanel();
						ImageIcon i= new ImageIcon(urlFoto);
						JLabel img=new JLabel();
						img.setIcon(i);
						img.setSize(124,134);
						p.add(img);
						img.setVisible(true);
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
	
	public void interfazGraficaAdivinador() {
		
		setTitle("Juego del Ahorcado");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 624, 387);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblIntroduceLetra = new JLabel("ADIVINE LA PALABRA");
		lblIntroduceLetra.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblIntroduceLetra.setBounds(317, 65, 171, 30);
		contentPane.add(lblIntroduceLetra);
		
		ImageIcon ip= new ImageIcon("C:\\Users\\David\\Desktop\\Carrera\\4º Carrera\\Sistemas Distribuidos\\Fotos Ahorcado\\fallo0.png");
		JLabel imgg=new JLabel();
		imgg.setBounds(40, 80, 120, 160);
		ImageIcon iscalado= new ImageIcon(ip.getImage().getScaledInstance(imgg.getWidth(), imgg.getHeight(), Image.SCALE_DEFAULT));		
		imgg.setIcon(iscalado);		
		imgg.setVisible(true);
		contentPane.add(imgg);
		
		JButton btnA = new JButton("A");
		btnA.setBounds(225, 106, 45, 23);
		contentPane.add(btnA);
		
		JButton btnB = new JButton("B");
		btnB.setBounds(274, 106, 45, 23);
		contentPane.add(btnB);
		
		JButton btnC = new JButton("C");
		btnC.setBounds(322, 106, 45, 23);
		contentPane.add(btnC);
		
		JButton btnD = new JButton("D");
		btnD.setBounds(371, 106, 45, 23);
		contentPane.add(btnD);
		
		JButton btnE = new JButton("E");
		btnE.setBounds(420, 106, 45, 23);
		contentPane.add(btnE);
		
		JButton btnF = new JButton("F");
		btnF.setBounds(469, 106, 45, 23);
		contentPane.add(btnF);
		
		JButton btnG = new JButton("G");
		btnG.setBounds(518, 106, 45, 23);
		contentPane.add(btnG);
		
		JButton btnN = new JButton("N");
		btnN.setBounds(518, 140, 45, 23);
		contentPane.add(btnN);
		
		JButton btnM = new JButton("M");
		btnM.setBounds(469, 140, 45, 23);
		contentPane.add(btnM);
		
		JButton btnL = new JButton("L");
		btnL.setBounds(420, 140, 45, 23);
		contentPane.add(btnL);
		
		JButton btnK = new JButton("K");
		btnK.setBounds(371, 140, 45, 23);
		contentPane.add(btnK);
		
		JButton btnJ = new JButton("J");
		btnJ.setBounds(322, 140, 45, 23);
		contentPane.add(btnJ);
		
		JButton btnI = new JButton("I");
		btnI.setBounds(274, 140, 45, 23);
		contentPane.add(btnI);
		
		JButton btnH = new JButton("H");
		btnH.setBounds(225, 140, 45, 23);
		contentPane.add(btnH);
		
		JButton btnU = new JButton("U");
		btnU.setBounds(247, 208, 45, 23);
		contentPane.add(btnU);
		
		JButton btnT = new JButton("T");
		btnT.setBounds(518, 174, 45, 23);
		contentPane.add(btnT);
		
		JButton btnS = new JButton("S");
		btnS.setBounds(469, 174, 45, 23);
		contentPane.add(btnS);
		
		JButton btnR = new JButton("R");
		btnR.setBounds(420, 174, 45, 23);
		contentPane.add(btnR);
		
		JButton btnQ = new JButton("Q");
		btnQ.setBounds(371, 174, 45, 23);
		contentPane.add(btnQ);
		
		JButton btnP = new JButton("P");
		btnP.setBounds(323, 174, 45, 23);
		contentPane.add(btnP);
		
		JButton btnO = new JButton("O");
		btnO.setBounds(274, 174, 45, 23);
		contentPane.add(btnO);
		
		JButton btnZ = new JButton("Z");
		btnZ.setBounds(492, 208, 45, 23);
		contentPane.add(btnZ);
		
		JButton btnY = new JButton("Y");
		btnY.setBounds(443, 208, 45, 23);
		contentPane.add(btnY);
		
		JButton btnX = new JButton("X");
		btnX.setBounds(394, 208, 45, 23);
		contentPane.add(btnX);
		
		JButton btnW = new JButton("W");
		btnW.setBounds(345, 208, 45, 23);
		contentPane.add(btnW);
		
		JButton btnV = new JButton("V");
		btnV.setBounds(297, 208, 45, 23);
		contentPane.add(btnV);
		
		JButton btnNEsp = new JButton("\u00D1");
		btnNEsp.setBounds(225, 174, 45, 23);
		contentPane.add(btnNEsp);
	}
	
}
