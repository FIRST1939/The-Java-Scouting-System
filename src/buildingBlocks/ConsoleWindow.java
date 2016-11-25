package buildingBlocks;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * This class constructs the ConsoleWindow ui and diverts all outputs from the system console to this ui.
 * @author Grayson Spidle
 *
 */
public class ConsoleWindow extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 5144300131763756053L;
	
	private Font font = new Font("Tahoma", Font.PLAIN, 15);
	
	public JScrollPane scroll;
	public JTextArea area;
	public JMenuBar menuBar; 
	public JMenu menuTools;
	public JMenuItem itemClear;
	
	public PrintStream out;
	public PrintStream err;

	/**
	 * The constructor. Initializing this constructor automatically diverts all outputs from the system console to this ui.
	 */
	public ConsoleWindow() {
		
		area = new JTextArea();
		area.setEditable(false);
		area.setFont(font);
		
		scroll = new JScrollPane();
		scroll.setViewportView(area);
		
		itemClear = new JMenuItem("Clear");
		itemClear.setActionCommand("clear");
		itemClear.addActionListener(this);
		
		menuTools = new JMenu("Tools");
		menuTools.add(itemClear);
		
		menuBar = new JMenuBar();
		menuBar.add(menuTools);
		
		out = new PrintStream(new OutputStream() {

			@Override
			public void write(int b) throws IOException {
				char c = (char) b;
				ConsoleWindow.this.actionPerformed(new ActionEvent(c, ActionEvent.ACTION_PERFORMED, "write"));
			}
			
		});
		
		err = new PrintStream(new OutputStream() {

			@Override
			public void write(int b) throws IOException {
				char c = (char) b;
				ConsoleWindow.this.actionPerformed(new ActionEvent(c, ActionEvent.ACTION_PERFORMED, "write"));
			}
			
		});
		
		System.setOut(out);
		System.setErr(err);
		
		this.add(scroll);
		this.setSize(250, 350);
		this.setTitle("System Console");
		this.setJMenuBar(menuBar);
		this.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {}
			
			@Override
			public void windowIconified(WindowEvent e) {}
			
			@Override
			public void windowDeiconified(WindowEvent e) {}
			
			@Override
			public void windowDeactivated(WindowEvent e) {}
			
			@Override
			public void windowClosing(WindowEvent e) {
				ConsoleWindow.this.setVisible(false);
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				ConsoleWindow.this.setVisible(false);
			}
			
			@Override
			public void windowActivated(WindowEvent e) {}
		});

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("write")) {
			char c = (char) e.getSource();
			area.setText(area.getText() + c);
		}
		else if (e.getActionCommand().equals("clear")) {
			area.setText("");
		}
	}
	
	@Override
	public void setFont(Font f) {
		font = f;
		area.setFont(font);
	}
 
}