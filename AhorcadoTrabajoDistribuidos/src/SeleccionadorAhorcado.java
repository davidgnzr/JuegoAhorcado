import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.EventListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class SeleccionadorAhorcado extends JFrame{
	private Socket s=null;
	private JPanel contentPane,panelB,panelC;
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
		try (	DataOutputStream dos =  new DataOutputStream(s.getOutputStream());
				DataInputStream dis =  new DataInputStream(s.getInputStream());
				){
				crearComienzo(dos);//Para que escriba la palabra a adivinar.
				boolean terminado =false;
				while(!terminado) {
					letra=dis.readLine();
					lEstado.setText("¿La palabra contiene la letra: "+letra+" ?");
					panelB.setVisible(true);//Aparecen los botones de si/no y se manda la respuesta.
					String end=dis.readLine();//Esperamos la respuesta para saber si era la última letra restante.
					if (end.equalsIgnoreCase("ENDM")||end.equalsIgnoreCase("ENDV")) {//Si el juego ha acabado se entra al if
						String fin=dis.readLine();						
						if(end.equalsIgnoreCase("ENDM")) {
							lblJuego.setText("GANASTE");
						}else{
							lblJuego.setText("PERDISTE");
						}
						int puntos=dis.readInt();	
						lIntentosRestantes.setVisible(false);
						JOptionPane.showMessageDialog(this, fin);
						panelB.setVisible(false);
						lEstado.setText("Juego finalizado");
						terminado=true;
						
						panelC = new JPanel();
						panelC.setBounds(250, 119, 265, 141);
						contentPane.add(panelC);
						panelC.setLayout(null);
						panelC.setVisible(false);
						
						//Se muestran los puntos conseguidos durante la partida.
						JLabel lPuntos = new JLabel("PUNTOS: "+puntos);
						lPuntos.setHorizontalAlignment(SwingConstants.CENTER);
						lPuntos.setFont(new Font("Tahoma", Font.PLAIN, 15));
						lPuntos.setBounds(53, 17, 171, 30);
						panelC.add(lPuntos);
						
						panelC.setVisible(true);
					}	
				}
		}catch (NullPointerException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(this, "El otro jugador ha abandonado la partida");
			contentPane.setVisible(false);
			dispose();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Pos:Se crea la interfaz gráfica.
	public void interfazGraficaSeleccionador() {

		setTitle("Juego del Ahorcado");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 624, 360);
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

		lEstado = new JLabel("Escriba la palabra a adivinar sin acentos");
		lEstado.setHorizontalAlignment(SwingConstants.CENTER);
		lEstado.setBounds(226, 61, 338, 23);
		contentPane.add(lEstado);

		panelB = new JPanel();
		panelB.setBounds(273, 119, 265, 94);
		contentPane.add(panelB);
		panelB.setLayout(null);
		panelB.setVisible(false);

		lImg = new JLabel();
		lImg.setBounds(30, 40, 140, 200);
		lImg.setVisible(true);
		contentPane.add(lImg);
		crearImagen("..\\FotosAhorcado\\fallo0.png");

		intentos = 7;
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
	
	//Pre: Se le pasa el DataOutputStream
	//Pos: Se manda la palabra elegida encriptada y sin encriptar.
	public void crearComienzo(DataOutputStream dos) {
		tPalabra = new JTextField();
		tPalabra.setBounds(22, 6, 113, 30);
		panelB.add(tPalabra);
		tPalabra.setColumns(10);
		
		JButton btnElegir = new JButton("Elegir");
		btnElegir.setBounds(144, 10, 68, 23);
		panelB.add(btnElegir);
		
		tPalabra.setVisible(true);
		btnElegir.setEnabled(false);
		btnElegir.setVisible(true);
		
		
		panelB.setVisible(true);
		tPalabra.addKeyListener(new KeyListener(){
            public void keyTyped (KeyEvent e){	
			}
            public void keyPressed (KeyEvent e){	
			}
            public void keyReleased (KeyEvent e){
				palabra=tPalabra.getText().toUpperCase();
				numLetras=palabra.length();
				if(numLetras==0) {
					btnElegir.setEnabled(false);
				}else {
					btnElegir.setEnabled(true);
				}
			}
		});
		
		btnElegir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			
				palabra=tPalabra.getText().toUpperCase();
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
				lPalabra.setText(palabra);
				lEstado.setText("Esperando letra del otro jugador...");
				botones(dos);//Se crean los botones, pero hasta que no se reciba la letra, no se mostrarán.	
			}
		});
	}
	
	//Pos: Se actualiza el numero de intentos restantes.
	public void updatelIntentosRestantes() {
		lIntentosRestantes.setText("Intentos restantes del contrincante: " + intentos);
	}
	
	//Pre: La ruta donde se encuentra la imagen es pasada como parámetro.
	//Pos: La imagen es actualizada con la nueva ruta.
	public void crearImagen(String ruta) {
		ImageIcon ip= new ImageIcon(ruta);
		ImageIcon iscalado= new ImageIcon(ip.getImage().getScaledInstance(lImg.getWidth(), lImg.getHeight(), Image.SCALE_DEFAULT));		
		lImg.setIcon(iscalado);	
	}
	
	//Pre:Se pasa la acción del evento y el dataOutputStream.
	//Pos:Se envia si la letra está contenida o no en la palabra..
	public void btnElegirPulsado(ActionEvent e,DataOutputStream dos) {
		JButton jbP= (JButton) e.getSource();
		String contenido=jbP.getText();
		palabraEncrip="";
		int cierto= palabra.indexOf(letra);//Se comprueba que el jugador no se ha equivocado a la hora de pulsar el botón.
		
		if(cierto!=-1 ) {
			if(contenido.equalsIgnoreCase("NO")) {//Si se ha equivocado se le avisa.
				JOptionPane.showMessageDialog(this, "SÍ ESTÁ LA LETRA. PRESTA ATENCIÓN");
			}
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
		}
				
		if(cierto==-1) {
			if(contenido.equalsIgnoreCase("SI")) {//Si se ha equivocado se le avisa.
				JOptionPane.showMessageDialog(this, "NO ESTÁ. PRESTA ATENCIÓN");
			}
			try {
				dos.writeBytes("NO"+"\r\n");
				dos.writeBytes(antigPalabraEncrip+"\r\n");
				intentoFoto++;
				String urlFoto="..\\FotosAhorcado\\fallo"+intentoFoto+".png";
				crearImagen(urlFoto);
				intentos--;
				updatelIntentosRestantes();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		lEstado.setText("Esperando letra del otro jugador...");
		panelB.setVisible(false);
	}
	
	//Pos: Se crean los botones para decidir si la letra está o no contenida en la palaba.
	//Al pulsarlos, se llama a la funcion btnElegirPulsado().
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
