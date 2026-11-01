package io.github.josseboxnice.zombieArenaPlugin.commands;

import io.papermc.paper.command.brigadier.CommandSourceStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.command.CommandSender;

import io.papermc.paper.command.brigadier.BasicCommand;

public class ZombieArenaCommand implements BasicCommand {

  @Override
  public void execute(final CommandSourceStack source, final String[] args) {
    System.out.println("hello");
    return;
  }

  @Override
  public boolean canUse(final CommandSender sender) {
    return true;
    /*
     * if (sender.isOp()) {
     * return true;
     * }
     */
  }

  @Override
  public Collection<String> suggest(final CommandSourceStack source, final String[] args) {
    List<String> list = new ArrayList<String>();
    list.add("zombiearena");
    list.add("za");
    return list;
  }
}
