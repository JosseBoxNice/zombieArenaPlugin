package io.github.josseboxnice.zombieArenaPlugin;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.entity.Player;


public class zombieArenaPlugin extends JavaPlugin implements Listener {
  @Override
  public void onEnable() {
    saveDefaultConfig();

    Bukkit.getPluginManager().registerEvents(this, this);
  }

  @EventHandler
  public void onInventoryClick(InventoryClickEvent event) {
    if (this.getConfig().getBoolean("allowDroppingItems")) {
      return;
    }

    if (event.getSlotType() == SlotType.OUTSIDE) {
      event.setCancelled(true);
    }
  }

  @EventHandler
  public void onPlayerDropItem(PlayerDropItemEvent event) {
    if (this.getConfig().getBoolean("allowDroppingItems")) {
      return;
    }
    
    event.setCancelled(true);
  }

  @EventHandler
  public void onPlayerDead(PlayerDeathEvent event) {
    if (this.getConfig().getBoolean("clearItemsOnDeath")) {
      event.getDrops().clear();
    }
    if (!this.getConfig().getBoolean("dropXPOnDeath")) {
      event.setShouldDropExperience(false);
    }
  } 
}
