import java.net.*;
import java.io.*;

public class Server {
    public static void main(String [] args){
        /* args[] check*/
        if (args.length != 1) {
            System.out.println("Usage: java Server <ServerPort>");
            System.exit(-1);
        }
        int port = Integer.valueOf(args[0]);
        /* listening TCP */
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("IP: "+ InetAddress.getLocalHost().getHostAddress() + ", Port: " + port);
            while(true){
                String ret1;    //return value to client 1
                String ret2;    // return value to client 2
                System.out.println("***New Game");
                /* TCP connection */
                Socket socket1 = serverSocket.accept();
                Socket socket2 = serverSocket.accept();
                /* read client 1 */
                BufferedReader buffer1 = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
                String choice1 = buffer1.readLine();
                /* read client 2 */
                BufferedReader buffer2 = new BufferedReader(new InputStreamReader(socket2.getInputStream()));
                String choice2 = buffer2.readLine();
                /* check invalid input choice */
                if((! choice1.equals("rock")) && (! choice1.equals("paper")) && (! choice1.equals("scissor"))){
                    System.out.println("Invalid choice1");
                    ret1 = "Invalid choice";
                    ret2 = "Invalid choice";
                }else if((! choice2.equals("rock")) && (! choice2.equals("paper")) && (! choice2.equals("scissor"))){
                    System.out.println("Invalid choice2");
                    ret1 = "Invalid choice";
                    ret2 = "Invalid choice";
                }else{
                    /* compare choices */
                    if(choice1.equals(choice2)){
                        System.out.println("Result: draw");
                        ret1 = "Draw";
                        ret2 = "Draw";
                    }else if((choice1.equals("rock")&&choice2.equals("paper")) || (choice1.equals("paper")&&choice2.equals("scissor")) || (choice1.equals("scissor")&&choice2.equals("rock"))){
                        System.out.println("winner: choice2");
                        ret1 = "You Loss";
                        ret2 = "You Win";
                    }else{
                        System.out.println("winner: choice1");
                        ret1 = "You Win";
                        ret2 = "You Loss";
                    }
                    //((choice2.equals("rock")&&choice1.equals("paper")) || (choice2.equals("paper")&&choice1.equals("scissor")) || (choice2.equals("scissor")&&choice1.equals("rock")))
                }
                /* write results */
                PrintWriter Writer1 = new PrintWriter(socket1.getOutputStream());
                Writer1.println(ret1);
                Writer1.flush();
                Writer1.close();
                buffer1.close();
                PrintWriter Writer2 = new PrintWriter(socket2.getOutputStream());
                Writer2.println(ret2);
                Writer2.flush();
                Writer2.close();
                buffer2.close();
                System.out.println("***Game Over");
            }
        } catch (Exception x) {
        x.printStackTrace();
        }
    }
}
