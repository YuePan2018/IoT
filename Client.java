import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {
    public static void main(String [] args){
        /* args check */
        if (args.length != 0) {
            System.out.println("Usage: java Client");
            System.exit(-1);
        }
        try {
            while(true){
                /* TCP connection */
            Socket clientSocket = new Socket("35.174.70.137",9090);
            PrintWriter outWriter = new PrintWriter(clientSocket.getOutputStream());
            BufferedReader in_buf = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                /* countdown when receiving from server */
                in_buf.readLine();
                System.out.println("(Please input before timeout: paper, rock or scissor)");
                int time = 15;      // count down of 14 seconds
                while (time>0){
                    try {
                        Thread.sleep(1000);
                        time--;
                        System.out.println("Time Left: " + time + "s");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                String input = "rock";
                System.out.println("Timeout");
                System.out.println(">Your final Choice: " + input);
                /* send choice to server*/
                outWriter.println(input);
                outWriter.flush();
                /* receive result from server*/
                System.out.println(">Result: " + in_buf.readLine());
                /* end game or start next round*/
                System.out.println("Wait for Next Round");
                in_buf.close();
                outWriter.close();
            }
        } catch (Exception x) {
            x.printStackTrace();
        }
    }
}
