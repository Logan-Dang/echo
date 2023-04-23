package echo;

import java.net.Socket;

public class SecurityHandler extends ProxyHandler {
  private static volatile boolean signedIn = false;
  private static final SecurityTable table = new SecurityTable();

  public SecurityHandler(Socket s) {
    super(s);
  }

  @Override
  protected String response(String msg) throws Exception {
    String[] args = msg.split(" ");
    if (signedIn) {
      return super.response(msg);
    }
    if (args[0].equals("new")) {
      table.put(args[1], args[2]);
      throw new Exception("New user created");
    }
    if (args[0].equals("login")) {
      String pass = table.get(args[1]);
      if (pass.equals(args[2])) {
        signedIn = true;
        return "Login success!";
      }
      throw new Exception("Login failed");
    }
    throw new Exception("Please sign in or create an account first");
  }
}
