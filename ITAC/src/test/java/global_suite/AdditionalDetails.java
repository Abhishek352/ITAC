package global_suite;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import keeptoo.KGradientPanel;

public class AdditionalDetails extends JFrame {

	private static JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
				AdditionalDetails frame = new AdditionalDetails();
				frame.setSize(720, 320);   //setting frame size to screensize
				frame.setLocationRelativeTo(null);
				frame.setResizable(false);
				frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				
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
	public AdditionalDetails() {
		
		//setPreferredSize(new Dimension(720, 200));
		setBounds(100, 100, 720, 320);
		contentPane = new JPanel();
		contentPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		contentPane.setBorder(null);
		contentPane.setBackground(ETAC.color);
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		
		KGradientPanel gradientPanel = new KGradientPanel();
		gradientPanel.setBorder(null);
		gradientPanel.setPreferredSize(new Dimension(700, 240));
		gradientPanel.kEndColor = new Color(255, 255, 255);
		gradientPanel.setkEndColor(new Color(152, 251, 152));
		gradientPanel.kStartColor = new Color(176, 224, 230);
		gradientPanel.setkStartColor(new Color(176, 224, 230));
		contentPane.add(gradientPanel);
		gradientPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel welcomeLabel = new JLabel("ETAC Additional Details",SwingConstants.CENTER);
		welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		welcomeLabel.setPreferredSize(new Dimension(720, 50));
		welcomeLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		welcomeLabel.setForeground(new Color(46, 139, 87));
		welcomeLabel.setBounds(0, 10, 684, 30);
		gradientPanel.add(welcomeLabel);
		welcomeLabel.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 22));
		gradientPanel.add(welcomeLabel, BorderLayout.NORTH);
		
		JLabel lblRbs = new JLabel("ETAC @ 2019");
		lblRbs.setPreferredSize(new Dimension(59, 30));
		lblRbs.setFont(new Font("Century Schoolbook", Font.BOLD, 12));
		lblRbs.setForeground(new Color(0, 51, 51));
		lblRbs.setHorizontalAlignment(SwingConstants.CENTER);
		//gradientPanel.add(lblRbs, BorderLayout.SOUTH);
		
		JPanel AddDetails_panel = new JPanel();
		AddDetails_panel.setBorder(null);
		AddDetails_panel.setPreferredSize(new Dimension(680, 200));
		AddDetails_panel.setOpaque(false);
		gradientPanel.add(AddDetails_panel, BorderLayout.CENTER);
		AddDetails_panel.setLayout(null);
		
		JLabel Note1_label = new JLabel("1. Ensure Input file is uploaded if any input field is selected above .");
		Note1_label.setBounds(60, 10, 371, 15);
		Note1_label.setFont(new Font("Arial", Font.BOLD, 12));
		AddDetails_panel.add(Note1_label);

		JLabel Note2_label = new JLabel("2. Ensure all excel files are closed before starting  execution.");
		Note2_label.setBounds(60, 30, 345, 15);
		Note2_label.setFont(new Font("Arial", Font.BOLD, 12));
		AddDetails_panel.add(Note2_label);

		JLabel Note4_label = new JLabel("3. Please clear the temp files before starting the execution. ");
		Note4_label.setBounds(60, 50, 337, 15);
		Note4_label.setFont(new Font("Arial", Font.BOLD, 12));
		AddDetails_panel.add(Note4_label);
		
		JLabel Note6_label = new JLabel("4.  Sample text for Special characters text field: @$%^&*()?#+\"'~");
		Note6_label.setBounds(60, 70, 452, 15);
		Note6_label.setFont(new Font("Arial", Font.BOLD, 12));
		AddDetails_panel.add(Note6_label);
		
		JLabel Note7_label = new JLabel("5.  Sample text for Asset Sizes max & min fields: 500");
		Note7_label.setBounds(60, 90, 452, 15);
		Note7_label.setFont(new Font("Arial", Font.BOLD, 12));
		AddDetails_panel.add(Note7_label);
		
		JLabel Note8_label = new JLabel("6.  Sample text for Response Time threshold field: 9");
		Note8_label.setBounds(60, 110, 452, 15);
		Note8_label.setFont(new Font("Arial", Font.BOLD, 12));
		AddDetails_panel.add(Note8_label);
		
		JLabel Note3_label = new JLabel("7. For Emulator & Real device, device details are mandatory.");
		Note3_label.setBounds(60, 130, 197, 15);
		Note3_label.setFont(new Font("Arial", Font.BOLD, 12));
		AddDetails_panel.add(Note3_label);

		JLabel Note5_label = new JLabel("8. For iOS real device, UD id needs to be given in device name field.");
		Note5_label.setBounds(60, 150, 452, 15);
		Note5_label.setFont(new Font("Arial", Font.BOLD, 12));
		AddDetails_panel.add(Note5_label);
		
		JLabel Note9_label = new JLabel("9. For Android real device, ADB device id needs to be given in device name field.");
		Note9_label.setBounds(60, 170, 452, 15);
		Note9_label.setFont(new Font("Arial", Font.BOLD, 12));
		AddDetails_panel.add(Note9_label);
		
		JLabel Note10_label = new JLabel("10. For any other information, please refer to the documents.");
		Note10_label.setBounds(60, 190, 452, 15);
		Note10_label.setFont(new Font("Arial", Font.BOLD, 12));
		AddDetails_panel.add(Note10_label);
		
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if (JOptionPane.showConfirmDialog(contentPane, "Are you sure to close this window?", "",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					dispose();
				}
			}
		});
	}

}
