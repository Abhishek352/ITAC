package global_suite;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JComboBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Color;

public class CheckCombobox1 extends JFrame {

	private static final long serialVersionUID = 1L;
	static JPanel buttonPanel;
	static CheckCombo checkCombo;
	ArrayList<String> mylist = new ArrayList<String>();

	public CheckCombobox1() {

	}

	public static JPanel createcombo(String labelname, ArrayList<String> options, CheckCombobox1 obj,
			Set<String> dropdownSelectedValues) {

		JLabel label = new JLabel(labelname,JLabel.LEFT);
		label.setForeground(Color.BLACK);
		checkCombo = obj.new CheckCombo(dropdownSelectedValues,options);
		
		JPanel cbpanel = new JPanel();
		cbpanel.setLayout(new BoxLayout(cbpanel, BoxLayout.Y_AXIS));
		cbpanel.setBackground(ETAC.color);
		cbpanel.add(label);
		cbpanel.setOpaque(false);

		for (int i = 0; i < options.size(); i++) {
			CheckComboStore ccs;
			if (i == 0)
				ccs = obj.new CheckComboStore(options.get(0), true);
			else
				ccs = obj.new CheckComboStore(options.get(i), false);
			checkCombo.setBackground(ETAC.color);
			checkCombo.addItem(ccs);
			
			cbpanel.add(checkCombo);// adding check combobox to panel
		}

		return cbpanel;
	}


	public class CheckCombo extends JComboBox {
		CheckComboRenderer renderer;
		CheckComboStore store;
		ActionListener listener;
		Set<String> dropdownSelectedValues;
		ArrayList<String> optionsListValues;

		public CheckCombo()
		{

		}
		public CheckCombo(Set<String> dropdownSelectedValues,ArrayList<String> optionsList) {
			this.dropdownSelectedValues = dropdownSelectedValues;
			this.optionsListValues=optionsList;
			renderer = new CheckComboRenderer();
			setRenderer(renderer);
			addActionListener(this);

		}
		public void actionPerformed(ActionEvent e) {

			store = (CheckComboStore) getSelectedItem();


			store.state = !store.state;	

			if (store.state) {
				if(store.id.equals("Select All"))
				{

					optionsListValues.remove("Select All");
					dropdownSelectedValues.addAll(optionsListValues);	

					JComboBox cb = (JComboBox) e.getSource();
					CheckComboStore store = (CheckComboStore) cb.getSelectedItem();
					CheckComboRenderer ccr = (CheckComboRenderer) cb.getRenderer();

					ccr.checkBox.setSelected((store.state = !store.state));

					for (int i = 0; i <=optionsListValues.size(); i++){

						//((CheckComboStore) getItemAt(i)).state = ccr.checkBox.isSelected();
						((CheckComboStore) getItemAt(i)).state=true;
						revalidate();
						validate();
						repaint();

					}

				}
				else
					dropdownSelectedValues.add(store.id);
			}
			else {
				dropdownSelectedValues.remove(store.id);
				//dropdownSelectedValues.addAll(optionsListValues);
			}
			if(store.id.equals("Select All")&& !store.state)
			{

				optionsListValues.remove("Select All");
				dropdownSelectedValues.removeAll(optionsListValues);	

				JComboBox cb = (JComboBox) e.getSource();
				CheckComboStore store = (CheckComboStore) cb.getSelectedItem();
				CheckComboRenderer ccr = (CheckComboRenderer) cb.getRenderer();

				ccr.checkBox.setSelected((store.state = !store.state));
				//ccr.checkBox.setSelected((store.state));

				for (int i = 0; i <=optionsListValues.size(); i++){
					System.out.println(i);
					//System.out.println(((CheckComboStore) getItemAt(i)).state);

					((CheckComboStore) getItemAt(i)).state=false;

					revalidate();
					validate();
					repaint();
				}

			}
			repaint();

		}
		public void setPopupVisible(boolean flag) {
		}

	}

	public class CheckComboRenderer implements ListCellRenderer {
		JCheckBox checkBox;

		public CheckComboRenderer() {
			checkBox = new JCheckBox();
		}

		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {

			CheckComboStore store = (CheckComboStore) value;
			checkBox.setText(store.id);
			checkBox.setSelected(((Boolean) store.state).booleanValue());
			checkBox.setBackground(isSelected ? Color.red : Color.white);
			checkBox.setForeground(isSelected ? Color.white : Color.black);
			return checkBox;
		}
	}

	public class CheckComboStore {
		String id;
		Boolean state;

		public CheckComboStore(String id, Boolean state) {
			this.id = id;
			this.state = state;
		}
	}
}
