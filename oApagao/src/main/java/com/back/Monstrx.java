package com.back;

public abstract class Monstrx {
    private String name;
    private int health;
    private int atk;
    private int def;

    public Monstrx(String name, int health, int atk, int def) {
        this.name = name;
        this.health = health;
        this.atk = atk;
        this.def = def;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
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

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public void setDef(int def) {
        this.def = def;
    }
}