import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.IOException;
import java.lang.Math;
import com.leapmotion.leap.*;

public class LeapClient {
    public static void main(String [] args){
        /* args check */
        if (args.length != 0) {
            System.out.println("Usage: java Client");
            System.exit(-1);
        }
        try {
            /* game rounds loop*/
            while(true){                
                /* TCP connection */
                Socket clientSocket = new Socket("35.174.70.137", 9090);
                PrintWriter outWriter = new PrintWriter(clientSocket.getOutputStream());
                BufferedReader in_buf = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                /* countdown when receiving from server */
                in_buf.readLine();
                System.out.println("(Please input before timeout: paper, rock or scissor)");
                // Create a sample listener and controller
                SampleListener listener = new SampleListener();
                Controller controller = new Controller();
                controller.addListener(listener);
                // count down of 14 seconds
                int time = 15;      
                while (time>0){
                    try {
                        Thread.sleep(1000);
                        time--;
                        System.out.println("Time Left: " + time + "s");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                /* identify input */
                Frame frame = controller.frame();
                String temp = "rock";
                for(Hand hand : frame.hands()) {
                    if(hand.grabAngle() > 2 ){
                        temp = "rock";
                    } else if(hand.grabAngle() < 0.6){
                        temp = "paper";
                    } else {
                        temp = "scissor";
                    }
                    break;
                }
                controller.removeListener(listener);
                System.out.println("Timeout");
                System.out.println(">Your final Choice: " + temp);
                /* send choice to server*/
                outWriter.println(temp);
                outWriter.flush();
                /* receive result from server*/
                System.out.println(">Result: " + in_buf.readLine());
                /* disconnect */               
                in_buf.close();
                outWriter.close();
                /* start next round */
                System.out.println("***press Enter to start next round");
                Scanner scan1 = new Scanner(System.in);
                if (scan1.hasNextLine()){
                    scan1.nextLine();
                }
            }
        } catch (Exception x) {
            x.printStackTrace();
        }
    }
}

class SampleListener extends Listener {
    int status = 0;
    public void onFrame(Controller controller) {
        // Get the most recent frame and report some basic information
        Frame frame = controller.frame();
        int temp;
        //Get hands
        for(Hand hand : frame.hands()) {
            
            if(hand.grabAngle() > 2 ){
                temp = 1;
            } else if(hand.grabAngle() < 0.6){
                temp = 2;
            } else {
                temp = 3;
            }
            if(status != temp){
                status = temp;
                if(status == 1){
                    System.out.println("rock");
                } else if(status ==2){
                    System.out.println("paper");
                }else if(status ==3){
                    System.out.println("scissor");
                }
            }
        }
    }
}