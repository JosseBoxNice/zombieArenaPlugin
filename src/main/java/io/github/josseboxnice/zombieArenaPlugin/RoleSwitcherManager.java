package io.github.josseboxnice.zombieArenaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class RoleSwitcherManager {
  public static void loadState(Player player, String role, JavaPlugin plugin) {
    File dataFolder = plugin.getDataFolder();
    File playerFile = new File(
        dataFolder,
        "players/" + player.getUniqueId() + ".yml");

    YamlConfiguration config = YamlConfiguration.loadConfiguration(playerFile);

    if (!config.contains("roles." + role)) {
      if (role.equals("admin")) {
        saveState(player, "player", plugin);
      } else {
        saveState(player, "admin", plugin);
      }
    }
    List<?> items = config.getList("roles." + role + ".invContents");
    List<?> armor = config.getList("roles." + role + ".invArmorContents");
    List<?> eChest = config.getList("roles." + role + ".eChestContents");

    ItemStack[] invContents = items.toArray(new ItemStack[0]);
    ItemStack[] invArmorContents = armor.toArray(new ItemStack[0]);
    ItemStack[] eChestContents = eChest.toArray(new ItemStack[0]);

    player.getInventory().setContents(invContents);
    player.getInventory().setArmorContents(invArmorContents);
    player.getEnderChest().setContents(eChestContents);
    player.getInventory().setItemInOffHand(config.getItemStack("roles." + role + ".invOffHand"));
    player.setLevel(config.getInt("roles." + role + ".XPLevel"));

    if (role.equals("admin")) {
      player.sendRichMessage("<green>Successfully changed role to admin!");
    } else {
      player.sendRichMessage("<green>Successfully changed role to player!");
    }
  }

  public static void saveState(Player player, String role, JavaPlugin plugin) {
    File dataFolder = plugin.getDataFolder();

    File playerFile = new File(
        dataFolder,
        "players/" + player.getUniqueId() + ".yml");

    YamlConfiguration config = YamlConfiguration.loadConfiguration(playerFile);

    ItemStack[] invContents = player.getInventory().getContents();
    ItemStack[] invArmorContents = player.getInventory().getArmorContents();
    ItemStack[] eChestContents = player.getEnderChest().getContents();
    ItemStack invOffHand = player.getInventory().getItemInOffHand();
    int XPLevel = player.getLevel();

    config.set("roles." + role + ".invContents", invContents);
    config.set("roles." + role + ".invArmorContents", invArmorContents);
    config.set("roles." + role + ".invOffHand", invOffHand);
    config.set("roles." + role + ".eChestContents", eChestContents);
    config.set("roles." + role + ".XPLevel", XPLevel);

    if (role.equals("admin")) {
      config.set("state", "player");
    } else {
      config.set("state", "admin");
    }

    try {
      config.save(playerFile);
    } catch (IOException e) {
      e.printStackTrace();
      return;
    }

    if (role.equals("admin")) {
      player.sendRichMessage("<green>Admin state saved succesfully!");
    } else {
      player.sendRichMessage("<green>Player state saved succesfully!");
    }
  }

  public static String getState(Player player, JavaPlugin plugin) {
    File dataFolder = plugin.getDataFolder();

    File playerFolder = new File(plugin.getDataFolder(), "players");

    if (!playerFolder.exists()) {
      playerFolder.mkdirs();
    }

    File playerFile = new File(
        dataFolder,
        "players/" + player.getUniqueId() + ".yml");

    YamlConfiguration config = YamlConfiguration.loadConfiguration(playerFile);

    if (!playerFolder.exists()) {
      playerFolder.mkdirs();
    }

    if (!config.contains("state")) {
      config.set("state", "player");
    }

    return config.getString("state");
  }
}
