package global_suite;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import keeptoo.KButton;
import keeptoo.KGradientPanel;
import site_monitoring.Globalvariables;


public class ITAC extends JFrame {

	//public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public int i = 0;

	public static Color color=new Color(230, 230, 230);
	public static Color linecolor=new Color(0,0,0);

	private static JPanel contentPane;
	private JTextField BLDomain_txtField;
	private JTextField SCDomain_txtField;
	private JTextField MADomain_testtxtField;
	private JTextField ASDomain_txtField;
	private JTextField MADomain_prodtxtField;
	private JTextField uploadfile_txtfield;
	private JTextField BrowserStackEmail_txtfield;
	private JTextField Browserdn_txtfield;
	private JTextField Browserpv_txtfield;
	private JTextField Browseran_txtfield;
	private JTextField Browserdp_txtfield;
	private JTextField BrowserStackPswd_txtfield;
	private CheckCombobox comboboxObj = new CheckCombobox();

	//ListCheckModel model = comboboxObj.getModel(); 

	int toolscount = 0;
	int radiobtncount = 0;
	int suitecount=0;

	int AS_height = 90;
	int other_half_height = 134;

	String ToolSelected = "";
	String radiobuttonSelected = "";
	String radiobuttonselected="";
	String checkboxselected = "";
	String tasksSelected="";
	String regressionPackSelection="";

	static JPanel panel;
	int txtfieldEmpty=0;
	int rangefieldempty=0;
	int spCharfieldempty=0;
	int fileEmpty=0;
	int PCdropdownEmpty=0;
	int ToolsdropdownEmpty=0;
	int dropdownEmpty=0;
	int signinEmpty=0;
	int RPcheckboxEmpty=0;
	int RP_tool_comboBoxisEmpty=0;
	int NewRegresPackisEmpty=0; 
	int browserDisplay=0;
	int RP_tool_select=0;
	int RT_rangeEmpty=0;
	int VDeviceName = 0;
	int VPlatformVersion = 0;

	//int baseHeight = 435;

	List<String> tasks = new ArrayList<>();
	Set<String> RPSelectedValues = new HashSet();
	Set<String> PCLocalBrowserSelectedValues = new HashSet();
	Set<String> PCBrowserStackSelectedValues = new HashSet();
	Set<String> PCRealSelectedValues = new HashSet();
	Set<String> PCEmulatorSelectedValues = new HashSet();
	Set<String> RPToolsListSelectedValues = new HashSet();
	Set<String> RPBrandSelectedValues = new HashSet();
	Set<String> RPEnvSelectedValues = new HashSet();
	Set<String> RPLocalBrowserSelectedValues = new HashSet();
	Set<String> RPBrowserStackSelectedValues = new HashSet();

	String filepath;
	JLabel message = new JLabel("File Uploaded Successfully");

	String environment="Environment";
	String toolsList="Task List";
	String brand="Brand";
	String localbrowser="Local Browser";
	String browserstacklabel="Browser Stack";
	String emulatorlabel = "Emulator";
	String realLabel = "Real Device";
	String realdevicelabel = "Real Device";
	String regressionpack="Regression pack";
	String rptools="Regression Pack";
	String browserStackHeading="Browser Stack Sign in Details";
	String RPToolslabel="Regression pack";
	String Emulator = "Emulator";
	String RealDevice = "Real Device";


	String[] Localbrowsers= {"Local_Chrome","Local_Firefox"};
	ArrayList<String> LocalBrowsersList =new ArrayList<String>(Arrays.asList(Localbrowsers));


	//String[] browserstack= {"Win10_Chrome_73.0","Win10_Firefox_66.0","Win10_MSEdge_17.0","Win10_IE11_11.0","Win8.1_IE11_11.0","Win8.1_Chrome_70.0","Win7_IE11_11.0","Win7_IE10_10.0","Win7_Chrome_70.0","Win7_Firefox_63.0","Win7_IE9_9.0","Win7_IE8_8.0","Mac Mojave_Safari_12.0","GooglePixel_9.0","Select All"};
	String[] browserstack= {"Win10_Chrome_73.0","Win10_Firefox_66.0","Win10_MSEdge_17.0","Win10_IE11_11.0","Win8.1_Chrome_70.0","Win7_IE10_10.0","Win7_Chrome_70.0","Win7_Firefox_63.0","Mac Mojave_Safari_12.0","GooglePixel_9.0","iPhone8","Select All"};
	ArrayList<String> BrowserstackList =new ArrayList<String>(Arrays.asList(browserstack));

	String[] emulator= {"Emulator Browser-Android", "Emulator App-Android", "Emulator Browser-iOS", "Emulator App-iOS","Select All"};
	ArrayList<String> EmulatorList =new ArrayList<String>(Arrays.asList(emulator));

	String[] real= {"Real Browser-Android", "Real App-Android", "Real Browser-iOS", "Real App-iOS","Select All"};
	ArrayList<String> RealList =new ArrayList<String>(Arrays.asList(real));

	String[] RPTools= {"Sample Test", "Select All"};

	ArrayList<String> RPToolsList =new ArrayList<String>(Arrays.asList(RPTools));

	String[] regression_tools_pack= {"Regression suite"};		//
	ArrayList<String> regression_tools_packList =new ArrayList<String>(Arrays.asList(regression_tools_pack));

	String[] browserValues= {"Please select","Local Browser", "Browser Stack", "Emulator", "Real Device"};
	private JPasswordField passwordField;
	private JTextField rangeMax;
	private JTextField rangeMin;
	private JTextField RT_range_field;
	private JTextField spCharInput;

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					ITAC obj = new ITAC();
					JFrame frame = new JFrame();     //Creating a Frame
					panel = obj.initUI(); 
					panel.setSize(new Dimension(720, 564));
					JScrollPane scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
							JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 
					frame.getContentPane().add(scrollPane);

					frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
					//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setSize(710, 565);   //setting frame size to screensize
					frame.setLocationRelativeTo(null);
					frame.setResizable(false);
					//To get popup while closing the frame
					frame.addWindowListener(new java.awt.event.WindowAdapter() {
						@Override
						public void windowClosing(java.awt.event.WindowEvent windowEvent) {
							if (JOptionPane.showConfirmDialog(contentPane, "Are you sure to close this window?", "",
									JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
								System.exit(0);
							}
						}
					});
					frame.revalidate();
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
	public ITAC() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 720, 590);
		contentPane = new JPanel();
		//contentPane.setSize(new Dimension(720, 590));
		//contentPane.setPreferredSize(new Dimension(720, 590));
		contentPane.setBorder(null);
		setContentPane(contentPane);
		//initUI();
	}

	public JPanel initUI() {

		contentPane.setLayout(new CardLayout(0, 0));
		KGradientPanel gradientPanel = new KGradientPanel();		
		gradientPanel.setkStartColor(new Color(176, 224, 230));
		gradientPanel.setkEndColor(new Color(152, 251, 152));
		contentPane.add(gradientPanel);
		gradientPanel.setLayout(new BorderLayout(0, 0));

		System.out.println("Application Started ");

		//	JLabel welcomeLabel = new JLabel("Digi Content Validation Whiz");
		JLabel welcomeLabel = new JLabel("Interactive Test Automation cartridge");
		welcomeLabel.setSize(new Dimension(0, 50));
		welcomeLabel.setPreferredSize(new Dimension(134, 50));
		welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		welcomeLabel.setForeground(new Color(46, 139, 87));
		welcomeLabel.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 24));
		gradientPanel.add(welcomeLabel, BorderLayout.NORTH);

		JPanel footer_panel = new JPanel();
		footer_panel.setSize(new Dimension(692, 130));
		footer_panel.setMinimumSize(new Dimension(720, 130));
		footer_panel.setOpaque(false);
		footer_panel.setPreferredSize(new Dimension(692, 130));
		gradientPanel.add(footer_panel, BorderLayout.SOUTH);
		footer_panel.setLayout(null);

		JPanel Uploadfile_panel = new JPanel();
		Uploadfile_panel.setOpaque(false);
		Uploadfile_panel.setPreferredSize(new Dimension(644, 100));
		Uploadfile_panel.setSize(new Dimension(644, 100));
		Uploadfile_panel.setBounds(20, 0, 644, 100);
		Uploadfile_panel.setBorder(new LineBorder(new Color(128, 128, 0), 1, true));
		footer_panel.add(Uploadfile_panel);
		Uploadfile_panel.setLayout(null);

		JLabel Browse_label = new JLabel("Browse");
		Browse_label.setLocation(10, 10);
		Browse_label.setForeground(new Color(0, 0, 0));
		Browse_label.setSize(45, 20);
		Browse_label.setFont(new Font("Arial", Font.PLAIN, 12));
		Uploadfile_panel.add(Browse_label);

		uploadfile_txtfield = new JTextField();
		uploadfile_txtfield.setLocation(70, 10);
		uploadfile_txtfield.setSize(120, 20);
		uploadfile_txtfield.setFont(new Font("Arial", Font.PLAIN, 12));
		Uploadfile_panel.add(uploadfile_txtfield);

		JButton Upload_btn = new JButton("Upload"); //Creating upload button
		Upload_btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Upload_btn.setRequestFocusEnabled(false);
		Upload_btn.setRolloverEnabled(false);
		Upload_btn.setLocation(190, 10);
		Upload_btn.setSize(new Dimension(80, 23));
		Upload_btn.setFont(new Font("Arial", Font.PLAIN, 12));
		Uploadfile_panel.add(Upload_btn);

		JLabel message_label = new JLabel("File uploaded successfully!");
		message_label.setVisible(false);
		message_label.setForeground(new Color(255, 69, 0));
		message_label.setFont(new Font("Arial", Font.PLAIN, 12));
		message_label.setSize(174, 20);
		message_label.setLocation(460, 10);
		Uploadfile_panel.add(message_label);

		JLabel Template_label = new JLabel("<HTML><U>Download Input File Template</U></HTML>");
		Template_label.setSize(170, 20);
		Template_label.setLocation(280, 10);
		Template_label.setForeground(new Color(0, 0, 0));
		Template_label.setFont(new Font("Arial", Font.PLAIN, 12));
		Template_label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Uploadfile_panel.add(Template_label);

		KButton StartExecution_btn = new KButton();
		StartExecution_btn.setBorder(new LineBorder(new Color(192, 192, 192)));
		StartExecution_btn.kAllowTab = false;
		StartExecution_btn.setkAllowTab(false);
		StartExecution_btn.setOpaque(false);
		StartExecution_btn.kPressedColor =  new Color(107, 142, 35);
		StartExecution_btn.setkPressedColor( new Color(107, 142, 35));
		StartExecution_btn.kHoverEndColor = new Color(34, 139, 34);
		StartExecution_btn.setkHoverEndColor(new Color(34, 139, 34));
		StartExecution_btn.setBackground(new Color(0, 0, 0));
		StartExecution_btn.kEndColor = new Color(0, 100, 0);
		StartExecution_btn.setkEndColor(new Color(0, 100, 0));
		StartExecution_btn.kBackGroundColor = new Color(0, 100, 0);
		StartExecution_btn.setkBackGroundColor(new Color(0, 128, 0));
		StartExecution_btn.kHoverStartColor = new Color(34, 139, 34);
		StartExecution_btn.setkHoverStartColor(new Color(34, 139, 34));
		StartExecution_btn.kHoverForeGround = Color.WHITE;
		StartExecution_btn.setkHoverForeGround(Color.WHITE);
		StartExecution_btn.setkStartColor(new Color(46, 139, 87));
		StartExecution_btn.kSelectedColor =  new Color(34, 139, 34);
		StartExecution_btn.setkSelectedColor( new Color(34, 139, 34));
		StartExecution_btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		StartExecution_btn.setText("Start Execution");	
		StartExecution_btn.setFont(new Font("Arial", Font.BOLD, 16));
		StartExecution_btn.setBounds(246, 50, 160, 35);
		StartExecution_btn.setSize(new Dimension(160, 35));		
		Uploadfile_panel.add(StartExecution_btn);

		JLabel lblDcvw = new JLabel("<html><p>WATT &copy; 2019</p></html>");
		lblDcvw.setBounds(0, 100, 694, 30);
		lblDcvw.setSize(new Dimension(692, 30));
		lblDcvw.setHorizontalAlignment(SwingConstants.CENTER);
		lblDcvw.setForeground(new Color(0, 51, 51));
		lblDcvw.setFont(new Font("Century Schoolbook", Font.BOLD, 12));
		//footer_panel.add(lblDcvw);



		JPanel left_panel = new JPanel();
		FlowLayout fl_left_panel = (FlowLayout) left_panel.getLayout();
		fl_left_panel.setHgap(10);
		left_panel.setOpaque(false);
		gradientPanel.add(left_panel, BorderLayout.WEST);

		JPanel right_panel = new JPanel();
		right_panel.setOpaque(false);
		FlowLayout fl_right_panel = (FlowLayout) right_panel.getLayout();
		fl_right_panel.setHgap(10);
		gradientPanel.add(right_panel, BorderLayout.EAST);

		JPanel center_panel = new JPanel();
		center_panel.setSize(new Dimension(644, (contentPane.getHeight()-(welcomeLabel.getHeight()+footer_panel.getHeight()+20))));
		center_panel.setOpaque(false);
		//center_panel.setBorder(new LineBorder(new Color(128, 128, 0), 1, true));
		gradientPanel.add(center_panel, BorderLayout.CENTER);
		center_panel.setLayout(null);
		JPanel first_half = new JPanel();
		first_half.setOpaque(false);
		first_half.setBounds(0, 0, center_panel.getWidth(), 143);
		first_half.setSize(new Dimension(644, 250));
		//first_half.setBorder(new LineBorder(new Color(128, 128, 0), 1, true));
		center_panel.add(first_half);
		first_half.setLayout(null);

		JLabel lblAdditionalDetails = new JLabel("<html><p>**<u>Additional Details</u></p></html>");
		lblAdditionalDetails.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblAdditionalDetails.setHorizontalAlignment(SwingConstants.LEFT);
		lblAdditionalDetails.setForeground(Color.BLACK);
		lblAdditionalDetails.setFont(new Font("Arial", Font.PLAIN, 12));
		lblAdditionalDetails.setBounds(0, 0, 120, 30);	
		first_half.add(lblAdditionalDetails);

		JCheckBox SelectAll_chckbx = new JCheckBox("Select All");
		SelectAll_chckbx.setVerticalAlignment(SwingConstants.TOP);
		SelectAll_chckbx.setSize(new Dimension(100, 30));
		SelectAll_chckbx.setAlignmentX(Component.CENTER_ALIGNMENT);
		SelectAll_chckbx.setHorizontalAlignment(SwingConstants.RIGHT);
		SelectAll_chckbx.setBounds(544, 0, 100, 30);

		SelectAll_chckbx.setForeground(new Color(0, 0, 0));
		SelectAll_chckbx.setFont(new Font("Arial", Font.PLAIN, 12));
		SelectAll_chckbx.setFocusable(false);
		SelectAll_chckbx.setOpaque(false);
		first_half.add(SelectAll_chckbx);

		JPanel BL_wrapper = new JPanel();
		BL_wrapper.setSize(new Dimension(310, 90));
		BL_wrapper.setOpaque(false);
		BL_wrapper.setBorder(new LineBorder(new Color(128, 128, 0), 1, true));
		BL_wrapper.setBackground(new Color(0, 0, 51));
		BL_wrapper.setBounds(0, 30, 310, 90);
		BL_wrapper.setLayout(null);
		first_half.add(BL_wrapper);

		JPanel BLcheckbox_wrapper = new JPanel();
		BLcheckbox_wrapper.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		BLcheckbox_wrapper.setOpaque(false);
		BLcheckbox_wrapper.setBounds(10, 0, 290, 30);
		BL_wrapper.add(BLcheckbox_wrapper);
		BLcheckbox_wrapper.setLayout(null);

		JCheckBox BrokenLinks_chckbx = new JCheckBox("Broken Links");
		BrokenLinks_chckbx.setBounds(90, 5, 109, 20);
		BrokenLinks_chckbx.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		BrokenLinks_chckbx.setHorizontalAlignment(SwingConstants.CENTER);
		BrokenLinks_chckbx.setFocusable(false);
		BrokenLinks_chckbx.setFocusTraversalKeysEnabled(false);
		BrokenLinks_chckbx.setFocusPainted(false);
		BrokenLinks_chckbx.setBorder(new EmptyBorder(0, 0, 0, 0));
		BrokenLinks_chckbx.setForeground(new Color(0, 128, 128));
		BrokenLinks_chckbx.setOpaque(false);
		BrokenLinks_chckbx.setFont(new Font("Arial", Font.BOLD, 14));
		BLcheckbox_wrapper.add(BrokenLinks_chckbx);
		BrokenLinks_chckbx.setActionCommand("BrokenLinks");


		JPanel BLshow_hide_panel = new JPanel();
		BLshow_hide_panel.setVisible(false);
		BLshow_hide_panel.setOpaque(false);
		BLshow_hide_panel.setBounds(100, 30, 200, 50);
		BL_wrapper.add(BLshow_hide_panel);
		BLshow_hide_panel.setLayout(new CardLayout(0, 0));

		JPanel BLdomain_panel = new JPanel();
		BLdomain_panel.setAlignmentY(Component.TOP_ALIGNMENT);
		BLdomain_panel.setVisible(false);
		BLdomain_panel.setRequestFocusEnabled(false);
		BLdomain_panel.setOpaque(false);
		BLdomain_panel.setBackground(new Color(0, 204, 153));
		BLshow_hide_panel.add(BLdomain_panel, "name_588163190955099");

		BLdomain_panel.setLayout(null);

		BLDomain_txtField = new JTextField();
		BLDomain_txtField.setEnabled(false);
		//BLDomain_txtField.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE));
		BLDomain_txtField.setFont(new Font("Arial", Font.PLAIN, 12));
		BLDomain_txtField.setBounds(0, 30, 200, 20);
		BLdomain_panel.add(BLDomain_txtField);		
		BLDomain_txtField.setColumns(10);

		JPanel BLinput_panel = new JPanel();
		BLinput_panel.setOpaque(false);
		BLinput_panel.setBorder(new EmptyBorder(0, 0, 0, 0));
		BLinput_panel.setBackground(new Color(0, 204, 204));
		BLinput_panel.setVisible(false);
		BLshow_hide_panel.add(BLinput_panel, "name_588283515114969");

		BLinput_panel.setLayout(null);

		JLabel BL_label = new JLabel("Upload the File below");
		BL_label.setForeground(new Color(0, 0, 0));
		BL_label.setFont(new Font("Arial", Font.PLAIN, 12));
		BL_label.setBounds(0, 0, 150, 15);
		BL_label.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		BLinput_panel.add(BL_label);


		JPanel BLradiobutton_panel = new JPanel();
		BLradiobutton_panel.setOpaque(false);
		BLradiobutton_panel.setFont(new Font("Arial", Font.PLAIN, 12));
		BLradiobutton_panel.setBounds(10, 30, 80, 50);
		BL_wrapper.add(BLradiobutton_panel);
		BLradiobutton_panel.setLayout(null);

		JRadioButton BLInput_rdbtn = new JRadioButton("Input");
		BLInput_rdbtn.setBorderPainted(true);
		BLInput_rdbtn.setHorizontalAlignment(SwingConstants.LEFT);
		BLInput_rdbtn.setFocusable(false);
		BLInput_rdbtn.setBorder(new EmptyBorder(0, 0, 0, 0));
		BLInput_rdbtn.setForeground(new Color(0, 0, 0));
		BLInput_rdbtn.setFont(new Font("Arial", Font.PLAIN, 12));
		BLInput_rdbtn.setOpaque(false);
		BLInput_rdbtn.setBounds(5, 0, 70, 15);
		BLInput_rdbtn.setEnabled(false);
		BLradiobutton_panel.add(BLInput_rdbtn);

		JRadioButton BLDomain_rdbtn = new JRadioButton("Domain");
		BLDomain_rdbtn.setFocusable(false);
		BLDomain_rdbtn.setBorder(new EmptyBorder(0, 0, 0, 0));
		BLDomain_rdbtn.setForeground(new Color(0, 0, 0));
		BLDomain_rdbtn.setOpaque(false);
		BLDomain_rdbtn.setFont(new Font("Arial", Font.PLAIN, 12));
		BLDomain_rdbtn.setBounds(5, 30, 70, 15);
		BLDomain_rdbtn.setEnabled(false);
		BLradiobutton_panel.add(BLDomain_rdbtn);

		JPanel SC_wrapper = new JPanel();
		SC_wrapper.setLayout(null);
		SC_wrapper.setSize(new Dimension(320, 90));
		SC_wrapper.setOpaque(false);
		SC_wrapper.setBorder(new LineBorder(new Color(128, 128, 0), 1, true));
		SC_wrapper.setBackground(new Color(0, 0, 51));
		SC_wrapper.setBounds(334, 30, 310, 90);
		first_half.add(SC_wrapper);

		JPanel SC_checkbox_wrapper = new JPanel();
		SC_checkbox_wrapper.setOpaque(false);
		SC_checkbox_wrapper.setBounds(10, 0, 290, 30);
		SC_wrapper.add(SC_checkbox_wrapper);
		SC_checkbox_wrapper.setLayout(null);


		JCheckBox SpecialCharacters_chckbx = new JCheckBox("Special Characters");
		SpecialCharacters_chckbx.setBounds(90, 5, 149, 20);
		SpecialCharacters_chckbx.setHorizontalAlignment(SwingConstants.CENTER);
		SpecialCharacters_chckbx.setOpaque(false);
		SpecialCharacters_chckbx.setForeground(new Color(0, 128, 128));
		SpecialCharacters_chckbx.setFont(new Font("Arial", Font.BOLD, 14));
		SpecialCharacters_chckbx.setFocusable(false);
		SpecialCharacters_chckbx.setFocusTraversalKeysEnabled(false);
		SpecialCharacters_chckbx.setFocusPainted(false);
		SpecialCharacters_chckbx.setBorder(new EmptyBorder(0, 0, 0, 0));
		SpecialCharacters_chckbx.setAlignmentY(1.0f);
		SpecialCharacters_chckbx.setActionCommand("BrokenLinks");
		SC_checkbox_wrapper.add(SpecialCharacters_chckbx);
		SpecialCharacters_chckbx.setActionCommand("FindingSpecialCharacters");

		JPanel SCshow_hide_panel = new JPanel();
		SCshow_hide_panel.setVisible(false);
		SCshow_hide_panel.setOpaque(false);
		SCshow_hide_panel.setBounds(100, 30, 200, 50);
		SC_wrapper.add(SCshow_hide_panel);
		SCshow_hide_panel.setLayout(new CardLayout(0, 0));

		JPanel SCdomain_panel = new JPanel();
		SCdomain_panel.setVisible(false);
		SCdomain_panel.setRequestFocusEnabled(false);
		SCdomain_panel.setOpaque(false);
		SCdomain_panel.setBackground(new Color(0, 204, 153));
		SCshow_hide_panel.add(SCdomain_panel, "name_588163190955099");		
		SCdomain_panel.setLayout(null);	

		SCDomain_txtField = new JTextField();
		SCDomain_txtField.setEnabled(false);
		//SCDomain_txtField.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE));
		SCDomain_txtField.setFont(new Font("Arial", Font.PLAIN, 12));
		SCDomain_txtField.setBounds(0, 30, 200, 20);
		//SCDomain_txtField.setEnabled(false);
		SCdomain_panel.add(SCDomain_txtField);		

		JPanel SCinput_panel = new JPanel();
		SCinput_panel.setOpaque(false);
		SCinput_panel.setBorder(new EmptyBorder(0, 0, 0, 0));
		SCinput_panel.setBackground(new Color(0, 204, 204));
		SCinput_panel.setVisible(false);
		SCshow_hide_panel.add(SCinput_panel, "name_588283515114969");

		SCinput_panel.setLayout(null);

		JLabel SC_label = new JLabel("Upload the File below");
		SC_label.setForeground(new Color(0, 0, 0));
		SC_label.setFont(new Font("Arial", Font.PLAIN, 12));
		SC_label.setBounds(0, 0, 150, 15);
		SC_label.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		SCinput_panel.add(SC_label);

		JPanel SC_radiobutton_panel = new JPanel();
		SC_radiobutton_panel.setLayout(null);
		SC_radiobutton_panel.setOpaque(false);
		SC_radiobutton_panel.setFont(new Font("Arial", Font.PLAIN, 12));
		SC_radiobutton_panel.setBounds(10, 30, 80, 50);
		SC_wrapper.add(SC_radiobutton_panel);

		JRadioButton SCInput_rdbtn = new JRadioButton("Input");
		SCInput_rdbtn.setOpaque(false);
		SCInput_rdbtn.setHorizontalAlignment(SwingConstants.LEFT);
		SCInput_rdbtn.setForeground(new Color(0, 0, 0));
		SCInput_rdbtn.setFont(new Font("Arial", Font.PLAIN, 12));
		SCInput_rdbtn.setFocusable(false);
		SCInput_rdbtn.setEnabled(false);
		SCInput_rdbtn.setBorderPainted(true);
		SCInput_rdbtn.setBorder(new EmptyBorder(0, 0, 0, 0));
		SCInput_rdbtn.setActionCommand("BrokenLinks_Input");
		SCInput_rdbtn.setBounds(5, 0, 80, 15);
		SCInput_rdbtn.setEnabled(false);
		SC_radiobutton_panel.add(SCInput_rdbtn);

		JRadioButton SCDomain_rdbtn = new JRadioButton("Domain");
		SCDomain_rdbtn.setOpaque(false);
		SCDomain_rdbtn.setForeground(new Color(0, 0, 0));
		SCDomain_rdbtn.setFont(new Font("Arial", Font.PLAIN, 12));
		SCDomain_rdbtn.setFocusable(false);
		SCDomain_rdbtn.setEnabled(false);
		SCDomain_rdbtn.setBorder(new EmptyBorder(0, 0, 0, 0));
		SCDomain_rdbtn.setBounds(5, 30, 80, 15);
		SCDomain_rdbtn.setEnabled(false);
		SC_radiobutton_panel.add(SCDomain_rdbtn);

		JPanel specialChar_panel = new JPanel();
		specialChar_panel.setVisible(false);
		specialChar_panel.setSize(new Dimension(300, 30));
		specialChar_panel.setOpaque(false);
		specialChar_panel.setBounds(10, 80, 300, 30);
		SC_wrapper.add(specialChar_panel);
		specialChar_panel.setLayout(null);
		//specialChar_panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE));

		JLabel lblSpecialCharecters = new JLabel("Special Characters");
		lblSpecialCharecters.setFont(new Font("Arial", Font.PLAIN, 12));
		lblSpecialCharecters.setForeground(new Color(0, 0, 0));
		lblSpecialCharecters.setBounds(0, 15, 120, 14);
		specialChar_panel.add(lblSpecialCharecters);

		spCharInput = new JTextField();
		spCharInput.setEnabled(false);
		spCharInput.setAlignmentX(Component.LEFT_ALIGNMENT);
		spCharInput.setFont(new Font("Tahoma", Font.PLAIN, 10));
		spCharInput.setBounds(120, 10, 170, 20);
		specialChar_panel.add(spCharInput);
		spCharInput.setColumns(10);



		JPanel MA_wrapper = new JPanel();
		MA_wrapper.setSize(new Dimension(310, 90));
		MA_wrapper.setOpaque(false);
		MA_wrapper.setBorder(new LineBorder(new Color(128, 128, 0), 1, true));
		MA_wrapper.setBounds(0, 140, 310, 90);
		first_half.add(MA_wrapper);
		MA_wrapper.setLayout(null);

		JPanel MA_checkbox_wrapper = new JPanel();
		MA_checkbox_wrapper.setSize(new Dimension(300, 30));
		MA_checkbox_wrapper.setOpaque(false);
		MA_checkbox_wrapper.setBounds(10, 0, 290, 30);
		MA_wrapper.add(MA_checkbox_wrapper);
		MA_checkbox_wrapper.setLayout(null);

		JCheckBox MissingAssets_chckbx = new JCheckBox("Missing Assets");
		MissingAssets_chckbx.setBounds(90, 5, 123, 20);
		MissingAssets_chckbx.setAlignmentX(Component.CENTER_ALIGNMENT);
		MissingAssets_chckbx.setHorizontalAlignment(SwingConstants.LEFT);
		MissingAssets_chckbx.setOpaque(false);
		MissingAssets_chckbx.setForeground(new Color(0, 128, 128));
		MissingAssets_chckbx.setFont(new Font("Arial", Font.BOLD, 14));
		MissingAssets_chckbx.setFocusable(false);
		MissingAssets_chckbx.setFocusTraversalKeysEnabled(false);
		MissingAssets_chckbx.setFocusPainted(false);
		MissingAssets_chckbx.setBorder(new EmptyBorder(0, 0, 0, 0));
		MA_checkbox_wrapper.add(MissingAssets_chckbx);
		MissingAssets_chckbx.setActionCommand("FindingMissingAssets");

		JPanel MAshow_hide_panel = new JPanel();
		MAshow_hide_panel.setSize(new Dimension(210, 60));
		MAshow_hide_panel.setVisible(false);
		MAshow_hide_panel.setOpaque(false);
		MAshow_hide_panel.setBounds(100, 30, 200, 80);
		MA_wrapper.add(MAshow_hide_panel);
		MAshow_hide_panel.setLayout(new CardLayout(0, 0));

		JPanel MAdomain_panel = new JPanel();
		MAdomain_panel.setSize(new Dimension(210, 100));
		MAdomain_panel.setVisible(false);
		MAdomain_panel.setRequestFocusEnabled(false);
		MAdomain_panel.setOpaque(false);
		MAdomain_panel.setBackground(new Color(0, 204, 153));
		MAshow_hide_panel.add(MAdomain_panel, "name_588163190955099");		
		MAdomain_panel.setLayout(null);	

		JLabel MADomaintest_label = new JLabel("Domain (test)");
		MADomaintest_label.setForeground(new Color(0, 0, 0));
		MADomaintest_label.setFont(new Font("Arial", Font.PLAIN, 12));
		MADomaintest_label.setBounds(0, 0, 132, 14);
		MAdomain_panel.add(MADomaintest_label);

		MADomain_testtxtField = new JTextField();
		MADomain_testtxtField.setEnabled(false);
		MADomain_testtxtField.setFont(new Font("Arial", Font.PLAIN, 12));
		MADomain_testtxtField.setBounds(0, 66, 200, 20);		MAdomain_panel.add(MADomain_testtxtField);		

		JLabel MADomainprod_label = new JLabel("Domain (prod)");
		MADomainprod_label.setForeground(new Color(0, 0, 0));
		MADomainprod_label.setFont(new Font("Arial", Font.PLAIN, 12));
		MADomainprod_label.setBounds(0, 46, 132, 14);
		MAdomain_panel.add(MADomainprod_label);

		MADomain_prodtxtField = new JTextField();
		MADomain_prodtxtField.setEnabled(false);
		MADomain_prodtxtField.setFont(new Font("Arial", Font.PLAIN, 12));
		MADomain_prodtxtField.setColumns(10);
		MADomain_prodtxtField.setBounds(0, 20, 200, 20);
		MAdomain_panel.add(MADomain_prodtxtField);		

		JPanel MAinput_panel = new JPanel();
		MAinput_panel.setSize(new Dimension(210, 60));
		MAinput_panel.setOpaque(false);
		MAinput_panel.setBorder(new EmptyBorder(0, 0, 0, 0));
		MAinput_panel.setBackground(new Color(0, 204, 204));
		MAinput_panel.setVisible(false);
		MAshow_hide_panel.add(MAinput_panel);		
		MAinput_panel.setLayout(null);

		JLabel MA_label = new JLabel("Upload the File below");
		//JLabel BLinputLabel =  new JLabel("Upload the File below", SwingConstants.LEFT);
		MA_label.setForeground(new Color(0, 0, 0));
		MA_label.setFont(new Font("Arial", Font.PLAIN, 12));
		MA_label.setBounds(0, 0, 150, 15);
		MA_label.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		MAinput_panel.add(MA_label);

		JPanel MA_radiobutton_panel = new JPanel();
		MA_radiobutton_panel.setLayout(null);
		MA_radiobutton_panel.setOpaque(false);
		MA_radiobutton_panel.setFont(new Font("Arial", Font.PLAIN, 12));
		MA_radiobutton_panel.setBounds(10, 30, 80, 60);
		MA_wrapper.add(MA_radiobutton_panel);

		JRadioButton MAInput_rdbtn = new JRadioButton("Input");
		MAInput_rdbtn.setOpaque(false);
		MAInput_rdbtn.setHorizontalAlignment(SwingConstants.LEFT);
		MAInput_rdbtn.setForeground(new Color(0, 0, 0));
		MAInput_rdbtn.setFont(new Font("Arial", Font.PLAIN, 12));
		MAInput_rdbtn.setFocusable(false);
		MAInput_rdbtn.setEnabled(false);
		MAInput_rdbtn.setBorderPainted(true);
		MAInput_rdbtn.setBorder(new EmptyBorder(0, 0, 0, 0));
		MAInput_rdbtn.setBounds(5, 0, 70, 15);
		MAInput_rdbtn.setEnabled(false);
		MA_radiobutton_panel.add(MAInput_rdbtn);

		JRadioButton MADomain_rdbtn = new JRadioButton("Domain");
		MADomain_rdbtn.setOpaque(false);
		MADomain_rdbtn.setForeground(new Color(0, 0, 0));
		MADomain_rdbtn.setFont(new Font("Arial", Font.PLAIN, 12));
		MADomain_rdbtn.setFocusable(false);
		MADomain_rdbtn.setEnabled(false);
		MADomain_rdbtn.setBorder(new EmptyBorder(0, 0, 0, 0));
		MADomain_rdbtn.setBounds(5, 30, 70, 15);
		MADomain_rdbtn.setEnabled(false);
		MA_radiobutton_panel.add(MADomain_rdbtn);


		JPanel AS_wrapper = new JPanel();
		AS_wrapper.setSize(new Dimension(320, AS_height));
		AS_wrapper.setOpaque(false);
		AS_wrapper.setBorder(new LineBorder(new Color(128, 128, 0), 1, true));
		AS_wrapper.setBounds(334, 140, 310, 90);
		first_half.add(AS_wrapper);
		AS_wrapper.setLayout(null);


		JPanel AS_checkbox_wrapper = new JPanel();
		AS_checkbox_wrapper.setOpaque(false);
		AS_checkbox_wrapper.setBounds(10, 0, 290, 30);
		AS_wrapper.add(AS_checkbox_wrapper);
		AS_checkbox_wrapper.setLayout(null);

		JCheckBox AssetsSizes_chckbx = new JCheckBox("Assets Sizes");
		AssetsSizes_chckbx.setRolloverEnabled(false);
		AssetsSizes_chckbx.setBounds(90, 5, 105, 20);
		AssetsSizes_chckbx.setHorizontalAlignment(SwingConstants.CENTER);
		AssetsSizes_chckbx.setOpaque(false);
		AssetsSizes_chckbx.setForeground(new Color(0, 128, 128));
		AssetsSizes_chckbx.setFont(new Font("Arial", Font.BOLD, 14));
		AssetsSizes_chckbx.setFocusable(false);
		AssetsSizes_chckbx.setFocusTraversalKeysEnabled(false);
		AssetsSizes_chckbx.setFocusPainted(false);
		AssetsSizes_chckbx.setBorder(new EmptyBorder(0, 0, 0, 0));
		AS_checkbox_wrapper.add(AssetsSizes_chckbx);
		AssetsSizes_chckbx.setActionCommand("GetAssetSize");


		JPanel ASshow_hide_panel = new JPanel();
		ASshow_hide_panel.setVisible(false);
		ASshow_hide_panel.setOpaque(false);
		ASshow_hide_panel.setBounds(100, 30, 200, 50);
		AS_wrapper.add(ASshow_hide_panel);
		ASshow_hide_panel.setLayout(new CardLayout(0, 0));

		JPanel ASdomain_panel = new JPanel();
		ASdomain_panel.setVisible(false);
		ASdomain_panel.setRequestFocusEnabled(false);
		ASdomain_panel.setOpaque(false);
		ASdomain_panel.setBackground(new Color(0, 204, 153));
		ASshow_hide_panel.add(ASdomain_panel, "name_588163190955099");		
		ASdomain_panel.setLayout(null);	

		ASDomain_txtField = new JTextField();
		ASDomain_txtField.setEnabled(false);
		//ASDomain_txtField.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE));
		ASDomain_txtField.setFont(new Font("Arial", Font.PLAIN, 12));
		ASDomain_txtField.setBounds(0, 30, 200, 20);
		//ASDomain_txtField.setEnabled(false);
		ASdomain_panel.add(ASDomain_txtField);		

		JPanel ASinput_panel = new JPanel();
		ASinput_panel.setOpaque(false);
		ASinput_panel.setBorder(new EmptyBorder(0, 0, 0, 0));
		ASinput_panel.setBackground(new Color(0, 204, 204));
		ASinput_panel.setVisible(false);
		ASshow_hide_panel.add(ASinput_panel);		
		ASinput_panel.setLayout(null);

		JLabel AS_label = new JLabel("Upload the File below");
		//JLabel BLinputLabel =  new JLabel("Upload the File below", SwingConstants.LEFT);
		AS_label.setForeground(new Color(0, 0, 0));
		AS_label.setFont(new Font("Arial", Font.PLAIN, 12));
		AS_label.setBounds(0, 0, 150, 15);
		AS_label.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		ASinput_panel.add(AS_label);

		JPanel AS_radiobutton_panel = new JPanel();
		AS_radiobutton_panel.setLayout(null);
		AS_radiobutton_panel.setOpaque(false);
		AS_radiobutton_panel.setFont(new Font("Arial", Font.PLAIN, 12));
		AS_radiobutton_panel.setBounds(10, 30, 80, 50);
		AS_wrapper.add(AS_radiobutton_panel);

		JRadioButton ASInput_rdbtn = new JRadioButton("Input");
		ASInput_rdbtn.setOpaque(false);
		ASInput_rdbtn.setHorizontalAlignment(SwingConstants.LEFT);
		ASInput_rdbtn.setForeground(new Color(0, 0, 0));
		ASInput_rdbtn.setFont(new Font("Arial", Font.PLAIN, 12));
		ASInput_rdbtn.setFocusable(false);
		ASInput_rdbtn.setEnabled(false);
		ASInput_rdbtn.setBorderPainted(true);
		ASInput_rdbtn.setBorder(new EmptyBorder(0, 0, 0, 0));
		//ASInput_rdbtn.setActionCommand("BrokenLinks_Input");
		ASInput_rdbtn.setBounds(0, 0, 70, 15);
		ASInput_rdbtn.setEnabled(false);
		AS_radiobutton_panel.add(ASInput_rdbtn);

		JRadioButton ASDomain_rdbtn = new JRadioButton("Domain");
		ASDomain_rdbtn.setOpaque(false);
		ASDomain_rdbtn.setForeground(new Color(0, 0, 0));
		ASDomain_rdbtn.setFont(new Font("Arial", Font.PLAIN, 12));
		ASDomain_rdbtn.setFocusable(false);
		ASDomain_rdbtn.setEnabled(false);
		ASDomain_rdbtn.setBorder(new EmptyBorder(0, 0, 0, 0));
		//ASDomain_rdbtn.setActionCommand("BrokenLinks_Domain");
		ASDomain_rdbtn.setBounds(0, 30, 80, 15);
		ASDomain_rdbtn.setEnabled(false);
		AS_radiobutton_panel.add(ASDomain_rdbtn);

		JPanel range_panel = new JPanel();
		range_panel.setVisible(false);
		range_panel.setSize(new Dimension(300, 40));
		range_panel.setOpaque(false);
		range_panel.setBounds(10, 80, 300, 40);
		AS_wrapper.add(range_panel);
		range_panel.setLayout(null);

		JLabel lblRange = new JLabel("Range");
		lblRange.setFont(new Font("Arial", Font.PLAIN, 12));
		lblRange.setForeground(new Color(0, 0, 0));
		lblRange.setBounds(0, 23, 46, 14);
		range_panel.add(lblRange);

		rangeMax = new JTextField();
		rangeMax.setEnabled(false);
		rangeMax.setAlignmentX(Component.LEFT_ALIGNMENT);
		rangeMax.setFont(new Font("Tahoma", Font.PLAIN, 10));
		rangeMax.setBounds(50, 20, 110, 20);
		//rangeMax.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE));
		range_panel.add(rangeMax);
		//rangeMax.setEnabled(false);
		rangeMax.setColumns(10);

		rangeMin = new JTextField();
		rangeMin.setEnabled(false);
		rangeMin.setColumns(10);
		rangeMin.setFont(new Font("Tahoma", Font.PLAIN, 10));
		rangeMin.setAlignmentX(0.0f);
		rangeMin.setBounds(170, 20, 120, 20);
		//rangeMin.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE));
		//rangeMax.setEnabled(false);
		range_panel.add(rangeMin);

		JLabel lblRed = new JLabel("*Max value in KB");
		lblRed.setForeground(new Color(128, 0, 0));
		lblRed.setFont(new Font("Arial", Font.PLAIN, 9));
		lblRed.setBounds(50, 5, 100, 14);
		range_panel.add(lblRed);

		JLabel lblAmber = new JLabel("Min value in KB");
		lblAmber.setForeground(new Color(255, 69, 0));
		lblAmber.setFont(new Font("Arial", Font.PLAIN, 9));
		lblAmber.setBounds(180, 5, 120, 14);
		range_panel.add(lblAmber);




		JPanel otherHalf_wrapper = new JPanel();
		otherHalf_wrapper.setSize(new Dimension(644, 114));
		otherHalf_wrapper.setOpaque(false);
		otherHalf_wrapper.setLayout(null);
		//otherHalf_wrapper.setBorder(new LineBorder(new Color(128, 128, 0), 1, true));
		otherHalf_wrapper.setBackground(new Color(0, 0, 51));
		otherHalf_wrapper.setBounds(0, first_half.getHeight(), 644, 114);
		center_panel.add(otherHalf_wrapper);
		otherHalf_wrapper.setLayout(null);


		JPanel PC_wrapper = new JPanel();
		PC_wrapper.setOpaque(false);
		PC_wrapper.setLayout(null);
		//PC_wrapper.setBorder(new LineBorder(new Color(128, 128, 0), 1, true));
		PC_wrapper.setBackground(new Color(0, 0, 51));
		PC_wrapper.setBounds(0, 0, 644, 84);
		PC_wrapper.setSize(new Dimension(644, 84));
		otherHalf_wrapper.add(PC_wrapper);


		JPanel new_PC_wrapper = new JPanel();
		new_PC_wrapper.setOpaque(false);
		new_PC_wrapper.setLayout(null);
		new_PC_wrapper.setBorder(new LineBorder(new Color(128, 128, 0), 1, true));
		new_PC_wrapper.setBackground(new Color(0, 0, 51));
		new_PC_wrapper.setBounds(0, 0, 310, 84);
		new_PC_wrapper.setSize(new Dimension(310, 84));
		PC_wrapper.add(new_PC_wrapper);

		JPanel PC_checkbox_wrapper = new JPanel();
		PC_checkbox_wrapper.setOpaque(false);
		PC_checkbox_wrapper.setBounds(10, 0, 290, 30);
		new_PC_wrapper.add(PC_checkbox_wrapper);
		PC_checkbox_wrapper.setLayout(null);

		JCheckBox PageComparison_chckbx = new JCheckBox("PageComparison");
		PageComparison_chckbx.setBounds(90, 0, 140, 30);
		PageComparison_chckbx.setRequestFocusEnabled(false);
		PageComparison_chckbx.setRolloverEnabled(false);
		PageComparison_chckbx.setIgnoreRepaint(true);
		PageComparison_chckbx.setActionCommand("PageComparison");
		PageComparison_chckbx.setHorizontalAlignment(SwingConstants.LEFT);
		PageComparison_chckbx.setOpaque(false);
		PageComparison_chckbx.setForeground(new Color(0, 128, 128));
		PageComparison_chckbx.setFont(new Font("Arial", Font.BOLD, 14));
		PageComparison_chckbx.setFocusable(false);
		PageComparison_chckbx.setFocusTraversalKeysEnabled(false);
		PageComparison_chckbx.setFocusPainted(false);
		PageComparison_chckbx.setBorder(new EmptyBorder(0, 0, 0, 0));
		PageComparison_chckbx.setAlignmentY(1.0f);
		PC_checkbox_wrapper.add(PageComparison_chckbx);


		JPanel PCselect_panel = new JPanel();
		PCselect_panel.setOpaque(false);
		PCselect_panel.setBounds(10, 30, 290, 100);
		//PCselect_panel.setBorder(new LineBorder(new Color(128, 128, 0), 1, true));		
		PCselect_panel.setLayout(null);
		new_PC_wrapper.add(PCselect_panel);

		JPanel PCselectbrowser_panel = new JPanel();
		PCselectbrowser_panel.setOpaque(false);
		PCselectbrowser_panel.setBounds(0, 0, 290, 45);
		PCselect_panel.add(PCselectbrowser_panel);
		PCselectbrowser_panel.setLayout(null);

		JLabel PC_browserlabel = new JLabel("Select Browser :");
		PC_browserlabel.setEnabled(false);
		PC_browserlabel.setForeground(new Color(0, 0, 0));
		PC_browserlabel.setFont(new Font("Arial", Font.PLAIN, 12));
		PC_browserlabel.setBounds(0, 0, 100, 20);
		PCselectbrowser_panel.add(PC_browserlabel);

		JComboBox PC_comboBox = new JComboBox();
		PC_comboBox.setFont(new Font("Arial", Font.PLAIN, 12));
		PC_comboBox.setFocusable(false);
		PC_comboBox.setBounds(0, 25, 290, 20);
		PC_comboBox.setBackground(color);
		PC_comboBox.setEnabled(false);		
		PC_comboBox.setModel(new DefaultComboBoxModel(browserValues));
		PCselectbrowser_panel.add(PC_comboBox);

		JLabel PC_label = new JLabel("Upload the File below");
		PC_label.setBounds(160, 3, 130, 15);
		PCselectbrowser_panel.add(PC_label);
		PC_label.setHorizontalAlignment(SwingConstants.RIGHT);
		PC_label.setForeground(Color.BLACK);
		PC_label.setFont(new Font("Arial", Font.PLAIN, 12));
		PC_label.setEnabled(false);
		PC_label.setBackground(Color.LIGHT_GRAY);

		JPanel PC_Browserpanel = new JPanel();
		PC_Browserpanel.setOpaque(false);
		PC_Browserpanel.setBounds(0, 55, 300, 45);
		PC_Browserpanel.setLayout(new CardLayout(0, 0));
		//PC_Browserpanel.setBorder(new LineBorder(new Color(128, 128, 0), 1, true));
		PC_Browserpanel.setVisible(false);
		PCselect_panel.add(PC_Browserpanel);

		JPanel PCEmulator_panel = new JPanel();
		PCEmulator_panel.setOpaque(false);
		PCEmulator_panel.setVisible(false);
		PCEmulator_panel.setBounds(0, 55, 300, 45);
		PCselect_panel.add(PCEmulator_panel);
		PCEmulator_panel.setLayout(null);

		JLabel PCEmulatorlabel = new JLabel("Emulators :");
		PCEmulatorlabel.setForeground(new Color(0, 0, 0));
		PCEmulatorlabel.setFont(new Font("Arial", Font.PLAIN, 12));
		PCEmulatorlabel.setBounds(0, 0, 100, 20);
		PCEmulator_panel.add(PCEmulatorlabel);

		JComboBox PCEmulator_comboBox = new JComboBox();
		PCEmulator_comboBox.setFont(new Font("Arial", Font.PLAIN, 12));
		PCEmulator_comboBox.setFocusable(false);
		PCEmulator_comboBox.setBounds(0, 25, 290, 20);
		PCEmulator_comboBox.setModel(new DefaultComboBoxModel(emulator));
		PCEmulator_panel.add(PCEmulator_comboBox);

		JPanel PCRealdevice_panel = new JPanel();
		PCRealdevice_panel.setOpaque(false);
		PCRealdevice_panel.setVisible(false);
		PCRealdevice_panel.setBounds(0, 55, 300, 45);
		PCselect_panel.add(PCRealdevice_panel);
		PCRealdevice_panel.setLayout(null);

		JLabel PCRealDevicelabel = new JLabel("Real Device :");
		PCRealDevicelabel.setForeground(new Color(0, 0, 0));
		PCRealDevicelabel.setFont(new Font("Arial", Font.PLAIN, 12));
		PCRealDevicelabel.setBounds(0, 0, 100, 20);
		PCRealdevice_panel.add(PCRealDevicelabel);

		JComboBox PCRealDevice_comboBox = new JComboBox();
		PCRealDevice_comboBox.setFont(new Font("Arial", Font.PLAIN, 12));
		PCRealDevice_comboBox.setFocusable(false);
		PCRealDevice_comboBox.setBounds(0, 25, 290, 20);
		PCRealDevice_comboBox.setModel(new DefaultComboBoxModel(real));
		PCRealdevice_panel.add(PCRealDevice_comboBox);

		JPanel RT_panel = new JPanel();
		RT_panel.setBounds(334, 0, 10, 10);
		RT_panel.setOpaque(false);
		RT_panel.setLayout(null);
		RT_panel.setBorder(new LineBorder(new Color(128, 128, 0), 1, true));
		RT_panel.setBackground(new Color(0, 0, 51));
		RT_panel.setSize(new Dimension(310, 84));
		PC_wrapper.add(RT_panel);

		JPanel RTcheckbox_panel = new JPanel();
		RTcheckbox_panel.setOpaque(false);
		RTcheckbox_panel.setBounds(10, 0, 290, 30);
		RT_panel.add(RTcheckbox_panel);
		RTcheckbox_panel.setLayout(null);

		JCheckBox ResponseTime_chckbx = new JCheckBox("Response Time");
		ResponseTime_chckbx.setBounds(90, 0, 140, 30);
		ResponseTime_chckbx.setRolloverEnabled(false);
		ResponseTime_chckbx.setRequestFocusEnabled(false);
		ResponseTime_chckbx.setOpaque(false);
		ResponseTime_chckbx.setIgnoreRepaint(true);
		ResponseTime_chckbx.setHorizontalAlignment(SwingConstants.LEFT);
		ResponseTime_chckbx.setForeground(new Color(0, 128, 128));
		ResponseTime_chckbx.setFont(new Font("Arial", Font.BOLD, 14));
		ResponseTime_chckbx.setFocusable(false);
		ResponseTime_chckbx.setFocusTraversalKeysEnabled(false);
		ResponseTime_chckbx.setFocusPainted(false);
		ResponseTime_chckbx.setBorder(new EmptyBorder(0, 0, 0, 0));
		ResponseTime_chckbx.setAlignmentY(1.0f);
		ResponseTime_chckbx.setActionCommand("ResponseTime");
		RTcheckbox_panel.add(ResponseTime_chckbx);


		JPanel RT_range_wrapper = new JPanel();
		RT_range_wrapper.setOpaque(false);
		RT_range_wrapper.setBounds(10, 29, 290, 45);
		RT_panel.add(RT_range_wrapper);
		RT_range_wrapper.setLayout(null);

		JLabel RT_upload_label = new JLabel("Upload the File below");
		RT_upload_label.setBounds(170, 0, 119, 15);
		RT_upload_label.setHorizontalAlignment(SwingConstants.RIGHT);
		RT_upload_label.setForeground(Color.BLACK);
		RT_upload_label.setFont(new Font("Arial", Font.PLAIN, 12));
		RT_upload_label.setEnabled(false);
		RT_upload_label.setBackground(Color.LIGHT_GRAY);
		RT_range_wrapper.add(RT_upload_label);

		JLabel RT_range_label = new JLabel("Threshold :");
		RT_range_label.setForeground(Color.BLACK);
		RT_range_label.setFont(new Font("Arial", Font.PLAIN, 12));
		RT_range_label.setBounds(0, 25, 91, 20);
		RT_range_label.setEnabled(false);
		RT_range_wrapper.add(RT_range_label);

		RT_range_field = new JTextField();
		RT_range_field.setFont(new Font("Arial", Font.PLAIN, 12));
		RT_range_field.setEnabled(false);
		//RT_range_field.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE));
		RT_range_field.setBounds(90, 25, 101, 20);
		RT_range_wrapper.add(RT_range_field);

		JLabel RT_seconds_label = new JLabel("in seconds");
		RT_seconds_label.setForeground(Color.BLACK);
		RT_seconds_label.setFont(new Font("Arial", Font.PLAIN, 12));
		RT_seconds_label.setBounds(200, 25, 70, 20);
		RT_seconds_label.setEnabled(false);
		RT_range_wrapper.add(RT_seconds_label);


		JCheckBox chckbxShowTools_healthchecks = new JCheckBox("Regression");
		chckbxShowTools_healthchecks.setRequestFocusEnabled(false);
		chckbxShowTools_healthchecks.setRolloverEnabled(false);
		chckbxShowTools_healthchecks.setFocusable(false);
		chckbxShowTools_healthchecks.setHorizontalAlignment(SwingConstants.RIGHT);
		chckbxShowTools_healthchecks.setAlignmentX(Component.RIGHT_ALIGNMENT);
		chckbxShowTools_healthchecks.setBounds(554, 84, 90, 30);		
		chckbxShowTools_healthchecks.setForeground(new Color(0, 0, 0));
		chckbxShowTools_healthchecks.setFont(new Font("Arial", Font.PLAIN, 12));
		chckbxShowTools_healthchecks.setOpaque(false);
		chckbxShowTools_healthchecks.setActionCommand("ShowTools_healthchecks");
		otherHalf_wrapper.add(chckbxShowTools_healthchecks);

		JPanel Tools_RP_wrapper = new JPanel();
		Tools_RP_wrapper.setVisible(false);
		Tools_RP_wrapper.setSize(new Dimension(otherHalf_wrapper.getWidth(), 84));
		Tools_RP_wrapper.setBorder(new LineBorder(new Color(128, 128, 0), 1, true));
		Tools_RP_wrapper.setOpaque(false);
		Tools_RP_wrapper.setBounds(0, 114, otherHalf_wrapper.getWidth(), 84);
		Tools_RP_wrapper.setLayout(null);
		otherHalf_wrapper.add(Tools_RP_wrapper);

		JPanel TRP_combo_panel = new JPanel();
		TRP_combo_panel.setOpaque(false);
		TRP_combo_panel.setBounds(10, 10, 290, 45);
		Tools_RP_wrapper.add(TRP_combo_panel);
		TRP_combo_panel.setEnabled(true);
		TRP_combo_panel.setLayout(null);

		JLabel RP_tool_label = new JLabel("Regression Suite");
		RP_tool_label.setForeground(new Color(0, 128, 128));
		RP_tool_label.setFont(new Font("Arial", Font.BOLD, 12));
		RP_tool_label.setBounds(0, 0, 100, 20);
		TRP_combo_panel.add(RP_tool_label);

		JComboBox RP_tool_comboBox = new JComboBox();
		RP_tool_comboBox.setFont(new Font("Arial", Font.PLAIN, 12));
		RP_tool_comboBox.setFocusable(false);
		RP_tool_comboBox.setBounds(0, 25, 290, 20);
		RP_tool_comboBox.setBackground(color);
		RP_tool_comboBox.setEnabled(false);
		TRP_combo_panel.add(RP_tool_comboBox);
		RP_tool_comboBox.setModel(new DefaultComboBoxModel(new String[] {"Please select", "Regression Pack"}));

		JPanel TRP_panel = new JPanel();
		TRP_panel.setOpaque(false);
		TRP_panel.setBounds(342, 10, 292, 45);
		Tools_RP_wrapper.add(TRP_panel);
		TRP_panel.setLayout(new CardLayout(0, 0));

		JLabel lblPlease = new JLabel("Please select a value from dropdown");
		lblPlease.setForeground(Color.WHITE);
		lblPlease.setFont(new Font("Arial", Font.PLAIN, 12));

		JPanel tools_selectbrowser_wrapper = new JPanel();
		tools_selectbrowser_wrapper.setVisible(false);
		tools_selectbrowser_wrapper.setOpaque(false);
		tools_selectbrowser_wrapper.setBounds(10, 70, 290, 45);
		Tools_RP_wrapper.add(tools_selectbrowser_wrapper);
		tools_selectbrowser_wrapper.setLayout(null);

		JLabel Tools_browserlabel = new JLabel("Select Browser :");
		Tools_browserlabel.setForeground(new Color(0, 0, 0));
		Tools_browserlabel.setFont(new Font("Arial", Font.PLAIN, 12));
		Tools_browserlabel.setBounds(0, 0, 100, 20);
		tools_selectbrowser_wrapper.add(Tools_browserlabel);

		JComboBox RP_comboBox = new JComboBox();
		RP_comboBox.setFont(new Font("Arial", Font.PLAIN, 12));
		RP_comboBox.setFocusable(false);
		RP_comboBox.setBounds(0, 25, 290, 20);
		tools_selectbrowser_wrapper.add(RP_comboBox);
		RP_comboBox.setModel(new DefaultComboBoxModel(browserValues));		

		JPanel RP_Browserpanel = new JPanel();
		RP_Browserpanel.setVisible(false);
		RP_Browserpanel.setOpaque(false);
		RP_Browserpanel.setBounds(342, 70, 292, 45);
		Tools_RP_wrapper.add(RP_Browserpanel);
		RP_Browserpanel.setLayout(new CardLayout(0, 0));

		JPanel RPEmulator_panel = new JPanel();
		RPEmulator_panel.setOpaque(false);
		RPEmulator_panel.setVisible(false);
		RPEmulator_panel.setBounds(342, 70, 292, 45);
		Tools_RP_wrapper.add(RPEmulator_panel);
		RPEmulator_panel.setLayout(null);

		JLabel RPEmulatorlabel = new JLabel("Emulators :");
		RPEmulatorlabel.setForeground(new Color(0, 0, 0));
		RPEmulatorlabel.setFont(new Font("Arial", Font.PLAIN, 12));
		RPEmulatorlabel.setBounds(0, 0, 100, 20);
		RPEmulator_panel.add(RPEmulatorlabel);

		JComboBox RPEmulator_comboBox = new JComboBox();
		RPEmulator_comboBox.setFont(new Font("Arial", Font.PLAIN, 12));
		RPEmulator_comboBox.setFocusable(false);
		RPEmulator_comboBox.setBounds(0, 25, 290, 20);
		RPEmulator_comboBox.setModel(new DefaultComboBoxModel(emulator));
		RPEmulator_panel.add(RPEmulator_comboBox);

		JPanel RPRealdevice_panel = new JPanel();
		RPRealdevice_panel.setOpaque(false);
		RPRealdevice_panel.setVisible(false);
		RPRealdevice_panel.setBounds(342, 70, 292, 45);
		Tools_RP_wrapper.add(RPRealdevice_panel);
		RPRealdevice_panel.setLayout(null);

		JLabel RPRealDevicelabel = new JLabel("Real Device :");
		RPRealDevicelabel.setForeground(new Color(0, 0, 0));
		RPRealDevicelabel.setFont(new Font("Arial", Font.PLAIN, 12));
		RPRealDevicelabel.setBounds(0, 0, 100, 20);
		RPRealdevice_panel.add(RPRealDevicelabel);

		JComboBox RPRealDevice_comboBox = new JComboBox();
		RPRealDevice_comboBox.setFont(new Font("Arial", Font.PLAIN, 12));
		RPRealDevice_comboBox.setFocusable(false);
		RPRealDevice_comboBox.setBounds(0, 25, 290, 20);
		RPRealDevice_comboBox.setModel(new DefaultComboBoxModel(real));
		RPRealdevice_panel.add(RPRealDevice_comboBox);

		JPanel BrowserStack_panel = new JPanel();
		BrowserStack_panel.setVisible(false);
		BrowserStack_panel.setOpaque(false);
		BrowserStack_panel.setSize(new Dimension(otherHalf_wrapper.getWidth(), 60));
		BrowserStack_panel.setBorder(new LineBorder(new Color(128, 128, 0), 1, true));
		BrowserStack_panel.setBounds(0, (PC_wrapper.getHeight()+20), otherHalf_wrapper.getWidth(), 60);
		BrowserStack_panel.setLayout(null);	
		otherHalf_wrapper.add(BrowserStack_panel);

		JPanel BS_header_panel = new JPanel();
		BS_header_panel.setOpaque(false);
		BS_header_panel.setBounds(10, 0, 644, 30);
		BrowserStack_panel.add(BS_header_panel);
		BS_header_panel.setLayout(new CardLayout(0, 0));

		JLabel lblBrowser = new JLabel("Browser Stack Sign in Details");
		lblBrowser.setHorizontalAlignment(SwingConstants.CENTER);
		lblBrowser.setForeground(new Color(0, 128, 128));
		lblBrowser.setFont(new Font("Arial", Font.BOLD, 14));
		BS_header_panel.add(lblBrowser);

		JPanel BS_content_panel = new JPanel();
		BS_content_panel.setOpaque(false);
		BS_content_panel.setBounds(10, 28, 644, 20);
		BrowserStack_panel.add(BS_content_panel);
		BS_content_panel.setLayout(null);



		JLabel BrowserStackEmail_label = new JLabel("Email");
		BrowserStackEmail_label.setForeground(new Color(0, 0, 0));
		BrowserStackEmail_label.setFont(new Font("Arial", Font.PLAIN, 12));
		BrowserStackEmail_label.setSize(50, 20);
		BS_content_panel.add(BrowserStackEmail_label);
		BrowserStackEmail_txtfield = new JTextField();
		BrowserStackEmail_txtfield.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE));
		BrowserStackEmail_txtfield.setFont(new Font("Arial", Font.PLAIN, 12));
		BrowserStackEmail_txtfield.setSize(200, 20);
		BrowserStackEmail_txtfield.setLocation(60, 0);
		BS_content_panel.add(BrowserStackEmail_txtfield);

		JLabel BrowserStackPswd_label = new JLabel("Automated Key");
		BrowserStackPswd_label.setForeground(new Color(0, 0, 0));
		BrowserStackPswd_label.setFont(new Font("Arial", Font.PLAIN, 12));
		BrowserStackPswd_label.setSize(97, 20);
		BrowserStackPswd_label.setLocation(315, 0);
		BS_content_panel.add(BrowserStackPswd_label);

		BrowserStackPswd_txtfield = new JPasswordField();
		BrowserStackPswd_txtfield.setFont(new Font("Arial", Font.PLAIN, 12));
		BrowserStackPswd_txtfield.setBounds(422, 0, 200, 20);
		BrowserStackPswd_txtfield.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE));
		BS_content_panel.add(BrowserStackPswd_txtfield);

		JPanel Browser_Panel = new JPanel();
		Browser_Panel.setVisible(false);
		Browser_Panel.setOpaque(false);
		Browser_Panel.setSize(new Dimension(otherHalf_wrapper.getWidth(), 240));
		Browser_Panel.setBorder(new LineBorder(new Color(128, 128, 0), 1, true));
		Browser_Panel.setBounds(0, (PC_wrapper.getHeight() + BrowserStack_panel.getHeight() + 20), 644, 100);
		Browser_Panel.setLayout(null);	
		otherHalf_wrapper.add(Browser_Panel);

		JPanel B_header_panel = new JPanel();
		B_header_panel.setOpaque(false);
		B_header_panel.setBounds(10, 0, 644, 30);
		Browser_Panel.add(B_header_panel);
		B_header_panel.setLayout(new CardLayout(0, 0));

		JLabel lbBrowser = new JLabel("Details");
		lbBrowser.setHorizontalAlignment(SwingConstants.CENTER);
		lbBrowser.setForeground(new Color(0, 128, 128));
		lbBrowser.setFont(new Font("Arial", Font.BOLD, 14));
		B_header_panel.add(lbBrowser);

		JPanel B_content_panel = new JPanel();
		B_content_panel.setOpaque(false);
		B_content_panel.setBounds(10, 28, 644, 120);
		Browser_Panel.add(B_content_panel);
		B_content_panel.setLayout(null);

		JLabel Browserdn_label = new JLabel("Device Name");
		Browserdn_label.setForeground(new Color(0, 0, 0));
		Browserdn_label.setFont(new Font("Arial", Font.BOLD, 12));
		Browserdn_label.setBounds(0, 0,100, 20);
		B_content_panel.add(Browserdn_label);

		Browserdn_txtfield = new JTextField();
		Browserdn_txtfield.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE));
		Browserdn_txtfield.setFont(new Font("Arial", Font.PLAIN, 12));
		Browserdn_txtfield.setBounds(110, 0, 500, 20);
		B_content_panel.add(Browserdn_txtfield);

		JLabel Browserpv_label = new JLabel("Platform Version");
		Browserpv_label.setSize(100, 20);
		Browserpv_label.setLocation(0, 30);
		Browserpv_label.setForeground(new Color(0, 0, 0));
		B_content_panel.add(Browserpv_label);

		Browserpv_txtfield = new JTextField();
		Browserpv_txtfield.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE));
		Browserpv_txtfield.setFont(new Font("Arial", Font.PLAIN, 12));
		Browserpv_txtfield.setSize(500, 20);
		Browserpv_txtfield.setLocation(110, 30);
		B_content_panel.add(Browserpv_txtfield);

		JLabel Browserdp_label = new JLabel("Driver Path");
		Browserdp_label.setSize(100, 20);
		Browserdp_label.setLocation(0, 60);
		Browserdp_label.setForeground(new Color(0, 0, 0));
		B_content_panel.add(Browserdp_label);

		Browserdp_txtfield = new JTextField();
		Browserdp_txtfield.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE));
		Browserdp_txtfield.setFont(new Font("Arial", Font.PLAIN, 12));
		Browserdp_txtfield.setSize(500, 20);
		Browserdp_txtfield.setLocation(110, 60);
		B_content_panel.add(Browserdp_txtfield);

		JLabel Browseran_label = new JLabel("App Name");
		Browseran_label.setSize(100, 20);
		Browseran_label.setLocation(0, 90);
		Browseran_label.setForeground(new Color(0, 0, 0));
		B_content_panel.add(Browseran_label);

		Browseran_txtfield = new JTextField();
		Browseran_txtfield.setSize(500, 20);
		Browseran_txtfield.setLocation(110, 90);
		Browseran_txtfield.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE));
		Browseran_txtfield.setFont(new Font("Arial", Font.PLAIN, 12));
		B_content_panel.add(Browseran_txtfield);

		LinkedList<JCheckBox> checkBoxes = new LinkedList<JCheckBox>(); //creating a checkbox list
		checkBoxes.add(BrokenLinks_chckbx); //adding all checkboxes to list
		checkBoxes.add(SpecialCharacters_chckbx);
		checkBoxes.add(MissingAssets_chckbx);
		checkBoxes.add(AssetsSizes_chckbx);
		checkBoxes.add(PageComparison_chckbx);
		checkBoxes.add(ResponseTime_chckbx);


		//checkBoxes.add(chckbxShowTools_healthchecks);
		for(JCheckBox checkbx:checkBoxes)
			checkbx.setBackground(color);


		LinkedList<JRadioButton> radiobuttons = new LinkedList<JRadioButton>(); //creating a radiobutton list
		radiobuttons.add(BLDomain_rdbtn); //adding all radiobuttons to list
		radiobuttons.add(BLInput_rdbtn);
		radiobuttons.add(SCDomain_rdbtn);
		radiobuttons.add(SCInput_rdbtn);
		radiobuttons.add(MAInput_rdbtn);
		radiobuttons.add(MADomain_rdbtn);
		radiobuttons.add(ASDomain_rdbtn);
		radiobuttons.add(ASInput_rdbtn);

		for(JRadioButton rdbtn:radiobuttons)
			rdbtn.setBackground(color);


		ButtonGroup BLbuttonGroup = createButtonGroup(BLDomain_rdbtn, BLInput_rdbtn, "BrokenLinks_Domain",
				"BrokenLinks_Input"); //calling createButtonGroup method which returns buttonGroup

		ButtonGroup SCbuttonGroup = createButtonGroup(SCDomain_rdbtn, SCInput_rdbtn, "SpecialCharacters_Domain",
				"SpecialCharacters_Input");

		ButtonGroup MAbuttonGroup = createButtonGroup(MADomain_rdbtn, MAInput_rdbtn, "MissingAssets_Domain",
				"MissingAssets_Input");


		ButtonGroup ASbuttonGroup = createButtonGroup(ASDomain_rdbtn, ASInput_rdbtn, "AssetSizes_Domain",
				"AssetSizes_Input");

		SelectAll_chckbx.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (SelectAll_chckbx.isSelected()) {
					for (JCheckBox checkBox : checkBoxes) {
						checkBox.setSelected(true); //when selectll checkbox is clicked all checkboxes are selected
					}
					for(JRadioButton rdbtn:radiobuttons)
					{
						rdbtn.setEnabled(true); //When selectAll checkbox is checked all radiobuttons are selected
					}

					//Setting all the input radiobuttons checked
					BLInput_rdbtn.setSelected(true);
					BLDomain_txtField.setEnabled(false);
					BLshow_hide_panel.setVisible(true); 
					BLinput_panel.setVisible(true);  
					BLdomain_panel.setVisible(false);

					SCInput_rdbtn.setSelected(true);
					SCDomain_txtField.setEnabled(false);
					SCshow_hide_panel.setVisible(true); 
					SCinput_panel.setVisible(true);  
					SCdomain_panel.setVisible(false);

					ASInput_rdbtn.setSelected(true);
					ASDomain_txtField.setEnabled(false);
					ASshow_hide_panel.setVisible(true); 
					ASinput_panel.setVisible(true);  
					ASdomain_panel.setVisible(false);
					range_panel.setVisible(true);
					rangeMax.setEnabled(true);
					rangeMin.setEnabled(true);


					MAInput_rdbtn.setSelected(true);
					MADomaintest_label.setEnabled(false);
					MADomain_testtxtField.setEnabled(false);
					MADomainprod_label.setEnabled(false);
					MADomain_prodtxtField.setEnabled(false);
					MAshow_hide_panel.setVisible(true); 
					MAinput_panel.setVisible(true);  
					MAdomain_panel.setVisible(false);

					BL_label.setEnabled(true); //enabling all the labels
					SC_label.setEnabled(true);
					MA_label.setEnabled(true);
					AS_label.setEnabled(true);
					PC_label.setEnabled(true);

					//Enabling the Page comparison dropdown and setting 1st item as selected
					PC_comboBox.setEnabled(true);
					PC_comboBox.setSelectedIndex(0);
					PC_browserlabel.setEnabled(true);
					PC_label.setEnabled(true);
					PC_browserlabel.setEnabled(true);
					PC_comboBox.setEnabled(true);

					RT_range_field.setEnabled(true);
					RT_range_label.setEnabled(true);
					RT_upload_label.setEnabled(true);	
					RT_seconds_label.setEnabled(true);	


					specialChar_panel.setVisible(true); 
					spCharInput.setEnabled(true);

					SC_wrapper.setSize(310, (90 + specialChar_panel.getHeight()));						
					AS_wrapper.setBounds(334, (140 + specialChar_panel.getHeight()), 310, AS_wrapper.getHeight());
					AS_wrapper.setSize(310, (AS_height + range_panel.getHeight()));		
					MA_wrapper.setBounds(0, (140 + specialChar_panel.getHeight()), 310, MA_wrapper.getHeight());

					first_half.setSize(new Dimension(center_panel.getWidth(), (AS_wrapper.getHeight()+SC_wrapper.getHeight()+70)));


				} else {
					for (JCheckBox checkBox : checkBoxes) {
						checkBox.setSelected(false); //Uncheck all tasks checkbox
					}
					for(JRadioButton rdbtn:radiobuttons)
					{
						rdbtn.setEnabled(false); //Uncheck all radiobuttons
					}
					//if selectall is unchecked clearing radiobutton selection for all checkboxes
					BLbuttonGroup.clearSelection(); 
					SCbuttonGroup.clearSelection();
					ASbuttonGroup.clearSelection();
					MAbuttonGroup.clearSelection();
					//Setting all the textfields to disable state

					BLshow_hide_panel.setVisible(false); 
					BLinput_panel.setVisible(false);  
					BLdomain_panel.setVisible(false); 

					SCshow_hide_panel.setVisible(false); 
					SCinput_panel.setVisible(false);  
					SCdomain_panel.setVisible(false);


					MAshow_hide_panel.setVisible(false); 
					MAinput_panel.setVisible(false);  
					MAdomain_panel.setVisible(false);

					ASshow_hide_panel.setVisible(false); 
					ASinput_panel.setVisible(false);  
					ASdomain_panel.setVisible(false);
					range_panel.setVisible(false);


					PC_label.setEnabled(false);
					PC_browserlabel.setEnabled(false);
					PC_comboBox.setSelectedIndex(0);
					PC_comboBox.setEnabled(false);

					RT_range_field.setEnabled(false);
					RT_range_label.setEnabled(false);
					RT_upload_label.setEnabled(false);
					RT_seconds_label.setEnabled(false);

					BLDomain_txtField.setEnabled(false);
					SCDomain_txtField.setEnabled(false);	
					ASDomain_txtField.setEnabled(false);
					rangeMax.setEnabled(false);
					rangeMin.setEnabled(false);
					MAInput_rdbtn.setSelected(false);

					//Disable all the labels
					BL_label.setEnabled(false);
					SC_label.setEnabled(false);
					MA_label.setEnabled(false);
					AS_label.setEnabled(false);
					PC_label.setEnabled(false);
					PC_browserlabel.setEnabled(false);
					PC_comboBox.setSelectedIndex(0);
					PC_comboBox.setEnabled(false);//Disable the PC_Combobox

					specialChar_panel.setVisible(false); 	
					spCharInput.setEnabled(false);
					SC_wrapper.setSize(310, 90);

					AS_wrapper.setSize(310, AS_height);
					MA_wrapper.setSize(310, AS_height);

					MA_wrapper.setBounds(0, 140, 310, MA_wrapper.getHeight());
					AS_wrapper.setBounds(334, 140, 310, AS_wrapper.getHeight());


					int whichEverHeight = (MA_wrapper.getHeight()>AS_wrapper.getHeight()) ?(MA_wrapper.getHeight()+SC_wrapper.getHeight()+70) : (AS_wrapper.getHeight()+SC_wrapper.getHeight()+70);
					first_half.setSize(new Dimension(center_panel.getWidth(), whichEverHeight));
					/*otherHalf_wrapper.setBounds(0, first_half.getHeight(), 644, otherHalf_wrapper.getHeight());
					center_panel.setSize(new Dimension(center_panel.getWidth(), (first_half.getHeight() + otherHalf_wrapper.getHeight())));
					contentPane.setPreferredSize(new Dimension(720, (200+ first_half.getHeight() + otherHalf_wrapper.getHeight())));*/

				}
				otherHalf_wrapper.setBounds(0, first_half.getHeight(), 644, otherHalf_wrapper.getHeight());
				center_panel.setSize(new Dimension(center_panel.getWidth(), (first_half.getHeight() + otherHalf_wrapper.getHeight())));
				contentPane.setPreferredSize(new Dimension(720, (200+ first_half.getHeight() + otherHalf_wrapper.getHeight())));
				center_panel.revalidate();
				contentPane.revalidate();

			}
		});
		for(JCheckBox check: checkBoxes) {
			check.addItemListener(new ItemListener() {

				@Override
				public void itemStateChanged(ItemEvent e) {
					JCheckBox check1 = (JCheckBox)e.getSource();
					if(!check1.isSelected()) { 
						//when we uncheck atleast one checkbox selectall checkbox is unchecked(if selectall is checked already) 
						SelectAll_chckbx.setSelected(false);
					}
				}
			});
		}



		BrokenLinks_chckbx.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (BrokenLinks_chckbx.isSelected()) {

					BLbuttonGroup.clearSelection();
					BLDomain_rdbtn.setEnabled(true);
					BLInput_rdbtn.setEnabled(true);
					BLInput_rdbtn.setSelected(true);
					BL_label.setEnabled(true);
					BLshow_hide_panel.setVisible(true); 
					BLinput_panel.setVisible(true);  
					BLdomain_panel.setVisible(false);
					BLDomain_txtField.setEnabled(true);

				} else {				

					BLbuttonGroup.clearSelection();
					BLDomain_rdbtn.setSelected(false);
					BLInput_rdbtn.setSelected(false);
					BLDomain_rdbtn.setEnabled(false);
					BLDomain_txtField.setEnabled(false);
					BLInput_rdbtn.setEnabled(false);
					BL_label.setEnabled(false);
					BLshow_hide_panel.setVisible(false); 
				}
			}
		});

		BLDomain_rdbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (BLDomain_rdbtn.isSelected()) 
				{
					BLDomain_txtField.setEnabled(true);
					BLshow_hide_panel.setVisible(true); 
					BLinput_panel.setVisible(false);  
					BLdomain_panel.setVisible(true); 
				}else {
					BLshow_hide_panel.setVisible(false); 
					BLinput_panel.setVisible(false);  
					BLdomain_panel.setVisible(false); 
					BLDomain_txtField.setEnabled(false);
				}
			}

		});
		BLInput_rdbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (BLInput_rdbtn.isSelected()) 
				{
					BLDomain_txtField.setEnabled(false);
					BLshow_hide_panel.setVisible(true); 
					BLinput_panel.setVisible(true);  
					BLdomain_panel.setVisible(false); 
				}else {
					BLshow_hide_panel.setVisible(false); 
					BLinput_panel.setVisible(false);  
					BLdomain_panel.setVisible(false); 
				}

			}

		});	

		SpecialCharacters_chckbx.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (SpecialCharacters_chckbx.isSelected()) {
					SCbuttonGroup.clearSelection();
					SCDomain_rdbtn.setEnabled(true);
					SCInput_rdbtn.setEnabled(true);
					SCInput_rdbtn.setSelected(true);
					SC_label.setEnabled(true);
					SCshow_hide_panel.setVisible(true); 
					SCinput_panel.setVisible(true);  
					SCdomain_panel.setVisible(false);

					specialChar_panel.setVisible(true); 
					spCharInput.setEnabled(true);
					SC_wrapper.setSize(310, (90 + specialChar_panel.getHeight()));		
					MA_wrapper.setBounds(0, (140 + specialChar_panel.getHeight()), 310, MA_wrapper.getHeight());
					AS_wrapper.setBounds(334, (140 + specialChar_panel.getHeight()), 310, AS_wrapper.getHeight());
					int newHeight = (AS_wrapper.getHeight()+SC_wrapper.getHeight()+70);
					first_half.setSize(new Dimension(center_panel.getWidth(), newHeight));
					//System.out.println("if---- "+first_half.getHeight());



				} else {
					SCbuttonGroup.clearSelection();
					SCDomain_rdbtn.setEnabled(false);
					SCDomain_txtField.setEnabled(false);
					SCInput_rdbtn.setEnabled(false);
					SC_label.setEnabled(false);
					SCshow_hide_panel.setVisible(false);
					SCInput_rdbtn.setSelected(false); 

					specialChar_panel.setVisible(false); 	
					spCharInput.setEnabled(false);
					SC_wrapper.setSize(310, 90);	
					MA_wrapper.setBounds(0, 140, 310, MA_wrapper.getHeight());
					AS_wrapper.setBounds(334, 140, 310, AS_wrapper.getHeight());
					int newHeight = (AS_wrapper.getHeight()+SC_wrapper.getHeight()+70);
					first_half.setSize(new Dimension(center_panel.getWidth(), newHeight));
					//System.out.println("else---- "+first_half.getHeight());


				}
				otherHalf_wrapper.setBounds(0, first_half.getHeight(), 644, otherHalf_wrapper.getHeight());
				center_panel.setSize(new Dimension(center_panel.getWidth(), (first_half.getHeight() + otherHalf_wrapper.getHeight())));
				contentPane.setPreferredSize(new Dimension(720, (200+ first_half.getHeight() + otherHalf_wrapper.getHeight())));
				contentPane.revalidate();

			}
		});
		SCDomain_rdbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (SCDomain_rdbtn.isSelected()) 
				{
					SCDomain_txtField.setEnabled(true);
					SCshow_hide_panel.setVisible(true); 
					SCinput_panel.setVisible(false);  
					SCdomain_panel.setVisible(true); 
				}else {
					SCshow_hide_panel.setVisible(false); 
					SCinput_panel.setVisible(false);  
					SCdomain_panel.setVisible(false); 
				}
			}

		});
		SCInput_rdbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (SCInput_rdbtn.isSelected()) 
				{
					SCDomain_txtField.setEnabled(false);
					SCshow_hide_panel.setVisible(true); 
					SCinput_panel.setVisible(true);  
					SCdomain_panel.setVisible(false); 

				}else {
					SCshow_hide_panel.setVisible(false); 
					SCinput_panel.setVisible(false);  
					SCdomain_panel.setVisible(false); 
				}
			}

		});
		MissingAssets_chckbx.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (MissingAssets_chckbx.isSelected()) {
					MAbuttonGroup.clearSelection();
					MADomain_rdbtn.setEnabled(true);
					MAInput_rdbtn.setEnabled(true);
					MAInput_rdbtn.setSelected(true);
					MA_label.setEnabled(true);
					MAshow_hide_panel.setVisible(true); 
					MAinput_panel.setVisible(true);  
					MAdomain_panel.setVisible(false);

				} else {

					MAbuttonGroup.clearSelection();
					MADomain_rdbtn.setEnabled(false);
					MAInput_rdbtn.setEnabled(false);
					MAInput_rdbtn.setSelected(false);
					MA_label.setEnabled(false);
					MADomaintest_label.setEnabled(false);
					MADomain_testtxtField.setEnabled(false);
					MADomainprod_label.setEnabled(false);
					MADomain_prodtxtField.setEnabled(false);
					MAshow_hide_panel.setVisible(false); 

					MAshow_hide_panel.setSize(new Dimension(200, 60));
					MA_wrapper.setSize(new Dimension(310,  90));
					int whichEverHeight = (MA_wrapper.getHeight()>AS_wrapper.getHeight()) ?(MA_wrapper.getHeight()+SC_wrapper.getHeight()+70) : (AS_wrapper.getHeight()+SC_wrapper.getHeight()+70);
					first_half.setSize(new Dimension(center_panel.getWidth(), whichEverHeight) );
					otherHalf_wrapper.setBounds(0, first_half.getHeight(),644, otherHalf_wrapper.getHeight());
					center_panel.setSize(new Dimension(center_panel.getWidth(), (first_half.getHeight() + otherHalf_wrapper.getHeight())));
					contentPane.setPreferredSize(new Dimension(720, (200+ first_half.getHeight() + otherHalf_wrapper.getHeight())));

				}
			}

		});
		MADomain_rdbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (MADomain_rdbtn.isSelected()) 
				{
					MADomaintest_label.setEnabled(true);
					MADomain_testtxtField.setEnabled(true);
					MADomainprod_label.setEnabled(true);
					MADomain_prodtxtField.setEnabled(true);

					MAshow_hide_panel.setVisible(true); 
					MAinput_panel.setVisible(false);  
					MAdomain_panel.setVisible(true);

					MAshow_hide_panel.setSize(new Dimension(200, 100));
					MA_wrapper.setSize(new Dimension(310, (30 + MAshow_hide_panel.getHeight()) ));		
					int newHeight = (MA_wrapper.getHeight()+SC_wrapper.getHeight()+70);	
					first_half.setSize(new Dimension(center_panel.getWidth(), newHeight) );

				}else {


					MAshow_hide_panel.setVisible(false); 
					MAinput_panel.setVisible(false);  
					MAdomain_panel.setVisible(false); 

					MAshow_hide_panel.setSize(new Dimension(200, 60));
					MA_wrapper.setSize(new Dimension(310,  90));
					int whichEverHeight = (MA_wrapper.getHeight()>AS_wrapper.getHeight()) ? (MA_wrapper.getHeight()+SC_wrapper.getHeight()+70) : (AS_wrapper.getHeight()+SC_wrapper.getHeight()+70);
					first_half.setSize(new Dimension(center_panel.getWidth(), whichEverHeight) );
				}
				otherHalf_wrapper.setBounds(0, first_half.getHeight(),644, otherHalf_wrapper.getHeight());
				center_panel.setSize(new Dimension(center_panel.getWidth(), (first_half.getHeight() + otherHalf_wrapper.getHeight())));
				contentPane.setPreferredSize(new Dimension(720, (200+ first_half.getHeight() + otherHalf_wrapper.getHeight())));
			}

		});
		MAInput_rdbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (MAInput_rdbtn.isSelected()) 
				{
					MADomaintest_label.setEnabled(false);
					MADomain_testtxtField.setEnabled(false);
					MADomainprod_label.setEnabled(false);
					MADomain_prodtxtField.setEnabled(false);
					MAshow_hide_panel.setVisible(true); 
					MAinput_panel.setVisible(true);  
					MAdomain_panel.setVisible(false);

					MAshow_hide_panel.setSize(new Dimension(200, 60));
					MA_wrapper.setSize(new Dimension(310,  90));
					int whichEverHeight = (MA_wrapper.getHeight()>AS_wrapper.getHeight()) ? (MA_wrapper.getHeight()+SC_wrapper.getHeight()+70) : (AS_wrapper.getHeight()+SC_wrapper.getHeight()+70);
					first_half.setSize(new Dimension(center_panel.getWidth(), whichEverHeight) );
					otherHalf_wrapper.setBounds(0, first_half.getHeight(), 644, otherHalf_wrapper.getHeight());
					center_panel.setSize(new Dimension(center_panel.getWidth(), (first_half.getHeight() + otherHalf_wrapper.getHeight())));
					contentPane.setPreferredSize(new Dimension(720, (200+ first_half.getHeight() + otherHalf_wrapper.getHeight())));

				}else {

					MAshow_hide_panel.setVisible(false); 
					MAinput_panel.setVisible(false);  
					MAdomain_panel.setVisible(false); 
				}

				center_panel.revalidate();
				contentPane.revalidate();
				validate();
			}

		});

		AssetsSizes_chckbx.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (AssetsSizes_chckbx.isSelected()) {
					ASbuttonGroup.clearSelection();
					ASDomain_rdbtn.setEnabled(true);
					ASInput_rdbtn.setEnabled(true);
					ASInput_rdbtn.setSelected(true);
					AS_label.setEnabled(true);
					ASshow_hide_panel.setVisible(true); 
					ASinput_panel.setVisible(true);  
					ASdomain_panel.setVisible(false);
					range_panel.setVisible(true);
					rangeMax.setEnabled(true);
					rangeMin.setEnabled(true);


					AS_wrapper.setSize(310, (AS_height + range_panel.getHeight()));		
					int newHeight = (AS_wrapper.getHeight()+SC_wrapper.getHeight()+70);			
					first_half.setSize(new Dimension(center_panel.getWidth(), newHeight));

					RT_panel.revalidate();
				} else {
					ASbuttonGroup.clearSelection();
					ASDomain_rdbtn.setEnabled(false);
					ASDomain_txtField.setEnabled(false);
					ASInput_rdbtn.setEnabled(false);
					ASInput_rdbtn.setSelected(false);
					AS_label.setEnabled(false);
					ASshow_hide_panel.setVisible(false);
					range_panel.setVisible(false);
					rangeMax.setEnabled(false);
					rangeMin.setEnabled(false);

					AS_wrapper.setSize(310, AS_height);
					int whichEverHeight = (MA_wrapper.getHeight()>AS_wrapper.getHeight()) ? (MA_wrapper.getHeight()+SC_wrapper.getHeight()+70) : (AS_wrapper.getHeight()+SC_wrapper.getHeight()+70);
					first_half.setSize(new Dimension(center_panel.getWidth(), whichEverHeight));
					/*otherHalf_wrapper.setBounds(0, first_half.getHeight(), 644, otherHalf_wrapper.getHeight());
					center_panel.setSize(new Dimension(center_panel.getWidth(), (first_half.getHeight() + otherHalf_wrapper.getHeight())));
					contentPane.setPreferredSize(new Dimension(720, (200+ first_half.getHeight() + otherHalf_wrapper.getHeight())));
					RT_panel.revalidate();*/
				}


				otherHalf_wrapper.setBounds(0, first_half.getHeight(), 644, otherHalf_wrapper.getHeight());
				center_panel.setSize(new Dimension(center_panel.getWidth(), (first_half.getHeight() + otherHalf_wrapper.getHeight())));
				contentPane.setPreferredSize(new Dimension(720, (200+ first_half.getHeight() + otherHalf_wrapper.getHeight())));
				contentPane.revalidate();


			}
		});
		ASDomain_rdbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (ASDomain_rdbtn.isSelected()) {
					ASDomain_txtField.setEnabled(true);
					ASshow_hide_panel.setVisible(true); 
					ASinput_panel.setVisible(false);  
					ASdomain_panel.setVisible(true);
				}else {
					ASshow_hide_panel.setVisible(false); 
					ASinput_panel.setVisible(false);  
					ASdomain_panel.setVisible(false);
				}
			}

		});
		ASInput_rdbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (ASInput_rdbtn.isSelected()) {
					ASDomain_txtField.setEnabled(false);
					ASshow_hide_panel.setVisible(true); 
					ASinput_panel.setVisible(true);  
					ASdomain_panel.setVisible(false);
				}else {
					ASshow_hide_panel.setVisible(false); 
					ASinput_panel.setVisible(false);  
					ASdomain_panel.setVisible(false);
				}
			}

		});

		PageComparison_chckbx.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (PageComparison_chckbx.isSelected()) {
					PC_label.setEnabled(true);
					PC_browserlabel.setEnabled(true);
					PC_comboBox.setEnabled(true);

					chckbxShowTools_healthchecks.setSelected(false);
					Tools_RP_wrapper.setVisible(false);
					
					otherHalf_wrapper.setSize(new Dimension(644, (PC_wrapper.getHeight()+30)));
					center_panel.setSize(center_panel.getWidth(), center_panel.getHeight());
					contentPane.setPreferredSize(new Dimension(720, (200+ first_half.getHeight() + otherHalf_wrapper.getHeight())));
					Uploadfile_panel.repaint();
					Uploadfile_panel.revalidate();
					otherHalf_wrapper.revalidate();
					center_panel.revalidate();
					contentPane.repaint();
					contentPane.validate();

				} else {

					PC_label.setEnabled(false);
					PC_browserlabel.setEnabled(false);
					PC_comboBox.setSelectedIndex(0);
					PC_comboBox.setEnabled(false);		

					new_PC_wrapper.setSize(new Dimension(310, (84)));
					PC_wrapper.setSize(new Dimension(644,new_PC_wrapper.getHeight()));
					chckbxShowTools_healthchecks.setBounds(554, new_PC_wrapper.getHeight(), 90, 30);
					Tools_RP_wrapper.setBounds(0, (PC_wrapper.getHeight()+30), otherHalf_wrapper.getWidth(), Tools_RP_wrapper.getHeight());
					center_panel.setSize(new Dimension(center_panel.getWidth(), (first_half.getHeight() + otherHalf_wrapper.getHeight())));
					contentPane.setPreferredSize(new Dimension(720, (200+ first_half.getHeight() + otherHalf_wrapper.getHeight())));
					center_panel.repaint();
					contentPane.repaint();
					contentPane.validate();

				}

			}
		});

		//Action performed if  Page Comparison Dropdown is clicked
		PC_comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {//here your actionPerformed method
				Browser_Panel.setVisible(false);
				BrowserStack_panel.setVisible(false);
				//Checking if Please select is selected 
				if(PC_comboBox.getSelectedItem().equals("Please select"))
				{
					new_PC_wrapper.setSize(new Dimension(310, (84)));
					PC_wrapper.setSize(new Dimension(644,new_PC_wrapper.getHeight()));
					chckbxShowTools_healthchecks.setBounds(554, new_PC_wrapper.getHeight(), 90, 30);
					otherHalf_wrapper.setSize(new Dimension(644, (PC_wrapper.getHeight()+30)));

					PC_Browserpanel.removeAll(); //Removing all components in PC_browsepanel
					PC_Browserpanel.setVisible(false);

				}
				//Checking if Local Browser is selected 
				else   if(PC_comboBox.getSelectedItem().equals(localbrowser))
				{

					new_PC_wrapper.setSize(new Dimension(310, (84+55)));
					PC_Browserpanel.setSize(new Dimension(310,100));
					PC_wrapper.setSize(new Dimension(644,new_PC_wrapper.getHeight()));
					otherHalf_wrapper.setSize(new Dimension(644, (PC_wrapper.getHeight()+30)));

					PC_Browserpanel.removeAll();
					PC_Browserpanel.setVisible(true);
					browserDisplay--;
					//Adding local browser dropdown to panel
					PC_Browserpanel.add(CheckCombobox.createcombo(localbrowser, LocalBrowsersList, comboboxObj,PCLocalBrowserSelectedValues));
					center_panel.setSize(new Dimension(center_panel.getWidth(), (first_half.getHeight() + otherHalf_wrapper.getHeight())));
					contentPane.setPreferredSize(new Dimension(720, (200+ first_half.getHeight() + otherHalf_wrapper.getHeight())));
					center_panel.repaint();
					contentPane.repaint();
					contentPane.revalidate();
				}
				//Checking if  Browser Stack is selected 
				else if(PC_comboBox.getSelectedItem().equals(browserstacklabel))
				{
					new_PC_wrapper.setSize(new Dimension(310, (84+55)));
					PC_Browserpanel.setSize(new Dimension(310,100));
					PC_wrapper.setSize(new Dimension(644,new_PC_wrapper.getHeight()));
					BrowserStack_panel.setVisible(true);
					BrowserStack_panel.setBounds(0, (PC_wrapper.getHeight()+30), otherHalf_wrapper.getWidth(), 60);
					otherHalf_wrapper.setSize(new Dimension(644, (PC_wrapper.getHeight()+30+BrowserStack_panel.getHeight())));
					
					PC_Browserpanel.removeAll();
					PC_Browserpanel.setVisible(true);
					//Adding browser stack dropdown to panel
					PC_Browserpanel.add(CheckCombobox.createcombo(browserstacklabel, BrowserstackList, comboboxObj,PCBrowserStackSelectedValues));
					browserDisplay++; //Increasing the count is browser stack is displayed

					PC_Browserpanel.add(CheckCombobox.createcombo(localbrowser, LocalBrowsersList, comboboxObj,PCLocalBrowserSelectedValues));
					center_panel.setSize(new Dimension(center_panel.getWidth(), (first_half.getHeight() + otherHalf_wrapper.getHeight())));
					contentPane.setPreferredSize(new Dimension(720, (200+ first_half.getHeight() + otherHalf_wrapper.getHeight())));
					center_panel.repaint();
					contentPane.repaint();
					contentPane.validate();
				}
				else if(PC_comboBox.getSelectedItem().equals(Emulator)) {
					new_PC_wrapper.setSize(new Dimension(310, (84+55)));
					PC_Browserpanel.setSize(new Dimension(310,100));
					PC_wrapper.setSize(new Dimension(644,new_PC_wrapper.getHeight()));
					otherHalf_wrapper.setSize(new Dimension(644, (PC_wrapper.getHeight()+30+30+Browser_Panel.getHeight())));

					PC_Browserpanel.removeAll();
					PC_Browserpanel.setVisible(true);

					PCEmulator_panel.setVisible(true);
					//Adding Emulator dropdown to panel

					PC_Browserpanel.add(PCEmulator_panel);

					browserDisplay--; 

					Browser_Panel.setVisible(true);
					Browser_Panel.setBounds(0, (PC_wrapper.getHeight()+30), otherHalf_wrapper.getWidth(), 160);
					otherHalf_wrapper.setSize(new Dimension(644, (PC_wrapper.getHeight()+30+Browser_Panel.getHeight())));

					center_panel.setSize(new Dimension(center_panel.getWidth(), (first_half.getHeight() + otherHalf_wrapper.getHeight() + otherHalf_wrapper.getHeight())));
					contentPane.setPreferredSize(new Dimension(720, (200+ first_half.getHeight() + otherHalf_wrapper.getHeight() + otherHalf_wrapper.getHeight())));
					center_panel.repaint();
					contentPane.repaint();
					contentPane.validate();
				}
				else if(PC_comboBox.getSelectedItem().equals(RealDevice)) {
					new_PC_wrapper.setSize(new Dimension(310, (84+55)));
					PC_Browserpanel.setSize(new Dimension(310,100));
					PC_wrapper.setSize(new Dimension(644,new_PC_wrapper.getHeight()));
					otherHalf_wrapper.setSize(new Dimension(644, (PC_wrapper.getHeight()+30+30+Browser_Panel.getHeight())));
					PC_Browserpanel.removeAll();
					PC_Browserpanel.setVisible(true);
					PCRealdevice_panel.setVisible(true);
					//Adding Real Device dropdown to panel
					PC_Browserpanel.add(PCRealdevice_panel);
					browserDisplay--; 

					Browser_Panel.setVisible(true);
					Browser_Panel.setBounds(0, (PC_wrapper.getHeight()+30), otherHalf_wrapper.getWidth(), 160);
					otherHalf_wrapper.setSize(new Dimension(644, (PC_wrapper.getHeight()+30+Browser_Panel.getHeight())));


					//PC_Browserpanel.add(CheckCombobox.createcombo(localbrowser, LocalBrowsersList, comboboxObj,PCLocalBrowserSelectedValues));
					center_panel.setSize(new Dimension(center_panel.getWidth(), (first_half.getHeight() + otherHalf_wrapper.getHeight())));
					contentPane.setPreferredSize(new Dimension(720, (200+ first_half.getHeight() + otherHalf_wrapper.getHeight())));
					center_panel.repaint();
					contentPane.repaint();
					contentPane.validate();
				}


				center_panel.setSize(new Dimension(center_panel.getWidth(), (first_half.getHeight() + otherHalf_wrapper.getHeight())));
				contentPane.setPreferredSize(new Dimension(720, (200+ first_half.getHeight() + otherHalf_wrapper.getHeight())));
				center_panel.repaint();
				contentPane.repaint();
				contentPane.validate();
			}
		});
		ResponseTime_chckbx.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (ResponseTime_chckbx.isSelected()) {
					RT_range_field.setEnabled(true);
					RT_range_label.setEnabled(true);
					RT_upload_label.setEnabled(true);
					RT_seconds_label.setEnabled(true);
				} else {
					RT_range_field.setEnabled(false);
					RT_range_label.setEnabled(false);
					RT_upload_label.setEnabled(false);
					RT_seconds_label.setEnabled(false);
				}
			}
		});
		chckbxShowTools_healthchecks.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (chckbxShowTools_healthchecks.isSelected()) {

					PageComparison_chckbx.setSelected(false);
					PC_label.setEnabled(false);
					PC_browserlabel.setEnabled(false);
					PC_comboBox.setSelectedIndex(0);
					PC_comboBox.setEnabled(false);		

					new_PC_wrapper.setSize(new Dimension(310, (84)));
					PC_wrapper.setSize(new Dimension(644,new_PC_wrapper.getHeight()));
					chckbxShowTools_healthchecks.setBounds(554, new_PC_wrapper.getHeight(), 90, 30);
					Tools_RP_wrapper.setBounds(0, (PC_wrapper.getHeight()+30), otherHalf_wrapper.getWidth(), Tools_RP_wrapper.getHeight());
					center_panel.setSize(new Dimension(center_panel.getWidth(), (first_half.getHeight() + otherHalf_wrapper.getHeight())));
					contentPane.setPreferredSize(new Dimension(720, (200+ first_half.getHeight() + otherHalf_wrapper.getHeight())));
					center_panel.repaint();

					Tools_RP_wrapper.setBounds(0, (PC_wrapper.getHeight()+30), otherHalf_wrapper.getWidth(), 84);
					Tools_RP_wrapper.setVisible(true);
					RP_tool_label.setEnabled(true);
					RP_tool_comboBox.setEnabled(true);

					contentPane.repaint();
					contentPane.validate();
					if(PC_comboBox.getSelectedItem().equals(browserstacklabel) || RP_comboBox.getSelectedItem().equals(browserstacklabel)) {
						BrowserStack_panel.setBounds(0,  (PC_wrapper.getHeight()+30+Tools_RP_wrapper.getHeight()+20), otherHalf_wrapper.getWidth(), 60);						
						BrowserStack_panel.setVisible(true);
						otherHalf_wrapper.setSize(new Dimension(644, (PC_wrapper.getHeight()+30+Tools_RP_wrapper.getHeight()+20+BrowserStack_panel.getHeight()+30)));
					}else if(PC_comboBox.getSelectedItem().equals(realLabel) || RP_comboBox.getSelectedItem().equals(realLabel) || PC_comboBox.getSelectedItem().equals(emulatorlabel) || RP_comboBox.getSelectedItem().equals(emulatorlabel)){
						Tools_RP_wrapper.setBounds(0, (PC_wrapper.getHeight()+Browser_Panel.getHeight()+60), otherHalf_wrapper.getWidth(), 84);
						otherHalf_wrapper.setSize(new Dimension(644, (PC_wrapper.getHeight()+Tools_RP_wrapper.getHeight()+Browser_Panel.getHeight()+80)));
					}
					else {
						otherHalf_wrapper.setSize(new Dimension(644, (PC_wrapper.getHeight()+Tools_RP_wrapper.getHeight()+30)));

					}




				} else {
					Tools_RP_wrapper.setVisible(false);
					Browser_Panel.setVisible(false);
					BrowserStack_panel.setVisible(false);
					RP_comboBox.setSelectedIndex(0);
					RP_tool_comboBox.setSelectedIndex(0);
					RP_tool_label.setEnabled(false);
					RP_tool_comboBox.setEnabled(false);
					tools_selectbrowser_wrapper.setVisible(false);
					RP_Browserpanel.setVisible(false);

					Uploadfile_panel.add(StartExecution_btn);

					if(PC_comboBox.getSelectedItem().equals(browserstacklabel) || RP_comboBox.getSelectedItem().equals(browserstacklabel)) {						
						BrowserStack_panel.setBounds(0, (PC_wrapper.getHeight()+30),  otherHalf_wrapper.getWidth(), 60);
						BrowserStack_panel.setVisible(true);
						otherHalf_wrapper.setSize(new Dimension(644, (PC_wrapper.getHeight()+30+BrowserStack_panel.getHeight())));
					}else if(PC_comboBox.getSelectedItem().equals(realLabel) || RP_comboBox.getSelectedItem().equals(realLabel) || PC_comboBox.getSelectedItem().equals(emulatorlabel) || RP_comboBox.getSelectedItem().equals(emulatorlabel)){
						Browser_Panel.setVisible(true);
						otherHalf_wrapper.setSize(new Dimension(644, (PC_wrapper.getHeight()+Browser_Panel.getHeight()+60)));
					}
					else {
						otherHalf_wrapper.setSize(new Dimension(644, (PC_wrapper.getHeight()+30)));
					}

				}


				center_panel.setSize(center_panel.getWidth(), center_panel.getHeight());
				contentPane.setPreferredSize(new Dimension(720, (200+ first_half.getHeight() + otherHalf_wrapper.getHeight())));
				Uploadfile_panel.repaint();
				Uploadfile_panel.revalidate();
				otherHalf_wrapper.revalidate();
				center_panel.revalidate();
				contentPane.repaint();
				contentPane.validate();
			}
		});

		RP_tool_comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if(RP_tool_comboBox.getSelectedItem().equals(rptools)) {
					TRP_panel.removeAll();
					TRP_panel.setVisible(true);
					if(PageComparison_chckbx.isSelected() ) {
						if(PC_comboBox.getSelectedItem().equals(realLabel) || PC_comboBox.getSelectedItem().equals(emulatorlabel)) {
							Tools_RP_wrapper.setBounds(0,  (PC_wrapper.getHeight()+30+Browser_Panel.getHeight()+30),  644, 145);
						}
						else {
							Tools_RP_wrapper.setBounds(0,  (PC_wrapper.getHeight()+30),  644, 145);
						}
						if(PC_comboBox.getSelectedItem().equals(browserstacklabel)) {
							BrowserStack_panel.setBounds(0, (PC_wrapper.getHeight()+30+ Tools_RP_wrapper.getHeight()+20), 644, 60);
							otherHalf_wrapper.setSize(new Dimension(644, (PC_wrapper.getHeight()+30+Tools_RP_wrapper.getHeight()+20+BrowserStack_panel.getHeight()+30+Browser_Panel.getHeight())));
						}

						else {
							otherHalf_wrapper.setSize(new Dimension(644, (PC_wrapper.getHeight()+30+Tools_RP_wrapper.getHeight()+30+Browser_Panel.getHeight())));
						}
					}
					else {
						Tools_RP_wrapper.setBounds(0,  (PC_wrapper.getHeight()+30),  644, 145);
						otherHalf_wrapper.setSize(new Dimension(644, (PC_wrapper.getHeight()+30+Tools_RP_wrapper.getHeight()+30)));

					}


					TRP_panel.add(CheckCombobox.createcombo(toolsList, RPToolsList,comboboxObj,RPToolsListSelectedValues));
					//            		tools_brandlist_wrapper.setVisible(true);
					//            		tools_environlist_wrapper.setVisible(true);
					tools_selectbrowser_wrapper.setVisible(true);
					RP_Browserpanel.setVisible(true);


				}
				Uploadfile_panel.repaint();
				Uploadfile_panel.revalidate();
				center_panel.setSize(new Dimension(center_panel.getWidth(), (first_half.getHeight() + otherHalf_wrapper.getHeight())));
				contentPane.setPreferredSize(new Dimension(720, (200+ first_half.getHeight() + otherHalf_wrapper.getHeight())));
				otherHalf_wrapper.revalidate();
				center_panel.revalidate();
				contentPane.revalidate();
				validate();
			}
		});

		//Action to be performed if Regression pack browser is selected
		RP_comboBox.addActionListener(new ActionListener() {
			//here your actionPerformed method
			public void actionPerformed(ActionEvent arg0) {
				Browser_Panel.setVisible(false);
				BrowserStack_panel.setVisible(false); //Removing the browser Stack panel
				otherHalf_wrapper.setSize(new Dimension(644, (PC_wrapper.getHeight()+30+Tools_RP_wrapper.getHeight())));

				//Checking if Please select is selected in dropdown
				if(RP_comboBox.getSelectedItem().equals("Please select"))
				{
					if(PageComparison_chckbx.isSelected() ) { 
						if(PC_comboBox.getSelectedItem().equals(localbrowser)||PC_comboBox.getSelectedItem().equals("Please select"))
						{

							otherHalf_wrapper.setSize(new Dimension(644, (PC_wrapper.getHeight()+30+Tools_RP_wrapper.getHeight())));
						}
					} 
					else {
						otherHalf_wrapper.setSize(new Dimension(644, (PC_wrapper.getHeight()+30+Tools_RP_wrapper.getHeight()+30)));
					}
					RP_Browserpanel.removeAll();
					RP_Browserpanel.setVisible(false);
					RP_Browserpanel.revalidate();
				}
				//Checking if Local browser is selected in dropdown
				else if(RP_comboBox.getSelectedItem().equals(localbrowser))
				{

					if(PageComparison_chckbx.isSelected() ) {
						if(PC_comboBox.getSelectedItem().equals(localbrowser)||PC_comboBox.getSelectedItem().equals("Please select"))
						{
							otherHalf_wrapper.setSize(new Dimension(644, (PC_wrapper.getHeight()+30+Tools_RP_wrapper.getHeight())));

						}else if(PC_comboBox.getSelectedItem().equals(emulatorlabel) && PC_comboBox.getSelectedItem().equals(realLabel)) {
							Browser_Panel.setVisible(true);
							Tools_RP_wrapper.setBounds(0,  (PC_wrapper.getHeight()+30+Browser_Panel.getHeight()+30),  644, 145);
							otherHalf_wrapper.setSize(new Dimension(644, (PC_wrapper.getHeight()+30+Tools_RP_wrapper.getHeight()+30+Browser_Panel.getHeight())));
						}
					}
					else {
						Tools_RP_wrapper.setBounds(0,  (PC_wrapper.getHeight()+30),  644, 145);
						otherHalf_wrapper.setSize(new Dimension(644, (PC_wrapper.getHeight()+30+Tools_RP_wrapper.getHeight()+30)));

					}
					RP_Browserpanel.removeAll();//Removing all components 
					RP_Browserpanel.setVisible(true);
					//Adding local browser dropdown
					RP_Browserpanel.add(CheckCombobox.createcombo(localbrowser, LocalBrowsersList, comboboxObj,RPLocalBrowserSelectedValues));
				}
				//Checking if browser stack is selected in dropdown
				else if(RP_comboBox.getSelectedItem().equals(browserstacklabel))
				{
					BrowserStack_panel.setVisible(true);
					if(PageComparison_chckbx.isSelected() ) {
						if(PC_comboBox.getSelectedItem().equals(emulatorlabel) && PC_comboBox.getSelectedItem().equals(realLabel) ) {
							Browser_Panel.setVisible(true);
							BrowserStack_panel.setBounds(0, PC_wrapper.getHeight()+30+ Tools_RP_wrapper.getHeight()+30+Browser_Panel.getHeight()+30, otherHalf_wrapper.getWidth(), 60);
							otherHalf_wrapper.setSize(new Dimension(644, (PC_wrapper.getHeight()+30+Tools_RP_wrapper.getHeight()+30+Browser_Panel.getHeight()+BrowserStack_panel.getHeight()+30)));
						}else {
							BrowserStack_panel.setBounds(0, PC_wrapper.getHeight()+30+ Tools_RP_wrapper.getHeight()+30+Browser_Panel.getHeight()+30, otherHalf_wrapper.getWidth(), 60);
							otherHalf_wrapper.setSize(new Dimension(644, (PC_wrapper.getHeight()+30+Tools_RP_wrapper.getHeight()+30+Browser_Panel.getHeight()+BrowserStack_panel.getHeight()+30)));

						}
					}
					else {
						Tools_RP_wrapper.setBounds(0, PC_wrapper.getHeight()+30, otherHalf_wrapper.getWidth(), 145);
						BrowserStack_panel.setBounds(0, PC_wrapper.getHeight()+30+ Tools_RP_wrapper.getHeight()+30, otherHalf_wrapper.getWidth(), 60);
						otherHalf_wrapper.setSize(new Dimension(644, (PC_wrapper.getHeight()+30+Tools_RP_wrapper.getHeight()+30+BrowserStack_panel.getHeight()+30)));

					}
					RP_Browserpanel.removeAll();
					RP_Browserpanel.setVisible(true);
					//Adding browser stack  dropdown
					RP_Browserpanel.add(CheckCombobox.createcombo(browserstacklabel, BrowserstackList, comboboxObj,RPBrowserStackSelectedValues));

				}

				else if(RP_comboBox.getSelectedItem().equals(emulatorlabel))
				{
					if(!PC_comboBox.getSelectedItem().equals(emulatorlabel) || !PC_comboBox.getSelectedItem().equals(realLabel) ||PC_comboBox.getSelectedItem().equals("Please select"))
					{
						Browser_Panel.setBounds(0, (PC_wrapper.getHeight()+30), otherHalf_wrapper.getWidth(), 160);
						Tools_RP_wrapper.setBounds(0,  (PC_wrapper.getHeight()+30+Browser_Panel.getHeight()+30),  644, 145);
						otherHalf_wrapper.setSize(new Dimension(644, (PC_wrapper.getHeight()+30+Browser_Panel.getHeight()+30+Tools_RP_wrapper.getHeight()+30)));
					}
					Browser_Panel.setVisible(true);
					RP_Browserpanel.removeAll();//Removing all components 
					RP_Browserpanel.setVisible(true);
					RPEmulator_panel.setVisible(true);
					RP_Browserpanel.add(RPEmulator_panel);
					//Adding local browser dropdown
					//	RP_Browserpanel.add(CheckCombobox.createcombo(emulatorlabel, EmulatorList, comboboxObj,PCEmulatorSelectedValues));

				}
				else if(RP_comboBox.getSelectedItem().equals(realLabel))
				{
					if(!PC_comboBox.getSelectedItem().equals(realLabel) || !PC_comboBox.getSelectedItem().equals(emulatorlabel) ||PC_comboBox.getSelectedItem().equals("Please select"))
					{

						Browser_Panel.setBounds(0, (PC_wrapper.getHeight()+30), otherHalf_wrapper.getWidth(), 160);
						Tools_RP_wrapper.setBounds(0,  (PC_wrapper.getHeight()+30+Browser_Panel.getHeight()+30),  644, 145);
						otherHalf_wrapper.setSize(new Dimension(644, (PC_wrapper.getHeight()+30+Browser_Panel.getHeight()+30+Tools_RP_wrapper.getHeight()+30)));
					}
					Browser_Panel.setVisible(true);
					RP_Browserpanel.removeAll();//Removing all components 
					RP_Browserpanel.setVisible(true);
					RPRealdevice_panel.setVisible(true);
					RP_Browserpanel.add(RPRealdevice_panel);
					//Adding local browser dropdown
					//	RP_Browserpanel.add(CheckCombobox.createcombo(realLabel, RealList, comboboxObj,PCRealSelectedValues));
				}
				if(PageComparison_chckbx.isSelected() && PC_comboBox.getSelectedItem().equals(emulatorlabel) && PC_comboBox.getSelectedItem().equals(realLabel) ) {
					Browser_Panel.setVisible(true);
				}
				center_panel.setSize(new Dimension(center_panel.getWidth(), (first_half.getHeight() + otherHalf_wrapper.getHeight())));
				contentPane.setPreferredSize(new Dimension(720, (200+ first_half.getHeight() + otherHalf_wrapper.getHeight())));
				center_panel.revalidate();
				contentPane.revalidate();
				validate();
			}
		});

		//Start Execution Listener
		StartExecution_btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				toolscount = 0;
				radiobtncount = 0;
				txtfieldEmpty=0;
				rangefieldempty=0;
				spCharfieldempty=0;
				RT_rangeEmpty=0;
				fileEmpty=0;
				ToolSelected="";
				tasksSelected="";
				suitecount=0; // chckbxShowTools_healthchecks is selected or not
				PCdropdownEmpty=0;
				ToolsdropdownEmpty=0; // for tools checkcombo's are not selected
				//RPcheckboxEmpty=0; is nothing but RP_tools
				signinEmpty=0;
				regressionPackSelection="";
				RP_tool_comboBoxisEmpty =0;
				NewRegresPackisEmpty = 0;
				VDeviceName = 0;
				VPlatformVersion = 0;

				//Checking which checkbox and radiobutton is selected 
				radioBtnValidation(BrokenLinks_chckbx, BLbuttonGroup);
				radioBtnValidation(SpecialCharacters_chckbx, SCbuttonGroup);
				radioBtnValidation(AssetsSizes_chckbx, ASbuttonGroup);
				radioBtnValidation(MissingAssets_chckbx,MAbuttonGroup);

				if (SpecialCharacters_chckbx.isSelected() && spCharInput.getText().equals("")) {
					spCharfieldempty =1;
				}
				if (PageComparison_chckbx.isSelected()) {
					if(BrowserStack_panel.isVisible()){

						if(BrowserStackEmail_txtfield.getText().equals("")||BrowserStackPswd_txtfield.getText().equals(""))
							signinEmpty++;
						else
							signinEmpty=0;
					}
					if(PC_comboBox.getSelectedItem().equals("Please select"))	
						PCdropdownEmpty++;
					else if(PC_comboBox.getSelectedItem().equals(localbrowser)&&PCLocalBrowserSelectedValues.isEmpty())
						PCdropdownEmpty++;
					else if(PC_comboBox.getSelectedIndex()==2 &&PCBrowserStackSelectedValues.isEmpty())	
						PCdropdownEmpty++;
					//					else if(PC_comboBox.getSelectedItem().equals(emulatorlabel) &&PCEmulatorSelectedValues.isEmpty())
					//						PCdropdownEmpty++;
					//					else if(PC_comboBox.getSelectedItem().equals(realLabel) &&PCRealSelectedValues.isEmpty())
					//						PCdropdownEmpty++;
					else		
						radiobtncount++;
				}
				if(AssetsSizes_chckbx.isSelected() && (rangeMax.getText().isEmpty())){
					rangefieldempty=1;
				}
				if(ResponseTime_chckbx.isSelected()) { 
					suitecount=1;
					if(RT_range_field.getText().equals("")) {
						RT_rangeEmpty=1;
					}
				}
				if(BLDomain_rdbtn.isSelected() &&BLDomain_txtField.getText().isEmpty()) {
					txtfieldEmpty=1;
				}else if(SCDomain_rdbtn.isSelected()&& SCDomain_txtField.getText().isEmpty())
					txtfieldEmpty=1;
				else if(MADomain_rdbtn.isSelected()){
					if(MADomain_prodtxtField.getText().isEmpty()||MADomain_testtxtField.getText().isEmpty())
						txtfieldEmpty=1;
				}else if(ASDomain_rdbtn.isSelected() && ASDomain_txtField.getText().isEmpty()) {
					txtfieldEmpty=1;
				}else{
					txtfieldEmpty=0;
				}	

				if((PC_label.isEnabled()) || BLInput_rdbtn.isSelected()||SCInput_rdbtn.isSelected()||MAInput_rdbtn.isSelected()||ASInput_rdbtn.isSelected())
				{

					if(uploadfile_txtfield.getText().equals(""))
						fileEmpty=1;				
				}
				
				for (JCheckBox checkBox : checkBoxes) {
					if (checkBox.isSelected()) {
						ToolSelected += checkBox.getActionCommand() + ",";
						tasksSelected+=checkBox.getText()+ ",";
						toolscount++;	
						i++;
					}
				}
				if(chckbxShowTools_healthchecks.isSelected()) {
					suitecount=1;
					if(RP_tool_comboBox.getSelectedIndex()==0) {
						RP_tool_comboBoxisEmpty++;
						NewRegresPackisEmpty++;
					}else if(RP_tool_comboBox.getSelectedIndex()==1 ) { //Tools Selected
						if(RPToolsListSelectedValues.contains("Sample Test")) {
							tasksSelected+= "SampleTest,";
							ToolSelected += "SampleTest,";
						}

					}else if(RP_tool_comboBox.getSelectedIndex()==2) { 
						ToolSelected+="RPTools,";
						if(RPBrandSelectedValues.isEmpty()||RPEnvSelectedValues.isEmpty()||RPToolsListSelectedValues.isEmpty())
							ToolsdropdownEmpty++;
						else if(RP_comboBox.getSelectedIndex()==0)
							ToolsdropdownEmpty++;
						else if((RP_comboBox.getSelectedIndex()==1 && RPLocalBrowserSelectedValues.isEmpty())||(RP_comboBox.getSelectedIndex()==2 && RPBrowserStackSelectedValues.isEmpty()))
						{
							ToolsdropdownEmpty++;
						}
						if(RP_comboBox.getSelectedIndex()==2 && BrowserStack_panel.isVisible()){										
							if(BrowserStackEmail_txtfield.getText().equals("")||BrowserStackPswd_txtfield.getText().equals(""))
								signinEmpty++;
							else
								signinEmpty=0;		

						}
						
//						if(RP_comboBox.getSelectedIndex()==3 || RP_comboBox.getSelectedIndex()==4) {
//							if(Browserdn_txtfield.getText()!=null && Browserpv_txtfield.getText()!=null) {
//								VDeviceName++;
//								VPlatformVersion++;
//							}
//							
//						}
//						
//						if(PC_comboBox.getSelectedIndex()==3 || PC_comboBox.getSelectedIndex()==4) {
//							if(Browserdn_txtfield.getText()!=null && Browserpv_txtfield.getText()!=null) {
//								VDeviceName++;
//								VPlatformVersion++;
//							}
//						}

					}
				}

				if (((toolscount != 0 && toolscount == radiobtncount &&txtfieldEmpty==0 && rangefieldempty==0 )||suitecount==1) && 
						RP_tool_comboBoxisEmpty==0 && fileEmpty==0  && signinEmpty==0 && ToolsdropdownEmpty==0 
						&& NewRegresPackisEmpty==0 && RT_rangeEmpty==0 ) {


					try {
						System.out.println("********* Property file creation started *********");
						Properties prop = new Properties();
						OutputStream output = null;
						try {

							output = new FileOutputStream("testConfig.properties");

							// set the properties value
							int i=0;
							prop.setProperty("TasksSelected",ToolSelected);
							if (PageComparison_chckbx.isSelected()) {

								prop.setProperty("PageComparison-Browser", PC_comboBox.getSelectedItem().toString());
								if(PC_comboBox.getSelectedItem().equals(localbrowser))
								{
									prop.setProperty("PageComparison-Browser-Local", arrayToString(PCLocalBrowserSelectedValues));
								}
								else if(PC_comboBox.getSelectedItem().equals(browserstacklabel))
								{
									prop.setProperty("PageComparison-BrowserStack", arrayToString(PCBrowserStackSelectedValues));
									prop.setProperty("BrowserStack-Username",BrowserStackEmail_txtfield.getText());
									prop.setProperty("BrowserStack-Password", BrowserStackPswd_txtfield.getText());
								}
								else if(PC_comboBox.getSelectedItem().equals(emulatorlabel)) {
									if(PCEmulator_comboBox.getSelectedItem().equals("Emulator Browser-Android")) {
										prop.setProperty("PageComparison-Emulator", "Emulator Browser-Android");
									}else if(PCEmulator_comboBox.getSelectedItem().equals("Emulator App-Android")) {
										prop.setProperty("PageComparison-Emulator", "Emulator App-Android");
									}else if(PCEmulator_comboBox.getSelectedItem().equals("Emulator Browser-iOS")) {
										prop.setProperty("PageComparison-Emulator", "Emulator Browser-iOS");
									}else if(PCEmulator_comboBox.getSelectedItem().equals("Emulator App-iOS")) {
										prop.setProperty("PageComparison-Emulator", "Emulator App-iOS");
									}
									prop.setProperty("Device Name", Browserdn_txtfield.getText());
									prop.setProperty("Platform Version", Browserpv_txtfield.getText());
									prop.setProperty("Driver Path", Browserdp_txtfield.getText());
									prop.setProperty("App Name", Browseran_txtfield.getText());
									//prop.setProperty("PageComparison-Emulator", arrayToString(PCEmulatorSelectedValues));
								}
								else if(PC_comboBox.getSelectedItem().equals(realLabel)) {
									if(PCRealDevice_comboBox.getSelectedItem().equals("Real Browser-Android")) {
										prop.setProperty("PageComparison-Real-Device", "Real Browser-Android");
									}else if(PCRealDevice_comboBox.getSelectedItem().equals("Real App-Android")) {
										prop.setProperty("PageComparison-Real-Device", "Real App-Android");
									}else if(PCRealDevice_comboBox.getSelectedItem().equals("Real Browser-iOS")) {
										prop.setProperty("PageComparison-Real-Device", "Real Browser-iOS");
									}else if(PCRealDevice_comboBox.getSelectedItem().equals("Real App-iOS")) {
										prop.setProperty("PageComparison-Real-Device", "Real App-iOS");
									}
									prop.setProperty("Device Name", Browserdn_txtfield.getText());
									prop.setProperty("Platform Version", Browserpv_txtfield.getText());
									prop.setProperty("Driver Path", Browserdp_txtfield.getText());
									prop.setProperty("App Name", Browseran_txtfield.getText());
									//prop.setProperty("PageComparison-Real-Device", arrayToString(PCRealSelectedValues));
								}
							}
							if (ResponseTime_chckbx.isSelected()) {
								prop.setProperty("ResponseTime", RT_range_field.getText());
							}
							if (SpecialCharacters_chckbx.isSelected()) {
								prop.setProperty("SpecialCharacters-TextField", spCharInput.getText());
							}
							for (JRadioButton radiobtn : radiobuttons) {
								if (radiobtn.isSelected()) {
									radiobuttonSelected += radiobtn.getActionCommand() + ",";
									radiobuttonselected=radiobtn.getActionCommand();

									if(radiobuttonselected.equals("BrokenLinks_Input"))
									{
										prop.setProperty("BrokenLinks-Input", "BrokenLinks_FileInput");
									}
									else if(radiobuttonselected.equals("BrokenLinks_Domain"))
									{
										prop.setProperty("BrokenLinks-Input", "BrokenLinks_Domain");
										prop.setProperty("BrokenLinks-Domain",BLDomain_txtField.getText());

									}
									else if(radiobuttonselected.equals("SpecialCharacters_Input"))
									{
										prop.setProperty("SpecialCharacters-Input", "SpecialCharacter_FileInput");
									}
									else if(radiobuttonselected.equals("SpecialCharacters_Domain"))
									{
										prop.setProperty("SpecialCharacters-Input", "SpecialCharacters_Domain");
										prop.setProperty("SpecialCharacters-Domain",SCDomain_txtField.getText());
									}
									else if(radiobuttonselected.equals("MissingAssets_Input"))
									{
										prop.setProperty("MissingAssets-Input", "MissingAssets_FileInput");
									}
									else if(radiobuttonselected.equals("MissingAssets_Domain"))
									{
										prop.setProperty("MissingAssets-Input", "MissingAssets_Domain");
										prop.setProperty("MissingAssets-Domain","MissingAssets_Test,MissingAssets_Prod");
										prop.setProperty("MissingAssets-Test-URl", MADomain_testtxtField.getText());
										prop.setProperty("MissingAssets-Prod-URl", MADomain_prodtxtField.getText());
									}
									else if(radiobuttonselected.equals("AssetSizes_Input"))
									{
										prop.setProperty("AssetSizes-Input", "AssetSizes_FileInput");
										prop.setProperty("Red", rangeMax.getText());
										if(rangeMin.getText().isEmpty()){
											prop.setProperty("Amber", "0");
										}else{
											prop.setProperty("Amber", rangeMin.getText());
										}
									}
									else if(radiobuttonselected.equals("AssetSizes_Domain"))
									{
										prop.setProperty("AssetSizes-Input", "AssetSizes_Domain");
										prop.setProperty("AssetSizes_Domain",ASDomain_txtField.getText());
										prop.setProperty("Red", rangeMax.getText());
										if(rangeMin.getText().isEmpty()){
											prop.setProperty("Amber", "0");
										}else{
											prop.setProperty("Amber", rangeMin.getText());
										}
									}
								}
							}
							if(RP_tool_comboBox.getSelectedIndex()==1) {
								prop.setProperty("P2-RegressionPack-Tools", arrayToString(RPToolsListSelectedValues));
								//								prop.setProperty("P2-RegressionPack-Brand", arrayToString(RPBrandSelectedValues));
								//								prop.setProperty("P2-RegressionPack-Environment", arrayToString(RPEnvSelectedValues));
								prop.setProperty("P2-RegressionPack-Browser", RP_comboBox.getSelectedItem().toString());

								if(RP_comboBox.getSelectedItem().equals(localbrowser))
								{
									prop.setProperty("P2-RegressionPack-Browser", localbrowser);
									prop.setProperty("P2-RegressionPack-Browser-Local",arrayToString(RPLocalBrowserSelectedValues));
								}
								else if(RP_comboBox.getSelectedItem().equals(browserstacklabel))
								{
									prop.setProperty("P2-RegressionPack-Browser", browserstacklabel);
									prop.setProperty("P2-RegressionPack-Browser-Local", arrayToString(RPBrowserStackSelectedValues));
									prop.setProperty("BrowserStack-Username", BrowserStackEmail_txtfield.getText());
									prop.setProperty("BrowserStack-Password", BrowserStackPswd_txtfield.getText());
								}
								else if(RP_comboBox.getSelectedItem().equals(emulatorlabel))
								{
									prop.setProperty("P2-RegressionPack-Browser", emulatorlabel);
									if(RPEmulator_comboBox.getSelectedItem().equals("Emulator Browser-Android")) {
										prop.setProperty("P2-RegressionPack-Browser-Local", "Emulator Browser-Android");
									}else if(RPEmulator_comboBox.getSelectedItem().equals("Emulator App-Android")) {
										prop.setProperty("P2-RegressionPack-Browser-Local", "Emulator App-Android");
									}else if(RPEmulator_comboBox.getSelectedItem().equals("Emulator Browser-iOS")) {
										prop.setProperty("P2-RegressionPack-Browser-Local", "Emulator Browser-iOS");
									}else if(RPEmulator_comboBox.getSelectedItem().equals("Emulator App-iOS")) {
										prop.setProperty("P2-RegressionPack-Browser-Local", "Emulator App-iOS");
									}
									prop.setProperty("Device Name", Browserdn_txtfield.getText());
									prop.setProperty("Platform Version", Browserpv_txtfield.getText());
									prop.setProperty("Driver Path", Browserdp_txtfield.getText());
									prop.setProperty("App Name", Browseran_txtfield.getText());
									//prop.setProperty("P2-RegressionPack-Browser-Local",arrayToString(PCEmulatorSelectedValues));
								}
								else if(RP_comboBox.getSelectedItem().equals(realLabel))
								{
									prop.setProperty("P2-RegressionPack-Browser", emulatorlabel);
									if(RPRealDevice_comboBox.getSelectedItem().equals("Real Browser-Android")) {
										prop.setProperty("P2-RegressionPack-Browser-Local", "Real Browser-Android");
									}else if(RPRealDevice_comboBox.getSelectedItem().equals("Real App-Android")) {
										prop.setProperty("P2-RegressionPack-Browser-Local", "Real App-Android");
									}else if(RPRealDevice_comboBox.getSelectedItem().equals("Real Browser-iOS")) {
										prop.setProperty("P2-RegressionPack-Browser-Local", "Real Browser-iOS");
									}else if(RPRealDevice_comboBox.getSelectedItem().equals("Real App-iOS")) {
										prop.setProperty("P2-RegressionPack-Browser-Local", "Real App-iOS");
									}
									prop.setProperty("Device Name", Browserdn_txtfield.getText());
									prop.setProperty("Platform Version", Browserpv_txtfield.getText());
									prop.setProperty("Driver Path", Browserdp_txtfield.getText());
									prop.setProperty("App Name", Browseran_txtfield.getText());
									//prop.setProperty("P2-RegressionPack-Browser-Local",arrayToString(PCRealSelectedValues));
								}

							}

							prop.store(output, null);

							System.out.println("** File is created **");

						} catch (IOException io) {
							io.printStackTrace();
						} finally {
							if (output != null) {
								try {
									output.close();
								} catch (IOException e1) {
									e1.printStackTrace();

								}
							}
							System.out.println("********* Property file writing ended *********");

						}
					}catch (Exception e) {
						e.printStackTrace();
					}

					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								System.out.println("TasksSelected : "+tasksSelected);
								Globalvariables.clearvalues();
								XmlGenerate.generateXml();
								Globalvariables.main(null);
								Results2.main(null);

							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				}

				else if(PCdropdownEmpty!=0){
					JOptionPane.showMessageDialog(contentPane, "please select the browser values for Page comparison");
					/*StartExecution_btn.kSelectedColor = new Color(107, 142, 35);
					StartExecution_btn.setkSelectedColor(new Color(107, 142, 35));*/
				}
				else if((toolscount!=radiobtncount||toolscount==0 )&&(suitecount==0 )){
					JOptionPane.showMessageDialog(contentPane, "Please select atleast  1 Checkbox");
				}else if(spCharfieldempty==1){
					JOptionPane.showMessageDialog(contentPane, "Please give input for Special Characters");	
				}
				else if(txtfieldEmpty==1){
					JOptionPane.showMessageDialog(contentPane, "Please give input for domain");	
				}
				else if(rangefieldempty==1){
					JOptionPane.showMessageDialog(contentPane, "Please provide Max value for Asset Sizes");	
				}else if(RT_rangeEmpty!=0){
					JOptionPane.showMessageDialog(contentPane, "Please provide Threshold value for Response Time");
				}
				else if(RP_tool_comboBoxisEmpty!=0){
					JOptionPane.showMessageDialog(contentPane, "Please select Regression Pack or Tools");
				}
				else if(NewRegresPackisEmpty!=0){
					JOptionPane.showMessageDialog(contentPane, "Please select atleast 1 value in Regression Pack");
				}
				/*else if(RPcheckboxEmpty!=0)
					JOptionPane.showMessageDialog(contentPane, "Please select atleast 1 checkbox for Regression Pack");*/

				else if(ToolsdropdownEmpty!=0){
					JOptionPane.showMessageDialog(contentPane,"Please provide all input values for Tools" );
				}
				else if(signinEmpty!=0){
					JOptionPane.showMessageDialog(contentPane, "Please provide the Browser Stack sign in details");
				}
				else if(fileEmpty==1){
					JOptionPane.showMessageDialog(contentPane, "Please provide the Input file");
				}else if(PC_comboBox.getSelectedItem().equals(realLabel) || PC_comboBox.getSelectedItem().equals(emulatorlabel)
						|| RP_comboBox.getSelectedItem().equals(realLabel)|| RP_comboBox.getSelectedItem().equals(emulatorlabel)) {
					if(Browserdn_txtfield.getText().isEmpty() || Browserpv_txtfield.getText().isEmpty()) {
						JOptionPane.showMessageDialog(contentPane, "Please provide the Device details");
					}
				}

			}


		});



		JFileChooser chooser = new JFileChooser();
		JFileChooser templatechooser = new JFileChooser();
		File selectedFile = new File("inputdata");

		Template_label.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent arg0) {

				FileNameExtensionFilter filter = new FileNameExtensionFilter("*xlsx", "xlsx");
				templatechooser.addChoosableFileFilter(filter);
				templatechooser.setAcceptAllFileFilterUsed(false);

				templatechooser.setSelectedFile(selectedFile);

				int returnVal = templatechooser.showSaveDialog(contentPane);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					try {
						Runtime.getRuntime().exec("cmd /c taskkill /f /im excel.exe");
						FileInputStream Fread = new FileInputStream(
								System.getProperty("user.dir") + "\\sampleTemplate.xlsx");

						String filename = templatechooser.getSelectedFile().toString();
						String extension = ".xlsx";
						if (filename.toString().contains(extension)) {
							filename = templatechooser.getSelectedFile().toString();
						} else {
							filename = templatechooser.getSelectedFile().toString() + ".xlsx";
						}
						FileOutputStream Fwrite = new FileOutputStream(filename);
						System.out.println("File is Copied to : " + templatechooser.getSelectedFile());
						int c;
						while ((c = Fread.read()) != -1)
							Fwrite.write((char) c);
						Fread.close();
						Fwrite.close();

					} catch (FileNotFoundException ex) {
						ex.printStackTrace();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
				templatechooser.removeChoosableFileFilter(filter);

			}

			@Override
			public void mouseEntered(java.awt.event.MouseEvent arg0) {

			}

			@Override
			public void mouseExited(java.awt.event.MouseEvent arg0) {

			}

			@Override
			public void mousePressed(java.awt.event.MouseEvent arg0) {

			}

			@Override
			public void mouseReleased(java.awt.event.MouseEvent arg0) {

			}

		});
		/*lblAdditionalDetails.addMouseListener(new MouseAdapter() {

			public void mouseClicked(java.awt.event.MouseEvent arg0) {

				 	JPanel panel = new JPanel();
				    panel.setBackground(Color.BLUE);
				    panel.setMinimumSize(new Dimension(200,200));

				JOptionPane.showMessageDialog(null, panel);

			}


		});*/
		Upload_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					Runtime.getRuntime().exec("cmd /c taskkill /f /im excel.exe");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				message_label.setText("");
				chooser.setPreferredSize(new Dimension(600, 400));
				FileNameExtensionFilter filter = new FileNameExtensionFilter("*xlsx", "xlsx");
				chooser.addChoosableFileFilter(filter);
				chooser.setAcceptAllFileFilterUsed(false);
				chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
				System.out.println("user is in " + System.getProperty("user.dir"));
				int returnVal = chooser.showOpenDialog(contentPane);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());

					uploadfile_txtfield.setText(chooser.getSelectedFile().getName());
					contentPane.setVisible(true);
					chooser.removeChoosableFileFilter(filter);
					filepath = chooser.getSelectedFile().toString();
				} else {
					System.out.println("File Chooser was cancelled");
					uploadfile_txtfield.setText(null);
					// btnNewButton.setEnabled(false);

				}
				if (filepath == null) {
					System.out.println("I got no file name!!");
				}

				try {
					if (filepath != null) {
						FileInputStream Fread = new FileInputStream(chooser.getSelectedFile().getAbsolutePath());
						System.out.println("in upload button");
						FileOutputStream Fwrite = new FileOutputStream(".\\InputData\\Testinputfile.xlsx");
						int c;
						while ((c = Fread.read()) != -1)
							Fwrite.write((char) c);
						if (Fread.read() == -1) {
							message_label.setText("File uploaded successfully");
							message_label.setVisible(true);
							Uploadfile_panel.add(message_label);
							// btnNewButton.setEnabled(true);
							//contentPane.setVisible(true);
						}

						Fread.close();
						Fwrite.close();
						filepath = null;
					} else {
						message_label.setText(null);
						Uploadfile_panel.add(message_label);
						/*
						 * message_label.setForeground(new Color(0, 100, 0)); message_label.setFont(new
						 * Font("Tahoma", Font.PLAIN, 18));
						 * 
						 */
						//contentPane.setVisible(true);
						JOptionPane.showMessageDialog(null, "please select the input file");

					}

					// JOptionPane.showMessageDialog(null, "please choose a file to upload");
				} catch (FileNotFoundException ex) {
					ex.printStackTrace();
				} catch (IOException ex) {
					ex.printStackTrace();
				} catch (NullPointerException ex) {
					ex.printStackTrace();
				}

			}

		});
		lblAdditionalDetails.addMouseListener(new MouseAdapter() {

			public void mouseClicked(java.awt.event.MouseEvent arg0) {

				AdditionalDetails.main(null);
			}
		});
		contentPane.setSize(new Dimension(720, (200+ first_half.getHeight() + otherHalf_wrapper.getHeight())));
		contentPane.setPreferredSize(new Dimension(720, (200+ first_half.getHeight() + otherHalf_wrapper.getHeight())));
		gradientPanel.setSize(new Dimension(contentPane.getWidth(), contentPane.getHeight()));

		return contentPane;
	}// end of initUI


	public ButtonGroup createButtonGroup(JRadioButton radiobtn1, JRadioButton radiobtn2, String text1, String text2) {
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(radiobtn1);
		radiobtn1.setActionCommand(text1);
		buttonGroup.add(radiobtn2);
		radiobtn2.setActionCommand(text2);
		return buttonGroup;
	}

	public ButtonGroup createButtonGroup(JRadioButton radiobtn1, JRadioButton radiobtn2, JRadioButton radiobtn3,
			String text1, String text2, String text3) {
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(radiobtn1);
		radiobtn1.setActionCommand(text1);
		buttonGroup.add(radiobtn2);
		radiobtn2.setActionCommand(text2);
		buttonGroup.add(radiobtn3);
		radiobtn3.setActionCommand(text3);
		return buttonGroup;
	}

	public boolean getSelectedRadioButton(ButtonGroup buttonGroup) {

		Enumeration<AbstractButton> abstractButtons = buttonGroup.getElements();
		JRadioButton radioButton = null;

		while (abstractButtons.hasMoreElements()) {
			radioButton = (JRadioButton) abstractButtons.nextElement();
			if (radioButton.isSelected()) {
				System.out.println("radiobutton selected is "+radioButton.getActionCommand());
				return true;
			}
		}
		return false;
	}
	public void radioBtnValidation(JCheckBox checkBox, ButtonGroup buttongroup) {
		if (checkBox.isSelected() && getSelectedRadioButton(buttongroup)) {
			System.out.println(checkBox.getText());
			radiobtncount++;

		}

	}
	public String arrayToString(Set<String> dropdownSelectedValues)
	{
		String[] selectedValues = new String[dropdownSelectedValues.size()];
		String dropdownValues="";
		// copy elements from set to string array
		int x = 0;
		for (String s: dropdownSelectedValues)
		{
			selectedValues[x++] = s;
			dropdownValues+=s+",";
		}
		return dropdownValues;
	}
}
