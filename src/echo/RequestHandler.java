package echo;

import java.net.Socket;

public class RequestHandler extends Correspondent implements Runnable {
  protected boolean active; // response can set to false to terminate thread

  public RequestHandler(Socket s) {
    super(s);
    active = true;
  }

  public RequestHandler() {
    super();
    active = true;
  }

  // override in a subclass:
  protected String response(String msg) throws Exception {
    return "echo: " + msg;
  }

  // any housekeeping can be done by an override of this:
  protected void shutDown() {
    if (Server.DEBUG)
      System.out.println("handler shutting down");
  }

  public void run() {
    while (active) {
      try {
        String request = this.receive();
        if (request.equals("quit")) {
          shutDown();
          break;
        }
        send(response(request));
      } catch (Exception e) {
        send(e.getMessage() + "... ending session");
        break;
      }
    }
    // close
  }
}