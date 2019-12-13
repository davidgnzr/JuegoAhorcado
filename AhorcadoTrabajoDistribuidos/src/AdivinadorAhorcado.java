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
	private JLabel lCorrecto;
	private JLabel lIntentosRestantes;
	private JLabel lblIntroduceLetra;
	private String letraP;
	private JPanel panelB;
	private int intentos;
	
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
			String palabraAdivinar=dis.readLine();
			setlPEncrip(palabraAdivinar);
			crearBotones(dos);
			int intentoFoto=1;
			boolean terminado=false;
			while(!terminado) {
				setlEstado("Pulsa la letra a preguntar");				
				String esta=dis.readLine();
				if (esta.equalsIgnoreCase("ENDV")||esta.equalsIgnoreCase("ENDM")) {//Si el juego ha acabado se entra al if
					String Pfinal=dis.readLine();
					String fin=dis.readLine();
					if(esta.equalsIgnoreCase("ENDM")) {//Si el juego ha terminado con la muerte, se pone la foto del muñeco colgado.
						String urlFoto="FotosAhorcado\\fallo"+intentoFoto+".png";
						crearImagen(urlFoto);
						setlblIntroduceLetra("PERDISTE");
					}else{//Si el juego ha terminado con la palabra acertada, se mantiene la foto que estaba desde el último fallo.	
						setlblIntroduceLetra("GANASTE");
					}
					lCorrecto.setVisible(false);
					setlPEncrip(Pfinal);
					setlEstado("Juego finalizado");
					lIntentosRestantes.setVisible(false);
					JOptionPane.showMessageDialog(this, fin);
					panelB.setVisible(false);
					terminado=true;
				}
				else if (esta.equalsIgnoreCase(palabraAdivinar)){
					setlCorrecto("INCORRECTO. Letra no contenida.");
					String urlFoto="FotosAhorcado\\fallo"+intentoFoto+".png";
					crearImagen(urlFoto);
					intentoFoto++;
					intentos--;
					updatelIntentosRestantes();				
					panelB.setVisible(true);
					
				}				
				else {
					setlCorrecto("CORRECTO!! Letra contenida.");
					palabraAdivinar=esta;
					setlPEncrip(palabraAdivinar);
					panelB.setVisible(true);					
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
	
	public void setlblIntroduceLetra(String s) {
		lblIntroduceLetra.setText(s);
	}
	
	public void setlEstado(String s) {
		lEstado.setText(s);
	}
	
	public void updatelIntentosRestantes() {
		lIntentosRestantes.setText("Intentos restantes: " + intentos);
	}
	public void setlCorrecto(String s) {
		lCorrecto.setText(s);
	}
	
	public void btnPulsado(ActionEvent e,DataOutputStream dos) {
		JButton jbP= (JButton) e.getSource();
		letraP=jbP.getText();
		try {
			dos.writeBytes(letraP+"\r\n");
			setlEstado("Esperando la respuesta del otro jugador...");
			jbP.setEnabled(false);
			panelB.setVisible(false);
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
		
		lblIntroduceLetra = new JLabel("ADIVINE LA PALABRA");
		lblIntroduceLetra.setHorizontalAlignment(SwingConstants.CENTER);
		lblIntroduceLetra.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblIntroduceLetra.setBounds(307, 26, 171, 30);
		contentPane.add(lblIntroduceLetra);
				
		lPEncrip = new JLabel("");
		lPEncrip.setHorizontalAlignment(SwingConstants.CENTER);
		lPEncrip.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lPEncrip.setBounds(298, 78, 191, 30);
		contentPane.add(lPEncrip);
		
		lEstado = new JLabel("El otro jugador esta eligiendo la palabra...");
		lEstado.setHorizontalAlignment(SwingConstants.CENTER);
		lEstado.setBounds(226, 61, 338, 23);
		contentPane.add(lEstado);
		
		panelB = new JPanel();
		panelB.setBounds(212, 110, 371, 134);
		contentPane.add(panelB);
		
		lImg=new JLabel();
		lImg.setBounds(30, 40, 140, 200);	
		lImg.setVisible(true);
		contentPane.add(lImg);
		crearImagen("FotosAhorcado\\fallo0.png");
		
		intentos=6;
		lIntentosRestantes = new JLabel("Intentos restantes: " + intentos);
		lIntentosRestantes.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lIntentosRestantes.setBounds(40, 250, 128, 25);
		contentPane.add(lIntentosRestantes);
		
		lCorrecto = new JLabel("");
		lCorrecto.setHorizontalAlignment(SwingConstants.CENTER);
		lCorrecto.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lCorrecto.setBounds(212, 250, 361, 25);
		contentPane.add(lCorrecto);
		
	}
	
	public void crearImagen(String ruta) {
		ImageIcon ip= new ImageIcon(ruta);
		ImageIcon iscalado= new ImageIcon(ip.getImage().getScaledInstance(lImg.getWidth(), lImg.getHeight(), Image.SCALE_DEFAULT));		
		lImg.setIcon(iscalado);	
	}
	
	public void crearBotones(DataOutputStream dos) {
		
		JButton btnA = new JButton("A");
		btnA.setBounds(226, 122, 45, 23);
		contentPane.add(btnA);
		btnA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPulsado(e,dos);
			}
		});
		
		JButton btnB = new JButton("B");
		btnB.setBounds(275, 122, 45, 23);
		contentPane.add(btnB);
		btnB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPulsado(e,dos);
			}
		});

		
		JButton btnC = new JButton("C");
		btnC.setBounds(323, 122, 45, 23);
		contentPane.add(btnC);
		btnC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPulsado(e,dos);
			}
		});
		
		JButton btnD = new JButton("D");
		btnD.setBounds(372, 122, 45, 23);
		contentPane.add(btnD);
		btnD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPulsado(e,dos);
			}
		});
		
		JButton btnE = new JButton("E");
		btnE.setBounds(421, 122, 45, 23);
		contentPane.add(btnE);
		btnE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPulsado(e,dos);
			}
		});
		
		JButton btnF = new JButton("F");
		btnF.setBounds(470, 122, 45, 23);
		contentPane.add(btnF);
		btnF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPulsado(e,dos);
			}
		});
		
		JButton btnG = new JButton("G");
		btnG.setBounds(519, 122, 45, 23);
		contentPane.add(btnG);
		btnG.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPulsado(e,dos);
			}
		});
		
		JButton btnN = new JButton("N");
		btnN.setBounds(519, 156, 45, 23);
		contentPane.add(btnN);
		btnN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPulsado(e,dos);
			}
		});
		
		JButton btnM = new JButton("M");
		btnM.setBounds(470, 156, 45, 23);
		contentPane.add(btnM);
		btnM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPulsado(e,dos);
			}
		});
		
		JButton btnL = new JButton("L");
		btnL.setBounds(421, 156, 45, 23);
		contentPane.add(btnL);
		btnL.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPulsado(e,dos);
			}
		});
		
		JButton btnK = new JButton("K");
		btnK.setBounds(372, 156, 45, 23);
		contentPane.add(btnK);
		btnK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPulsado(e,dos);
			}
		});
		
		JButton btnJ = new JButton("J");
		btnJ.setBounds(323, 156, 45, 23);
		contentPane.add(btnJ);
		btnJ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPulsado(e,dos);
			}
		});
		
		JButton btnI = new JButton("I");
		btnI.setBounds(275, 156, 45, 23);
		contentPane.add(btnI);
		btnI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPulsado(e,dos);
			}
		});
		
		JButton btnH = new JButton("H");
		btnH.setBounds(226, 156, 45, 23);
		contentPane.add(btnH);
		btnH.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPulsado(e,dos);
			}
		});
		
		JButton btnU = new JButton("U");
		btnU.setBounds(248, 224, 45, 23);
		contentPane.add(btnU);
		btnU.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPulsado(e,dos);
			}
		});
		
		JButton btnT = new JButton("T");
		btnT.setBounds(519, 190, 45, 23);
		contentPane.add(btnT);
		btnT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPulsado(e,dos);
			}
		});
		
		JButton btnS = new JButton("S");
		btnS.setBounds(470, 190, 45, 23);
		contentPane.add(btnS);
		btnS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPulsado(e,dos);
			}
		});
		
		JButton btnR = new JButton("R");
		btnR.setBounds(421, 190, 45, 23);
		contentPane.add(btnR);
		btnR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPulsado(e,dos);
			}
		});
		
		JButton btnQ = new JButton("Q");
		btnQ.setBounds(372, 190, 45, 23);
		contentPane.add(btnQ);
		btnQ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPulsado(e,dos);
			}
		});
		
		JButton btnP = new JButton("P");
		btnP.setBounds(324, 190, 45, 23);
		contentPane.add(btnP);
		btnP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPulsado(e,dos);
			}
		});
		
		JButton btnO = new JButton("O");
		btnO.setBounds(275, 190, 45, 23);
		contentPane.add(btnO);
		btnO.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPulsado(e,dos);
			}
		});
		
		JButton btnZ = new JButton("Z");
		btnZ.setBounds(493, 224, 45, 23);
		contentPane.add(btnZ);
		btnZ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPulsado(e,dos);
			}
		});
		
		JButton btnY = new JButton("Y");
		btnY.setBounds(444, 224, 45, 23);
		contentPane.add(btnY);
		btnY.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPulsado(e,dos);
			}
		});
		
		JButton btnX = new JButton("X");
		btnX.setBounds(395, 224, 45, 23);
		contentPane.add(btnX);
		btnX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPulsado(e,dos);
			}
		});
	
		JButton btnW = new JButton("W");
		btnW.setBounds(346, 224, 45, 23);
		contentPane.add(btnW);
		btnW.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPulsado(e,dos);
			}
		});
		
		JButton btnV = new JButton("V");
		btnV.setBounds(298, 224, 45, 23);
		contentPane.add(btnV);
		btnV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPulsado(e,dos);
			}
		});
		
		JButton btnNEsp = new JButton("\u00D1");
		btnNEsp.setBounds(226, 190, 45, 23);
		contentPane.add(btnNEsp);
		btnNEsp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPulsado(e,dos);
			}
		});
		
		panelB.add(btnA);	
		panelB.add(btnB);
		panelB.add(btnC);
		panelB.add(btnD);
		panelB.add(btnE);
		panelB.add(btnF);
		panelB.add(btnG);
		panelB.add(btnH);
		panelB.add(btnI);
		panelB.add(btnJ);
		panelB.add(btnK);
		panelB.add(btnL);
		panelB.add(btnM);
		panelB.add(btnN);
		panelB.add(btnNEsp);
		panelB.add(btnO);
		panelB.add(btnP);
		panelB.add(btnQ);
		panelB.add(btnR);
		panelB.add(btnS);
		panelB.add(btnT);
		panelB.add(btnU);
		panelB.add(btnV);
		panelB.add(btnW);
		panelB.add(btnX);
		panelB.add(btnY);
		panelB.add(btnZ);
		
	}
	
}
