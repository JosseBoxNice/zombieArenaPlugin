package io.github.josseboxnice.zombieArenaPlugin.commands;

import io.papermc.paper.command.brigadier.CommandSourceStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.josseboxnice.zombieArenaPlugin.RoleSwitcherManager;
import io.papermc.paper.command.brigadier.BasicCommand;

public class ZombieArenaCommand implements BasicCommand {
  private final JavaPlugin plugin;

  public ZombieArenaCommand(JavaPlugin plugin) {
    this.plugin = plugin;
  }

  @Override
  public void execute(final CommandSourceStack source, final String[] args) {
    CommandSender sender = source.getSender();

    if (args.length == 0) {
      sender.sendRichMessage("<red>Usage: /zombiearena role");
      return;
    }

    if (args[0].equals("role")) {

      if (args.length == 1) {
        sender.sendRichMessage("<red>Usage: /zombiearena role <player | admin>");
        return;
      }

      Player player = null;
      try {
        player = source.getPlayerOrThrow();
      } catch (CommandSyntaxException e) {
        sender.sendPlainMessage("Only players can use this command");
        return;
      }

      String currentState = RoleSwitcherManager.getState(player, plugin);

      if (args[1].equals(currentState)) {
        player.sendRichMessage("<red>Can't change from " + args[1] + " -> " + args[1] + "!");
        return;
      }

      switch (args[1]) {
        case "player" -> {
          RoleSwitcherManager.saveState(player, "admin", plugin);
          RoleSwitcherManager.loadState(player, "player", plugin);
        }
        case "admin" -> {
          RoleSwitcherManager.saveState(player, "player", plugin);
          RoleSwitcherManager.loadState(player, "admin", plugin);
        }
        default -> {
          source.getSender().sendRichMessage("<red>Invalid role! Use player or admin.");
          return;
        }
      }
    } else {
      source.getSender().sendRichMessage("<red>Usage: /zombiearena role");
      return;
    }
  }

  @Override
  public boolean canUse(final CommandSender sender) {
    if (sender.isOp()) {
      return true;
    }
    return false;
  }

  @Override
  public Collection<String> suggest(final CommandSourceStack source, final String[] args) {
    List<String> list = new ArrayList<String>();

    switch (args.length) {
      case 0, 1:
        list.add("role");
        return list;
      case 2:
        list.add("player");
        list.add("admin");
        return list;
      default:
        return list;
    }
  }
}
