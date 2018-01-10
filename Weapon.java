/** a weapon */
public class Weapon {
	/** A description of this weapon. */
	private String description;

	/** This weapon's name. */
	private String name;

	/** The damage of this weapon. */
	private int damage;

	/**
	 * @param name
	 *            This weapon's name.
	 * @param damage
	 *            The damage of this weapon.
	 * @param description
	 *            A description of this weapon.
	 */
	public Weapon(String name, int damage, String description) {
		this.name = name;
		this.damage = damage;
		this.description = description;
	}

	/** Returns a description of this weapon. */
	public String getDescription() {
		return description;
	}

	/** Returns this weapon's name. */
	public String getName() {
		return name;
	}

	/** Returns the damage of this weapon. */
	public int getDamage() {
		return damage;
	}

}
