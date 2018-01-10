import java.util.ArrayList;

/** A room */
public class Room {

	/** This room's description. */
	private String description;

	/** This room's name. */
	private String name;

	/** A weapon in this room. */
	private Weapon weapon;

	/** A monster in this room. */
	private Monster monster;

	/** A treasure in this room. */
	private Treasure treasure;

	/** A list of this room's neighbors. */
	private ArrayList<Room> neighbors = new ArrayList<Room>();

	/** A list of this room's exits. */
	private ArrayList<String> exits = new ArrayList<String>();

	/**
	 * @param name
	 *            A name for the room
	 * @param description
	 *            A description for the room
	 */
	public Room(String name, String description) {
		this.name = name;
		this.description = description;
	}

	/**
	 * @param direction
	 *            The direction of the neighbor
	 * @param name
	 *            The neighbor's name
	 */
	public void addNeighbor(String direction, Room name) {
		exits.add(direction);
		neighbors.add(name);

	}

	/**
	 * Sets a monster in the room
	 * 
	 * @param monster
	 *            The monster in the room
	 * 
	 */
	public void setMonster(Monster monster) {
		this.monster = monster;

	}

	/**
	 * Sets a treasure in the room
	 * 
	 * @param treasure
	 *            The treasure in the room
	 * 
	 */
	public void setTreasure(Treasure treasure) {
		this.treasure = treasure;

	}

	/**
	 * Sets a weapon in the room
	 * 
	 * @param weapon
	 *            The weapon in the room
	 * 
	 */
	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;

	}

	/** Returns the name of the room. */
	public String getName() {
		return name;
	}

	/** Return's the description of the room. */
	public String getDescription() {
		return description;
	}

	/**
	 * 
	 * @param direction
	 *            The direction of the neighbor
	 * @return the neighbor
	 */
	public Room getNeighbor(String direction) {
		int index = exits.indexOf(direction);
		return neighbors.get(index);
	}

	/** Lists the exits. */
	public String listExits() {
		return exits.toString();

	}

	/** Returns the monster in the room. */
	public Monster getMonster() {
		return monster;
	}

	/** Returns the treasure in the room. */
	public Treasure getTreasure() {
		return treasure;
	}

	/** Returns the weapon in the room. */
	public Weapon getWeapon() {
		return weapon;
	}
}
