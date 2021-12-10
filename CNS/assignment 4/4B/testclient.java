import java.io.*;
import java.net.*;
import java.math.*;
import java.util.*;
class testclient
{
    public static void main(String args[]) throws IOException
    {
        InetAddress addr=InetAddress.getByName ("Localhost");
        System.out.println(addr);
        Socket connection=new Socket (addr, 2048);
        BufferedInputStream in=new BufferedInputStream (connection.getInputStream());
        DataOutputStream out=new DataOutputStream(connection.getOutputStream());
        Scanner scr=new Scanner(System.in);// this will be used to accept i/p from console
        System.out.println(".......Client........");
        System.out.println("Connect");
        System.out.println("Enter the number of frames to be requested to the server");
        int c=scr.nextInt();
        out.write(c);
        out.flush();
        System.out.println("Enter the type of trans. Error=1 ; No Error=0");
        int choice=scr.nextInt();
        out.write(choice); 
        int check=0;
        int i=0;
        int j=0;
        boolean right[] = new boolean[c];
        if (choice==0){
            for(j=0; j<c;++j){
                i=in.read();
                System.out.println("received frame no: "+i);
                System.out.println("Sending acknowledgement for frame no: "+i);
                out.write(i);
                out. flush();
            }
            out.flush();
        }
        else { //Error case
            for(int k =0; k<c; k++){
                i= in.read();
                if(i!= c/2){
                    // System.out.println("i: "+i+" check : "+check);
                    System.out.println("received frame no: "+i);
                    System.out.println("Sending acknowledgement for frame no: "+i);
                    out.write(i);
                    right[k] = true;
                }
                else{
                    // System.out.println("Discarded frame no: "+k);
                    System.out.println("Didn't receive data for frame no: "+ k);
                    System.out.println("Sending NEGATIVE acknowledgement");
                    out.write(-1);
                    right[k] = false;
                }
                out.flush();
            }
            for(int k = c/2; ; k++){
                i= in.read();
                // System.out.println("i: "+i+" check : "+check);
                System.out.println("received frame no: "+i);
                System.out.println("Sending acknowledgement for frame no: "+i);
                out.write(i);
                right[k] = true;
                break;
            }
            
        }//end of else for error
        in.close();
        out.close();
        System.out.println("Quiting");
    } // end of main method
} // end of main class



