package me.jnk.projecthades.enemies;

public class Bob implements Enemy{

    @Override
    public double getHealth() {
        return 20;
    }

    @Override
    public double getDamage() {
        return 8;
    }

    @Override
    public String getType() {
        return "Bob";
    }
}
