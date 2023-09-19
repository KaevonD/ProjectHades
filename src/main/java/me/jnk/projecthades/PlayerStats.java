package me.jnk.projecthades;

public class PlayerStats {

    private int health;
    private int damage;

    public PlayerStats() {
        health = 100;
        damage = 100;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
