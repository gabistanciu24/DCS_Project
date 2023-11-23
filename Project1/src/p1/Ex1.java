package p1;

import java.util.ArrayList;

import Components.Activation;
import Components.Condition;
import Components.GuardMapping;
import Components.PetriNet;
import Components.PetriNetWindow;
import Components.PetriTransition;
import DataObjects.DataFloat;
import DataObjects.DataInteger;
import DataObjects.DataREL;
import DataObjects.DataRELQueue;
import DataObjects.DataTransfer;
import DataOnly.TransferOperation;
import Enumerations.LogicConnector;
import Enumerations.TransitionCondition;
import Enumerations.TransitionOperation;

public class Ex1 {

	public static void main(String[] args) {
		// client OER_TPN 
		PetriNet pn = new PetriNet();
		pn.PetriNetName = "client";
		pn.NetworkPort = 1080;

		DataFloat p10 = new DataFloat();
		p10.SetName("p10");
		p10.SetValue(1.0f);
		pn.PlaceList.add(p10);

		
		DataFloat p11 = new DataFloat();
		p11.SetName("p11");
		pn.PlaceList.add(p11);
		
		DataTransfer p13 = new DataTransfer();
		p13.SetName("p13");
		p13.Value = new TransferOperation("localhost", "1081", "p21");
		pn.PlaceList.add(p13);
		
		DataFloat p14 = new DataFloat();
		p14.SetName("p14");
		pn.PlaceList.add(p14);
		
		DataFloat p15 = new DataFloat();
		p15.SetName("p15");
		pn.PlaceList.add(p15);
		
		DataFloat p16 = new DataFloat();
		p16.SetName("p16");
		pn.PlaceList.add(p16);
		
		
		// T1 ------------------------------------------------
		PetriTransition t11 = new PetriTransition(pn);
		t11.TransitionName = "t11";
		t11.InputPlaceName.add("p11");
		t11.InputPlaceName.add("p10");
		
		Condition T11Ct1 = new Condition(t11, "p11", TransitionCondition.NotNull);
		Condition T11Ct2 = new Condition(t11, "p10", TransitionCondition.NotNull);
		
		T11Ct1.SetNextCondition(LogicConnector.AND, T11Ct2);
		
		GuardMapping grdT11 = new GuardMapping();
		grdT11.condition = T11Ct1;

		grdT11.Activations.add(new Activation(t11,"p10" , TransitionOperation.Move, "p14"));
		grdT11.Activations.add(new Activation(t11, "p11", TransitionOperation.SendOverNetwork, "p13"));
		
		t11.GuardMappingList.add(grdT11);
		pn.Transitions.add(t11);

		// T2---------------------------------------------------------
		PetriTransition t12 = new PetriTransition(pn);
		t12.TransitionName = "t12";
		t12.InputPlaceName.add("p14");
		t12.InputPlaceName.add("p15");
		
		Condition T12Ct1 = new Condition(t12, "p14", TransitionCondition.NotNull);
		Condition T12Ct2 = new Condition(t12, "p15", TransitionCondition.NotNull);
		
		T12Ct1.SetNextCondition(LogicConnector.AND, T12Ct2);
		
		GuardMapping grdT12 = new GuardMapping();
		grdT12.condition = T12Ct1;

		grdT12.Activations.add(new Activation(t12,"p15" , TransitionOperation.Move, "p16"));
		grdT12.Activations.add(new Activation(t12, "p15", TransitionOperation.Move, "p10"));
		
		t12.GuardMappingList.add(grdT12);
		pn.Transitions.add(t12);
		
		PetriNetWindow frame = new PetriNetWindow(false);
        frame.petriNet = pn;
        frame.setVisible(true);

	/***********************************************/
	// server OER_TPN 
	PetriNet server = new PetriNet();
	server.PetriNetName = "server";
	server.NetworkPort = 1081;

	DataFloat p20 = new DataFloat();
	p20.SetName("p20");
	p20.SetValue(1.0f);
	server.PlaceList.add(p20);

	DataFloat p21 = new DataFloat();
	p21.SetName("p21");
	server.PlaceList.add(p21);
	
	DataFloat p22 = new DataFloat();
	p22.SetName("p22");
	server.PlaceList.add(p22);

	DataTransfer p23 = new DataTransfer();
	p23.SetName("p23");
	p23.Value = new TransferOperation("localhost", "1080", "p15");
	server.PlaceList.add(p23);

	DataFloat subConstantValue1 = new DataFloat();
	subConstantValue1.SetName("subConstantValue1");
	subConstantValue1.SetValue(0.01f);
	server.ConstantPlaceList.add(subConstantValue1);
	

	// T1 ------------------------------------------------
	PetriTransition t21 = new PetriTransition(server);
	t21.TransitionName = "t21";
	t21.InputPlaceName.add("p21");
	t21.InputPlaceName.add("p20");
	
	Condition T21Ct1 = new Condition(t21, "p21", TransitionCondition.NotNull);
	Condition T21Ct2 = new Condition(t21, "p20", TransitionCondition.NotNull);
	
	T11Ct1.SetNextCondition(LogicConnector.AND, T11Ct2);
	
	GuardMapping grdT21 = new GuardMapping();
	grdT21.condition = T21Ct1;

	ArrayList<String> lstInput = new ArrayList<String>();
	lstInput.add("p21");
	lstInput.add("subConstantValue1");
	grdT21.Activations.add(new Activation(t21, lstInput, TransitionOperation.Prod, "p22"));
	
	t21.GuardMappingList.add(grdT21);
	server.Transitions.add(t21);

	// T2---------------------------------------------------------
	PetriTransition t22 = new PetriTransition(server);
	t22.TransitionName = "t22";
	t22.InputPlaceName.add("p22");
	
	Condition T22Ct1 = new Condition(t22, "p22", TransitionCondition.NotNull);
	
	GuardMapping grdT22 = new GuardMapping();
	grdT22.condition = T22Ct1;

	grdT22.Activations.add(new Activation(t22,"p22" , TransitionOperation.SendOverNetwork, "p23"));
	grdT22.Activations.add(new Activation(t22, "p22", TransitionOperation.Move, "p20"));
	
	t22.GuardMappingList.add(grdT22);
	server.Transitions.add(t22);
	
	PetriNetWindow frame2 = new PetriNetWindow(false);
    frame2.petriNet = server;
    frame2.setVisible(true);
	}
}
