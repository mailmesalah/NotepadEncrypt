package notepaded;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;

import com.sun.org.apache.xalan.internal.xsltc.dom.AbsoluteIterator;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.CardLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class JNotepad extends JFrame {

	private JPanel	contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JNotepad frame = new JNotepad();
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
	public JNotepad() {		
		setTitle("Notepad E - New Document");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 574, 483);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JButton btnNew = new JButton("New");
		panel.add(btnNew);
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea.setText("");
				JNotepad.this.setTitle("Notepad E - New Document");
			}
		});
		
		JButton btnOpen = new JButton("Open");
		panel.add(btnOpen);
		
		JButton btnSave = new JButton("Save");
		panel.add(btnSave);
		
		JButton btnEncryptSave = new JButton("Encrypt & Save");
		panel.add(btnEncryptSave);
		btnEncryptSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(textArea.getText().trim().length()>0){
					String password=JOptionPane.showInputDialog(null, "Password");
					if((password)!=null){
				JFileChooser chooser = new JFileChooser();
				chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle("Save File");				
				chooser.setFileFilter(new FileFilter() {
					
					@Override
					public String getDescription() {
						
						return "*.ne";
					}
					
					@Override
					public boolean accept(File f) {							
						return f.getName().lastIndexOf(".ne")==(f.getName().length()-3) || f.isDirectory();
					}
				});
				
				// disable the "All files" option.
				chooser.setAcceptAllFileFilterUsed(false);
				if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
					String fileName=chooser.getSelectedFile().getAbsoluteFile()+(chooser.getSelectedFile().getAbsoluteFile().toString().lastIndexOf(".ne")>-1?"":".ne");
					File file = new File(fileName);
					if (file != null) {					
						Notepad np = new Notepad();
						np.isEncrypted=true;
												
						try {
							String data=AES256.encrypt(password,textArea.getText().toCharArray());							
							np.setData(data);
						} catch (Exception e) {					
							e.printStackTrace();
						}
						FileOpenSave.saveNotepad(np, file);
						JNotepad.this.setTitle("Notepad E - "+file.getName());
						JOptionPane.showMessageDialog(null, "Successfully Encrypted & Saved.");
					
					}
				}
				}
				}
			}
		});
		
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(textArea.getText().trim().length()>0){
				JFileChooser chooser = new JFileChooser();
				chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle("Save File");				
				chooser.setFileFilter(new FileFilter() {
					
					@Override
					public String getDescription() {
						
						return "*.ne";
					}
					
					@Override
					public boolean accept(File f) {							
						return f.getName().lastIndexOf(".ne")==(f.getName().length()-3) || f.isDirectory();
					}
				});
				
				// disable the "All files" option.
				chooser.setAcceptAllFileFilterUsed(false);
				if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
					String fileName=chooser.getSelectedFile().getAbsoluteFile()+(chooser.getSelectedFile().getAbsoluteFile().toString().lastIndexOf(".ne")>-1?"":".ne");
					File file = new File(fileName);
					if (file != null) {						
						Notepad np = new Notepad();
						np.isEncrypted=false;
						np.setData(textArea.getText());
						FileOpenSave.saveNotepad(np, file);
						JNotepad.this.setTitle("Notepad E - "+file.getName());
						JOptionPane.showMessageDialog(null, "Successfully Saved.");
					}
				}
			}
			}
		});
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
					JFileChooser chooser = new JFileChooser();
					chooser = new JFileChooser();
					chooser.setCurrentDirectory(new java.io.File("."));
					chooser.setDialogTitle("Open File");					
					chooser.setFileFilter(new FileFilter() {
						
						@Override
						public String getDescription() {
							
							return "*.ne";
						}
						
						@Override
						public boolean accept(File f) {							
							return f.getName().lastIndexOf(".ne")==(f.getName().length()-3) || f.isDirectory();
						}
					});
					// disable the "All files" option.
					chooser.setAcceptAllFileFilterUsed(false);
					if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
						File file = chooser.getSelectedFile();
						if (file != null) {
							Notepad np=FileOpenSave.openNotepad(file);
							if(np!=null){								
								if(!np.isEncrypted()){
									textArea.setText(np.getData());
								}else{	
									String password=JOptionPane.showInputDialog(null, "Password");
									if((password)!=null){
										try {
											String data=AES256.decrypt(password,np.getData().toCharArray());
											textArea.setText(data);
										} catch (Exception e) {										
											JOptionPane.showMessageDialog(null, "Invalid Password");
										}
									}
								}
								JNotepad.this.setTitle("Notepad E - "+file.getName());
							}
						}
					}
				
			}
		});
	}
}
