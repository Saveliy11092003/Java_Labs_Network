import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Receiver extends Thread {
    private AddressMap addressMap;
    private InetAddress groupAddress;

    private int port;

    public Receiver(String groupIP, int port, AddressMap addressMap) throws IOException {
        // initializing members
        this.port = port;
        this.groupAddress = InetAddress.getByName(groupIP);


        this.addressMap = addressMap;
    }

    @Override
    public void run() {
        try (MulticastSocket socket = new MulticastSocket(port)) {
            socket.joinGroup(groupAddress);
            while (!Thread.currentThread().isInterrupted()) {
                // while our thread is not interrupted doing some work

                // creating buffer for message symbols
                byte[] buffer = new byte[1024];

                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                try {
                    // receiving packet with message
                    socket.receive(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.err.println(e);
                }

                // binding receive-time with sender's address in addressMap
                addressMap.add(packet.getSocketAddress(), System.currentTimeMillis());
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e);
        }
    }
}