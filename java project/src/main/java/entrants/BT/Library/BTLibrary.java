// ******************************************************* 
//                   MACHINE GENERATED CODE                
//                       DO NOT MODIFY                     
//                                                         
// Generated on 10/26/2018 09:49:42
// ******************************************************* 
package entrants.BT.Library;

/**
 * BT library that includes the trees read from the following files:
 * <ul>
 * <li>config/BehaviourTree/BTs/Blinky.xbt</li>
 * <li>config/BehaviourTree/BTs/Inky.xbt</li>
 * <li>config/BehaviourTree/BTs/MsPacMan.xbt</li>
 * <li>config/BehaviourTree/BTs/Pinky.xbt</li>
 * <li>config/BehaviourTree/BTs/Sue.xbt</li>
 * </ul>
 */
public class BTLibrary implements jbt.execution.core.IBTLibrary {
	/** Tree generated from file config/BehaviourTree/BTs/Blinky.xbt. */
	private static jbt.model.core.ModelTask Blinky;
	/** Tree generated from file config/BehaviourTree/BTs/Inky.xbt. */
	private static jbt.model.core.ModelTask Inky;
	/** Tree generated from file config/BehaviourTree/BTs/MsPacMan.xbt. */
	private static jbt.model.core.ModelTask MsPacMan;
	/** Tree generated from file config/BehaviourTree/BTs/Pinky.xbt. */
	private static jbt.model.core.ModelTask Pinky;
	/** Tree generated from file config/BehaviourTree/BTs/Sue.xbt. */
	private static jbt.model.core.ModelTask Sue;

	/* Static initialization of all the trees. */
	static {
		Blinky = new jbt.model.task.composite.ModelRandomSelector(null,
				new entrants.BT.Model.Actions.MoveRight(null),
				new entrants.BT.Model.Actions.MoveLeft(null),
				new entrants.BT.Model.Actions.MoveUp(null),
				new entrants.BT.Model.Actions.MoveDown(null));

		Inky = new jbt.model.task.composite.ModelRandomSelector(null,
				new entrants.BT.Model.Actions.MoveRight(null),
				new entrants.BT.Model.Actions.MoveLeft(null),
				new entrants.BT.Model.Actions.MoveUp(null),
				new entrants.BT.Model.Actions.MoveDown(null));

		MsPacMan = new jbt.model.task.composite.ModelRandomSelector(null,
				new entrants.BT.Model.Actions.MoveRight(null),
				new entrants.BT.Model.Actions.MoveLeft(null),
				new entrants.BT.Model.Actions.MoveUp(null),
				new entrants.BT.Model.Actions.MoveDown(null));

		Pinky = new jbt.model.task.composite.ModelRandomSelector(null,
				new entrants.BT.Model.Actions.MoveRight(null),
				new entrants.BT.Model.Actions.MoveLeft(null),
				new entrants.BT.Model.Actions.MoveUp(null),
				new entrants.BT.Model.Actions.MoveDown(null));

		Sue = new jbt.model.task.composite.ModelRandomSelector(null,
				new entrants.BT.Model.Actions.MoveRight(null),
				new entrants.BT.Model.Actions.MoveLeft(null),
				new entrants.BT.Model.Actions.MoveUp(null),
				new entrants.BT.Model.Actions.MoveDown(null));

	}

	/**
	 * Returns a behaviour tree by its name, or null in case it cannot be found.
	 * It must be noted that the trees that are retrieved belong to the class,
	 * not to the instance (that is, the trees are static members of the class),
	 * so they are shared among all the instances of this class.
	 */
	public jbt.model.core.ModelTask getBT(java.lang.String name) {
		if (name.equals("Blinky")) {
			return Blinky;
		}
		if (name.equals("Inky")) {
			return Inky;
		}
		if (name.equals("MsPacMan")) {
			return MsPacMan;
		}
		if (name.equals("Pinky")) {
			return Pinky;
		}
		if (name.equals("Sue")) {
			return Sue;
		}
		return null;
	}

	/**
	 * Returns an Iterator that is able to iterate through all the elements in
	 * the library. It must be noted that the iterator does not support the
	 * "remove()" operation. It must be noted that the trees that are retrieved
	 * belong to the class, not to the instance (that is, the trees are static
	 * members of the class), so they are shared among all the instances of this
	 * class.
	 */
	public java.util.Iterator<jbt.util.Pair<java.lang.String, jbt.model.core.ModelTask>> iterator() {
		return new BTLibraryIterator();
	}

	private class BTLibraryIterator
			implements
			java.util.Iterator<jbt.util.Pair<java.lang.String, jbt.model.core.ModelTask>> {
		static final long numTrees = 5;
		long currentTree = 0;

		public boolean hasNext() {
			return this.currentTree < numTrees;
		}

		public jbt.util.Pair<java.lang.String, jbt.model.core.ModelTask> next() {
			this.currentTree++;

			if ((this.currentTree - 1) == 0) {
				return new jbt.util.Pair<java.lang.String, jbt.model.core.ModelTask>(
						"Blinky", Blinky);
			}

			if ((this.currentTree - 1) == 1) {
				return new jbt.util.Pair<java.lang.String, jbt.model.core.ModelTask>(
						"Inky", Inky);
			}

			if ((this.currentTree - 1) == 2) {
				return new jbt.util.Pair<java.lang.String, jbt.model.core.ModelTask>(
						"MsPacMan", MsPacMan);
			}

			if ((this.currentTree - 1) == 3) {
				return new jbt.util.Pair<java.lang.String, jbt.model.core.ModelTask>(
						"Pinky", Pinky);
			}

			if ((this.currentTree - 1) == 4) {
				return new jbt.util.Pair<java.lang.String, jbt.model.core.ModelTask>(
						"Sue", Sue);
			}

			throw new java.util.NoSuchElementException();
		}

		public void remove() {
			throw new java.lang.UnsupportedOperationException();
		}
	}
}
