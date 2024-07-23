package me.yurito.novpn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.event.EventHandler;

public class Listener implements net.md_5.bungee.api.plugin.Listener {
    String pfn = "NoVPN - ";
    String backUpAPI = "https://funkemunky.cc/vpn?ip=";
    Boolean fcheck = false;

    @EventHandler
    public void getIP(PreLoginEvent e) {
        try {
            String playerName = e.getConnection().getName();
            String address = String.valueOf(e.getConnection().getAddress());
            String[] NewAddress = address.split(":");
            String CleanNew = NewAddress[0].replace("/", "");
            if (this.KCheck(CleanNew)) {
                System.out.println(this.pfn + playerName + " is using &cProxy/VPN");
                e.setCancelReason(this.pfn + " VPN/Proxies are not allow in this server!");
                e.setCancelled(true);
            } else {
                System.out.println(this.pfn + playerName + " - Passed Checked");
                e.setCancelled(false);
            }
        } catch (Exception var6) {
            System.out.println(this.pfn + " Error: " + var6.getMessage());
        }

    }

    public static int searchCount(File fileA, String fileWord) throws FileNotFoundException {
        int count = 0;
        Scanner scanner = new Scanner(fileA);

        while(scanner.hasNextLine()) {
            String nextWord = scanner.next();
            System.out.println(nextWord);
            if (nextWord.equalsIgnoreCase(fileWord)) {
                ++count;
            }
        }

        return count;
    }

    private boolean KCheck(String playerIP) {
        try {
            URL url = new URL("https://proxycheck.io/v2/" + playerIP + "?vpn=1&asn=2");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

            label39:
            while(true) {
                while(true) {
                    String line;
                    if ((line = in.readLine()) == null) {
                        break label39;
                    }

                    String fullvpn = line.toLowerCase();
                    String s = "status";
                    String s2 = "ok";
                    String line2;
                    if (fullvpn.contains("\"" + s + "\": \"" + s2 + "\"")) {
                        String p = "proxy";
                        String p2 = "yes";
                        line2 = "\"" + p + "\": \"" + p2 + "\"";
                        if (fullvpn.contains(line2)) {
                            this.fcheck = true;
                            break label39;
                        }
                    } else {
                        URL url2 = new URL(this.backUpAPI + playerIP);
                        BufferedReader in2 = new BufferedReader(new InputStreamReader(url2.openStream()));

                        for(boolean var12 = false; (line2 = in2.readLine()) != null; this.fcheck = false) {
                            String word = "proxy";
                            String Found = "\"" + word + "\":true";
                            if (line2.toLowerCase().contains(Found)) {
                                this.fcheck = true;
                                break;
                            }
                        }
                    }
                }
            }

            in.close();
        } catch (MalformedURLException var16) {
            System.out.println("Malformed URL: " + var16.getMessage());
        } catch (IOException var17) {
            System.out.println("I/O Error: " + var17.getMessage());
        }

        return this.fcheck;
    }
}