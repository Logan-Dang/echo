package math;

import java.net.Socket;

import echo.RequestHandler;

public class MathHandler extends RequestHandler {
  public MathHandler(Socket s) {
    super(s);
  }

  @Override
  protected String response(String msg) throws Exception {
    String[] args = msg.split(" ");
    switch (args[0]) {
      case "add":
        return add(args);
      case "mul":
        return mul(args);
      case "sub":
        return "-" + add(args);
      case "div":
        return "1 / " + mul(args);
      default:
        throw new Exception("Arg needs to be of 'add', 'mul', 'sub', or 'div'.");
    }
  }

  private String add(String[] args) {
    double count = 0;
    for (int i = 1; i < args.length; i++) {
      count += Double.parseDouble(args[i]);
    }
    return String.valueOf(count);
  }

  private String mul(String[] args) {
    double count = 1;
    for (int i = 1; i < args.length; i++) {
      count *= Double.parseDouble(args[i]);
    }
    return String.valueOf(count);
  }
}
