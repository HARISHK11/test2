package com.chatserver.project;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Font;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class ClientEnd {

	public JFrame frame;
	private JTextField textField;
	private JTextField nameTextField;
    private static JTextArea textArea;
    private static Socket con;
    DataInputStream input;
    DataOutputStream output;
    private JScrollPane scrollPane;
  
	public static void main(String[] args) throws UnknownHostException, IOException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientEnd window = new ClientEnd();
					window.frame.setVisible(true);
				
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		con = new Socket("127.0.0.1", 8080);
		 while (true) {
			try {
				
				DataInputStream messageStream = new DataInputStream(con.getInputStream());
				String message = messageStream.readUTF();
				DataInputStream nameStream = new DataInputStream(con.getInputStream());
				String name = nameStream.readUTF();
				System.out.println("client values :"+message);
				textArea.setText(textArea.getText() + "\n" + name + ":" + message);
			} catch (Exception ev) {
				 textArea.setText(textArea.getText()+" \n" +"Network issues ");

				 try {
						Thread.sleep(2000);
						System.exit(0);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			}
			
				

		}
		
	}

	
	public ClientEnd() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(UIManager.getColor("MenuBar.highlight"));
		frame.setBounds(100, 100, 605, 378);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("NA1 Project : Client Chat");
		
		textField = new JTextField();
		textField.setFont(new Font("Lato Medium", Font.PLAIN, 22));
		textField.setForeground(new Color(0,0,0)); 
		textField.setBackground(new Color(225,239,228));
		textField.setBounds(12, 80, 344, 30);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		nameTextField = new JTextField("");
		nameTextField.setFont(new Font("Lato Semibold", Font.PLAIN, 24));
		nameTextField.setForeground(new Color(0,0,0));
		nameTextField.setBackground(new Color(225,239,228));
		nameTextField.setBounds(12, 40, 344, 30);
		frame.getContentPane().add(nameTextField);
		nameTextField.setColumns(10);
		
		JButton btnNewButton = new JButton("Send");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (textField.getText().equals("") || nameTextField.getText().equals("")) {
					if(textField.getText().equals("") && nameTextField.getText().equals("")){
						JOptionPane.showMessageDialog(null, "Please give your Name and Message!");	
					}
					else if(nameTextField.getText().equals("")){
						JOptionPane.showMessageDialog(null, "please give your name !");
					}
					else {
						JOptionPane.showMessageDialog(null, "Please give your message!");
					}
				}else  {
					textArea.setText(textArea.getText() + "\n" + nameTextField.getText()+":" + textField.getText());
					//textField.setText("");
					try {
						DataOutputStream message = new DataOutputStream(con.getOutputStream());
						message.writeUTF(textField.getText());
						DataOutputStream name = new DataOutputStream(con.getOutputStream());
						name.writeUTF(nameTextField.getText());
					} catch (IOException e1) {
						textArea.setText(textArea.getText() + "\n " + " Network issues");
						try {
							Thread.sleep(2000);
							System.exit(0);
						} catch (InterruptedException e2) {
							e2.printStackTrace();
						}

					}
					textField.setText("");
				}
			
			
			}
					
		});
		
	
		btnNewButton.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
		btnNewButton.setForeground(new Color(14,178,226));
		btnNewButton.setBackground(Color.BLUE);
		btnNewButton.setBounds(390, 66, 164, 38);
		frame.getContentPane().add(btnNewButton);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 149, 557, 157);
		frame.getContentPane().add(scrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setFont(new Font("Comic Sans MS", Font.PLAIN, 22));
		textArea.setForeground(new Color(0,0,0));
		textArea.setBackground(new Color(225,239,228));
		
	}
}
