package org.example.app.net;

public class NetworkDevide {
  
  protected String ip;
  protected boolean online = false;

  public NetworkDevide(String ip) {
    this.ip = ip;
  }

  public String getIP() {
    return this.ip;
  }

  public boolean isOnline() {
    System.out.println("Device is online: " + this.online);
    return this.online;
  }

  public class Router extends NetworkDevide {
    public Router(String ip) {
      super(ip);
    }
  }
}