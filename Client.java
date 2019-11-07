import java.net.*;
import java.io.*;

public class Client {
    public static void main(String [] args){
        if (args.length != 3) {
            System.out.println("Usage: java Client <ServerIP> <ServerPort> <Choice(lowercase)>");
            System.exit(-1);
        }
        try {
            int port = Integer.valueOf(args[1]);
            Socket clientSocket = new Socket(args[0],port);
            /*
            switch (args[2]){
                case "rock": output = 1;
                case "paper": output = 2;
                case "scissor": output = 3;
            }
            */
            /* send choice */
            PrintWriter outWriter = new PrintWriter(clientSocket.getOutputStream());
            outWriter.println(args[2]);
            outWriter.flush();
            /* receive result */
            BufferedReader in_buf = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            System.out.println("Result: " + in_buf.readLine());
            in_buf.close();
            outWriter.close();
        } catch (Exception x) {
            x.printStackTrace();
        }
    }
}
