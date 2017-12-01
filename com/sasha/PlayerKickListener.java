package com.sasha;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.IOException;
import java.util.ArrayList;

import static com.sasha.Main.lol;

public class PlayerKickListener extends PlayerListener {

    public static ArrayList<SecurePlayer> securePlayers = new ArrayList<>();

    public PlayerKickListener() {
        Bukkit.getServer().getPluginManager().registerEvent(Event.Type.PLAYER_KICK, this, Event.Priority.Highest, lol);
    }
    @Override
    public void onPlayerKick(final PlayerKickEvent e) {
        if (SecurePlayer.getSecurePlayer(e.getPlayer().getName()).isAuthed()) {
            try {
                LocationManager.storeLogOffLocation(e.getPlayer());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        PlayerJoinListener.securePlayers.remove(SecurePlayer.getSecurePlayer(e.getPlayer().getName()));
    }
}
