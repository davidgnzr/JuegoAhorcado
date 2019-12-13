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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class SeleccionadorAhorcado extends JFrame{
	private Socket s=null;
	private JPanel contentPane,panelB;
	private JLabel lPalabra,lImg,lEstado,lCorrecto,lIntentosRestantes,lblJuego;
	private int intentos,intentoFoto;
	private JTextField tPalabra;
	private String palabra,palabraEncrip,antigPalabraEncrip,letra;
	private int numLetras;
	
	
	public SeleccionadorAhorcado(Socket s) {
		this.s=s;
		interfazGraficaSeleccionador();
	}
	
	public void comenzarJuego() {
		try (BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));			
				DataInputStream dis =  new DataInputStream(s.getInputStream());
				DataOutputStream dos =  new DataOutputStream(s.getOutputStream());
					){
				crearComienzo(dos);
				boolean terminado =false;
				while(!terminado) {
					letra=dis.readLine();
					setlEstado("¿La palabra contiene la letra: "+letra+" ?");
					panelB.setVisible(true);
					String end=dis.readLine();
					if (end.equalsIgnoreCase("ENDM")||end.equalsIgnoreCase("ENDV")) {
						String fin=dis.readLine();						
						if(end.equalsIgnoreCase("ENDM")) {
							setlblIntroduceLetra("GANASTE");
						}else{
							setlblIntroduceLetra("PERDISTE");
						}
						lIntentosRestantes.setVisible(false);
						JOptionPane.showMessageDialog(this, fin);
						panelB.setVisible(false);
						setlEstado("Juego finalizado");
						terminado=true;
					}

				}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setlPalabra(String s) {
		lPalabra.setText(s);
	}
	
	public void setlblIntroduceLetra(String s) {
		lblJuego.setText(s);
	}
	
	public void setlEstado(String s) {
		lEstado.setText(s);
	}
	
	public void updatelIntentosRestantes() {
		lIntentosRestantes.setText("Intentos restantes del contrincante: " + intentos);
	}
	public void setlCorrecto(String s) {
		lCorrecto.setText(s);
	}
	
	public void btnElegirPulsado(ActionEvent e,DataOutputStream dos) {
		JButton jbP= (JButton) e.getSource();
		String contenido=jbP.getText();
		palabraEncrip="";
		if (contenido.equalsIgnoreCase("SI")) {
			int i=0;
			int indexl=0;			
			while(i<numLetras) {
				indexl=palabra.indexOf(letra, i);
				if (indexl==i) {
					palabraEncrip=palabraEncrip+letra+" ";
				}else {
					String letraAntigua= Character.toString(antigPalabraEncrip.charAt(i*2));
					palabraEncrip=palabraEncrip+letraAntigua+" ";
				}
				i++;										
			}
			
			try {
				dos.writeBytes("SI"+"\r\n");
				dos.writeBytes(palabraEncrip+"\r\n");
				antigPalabraEncrip=palabraEncrip;
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				
		}else if (contenido.equalsIgnoreCase("NO")) {
			try {
				dos.writeBytes("NO"+"\r\n");
				dos.writeBytes(antigPalabraEncrip+"\r\n");
				intentoFoto++;
				String urlFoto="FotosAhorcado\\fallo"+intentoFoto+".png";
				crearImagen(urlFoto);
				intentos--;
				updatelIntentosRestantes();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}			
		}
		setlEstado("Esperando letra del otro jugador...");
		panelB.setVisible(false);
	}
	
	public void interfazGraficaSeleccionador() {
		
		setTitle("Juego del Ahorcado");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 624, 387);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblJuego = new JLabel("JUEGO DEL AHORCADO");
		lblJuego.setHorizontalAlignment(SwingConstants.CENTER);
		lblJuego.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblJuego.setBounds(307, 26, 171, 30);
		contentPane.add(lblJuego);
				
		lPalabra = new JLabel("");
		lPalabra.setHorizontalAlignment(SwingConstants.CENTER);
		lPalabra.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lPalabra.setBounds(298, 78, 191, 30);
		contentPane.add(lPalabra);
		
		lEstado = new JLabel("Escriba la palabra a adivinar");
		lEstado.setHorizontalAlignment(SwingConstants.CENTER);
		lEstado.setBounds(226, 61, 338, 23);
		contentPane.add(lEstado);
		
		panelB = new JPanel();
		panelB.setBounds(273, 119, 265, 94);
		contentPane.add(panelB);
		panelB.setLayout(null);
		panelB.setVisible(false);
		
		lImg=new JLabel();
		lImg.setBounds(30, 40, 140, 200);	
		lImg.setVisible(true);
		contentPane.add(lImg);
		crearImagen("FotosAhorcado\\fallo0.png");
		
		intentos=6;
		lIntentosRestantes = new JLabel("Intentos restantes del contrincante: " + intentos);
		lIntentosRestantes.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lIntentosRestantes.setBounds(30, 250, 250, 25);
		contentPane.add(lIntentosRestantes);
		
		lCorrecto = new JLabel("");
		lCorrecto.setHorizontalAlignment(SwingConstants.CENTER);
		lCorrecto.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lCorrecto.setBounds(212, 250, 361, 25);
		contentPane.add(lCorrecto);
		
		
		
	}
	
	public void crearComienzo(DataOutputStream dos) {
		tPalabra = new JTextField();
		tPalabra.setBounds(22, 6, 113, 30);
		panelB.add(tPalabra);
		tPalabra.setColumns(10);
		
		JButton btnElegir = new JButton("Elegir");
		btnElegir.setBounds(144, 10, 68, 23);
		panelB.add(btnElegir);
		
		tPalabra.setVisible(true);
		btnElegir.setVisible(true);
		
		panelB.setVisible(true);
		btnElegir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			
				palabra=tPalabra.getText();
				numLetras=palabra.length();
				palabraEncrip="";
				for(int i=0;i<numLetras;i++) {
					palabraEncrip=palabraEncrip+"_ ";
				}
				try {
					dos.writeBytes(palabra+"\r\n");
					dos.writeBytes(palabraEncrip+"\r\n");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				antigPalabraEncrip=palabraEncrip;
				tPalabra.setVisible(false);
				btnElegir.setVisible(false);
				panelB.remove(tPalabra);
				panelB.remove(btnElegir);
				panelB.setVisible(false);
				setlPalabra(palabra);
				setlEstado("Esperando letra del otro jugador...");
				botones(dos);
				
			}
		});
	}
	
	public void crearImagen(String ruta) {
		ImageIcon ip= new ImageIcon(ruta);
		ImageIcon iscalado= new ImageIcon(ip.getImage().getScaledInstance(lImg.getWidth(), lImg.getHeight(), Image.SCALE_DEFAULT));		
		lImg.setIcon(iscalado);	
	}
	
	public void botones(DataOutputStream dos) {
		JButton btnSI = new JButton("SI");
		btnSI.setBounds(40, 11, 80, 40);
		btnSI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnElegirPulsado(e,dos);
			}
		});
		panelB.add(btnSI);
		
		JButton btnNO = new JButton("NO");
		btnNO.setBounds(130, 11, 80, 40);
		btnNO.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnElegirPulsado(e,dos);
			}
		});
		contentPane.add(btnNO);
		panelB.add(btnNO);

	}
}
