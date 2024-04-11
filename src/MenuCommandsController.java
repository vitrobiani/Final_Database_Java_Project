public class MenuCommandsController {
    public PairSet set = new PairSet();
    public static Creator creator = new CommandCreator();

    public MenuCommandsController(int choice) {
        set.addPair("command", menuOptions.values()[choice-1]);
    }

    public Command getCommand() {
        return (Command) creator.create(set);
    }
}