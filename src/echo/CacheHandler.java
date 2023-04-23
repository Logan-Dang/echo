package echo;

import java.net.Socket;

public class CacheHandler extends ProxyHandler {
  private static final Cache cache = new Cache();

  public CacheHandler(Socket s) {
    super(s);
  }

  @Override
  protected String response(String msg) throws Exception {
    String res = cache.get(msg);
    if (res == null) {
      System.out.println("Not in cache :/");
      res = super.response(msg);
      cache.put(msg, res);
    } else {
      System.out.println("Cached!");
    }
    return res;
  }
}
