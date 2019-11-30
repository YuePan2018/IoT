import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {
    public static void main(String [] args){
        /* args check */
        if (args.length != 2) {
            System.out.println("Usage: java Client <ServerIP> <ServerPort>");
            System.exit(-1);
        }
        try {
            /* TCP connection */
            Socket clientSocket = new Socket(args[0],Integer.valueOf(args[1]));
            PrintWriter outWriter = new PrintWriter(clientSocket.getOutputStream());
            BufferedReader in_buf = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            /* game rounds loop*/
            while(true){
                /* keyboard input choice */
                Scanner scan = new Scanner(System.in);
                String input;
                System.out.println("Input Your Choice: paper, rock or scissor");
                if (scan.hasNextLine()) {
                    input = scan.nextLine();
                }else{
                    System.out.println("key board input error");
                    break;
                }
                /* send choice to server*/
                outWriter.println(input);
                outWriter.flush();
                /* receive result from server*/
                System.out.println("Result: " + in_buf.readLine());
                /* end game or start next round*/
                System.out.println("Ready for Next Round?");
                System.out.println("press any key to continue or input \"end\" to end this game");
                if (scan.hasNextLine()){
                    outWriter.println(scan.nextLine());
                    outWriter.flush();
                    if (in_buf.readLine().equals("end")){
                        break;
                    }else {
                        System.out.println("Next Round");
                    }
                }
            }
            in_buf.close();
            outWriter.close();
            System.out.println("End Game");
        } catch (Exception x) {
            x.printStackTrace();
        }
    }
}
