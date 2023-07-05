package se233.chapter1.model.character;

import se233.chapter1.model.DamageType;
import se233.chapter1.model.item.Armor;
import se233.chapter1.model.item.Weapon;

public class BasedCharacter {
    protected String name, imgpath;
    protected DamageType type;
    protected Integer fullHp, basedPow, basedDef, basedRes;
    protected Integer hp, power, defense, resistance;
    protected Weapon weapon;
    protected Armor armor;

    public String getName() {
        return name;
    }

    public String getImagepath() {
        return imgpath;
    }

    public Integer getHp() {
        return hp;
    }

    public Integer getFullHp() {
        return fullHp;
    }

    public Integer getPower() {
        return power;
    }

    public Integer getDefense() {
        return defense;
    }

    public Integer getResistance() {
        return resistance;
    }

    //1.39
    public void equipWeapon(Weapon weapon,boolean isRefresh) {

        this.weapon = weapon;
        this.power = isRefresh ? this.basedPow : this.basedPow + weapon.getPower();
    }

    public void equipArmor(Armor armor,boolean isRefresh) {
        this.armor = armor;
        this.defense = isRefresh ? this.basedDef : this.basedDef + armor.getDefense();
        this.resistance = isRefresh ? this.basedRes : this.basedRes + armor.getResistance();
    }

    //1.39
    @Override
    public String toString() {
        return name;
    }

    public DamageType getType() {
        return type;
    }
}
