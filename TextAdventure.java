/** Text adventure game. */
public class TextAdventure {

	public static void main(String[] args) {
		new TextAdventure().run();
	}

	/** Damage done by the best weapon the player has picked up. */
	private int bestWeaponDamage;

	/** The room where the player currently is. */
	private Room currentRoom;

	/** Total value of all treasures taken by the player. */
	private int score;

	public TextAdventure() {
		// Create rooms
		Room airlock = new Room("airlock", "a cramped sanitized passage, filled with oxygenated air.");
		Room hangar = new Room("hangar", "a spacious area filled with ships.");
		Room engineering_bay = new Room("engineering bay",
				"a cluttered room filled with whirring machinery and floating tools.");
		Room lab = new Room("lab", "a blindingly white room, reognizable from the debrief.");
		Room quarantine_center = new Room("quarantine center",
				"a depressing room filled with the wailing of patients.");
		Room hallway = new Room("hallway", "a narrow passage with handles on all sides.");
		Room escape_pod = new Room("escape pod", "an intimidating last resort.");
		// Create connections
		airlock.addNeighbor("north", hangar);
		hangar.addNeighbor("south", airlock);
		hangar.addNeighbor("west", engineering_bay);
		hangar.addNeighbor("north", hallway);
		engineering_bay.addNeighbor("east", hangar);
		hallway.addNeighbor("south", hangar);
		hallway.addNeighbor("east", quarantine_center);
		hallway.addNeighbor("north", lab);
		quarantine_center.addNeighbor("west", hallway);
		lab.addNeighbor("south", hallway);
		lab.addNeighbor("north", escape_pod);
		escape_pod.addNeighbor("south", lab);
		// Create and install monsters
		hangar.setMonster(new Monster("alien", 2, "an angry green alien guard."));
		hallway.setMonster(new Monster("android", 4, "a terrifying android death machine."));
		escape_pod
				.setMonster(new Monster("fungus", 6, "a dense cloud of shape-shifting fungus released from the lab."));
		// Create and install treasures
		hangar.setTreasure(new Treasure("crystals", 10, "an alien energy source made from crystals."));
		engineering_bay
				.setTreasure(new Treasure("orb", 20, "a glowing orb. You're not sure what it does. Looks valuable."));
		lab.setTreasure(new Treasure("notebook", 30, "a notebook. The target of your infiltration."));
		escape_pod.setTreasure(new Treasure("survival", 40, "survival: the most important treasure of all."));
		// Create and install weapons
		engineering_bay
				.setWeapon(new Weapon("raygun", 5, "an alien raygun covered in lights. It fires a yellow beam."));
		quarantine_center.setWeapon(new Weapon("flamethrower", 7,
				"a tried and true weapon: the flamethrower. It spits fire. What else do you need?"));
		// The player starts in the entrance, armed with a sword
		currentRoom = airlock;
		bestWeaponDamage = 3;
	}

	/**
	 * Attacks the specified monster and prints the result. If the monster is
	 * present, this either kills it (if the player's weapon is good enough) or
	 * results in the player's death (and the end of the game).
	 */
	public void attack(String name) {
		Monster monster = currentRoom.getMonster();
		if (monster != null && monster.getName().equals(name)) {
			if (bestWeaponDamage > monster.getArmor()) {
				StdOut.println("You strike it dead!");
				currentRoom.setMonster(null);
			} else {
				StdOut.println("Your blow bounces off harmlessly.");
				StdOut.println("The " + monster.getName() + " dismantles your body!");
				StdOut.println();
				StdOut.println("GAME OVER");
				System.exit(0);
			}
		} else {
			StdOut.println("There is no " + name + " here.");
		}
	}

	/** Moves in the specified direction, if possible. */
	public void go(String direction) {
		Room destination = currentRoom.getNeighbor(direction);
		if (destination == null) {
			StdOut.println("You can't go that way from here.");
		} else {
			currentRoom = destination;
		}
	}

	/** Handles a command read from standard input. */
	public void handleCommand(String line) {
		String[] words = line.split(" ");
		if (currentRoom.getMonster() != null && !(words[0].equals("attack") || words[0].equals("look"))) {
			StdOut.println("You can't do that with unfriendlies about.");
			listCommands();
		} else if (words[0].equals("attack")) {
			attack(words[1]);
		} else if (words[0].equals("go")) {
			go(words[1]);
		} else if (words[0].equals("look")) {
			look();
		} else if (words[0].equals("take")) {
			take(words[1]);
		} else {
			StdOut.println("I don't understand that.");
			listCommands();
		}
	}

	/** Prints examples of possible commands as a hint to the player. */
	public void listCommands() {
		StdOut.println("Examples of commands:");
		StdOut.println("  attack alien");
		StdOut.println("  go north");
		StdOut.println("  look");
		StdOut.println("  take crystals");
	}

	/** Prints a description of the current room and its contents. */
	public void look() {
		StdOut.println("You are in " + currentRoom.getDescription());
		if (currentRoom.getMonster() != null) {
			StdOut.println("There is " + currentRoom.getMonster().getDescription());
		}
		if (currentRoom.getWeapon() != null) {
			StdOut.println("There is " + currentRoom.getWeapon().getDescription());
		}
		if (currentRoom.getTreasure() != null) {
			StdOut.println("There is " + currentRoom.getTreasure().getDescription());
		}
		StdOut.println("Exits: " + currentRoom.listExits());
	}

	/** Runs the game. */
	public void run() {
		listCommands();
		StdOut.println();
		while (true) {
			StdOut.println("You are in the " + currentRoom.getName() + ".");
			StdOut.print("> ");
			handleCommand(StdIn.readLine());
			StdOut.println();
		}
	}

	/** Attempts to pick up the specified treasure or weapon. */
	public void take(String name) {
		Treasure treasure = currentRoom.getTreasure();
		Weapon weapon = currentRoom.getWeapon();
		if (treasure != null && treasure.getName().equals(name)) {
			currentRoom.setTreasure(null);
			score += treasure.getValue();
			StdOut.println("Your score is now " + score + " out of 100.");
			if (score == 100) {
				StdOut.println();
				StdOut.println("You escape the alien facility with the secret notes! YOU WIN!");
				System.exit(0);
			}
		} else if (weapon != null && weapon.getName().equals(name)) {
			currentRoom.setWeapon(null);
			if (weapon.getDamage() > bestWeaponDamage) {
				bestWeaponDamage = weapon.getDamage();
				StdOut.println("You'll be a more effective fighter with this!");
			}
		} else {
			StdOut.println("There is no " + name + " here.");
		}
	}

}
