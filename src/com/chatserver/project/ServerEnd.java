package com.chatserver.project;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class ServerEnd {

	private JFrame frmServerChat;
	private JTextField textField;
	private JTextField nameTextField;
	private static   JTextArea textArea;
	private JButton btnNewButton;
	static ServerSocket server ;
	static Socket con;
	private JScrollPane scrollPane;
	private static JLabel lblNewLabel_2;
	private static JLabel lblNewLabel;
	
	

	
	public static void main(String[] args) throws IOException   {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerEnd window = new ServerEnd();
					window.frmServerChat.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	
		 serverConnection();
	
	
	}

	private static void serverConnection() throws IOException {
		server = new ServerSocket(8080);
		
		 con = server.accept();
		 // lblNewLabel_2.setText("Client found !");
		 lblNewLabel.setText("Client Connection Established");
		 
		 lblNewLabel_2.setForeground(new Color(0, 0, 128));
		 while (true) {
			try {
				
				DataInputStream messageStream = new DataInputStream(con.getInputStream());
				String message = messageStream.readUTF();
				DataInputStream nameStream = new DataInputStream(con.getInputStream());
				String name = nameStream.readUTF();
				System.out.println("server values :"+message);
				textArea.setText(textArea.getText() + "\n" + name + ":" + message);
			} catch (Exception ev) {
				 textArea.setText(textArea.getText()+" \n" +"Network issues ");
				 
					try {
						Thread.sleep(2000);
						System.exit(0);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}

		}
	}

	public ServerEnd() throws IOException {
		initialize();


		 
	}

	private void initialize() throws IOException {
		frmServerChat = new JFrame();
		frmServerChat.getContentPane().setBackground(UIManager.getColor("MenuBar.highlight"));
		frmServerChat.setForeground(Color.WHITE);
		frmServerChat.setBackground(Color.WHITE);
		frmServerChat.setTitle("NA1 Project : Server Chat");
		frmServerChat.getContentPane().setForeground(Color.PINK);
		frmServerChat.setBounds(100, 100, 605, 403);
		frmServerChat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmServerChat.getContentPane().setLayout(null);
		
		textField = new JTextField("");
		textField.setFont(new Font("Lato Semibold", Font.PLAIN, 24));
		textField.setForeground(new Color(0,0,0));
		textField.setBackground(new Color(225,239,228));
		textField.setBounds(12, 80, 344, 30);
		frmServerChat.getContentPane().add(textField);
		textField.setColumns(10);
		
		nameTextField = new JTextField("");
		nameTextField.setFont(new Font("Lato Semibold", Font.PLAIN, 24));
		nameTextField.setForeground(new Color(0,0,0));
		nameTextField.setBackground(new Color(225,239,228));
		nameTextField.setBounds(12, 40, 344, 30);
		frmServerChat.getContentPane().add(nameTextField);
		nameTextField.setColumns(10);
		
		btnNewButton = new JButton("Send");
		//btnNewButton.setBackground(Color.PINK);
		
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
				}
				
				else if(textField.isFocusable()){
					btnNewButton.setEnabled(true);
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
		frmServerChat.getContentPane().add(btnNewButton);
		 
		 scrollPane = new JScrollPane();
		 scrollPane.setBounds(12, 134, 557, 157);
		 frmServerChat.getContentPane().add(scrollPane);
		
		 textArea = new JTextArea();
		 scrollPane.setViewportView(textArea);
		textArea.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
		textArea.setForeground(new Color(0,0,0));
		textArea.setBackground(new Color(225,239,228));
		
		lblNewLabel = new JLabel();
		
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 16));
		
		lblNewLabel.setBounds(154, 13, 265, 33);
		frmServerChat.getContentPane().add(lblNewLabel);
		 if (server.isClosed()) {
			lblNewLabel.setText("Server is closed");
		}else{
			lblNewLabel.setText("Waiting for Connection");
			lblNewLabel.setForeground(new Color(29,156,54));
		}
		JLabel lblNewLabel_1 = new JLabel("Status");
		lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 22));
		lblNewLabel_1.setBounds(37, 12, 95, 30);
		frmServerChat.getContentPane().add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel();
		lblNewLabel_2.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
		lblNewLabel_2.setBounds(22, 303, 128, 30);
		frmServerChat.getContentPane().add(lblNewLabel_2);
	

	}
}
