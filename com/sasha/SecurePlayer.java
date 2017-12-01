package com.sasha;

import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class SecurePlayer {

    private boolean isAuthed;
    private Player player;
    private String password;
    private boolean needsToRegister;

    public SecurePlayer(Player player) {
        File file = new File("Credentials.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        isAuthed = false;
        this.player = player;
        if (Main.getPasswordFromFile(this) == null) {
            // new player, never registered
            needsToRegister = true;
            return;
        }
        needsToRegister = false;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isAuthed() {
        return isAuthed;
    }

    public boolean isNeedsToRegister() {
        return this.needsToRegister;
    }

    public void setNeedsToRegister(boolean needsToRegister) {
        this.needsToRegister = needsToRegister;
    }

    public void setAuthed(boolean authed) {
        isAuthed = authed;
    }

    public static SecurePlayer getSecurePlayer(String name) {
        for (SecurePlayer sp : PlayerJoinListener.securePlayers) {
            if (sp.getPlayer().getName().equalsIgnoreCase(name)) {
                return sp;
            }
        }
        return null;
    }
}
