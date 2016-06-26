import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;

public class ClientSocket {

	public static class ClientThread implements Runnable {
		private Socket s;

		BufferedReader br = null;

		public ClientThread(Socket s) {
			this.s = s;
			try {
				br = new BufferedReader(new InputStreamReader(
						s.getInputStream()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				String content = null;
				while ((content = br.readLine()) != null) {
					System.out.println(content);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	public static void main(String[] args) {
		try {
			Socket s = new Socket("127.0.0.1", 6000);
			// s.setSoTimeout(1000);
			new Thread(new ClientThread(s)).start();
			PrintStream ps=new PrintStream(s.getOutputStream());
			ps.println("come on man");
			Thread.sleep(100);
			s.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}