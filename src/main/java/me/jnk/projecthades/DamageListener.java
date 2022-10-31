package me.jnk.projecthades;

import me.jnk.projecthades.enemies.Bob;
import me.jnk.projecthades.enemies.Enemy;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.MetadataValue;

import java.util.ArrayList;
import java.util.List;

public class DamageListener implements Listener {
    ArrayList<Enemy> enemies = new ArrayList<>();

    private int enemyIndex(Object type) {
        for(int i = 0; i < enemies.size(); i++) {
            if(enemies.get(0).getType().equals(type)){
                return i;
            }
        }
        System.out.println("Enemy not found");
        return 0;
    }

    public DamageListener() {
        enemies.add(new Bob());
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {

        if(e.getDamager() instanceof LivingEntity) {
            if(e.getDamager() instanceof Player) {
                int dmg = MyListener.getPlayer(e.getDamager().getName()).getPlayerStats().getDamage();

                if(!(e.getEntity() instanceof ArmorStand)) {
                    ((LivingEntity)e.getEntity()).damage(dmg);

                }

                System.out.println("Damage: " + dmg);
            }
            else{
                List<MetadataValue> values = e.getDamager().getMetadata("type");
                Object type = values.get(0).value();
                System.out.println(type);
                double dmg = enemies.get(enemyIndex(type)).getDamage();
                ((LivingEntity)e.getEntity()).damage(dmg);


            }

            e.setCancelled(true);
        }
        else{
            System.out.println("was not done by mob");
        }



    }

}
