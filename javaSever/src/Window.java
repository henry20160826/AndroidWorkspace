import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Window extends JFrame {

	public static JTextField pathField;

	// ����
	public Window(String title) {
		super(title);
		JPanel p = new JPanel();
		pathField = new JTextField(20);
		JTextField name = new JTextField(20);
		JTextField password = new JTextField(20);
		JLabel label1 = new JLabel("·��    ");
		JLabel label2 = new JLabel("�û���");
		JLabel label3 = new JLabel("����    ");
		JButton button = new JButton("��ʼ");
		button.addActionListener(new OPENListener());
		p.add(label1);
		p.add(pathField);
		p.add(label2);
		p.add(name);
		p.add(label3);
		p.add(password);
		p.add(button);
		add(p);
		setSize(300, 300);
		setLocation(30, 50);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	// ����������
	public class OPENListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			File file = new File(pathField.getText());
			File[] files = file.listFiles();
			if (files != null) {
				for (int i = 0; i < files.length; i++) {
					if (!files[i].isDirectory()) {
						System.out.println(files[i].getName());
					} else {
						System.out.println("�ļ���:" + files[i].getName());
					}
				}
			}
			// ServerSocket serverSocket;
			// try {
			// serverSocket = new ServerSocket(6000);
			// Socket socket=serverSocket.accept();
			// ServiceSocket serviceSocket=new ServiceSocket(socket);
			// serviceSocket.start();
			// } catch (IOException e1) {
			// // TODO Auto-generated catch block
			// e1.printStackTrace();
			// }

		}
	}

	// ������
	public static void main(String[] args) {
		new Window("Sever");
		try {
			ServerSocket serverSocket = new ServerSocket(6000);
			while (true) {
				// System.out.println("ok");
				Socket s = serverSocket.accept();
				BufferedReader br = new BufferedReader(new InputStreamReader(
						s.getInputStream()));
				String line = br.readLine();
				System.out.println(line);
				PrintStream ps=new PrintStream(s.getOutputStream());
				File file = new File("E://");
				File[] files = file.listFiles();
				if (files != null) {
					for (int i = 0; i < files.length; i++) {
						if (!files[i].isDirectory()) {
//							System.out.println(files[i].getName());
							ps.println(files[i].getName());
						} else {
//							System.out.println("�ļ���:" + files[i].getName());
							ps.println("�ļ���:" + files[i].getName());
						}
					}
				}
				br.close();
//				s.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
