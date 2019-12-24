import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
public class AdivinadorAhorcado extends JFrame{
	private Socket s=null;
	private JPanel contentPane,panelB,panelC;
	private JLabel lPEncrip,lImg,lEstado,lCorrecto,lIntentosRestantes,lblIntroduceLetra;
	private String letraP;
	private int intentos;
	
	public AdivinadorAhorcado(Socket s) {
		this.s=s;
		
		//InterfazGráfica
		interfazGraficaAdivinador();
	}
	
	public void comenzarJuego() {
		
		try (	DataOutputStream dos =  new DataOutputStream(s.getOutputStream());
				DataInputStream dis =  new DataInputStream(s.getInputStream());
				){
			// TODO Auto-generated method stub	
			
			//Se guarda el nombre del jugador para almacenar su puntuación en la tabla de puntos.
			panelC = new JPanel();
			panelC.setBounds(250, 119, 265, 141);
			contentPane.add(panelC);
			panelC.setLayout(null);
			panelC.setVisible(false);
			
			JTextField tnombre = new JTextField();
			tnombre.setBounds(22, 6, 113, 30);
			panelC.add(tnombre);
			tnombre.setColumns(10);
			
			JButton btnElegir = new JButton("Iniciar");
			btnElegir.setBounds(144, 10, 111, 23);
			panelC.add(btnElegir);
			
			tnombre.setVisible(true);
			btnElegir.setVisible(true);
			
			panelC.setVisible(true);
			btnElegir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {			
					try {
						//Si no ha puesto nombre se le asigna anonimo.
						if (tnombre.getText().isEmpty()) {
							dos.writeBytes("anonimo\r\n");
						}else {
							dos.writeBytes(tnombre.getText()+"\r\n");
						}						
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					tnombre.setVisible(false);
					btnElegir.setVisible(false);
					panelC.remove(tnombre);
					panelC.remove(btnElegir);
					panelC.setVisible(false);
					lEstado.setText("El otro jugador esta eligiendo la palabra...");
				}
			});
			
			//Se recibe la palabra a adivinar y se crean los botones.
			String palabraAdivinar=dis.readLine();
			if(palabraAdivinar=="") {
				JOptionPane.showMessageDialog(this, "El otro jugador no ha elegido palabra.");
				contentPane.setVisible(false);
				dispose();
			}
			lPEncrip.setText(palabraAdivinar);
			crearBotones(dos);
			int intentoFoto=1;
			boolean terminado=false;
			while(!terminado) {
				lEstado.setText("Pulsa la letra a preguntar");				
				String esta=dis.readLine();
				if (esta.equalsIgnoreCase("ENDV")||esta.equalsIgnoreCase("ENDM")) {//Si el juego ha acabado se entra al if
					String palabraFinal=dis.readLine();
					String fin=dis.readLine();
					if(esta.equalsIgnoreCase("ENDM")) {//Si los intentos se han agotado, se pone la foto del muñeco colgado.
						String urlFoto="FotosAhorcado\\fallo"+intentoFoto+".png";
						crearImagen(urlFoto);
						lblIntroduceLetra.setText("PERDISTE");
					}else{//Si la palabra se ha acertado, se mantiene la foto que estaba desde el último fallo.	
						lblIntroduceLetra.setText("GANASTE");
					}
					int puntos=dis.readInt();	
					lCorrecto.setVisible(false);
					lPEncrip.setText(palabraFinal);
					lEstado.setText("Juego finalizado");
					lIntentosRestantes.setVisible(false);
					JOptionPane.showMessageDialog(this, fin);
					panelB.setVisible(false);
					terminado=true;
					
					//Se muestran los puntos conseguidos durante la partida.
					JLabel lPuntos = new JLabel("PUNTOS: "+puntos);
					lPuntos.setHorizontalAlignment(SwingConstants.CENTER);
					lPuntos.setFont(new Font("Tahoma", Font.PLAIN, 15));
					lPuntos.setBounds(53, 17, 171, 30);
					panelC.add(lPuntos);
					
					//Se recibe la puntuación máxima del jugador registrada en la tabla de puntos.
					String puntosMax=dis.readLine();
					JLabel lblNewLabel = new JLabel(puntosMax);
					lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
					lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
					lblNewLabel.setBounds(22, 57, 233, 30);
					panelC.add(lblNewLabel);
					panelC.setVisible(true);
					
				}
				else if (esta.equalsIgnoreCase(palabraAdivinar)){//Si lo recibido es la misma palabra que antes de preguntar la letra, la letra no está contenida.
					lCorrecto.setText("INCORRECTO. Letra no contenida.");
					String urlFoto="FotosAhorcado\\fallo"+intentoFoto+".png";
					crearImagen(urlFoto);
					intentoFoto++;
					intentos--;
					updatelIntentosRestantes();				
					panelB.setVisible(true);
					
				}				
				else {//La letra preguntada se encuentra en la palabra.
					lCorrecto.setText("CORRECTO!! Letra contenida.");
					palabraAdivinar=esta;
					lPEncrip.setText(palabraAdivinar);
					panelB.setVisible(true);					
				}			
			}
				
			
		}catch (NullPointerException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(this, "El otro jugador ha abandonado la partida");
			contentPane.setVisible(false);
			dispose();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Pos:Se crea la interfaz gráfica.
	public void interfazGraficaAdivinador() {
		
		setTitle("Juego del Ahorcado");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 624, 360);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblIntroduceLetra = new JLabel("ADIVINE LA PALABRA");
		lblIntroduceLetra.setHorizontalAlignment(SwingConstants.CENTER);
		lblIntroduceLetra.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblIntroduceLetra.setBounds(307, 26, 171, 30);
		contentPane.add(lblIntroduceLetra);
				
		lPEncrip = new JLabel("");//Aquí, se encontrará la palabra encriptada.
		lPEncrip.setHorizontalAlignment(SwingConstants.CENTER);
		lPEncrip.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lPEncrip.setBounds(278, 78, 220, 30);
		contentPane.add(lPEncrip);
		
		lEstado = new JLabel("Introduzca su nombre de jugador:");
		lEstado.setHorizontalAlignment(SwingConstants.CENTER);
		lEstado.setBounds(226, 61, 338, 23);
		contentPane.add(lEstado);
		
		lImg=new JLabel();//Aquí, irán apareciendo las fotos del ahorcado.
		lImg.setBounds(30, 40, 140, 200);	
		lImg.setVisible(true);
		contentPane.add(lImg);
		crearImagen("FotosAhorcado\\fallo0.png");
		
		intentos=7;//Aquí, aparecerán los intentos restantes.
		lIntentosRestantes = new JLabel("Intentos restantes: " + intentos);
		lIntentosRestantes.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lIntentosRestantes.setBounds(40, 250, 128, 25);
		contentPane.add(lIntentosRestantes);
				
	}
	
	//Pos: Se actualiza el numero de intentos restantes.
	public void updatelIntentosRestantes() {
		lIntentosRestantes.setText("Intentos restantes: " + intentos);
	}
	
	//Pre: La ruta donde se encuentra la imagen es pasada como parámetro.
	//Pos: La imagen es actualizada con la nueva ruta.
	public void crearImagen(String ruta) {
		ImageIcon ip= new ImageIcon(ruta);
		ImageIcon iscalado= new ImageIcon(ip.getImage().getScaledInstance(lImg.getWidth(), lImg.getHeight(), Image.SCALE_DEFAULT));		
		lImg.setIcon(iscalado);	
		lImg.setVisible(true);
	}
	
	//Pre:Se pasa la acción del evento y el dataOutputStream para poder enviar la letra pulsada al servidor.
	//Pos:Se envia la letra que contiene el botón pulsado.
	public void btnPulsado(ActionEvent e, DataOutputStream dos) {
		JButton jbP = (JButton) e.getSource();
		letraP = jbP.getText();
		try {
			dos.writeBytes(letraP + "\r\n");
			lEstado.setText("Esperando la respuesta del otro jugador...");
			jbP.setEnabled(false);
			panelB.setVisible(false);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	//Pos: Se crean los botones con sus letras. Al pulsarlos, se llama a la funcion btnPulsado().
	public void crearBotones(DataOutputStream dos) {
		
		panelB = new JPanel();//En este panel, estarán todos los botones con su letra.
		panelB.setBounds(212, 110, 371, 134);
		contentPane.add(panelB);
		panelB.setVisible(false);
		
		lCorrecto = new JLabel("");//Aquí, nos mostrará si la letra preguntada se encuentra en la palabra.
		lCorrecto.setHorizontalAlignment(SwingConstants.CENTER);
		lCorrecto.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lCorrecto.setBounds(212, 250, 361, 25);
		contentPane.add(lCorrecto);
		
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
		
		panelB.setVisible(true);
		
	}
	
}
