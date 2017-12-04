package com.sasha;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.omg.CORBA.Environment;

import java.io.IOException;
import java.util.ArrayList;

import static com.sasha.Main.lol;

public class PlayerJoinListener extends PlayerListener {

	public static ArrayList<SecurePlayer> securePlayers = new ArrayList<>();

	public PlayerJoinListener() {
		Bukkit.getServer().getPluginManager().registerEvent(Event.Type.PLAYER_JOIN, this, Event.Priority.Highest, lol);
	}

	@Override
	public void onPlayerJoin(final PlayerJoinEvent e) {
		SecurePlayer p = SecurePlayer.getSecurePlayer(e.getPlayer().getName());
		if (SecurePlayer.getSecurePlayer(e.getPlayer().getName()) != null) {
			return;
		}
		Location location = new Location(Bukkit.getServer().getWorld("world"), 0, 128, 0);
		SecurePlayer sp = new SecurePlayer(e.getPlayer());
		securePlayers.add(sp);
		if (sp.isNeedsToRegister()) {
			e.getPlayer().sendMessage(
					"\2478(\247dSasha\2475Auth\2478) \247eWelcome. Please \2476/register\247e to continue...");
			return;
		}
		if (e.getPlayer().getAddress().getHostString().equals(p.getIP())) {
			p.setAuthed(true);
			e.getPlayer().sendMessage(
					"\2478(\247dSasha\2475Auth\2478) \247eWelcome back. Since you have logged in from the same IP address, authentification has been bypassed.");
			try {
				p.getPlayer().teleport(LocationManager.getLocation(p.getPlayer()));
			} catch (IOException ex) {
				ex.printStackTrace();
				p.getPlayer().sendMessage("\2474Error whilst attempting teleportation to logout location!");
			}
			return;
		}
		e.getPlayer().teleport(location);
		e.getPlayer().sendMessage(
				"\2478(\247dSasha\2475Auth\2478) \247eWelcome back. Please \2476/login \247eto continue...");
	}
}
