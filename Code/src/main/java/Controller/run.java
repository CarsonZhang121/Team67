package Controller;
import Model.InputFile;
import Model.SimulationMonitor;

public class run {
    public static void main(String[] args){
        // TODO: 1. read the file to initialize the monitor;
        InputFile input = new InputFile();
        SimulationMonitor monitorSim = new SimulationMonitor();

        if (args.length == 0) {
            System.out.println("ERROR: Test scenario file name not found.");
            return;
        }

        input.loadSetting(args[0]);
        monitorSim.initialize(input);

        while (monitorSim.issimulationOn()) {
            monitorSim.runOneTurn();
        }

        monitorSim.report();
    }
}
