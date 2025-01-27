package smartlockserver.util;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * 客户端
 *
 * @author allron
 * @date 2019-01-23
 */
public class Client {
    public static void main(String[] args) {
        Client client = new Client();
        client.startAction();
    }

    private void readSocketInfo(BufferedReader reader) {
        new Thread(new MyRuns(reader)).start();
    }

    class MyRuns implements Runnable {

        BufferedReader reader;

        MyRuns(BufferedReader reader) {
            super();
            this.reader = reader;
        }

        public void run() {
            try {
                String lineString;
                while ((lineString = reader.readLine()) != null) {
                    System.out.println(lineString);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void startAction() {
        Socket socket = null;
        BufferedReader reader = null;
        BufferedWriter writer = null;
        BufferedReader reader2;
        try {
            socket = new Socket("192.168.31.114", 10086);
            reader = new BufferedReader(new InputStreamReader(System.in));
            reader2 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            readSocketInfo(reader2);
            String lineString;
            while (!(lineString = reader.readLine()).equals("exit")) {
                writer.write(lineString + "\n");
                writer.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (writer != null) {
                    writer.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}