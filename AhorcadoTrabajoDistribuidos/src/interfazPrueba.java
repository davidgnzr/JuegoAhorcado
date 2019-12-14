import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

public class interfazPrueba extends JPanel {

	/**
	 * Create the panel.
	 */
	public interfazPrueba() {
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("PUNTOS:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(307, 175, 177, 43);
		add(lblNewLabel);

	}

}
