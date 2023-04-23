package echo;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

  protected ServerSocket mySocket;
  protected int myPort;
  public static boolean DEBUG = true;
  protected Class<?> handlerType;

  public Server(int port, String handlerType) {
    try {
      myPort = port;
      mySocket = new ServerSocket(myPort);
      this.handlerType = (Class.forName(handlerType));
      System.out.println("Listening on port: " + port);
    } catch (Exception e) {
      System.err.println(e.getMessage());
      System.exit(1);
    } // catch
  }

  public void listen() throws InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException, NoSuchMethodException, SecurityException {
    while (true) {
      try {
        Socket s = mySocket.accept();
        RequestHandler handler = this.makeHandler(s);
        System.out.println("Accepted connection on port " + s.getPort() + " using handler " + handler.getClass());
        Thread t = new Thread(handler);
        t.start();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } // while
  }

  public RequestHandler makeHandler(Socket s) throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
    return (RequestHandler) handlerType.getDeclaredConstructor(s.getClass()).newInstance(s);

  }

  public static void main(String[] args) throws Exception {
    int port = 5555;
    String service = "echo.RequestHandler";
    if (1 <= args.length) {
      service = args[0];
    }
    if (2 <= args.length) {
      port = Integer.parseInt(args[1]);
    }
    Server server = new Server(port, service);
    server.listen();
  }
}