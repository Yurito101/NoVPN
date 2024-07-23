package me.yurito.novpn;

import net.md_5.bungee.api.plugin.Plugin;

public final class NoVPN extends Plugin {
    public void onEnable() {
        this.getLogger().info("Starting NoVPN");
        this.getProxy().getPluginManager().registerListener(this, new Listener());
    }

    public void onDisable() {
        this.getLogger().info("Stopping NoVPN");
    }
}
