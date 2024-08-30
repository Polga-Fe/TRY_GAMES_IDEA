package com.back;

public abstract class Personx {
    private String name;
    private int health;
    private int maxHealth;
    private int mana;
    private int atk;
    private int def;
    private double x; // Coordenada X
    private double y; // Coordenada Y

    public Personx(String name, double x, double y, int health, int maxHealth, int mana, int atk, int def) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.health = health;
        this.maxHealth = maxHealth;
        this.mana = mana;
        this.atk = atk;
        this.def = def;
    }

    public boolean isAlive() {
        return this.health > 0;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getMana() {
        return mana;
    }

    public int getAtk() {
        return atk;
    }

    public int getDef() {
        return def;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public void setDef(int def) {
        this.def = def;
    }

    // Métodos para coordenadas
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
