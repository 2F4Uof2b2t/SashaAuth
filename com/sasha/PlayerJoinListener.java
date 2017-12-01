package com.sasha;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.omg.CORBA.Environment;

import java.util.ArrayList;

import static com.sasha.Main.lol;

public class PlayerJoinListener extends PlayerListener {

    public static ArrayList<SecurePlayer> securePlayers = new ArrayList<>();

    public PlayerJoinListener() {
        Bukkit.getServer().getPluginManager().registerEvent(Event.Type.PLAYER_JOIN, this, Event.Priority.Highest, lol);
    }
    @Override
    public void onPlayerJoin(final PlayerJoinEvent e) {
        if (SecurePlayer.getSecurePlayer(e.getPlayer().getName()) != null) {
            return;
        }
        Location location = new Location(Bukkit.getServer().getWorld("world"), 0, 128, 0);
        SecurePlayer sp = new SecurePlayer(e.getPlayer());
        securePlayers.add(sp);
        if (sp.isNeedsToRegister()) {
            e.getPlayer().sendMessage("\2478(\247dSasha\2475Auth\2478) \247eWelcome. Please \2476/register\247e to continue...");
            return;
        }
        e.getPlayer().teleport(location);
        e.getPlayer().sendMessage("\2478(\247dSasha\2475Auth\2478) \247eWelcome back. Please \2476/login \247eto continue...");
    }
}
