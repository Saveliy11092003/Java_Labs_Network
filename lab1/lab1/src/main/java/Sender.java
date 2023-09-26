import java.io.IOException;
import java.net.*;

public class Sender extends Thread {

    private DatagramPacket packet;
    private int sleepTimeMs;

    private InetAddress groupAddress;
    private int port;
    private String message;

    public Sender(String groupIP, int port, String message, int sleepTimeMs) throws IOException {
        // initializing members
        this.groupAddress = InetAddress.getByName(groupIP);
        this.port = port;
        this.sleepTimeMs = sleepTimeMs;
        this.message = message;
    }

    @Override
    public void run() {
        try(MulticastSocket socket = new MulticastSocket(port)) {


            socket.joinGroup(groupAddress);
            while (!Thread.currentThread().isInterrupted()) {
                // while our thread is not interrupted doing some work
                try {
                    // sending packet with message to our group-mates
                    packet = new DatagramPacket(message.getBytes(), message.length(), groupAddress, port);
                    socket.send(packet);
                    // waiting sleepTimeMs milliseconds
                    Thread.sleep(sleepTimeMs);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println(e);
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
            System.err.println(e);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e);
        }
    }
}