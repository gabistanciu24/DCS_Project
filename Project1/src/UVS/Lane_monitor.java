package UVS;


import Components.Activation;
import Components.Condition;
import Components.GuardMapping;
import Components.PetriNet;
import Components.PetriNetWindow;
import Components.PetriTransition;
import DataObjects.DataCar;
import DataObjects.DataCarQueue;
import DataObjects.DataString;
import DataObjects.DataTransfer;
import DataOnly.TransferOperation;
import Enumerations.LogicConnector;
import Enumerations.TransitionCondition;
import Enumerations.TransitionOperation;


public class Lane_monitor {
    public static void main(String[] args) {
        PetriNet pn = new PetriNet();
        pn.PetriNetName = "Main Petri";
        pn.NetworkPort = 1081;

        DataString in = new DataString();
        in.SetName("in");
        pn.PlaceList.add(in);

        DataString p0 = new DataString();
        p0.SetName("P0");
        p0.SetValue("signal");
        pn.PlaceList.add(p0);

        DataString p1 = new DataString();
        p1.SetName("P1");
        pn.PlaceList.add(p1);

        // T0 ------------------------------------------------

        PetriTransition t0 = new PetriTransition(pn);
        t0.TransitionName = "t0";
        t0.InputPlaceName.add("in");
        t0.InputPlaceName.add("P0");

        Condition T0Ct1 = new Condition(t0,"in",TransitionCondition.NotNull);
        Condition T0Ct2 = new Condition(t0,"P0",TransitionCondition.NotNull);
        T0Ct1.SetNextCondition(LogicConnector.AND, T0Ct2);

        GuardMapping grdT0 = new GuardMapping();
        grdT0.condition= T0Ct1;
        grdT0.Activations.add(new Activation(t0, "in", TransitionOperation.Move, "P1"));
        grdT0.Activations.add(new Activation(t0, "P0", TransitionOperation.Move, "P0"));
        t0.GuardMappingList.add(grdT0);

        t0.Delay = 0;
        pn.Transitions.add(t0);

        System.out.println("Lane started \n ------------------------------");
        pn.Delay = 2000;
        //pn.Start();

        PetriNetWindow frame = new PetriNetWindow(false);
        frame.petriNet = pn;
        frame.setVisible(true);
    }

}
