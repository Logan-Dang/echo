package echo;

import java.util.HashMap;

public class Cache extends HashMap<String, String> {
  @Override
  public synchronized String get(Object key) {
    return super.get(key);
  }

  @Override
  public synchronized String put(String key, String value) {
    return super.put(key, value);
  }
}
