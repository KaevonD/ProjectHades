package me.jnk.projecthades;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {

        if(e.getDamager() instanceof LivingEntity) {
            if(e.getDamager() instanceof Player) {
                int dmg = MyListener.getPlayer(e.getDamager().getName()).getPlayerStats().getDamage();

                if(!(e.getEntity() instanceof ArmorStand)) {
                    ((LivingEntity)e.getEntity()).damage(dmg);
                }

                System.out.println("Damage: " + dmg);
                e.setCancelled(true);
            }

        }
        else{
            System.out.println("was not done by mob");
        }


    }

}
