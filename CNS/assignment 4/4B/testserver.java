
import java.io.*;
import java.net.*;
import java.util.*;
class testserver
{
    public static void main(String args[]) throws IOException
    {
        System.out.println("..Server..");
        System.out.println("Waiting for connection....");
        InetAddress addr=InetAddress.getByName("Localhost");
        ServerSocket ss = new ServerSocket(2048);
        Socket client = new Socket();
        client=ss.accept();
        BufferedInputStream in = new BufferedInputStream(client.getInputStream());
        DataOutputStream out = new DataOutputStream(client.getOutputStream());
        int p=in.read();
        System.out.println("Received request for sending " + p + " frames");
        boolean f[] = new boolean[p];
        int pc= in.read(); //Choice ... Error or non-error
        System.out.println("Sending....");
        if(pc==0){
            for (int i=0;i<p; ++i){
                System.out.println("sending frame number "+i);
                out.write(i);
                out.flush();
                System.out.println("Waiting for acknowledgement");
                try
                {
                    Thread.sleep (3000);
                }
                catch (Exception e) { }
                int a=in.read();
                System.out.println("received acknowledgement for frame "+i+" as "+a);
            }
            out.flush();
        }
        if(pc==1){
            for (int i=0;i<p; ++i){
                System.out.println("sending frame number "+i);
                out.write(i);
                out.flush();
                System.out.println("Waiting for acknowledgement");
                try
                {
                    Thread.sleep (3000);
                }
                catch (Exception e) { }
                int a=in.read();
                if (a !=255){
                    System.out.println("received acknowledgement for frame no: "+i+" as "+a);
                    f[i]=true;
                }
                else f[i] = false;
                // System.out.println("received acknowledgement for frame "+i+" as "+a);
            }
            out.flush();
        }
            for (int a=0; a<p;++a){
                if(f[a]==false && pc == 1){
                    System.out.println("Resending frame "+a);
                    out.write(a);
                    out.flush();
                    System.out.println("Waiting for acknoweledgement ");
                    try
                    {
                        Thread.sleep (3000);
                    }
                    catch (Exception e) { }
                    int b=in.read();
                    System.out.println("received acknoweledgement for frame no: "+a+" as "+b);
                    f[a]=true;
                    break;
                }
            }
        
        in.close();
        out.close();
        System.out.println("Quiting");
    } // end of main funtion
} // end of main class



