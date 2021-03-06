package net.richardprojects.autismchat3.commands;

import java.util.List;
import java.util.UUID;

import net.richardprojects.autismchat3.ACParty;
import net.richardprojects.autismchat3.AutismChat3;
import net.richardprojects.autismchat3.Messages;
import net.richardprojects.autismchat3.Utils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MeCommand implements CommandExecutor {

	private AutismChat3 plugin;
	
	public MeCommand(AutismChat3 plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(final CommandSender sender, Command arg1, String arg2,
			final String[] args) {
		if (sender instanceof Player) {
			if (args.length > 0) {
				plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {
					public void run() {
						Player player = (Player) sender;
						String msg = "";
						for(int i = 0; i < args.length; i++) {
							msg = msg + args[i] + " ";
						}
						
						int partyId = plugin.getACPlayer(player.getUniqueId()).getPartyId();
						ACParty party = plugin.getACParty(partyId);
						
						if (party != null) {
							for (UUID uuid : party.getMembers()) {
								Player cPlayer = plugin.getServer().getPlayer(uuid);
								
								if (cPlayer != null) {
									String name = Utils.formatName(plugin, player.getUniqueId(), cPlayer.getUniqueId());
									cPlayer.sendMessage(name + " " +  msg);
								}
							}
						}
					}					
				});				
				return true;
			} else {
				String msg = Utils.colorCodes(Messages.prefix_Bad + Messages.error_invalidArgs);
				sender.sendMessage(msg);
				return false;
			}
		} else {
			String msg = Utils.colorCodes(Messages.prefix_Bad + "&7You cannot use this command from the console");
			sender.sendMessage(msg);
			return true;
		}
		
	}
}
