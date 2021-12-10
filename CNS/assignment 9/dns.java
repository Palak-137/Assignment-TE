import java.net.*;
import java.io.*;
import java.util.*;

class dns {
    public static void main(String[] args) {
        int n = 0;
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        while (n < 3) {
            System.out.println("\n **** Menu **** \n 1. DNS \n 2. Reverse DNS \n 3. Exit ");
            System.out.print(" Choice :");
            n = Integer.parseInt(System.console().readLine());
            if (n == 1) {
                try {
                    System.out.print("\nEnter Host Name : ");
                    String hostname = read.readLine();
                    InetAddress[] address;
                    address = InetAddress.getAllByName(hostname);
                    for (InetAddress var : address) {
                        System.out.println("\nIP: " + var.toString());
                    }

                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
            if (n == 2) {
                try {
                    System.out.print("\nEnter IP address : ");
                    String ipsString = read.readLine();
                    InetAddress ia = InetAddress.getByName(ipsString);
                    System.out.println("IP: " + ipsString);
                    System.out.println("\nHost Name: " + ia.getHostName());
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
        System.out.println("Exiting...");
        System.out.println("Done");
    }
}
