import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InterfazAdivinador extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfazAdivinador frame = new InterfazAdivinador();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public InterfazAdivinador() {
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
		
		JButton btnA = new JButton("A");
		btnA.setEnabled(false);
		btnA.setBounds(226, 122, 45, 23);
		contentPane.add(btnA);
		
		JButton btnB = new JButton("B");
		btnB.setBounds(275, 122, 45, 23);
		contentPane.add(btnB);
		
		JButton btnC = new JButton("C");
		btnC.setBounds(323, 122, 45, 23);
		contentPane.add(btnC);
		
		JButton btnD = new JButton("D");
		btnD.setBounds(372, 122, 45, 23);
		contentPane.add(btnD);
		
		JButton btnE = new JButton("E");
		btnE.setBounds(421, 122, 45, 23);
		contentPane.add(btnE);
		
		JButton btnF = new JButton("F");
		btnF.setBounds(470, 122, 45, 23);
		contentPane.add(btnF);
		
		JButton btnG = new JButton("G");
		btnG.setBounds(519, 122, 45, 23);
		contentPane.add(btnG);
		
		JButton btnN = new JButton("N");
		btnN.setBounds(519, 156, 45, 23);
		contentPane.add(btnN);
		
		JButton btnM = new JButton("M");
		btnM.setBounds(470, 156, 45, 23);
		contentPane.add(btnM);
		
		JButton btnL = new JButton("L");
		btnL.setBounds(421, 156, 45, 23);
		contentPane.add(btnL);
		
		JButton btnK = new JButton("K");
		btnK.setBounds(372, 156, 45, 23);
		contentPane.add(btnK);
		
		JButton btnJ = new JButton("J");
		btnJ.setBounds(323, 156, 45, 23);
		contentPane.add(btnJ);
		
		JButton btnI = new JButton("I");
		btnI.setBounds(275, 156, 45, 23);
		contentPane.add(btnI);
		
		JButton btnH = new JButton("H");
		btnH.setBounds(226, 156, 45, 23);
		contentPane.add(btnH);
		
		JButton btnU = new JButton("U");
		btnU.setBounds(248, 224, 45, 23);
		contentPane.add(btnU);
		
		JButton btnT = new JButton("T");
		btnT.setBounds(519, 190, 45, 23);
		contentPane.add(btnT);
		
		JButton btnS = new JButton("S");
		btnS.setBounds(470, 190, 45, 23);
		contentPane.add(btnS);
		
		JButton btnR = new JButton("R");
		btnR.setBounds(421, 190, 45, 23);
		contentPane.add(btnR);
		
		JButton btnQ = new JButton("Q");
		btnQ.setBounds(372, 190, 45, 23);
		contentPane.add(btnQ);
		
		JButton btnP = new JButton("P");
		btnP.setBounds(324, 190, 45, 23);
		contentPane.add(btnP);
		
		JButton btnO = new JButton("O");
		btnO.setBounds(275, 190, 45, 23);
		contentPane.add(btnO);
		
		JButton btnZ = new JButton("Z");
		btnZ.setBounds(493, 224, 45, 23);
		contentPane.add(btnZ);
		
		JButton btnY = new JButton("Y");
		btnY.setBounds(444, 224, 45, 23);
		contentPane.add(btnY);
		
		JButton btnX = new JButton("X");
		btnX.setBounds(395, 224, 45, 23);
		contentPane.add(btnX);
		
		JButton btnW = new JButton("W");
		btnW.setBounds(346, 224, 45, 23);
		contentPane.add(btnW);
		
		JLabel lPEncrip = new JLabel("_ _ _ _");
		lPEncrip.setHorizontalAlignment(SwingConstants.CENTER);
		lPEncrip.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lPEncrip.setBounds(298, 78, 191, 30);
		contentPane.add(lPEncrip);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(226, 61, 338, 23);
		contentPane.add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBounds(212, 110, 371, 184);
		contentPane.add(panel);

		
		JButton btnNEsp = new JButton("\u00D1");
		panel.add(btnNEsp);
		
		JButton btnV = new JButton("V");
		panel.add(btnV);
		
		JLabel lblIntentosRestantes = new JLabel("Intentos restantes:");
		lblIntentosRestantes.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblIntentosRestantes.setBounds(23, 312, 128, 25);
		contentPane.add(lblIntentosRestantes);
		
		JLabel lblCorrecto = new JLabel("CORRECTO");
		lblCorrecto.setHorizontalAlignment(SwingConstants.CENTER);
		lblCorrecto.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblCorrecto.setBounds(212, 305, 361, 25);
		contentPane.add(lblCorrecto);
	}
}
