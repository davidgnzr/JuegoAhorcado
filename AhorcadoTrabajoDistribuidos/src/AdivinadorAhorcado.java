import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	private JLabel lPEncrip;
	private JLabel lImg;
	private JLabel lEstado;
	private String letraP;
	
	public AdivinadorAhorcado(Socket s) {
		this.s=s;
		
		//InterfazGráfica
		interfazGraficaAdivinador();
		

	}
	
	public void comenzarJuego() {
		
		try (	BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));					
				DataInputStream dis =  new DataInputStream(s.getInputStream());
				){
			// TODO Auto-generated method stub
			String palabraAdivinar=dis.readLine();
			System.out.println(palabraAdivinar);
			setlPEncrip(palabraAdivinar);
			setlEstado("");
			
			int intentoFoto=0;
			boolean terminado=false;
			while(!terminado) {
				System.out.println("Introduce letra a preguntar");
//				letraP=teclado.readLine();
				
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
	
	public void setlPEncrip(String s) {
		lPEncrip.setText(s);
	}
	
	public void setlEstado(String s) {
		lEstado.setText(s);
	}
	
	public void btnPulsado(ActionEvent e) {
		JButton jbP= (JButton) e.getSource();
		System.out.println(jbP.getText());
		letraP=jbP.getText();
		jbP.setEnabled(false);
		try (	DataOutputStream dos =  new DataOutputStream(s.getOutputStream());){
			dos.writeBytes(letraP+"\r\n");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
		lblIntroduceLetra.setHorizontalAlignment(SwingConstants.CENTER);
		lblIntroduceLetra.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblIntroduceLetra.setBounds(307, 26, 171, 30);
		contentPane.add(lblIntroduceLetra);
		
		ImageIcon ip= new ImageIcon("C:\\Users\\David\\Desktop\\Carrera\\4º Carrera\\Sistemas Distribuidos\\Fotos Ahorcado\\fallo0.png");
		lImg=new JLabel();
		lImg.setBounds(40, 80, 120, 160);
		ImageIcon iscalado= new ImageIcon(ip.getImage().getScaledInstance(lImg.getWidth(), lImg.getHeight(), Image.SCALE_DEFAULT));		
		lImg.setIcon(iscalado);		
		lImg.setVisible(true);
		contentPane.add(lImg);
		
		lPEncrip = new JLabel("");
		lPEncrip.setHorizontalAlignment(SwingConstants.CENTER);
		lPEncrip.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lPEncrip.setBounds(298, 78, 191, 30);
		contentPane.add(lPEncrip);
		
		lEstado = new JLabel("El otro jugador esta eligiendo la palabra...");
		lEstado.setHorizontalAlignment(SwingConstants.CENTER);
		lEstado.setBounds(226, 61, 338, 23);
		contentPane.add(lEstado);
		
		JButton btnA = new JButton("A");
		btnA.setBounds(226, 122, 45, 23);
		contentPane.add(btnA);
		btnA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPulsado(e);
			}
		});
		
		JButton btnB = new JButton("B");
		btnB.setBounds(275, 122, 45, 23);
		contentPane.add(btnB);
		btnB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPulsado(e);
			}
		});
		
		JButton btnC = new JButton("C");
		btnC.setBounds(323, 122, 45, 23);
		contentPane.add(btnC);
		btnC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPulsado(e);
			}
		});
		
		JButton btnD = new JButton("D");
		btnD.setBounds(372, 122, 45, 23);
		contentPane.add(btnD);
		btnD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPulsado(e);
			}
		});
		
		JButton btnE = new JButton("E");
		btnE.setBounds(421, 122, 45, 23);
		contentPane.add(btnE);
		btnE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPulsado(e);
			}
		});
		
		JButton btnF = new JButton("F");
		btnF.setBounds(470, 122, 45, 23);
		contentPane.add(btnF);
		btnF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPulsado(e);
			}
		});
		
		JButton btnG = new JButton("G");
		btnG.setBounds(519, 122, 45, 23);
		contentPane.add(btnG);
		btnG.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPulsado(e);
			}
		});
		
		JButton btnN = new JButton("N");
		btnN.setBounds(519, 156, 45, 23);
		contentPane.add(btnN);
		btnN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPulsado(e);
			}
		});
		
		JButton btnM = new JButton("M");
		btnM.setBounds(470, 156, 45, 23);
		contentPane.add(btnM);
		btnM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPulsado(e);
			}
		});
		
		JButton btnL = new JButton("L");
		btnL.setBounds(421, 156, 45, 23);
		contentPane.add(btnL);
		btnL.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPulsado(e);
			}
		});
		
		JButton btnK = new JButton("K");
		btnK.setBounds(372, 156, 45, 23);
		contentPane.add(btnK);
		btnK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPulsado(e);
			}
		});
		
		JButton btnJ = new JButton("J");
		btnJ.setBounds(323, 156, 45, 23);
		contentPane.add(btnJ);
		btnJ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPulsado(e);
			}
		});
		
		JButton btnI = new JButton("I");
		btnI.setBounds(275, 156, 45, 23);
		contentPane.add(btnI);
		btnI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPulsado(e);
			}
		});
		
		JButton btnH = new JButton("H");
		btnH.setBounds(226, 156, 45, 23);
		contentPane.add(btnH);
		btnH.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPulsado(e);
			}
		});
		
		JButton btnU = new JButton("U");
		btnU.setBounds(248, 224, 45, 23);
		contentPane.add(btnU);
		btnU.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPulsado(e);
			}
		});
		
		JButton btnT = new JButton("T");
		btnT.setBounds(519, 190, 45, 23);
		contentPane.add(btnT);
		btnT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPulsado(e);
			}
		});
		
		JButton btnS = new JButton("S");
		btnS.setBounds(470, 190, 45, 23);
		contentPane.add(btnS);
		btnS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPulsado(e);
			}
		});
		
		JButton btnR = new JButton("R");
		btnR.setBounds(421, 190, 45, 23);
		contentPane.add(btnR);
		btnR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPulsado(e);
			}
		});
		
		JButton btnQ = new JButton("Q");
		btnQ.setBounds(372, 190, 45, 23);
		contentPane.add(btnQ);
		btnQ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPulsado(e);
			}
		});
		
		JButton btnP = new JButton("P");
		btnP.setBounds(324, 190, 45, 23);
		contentPane.add(btnP);
		btnP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPulsado(e);
			}
		});
		
		JButton btnO = new JButton("O");
		btnO.setBounds(275, 190, 45, 23);
		contentPane.add(btnO);
		btnO.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPulsado(e);
			}
		});
		
		JButton btnZ = new JButton("Z");
		btnZ.setBounds(493, 224, 45, 23);
		contentPane.add(btnZ);
		btnZ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPulsado(e);
			}
		});
		
		JButton btnY = new JButton("Y");
		btnY.setBounds(444, 224, 45, 23);
		contentPane.add(btnY);
		btnY.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPulsado(e);
			}
		});
		
		JButton btnX = new JButton("X");
		btnX.setBounds(395, 224, 45, 23);
		contentPane.add(btnX);
		btnX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPulsado(e);
			}
		});
	
		JButton btnW = new JButton("W");
		btnW.setBounds(346, 224, 45, 23);
		contentPane.add(btnW);
		btnW.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPulsado(e);
			}
		});
		
		JButton btnV = new JButton("V");
		btnV.setBounds(298, 224, 45, 23);
		contentPane.add(btnV);
		btnV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPulsado(e);
			}
		});
		
		JButton btnNEsp = new JButton("\u00D1");
		btnNEsp.setBounds(226, 190, 45, 23);
		contentPane.add(btnNEsp);
		btnNEsp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPulsado(e);
			}
		});
		
	}
	
}
