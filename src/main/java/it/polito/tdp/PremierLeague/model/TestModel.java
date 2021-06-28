package it.polito.tdp.PremierLeague.model;

public class TestModel {

	public static void main(String[] args) {
		
		Model model	= new Model();
		
		model.creaGrafo(0.5);
		
		System.out.println(model.getTopPlayer());
		
		System.out.println(model.dreamTeam(3));
	}

}
