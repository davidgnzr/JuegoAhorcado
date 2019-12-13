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
	private JTextField textField;

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
		
		JButton btnU = new JButton("U");
		btnU.setBounds(248, 224, 45, 23);
		contentPane.add(btnU);
		
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
		
		JLabel lblIntentosRestantes = new JLabel("Intentos restantes:");
		lblIntentosRestantes.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblIntentosRestantes.setBounds(23, 312, 128, 25);
		contentPane.add(lblIntentosRestantes);
		
		JLabel lblCorrecto = new JLabel("CORRECTO");
		lblCorrecto.setHorizontalAlignment(SwingConstants.CENTER);
		lblCorrecto.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblCorrecto.setBounds(212, 305, 361, 25);
		contentPane.add(lblCorrecto);
		
		JPanel panel = new JPanel();
		panel.setBounds(273, 119, 265, 94);
		contentPane.add(panel);
		panel.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(0, 11, 86, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		JButton btnSI = new JButton("SI");
		btnSI.setBounds(129, 77, 80, 40);
		contentPane.add(btnSI);
		
		JButton btnNO = new JButton("NO");
		btnNO.setBounds(139, 119, 80, 40);
		contentPane.add(btnNO);
		
				btnNO.setVisible(true);
		btnSI.setVisible(true);
	}
}
