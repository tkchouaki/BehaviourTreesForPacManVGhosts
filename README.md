# Testing Java Behaviour Trees in the PacManVGhosts Environment.This projet is based on The [Ms. Pac-Man Vs. Ghost Team Competition Environment](https://github.com/solar-1992/PacManEngine).We implemented ghosts agents that use behaviour trees to decide what to do.The knowledge of each ghost is retrieved from the game engine & kept in a custom structure. We use [graphstream](http://graphstream-project.org) to visualize the knowledge of each ghost.We use the [JBT Library](https://github.com/gaia-ucm/jbt) to define the behaviour tree used by the ghosts.## How to run it.The program can be launched directly using the `Main` class, you can provide the Following command line arguments : * -e,--execution <arg>: The type of execution (normal or experience). Default is normal.* -gs,--graphstream <arg> : only useful when running in normal mode, no means do not use graphstream to visualze the knowledge of each ghost everything else means yes. Default is yes.*  -n,--experiencesNumber <arg>: only useful when running in experience mode, the number of experiences to run for each chasing mode in the experience execution mode, should be >=1. Default is 10.Launching the program without specifying any argument will display two Windows : one for the game and one displaying the knowledge of each ghost agent using graphstream.## Contact* [Tarek CHOUAKI](mailto:tkchouaki@icloud.com)* [Pierre DUBAILLAY](mailto:pierredubaillay@outlook.fr)* [Tristan DE BLAUWE](mailto:tdb.work@outlook.fr)