package global_suite;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import keeptoo.KGradientPanel;
import java.awt.ComponentOrientation;
import java.awt.CardLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

public class Results2 extends JFrame {
	
	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
    Desktop desktop = Desktop.getDesktop();
	
   public static String resultPath = System.getProperty("user.dir")+"\\Results";
   public static String HTML_resultPath = System.getProperty("user.dir")+"\\Results\\HTML_results\\";
   public static String HTML_globalPath = System.getProperty("user.dir")+"\\Results\\HTML_results\\Globalreport.html";
	
	private static JPanel contentPane;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
			Results2 frame = new Results2();
			frame.setSize(720, 260);   //setting frame size to screensize
			frame.setLocationRelativeTo(null);
			frame.setResizable(false);

			frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	public Results2() {
		
		
		JOptionPane.showMessageDialog(null, "Execution is completed Please click Ok to View Results");

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		//setPreferredSize(new Dimension(720, 200));
		setBounds(100, 100, 720, 260);
		contentPane = new JPanel();
		contentPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		contentPane.setBorder(null);
		contentPane.setBackground(ITAC.color);
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
		

		JLabel welcomeLabel = new JLabel("ITAC Results Summary",SwingConstants.CENTER);
		welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		welcomeLabel.setPreferredSize(new Dimension(720, 50));
		welcomeLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		welcomeLabel.setForeground(new Color(46, 139, 87));
		welcomeLabel.setBounds(0, 10, 684, 30);
		gradientPanel.add(welcomeLabel);
		welcomeLabel.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 22));
		gradientPanel.add(welcomeLabel, BorderLayout.NORTH);
		
		JLabel lblRbs = new JLabel("WATT @ 2019");
		lblRbs.setPreferredSize(new Dimension(59, 30));
		lblRbs.setFont(new Font("Century Schoolbook", Font.BOLD, 12));
		lblRbs.setForeground(new Color(0, 51, 51));
		lblRbs.setHorizontalAlignment(SwingConstants.CENTER);
		//gradientPanel.add(lblRbs, BorderLayout.SOUTH);
		
		JPanel panel = new JPanel();
		panel.setBorder(null);
		panel.setPreferredSize(new Dimension(680, 200));
		panel.setOpaque(false);
		gradientPanel.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblResultsPath = new JLabel("Results Path:");
		lblResultsPath.setBounds(50, 44, 91, 17);
		lblResultsPath.setHorizontalAlignment(SwingConstants.LEFT);
		lblResultsPath.setFont(new Font("Arial", Font.BOLD, 12));
		panel.add(lblResultsPath);
		
		JLabel Template_label = new JLabel("<html><style>p{word-break: break-all;text-decoration: underline; width:360px}</style><p>"+HTML_globalPath+"</p></html>");
		Template_label.setSize(470, 27);
		Template_label.setLocation(150, 40);
		Template_label.setForeground(new Color(128, 0, 0));
		Template_label.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 12));
		Template_label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panel.add(Template_label);
		
		Template_label.addMouseListener(new MouseAdapter()  
		{  
		    public void mouseClicked(MouseEvent e)  
		    {  
		    	File file=null;
		    	file = new File(HTML_resultPath);
		    	
		    	try {
                    desktop.open(file);
                } catch (IOException exception) {
                	exception.printStackTrace();
                	JOptionPane.showMessageDialog(null, "Error occured:File cannot be opened ");
                }
		    }  
		}); 
		
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

