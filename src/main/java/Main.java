import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Main extends JavaPlugin implements Listener, Runnable {
    boolean enabled = false;

    public void onEnable() {
        this.getServer().getPluginManager().registerEvents((Listener)this, (Plugin)this);
    }
    //Begins gliding
    public void startGlide() {
        for (final Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.setGliding(true);
        }
        Bukkit.getScheduler().runTaskTimer(this, this, 0L, 100L);
    }

    //Every player that joins is gliding too
    @EventHandler
    public void onPlayerJoinEvent(final PlayerJoinEvent event) {
        if (enabled) {
            event.getPlayer().setGliding(true);
        }
    }

    //Stops gliders on disable
    @EventHandler
    public void onEntityToggleGlideEvent(final EntityToggleGlideEvent event) {
        if (enabled && event.getEntity() instanceof Player) {
            event.setCancelled(true);
        }
    }

    //Potion effects
    @Override
    public void run() {
        if (enabled) {
            for (final Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                onlinePlayer.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100, 1));
                onlinePlayer.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100, 0));
            }
        }
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (command.getName().equals("togglefly")) {
            enabled = !enabled;
            if (enabled) {
                startGlide();
            }
            return true;
        }
        return false;
    }
}
