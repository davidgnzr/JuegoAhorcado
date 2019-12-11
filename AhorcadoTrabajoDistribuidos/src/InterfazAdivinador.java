import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;

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
		lblIntroduceLetra.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblIntroduceLetra.setBounds(317, 65, 171, 30);
		contentPane.add(lblIntroduceLetra);
		
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
