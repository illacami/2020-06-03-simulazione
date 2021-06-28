package it.polito.tdp.PremierLeague.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	private PremierLeagueDAO dao;
	private Graph<Player, DefaultWeightedEdge> grafo;
	private Map<Integer, Player> idPlayer;
	private Map<Integer, Player> vertici;
	private List<Player> best ;
	
	public Model() {
		dao = new PremierLeagueDAO();
		idPlayer = new HashMap<Integer, Player>();
		dao.listAllPlayers(idPlayer);
	}
	
	
	public Map<Integer,Player> getVertici(double minGoal){
		
		vertici = new HashMap<Integer, Player>();
		dao.getVertici(minGoal, vertici);
		return vertici;
	}
	
	public List<Adiacenza> getAdiacenze() {
		return dao.getAdiacenze(idPlayer);
	}
	
	public void creaGrafo(double minGoal) {
		
		grafo = new SimpleDirectedWeightedGraph<Player, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		//aggiungo vertici
		Graphs.addAllVertices(grafo, this.getVertici(minGoal).values());
		
		//aggiungo archi
		for(Adiacenza a : this.getAdiacenze()) {
			if(grafo.containsVertex(a.getP1()) && grafo.containsVertex(a.getP2())) {
				if(a.getPeso() < 0) {
					//arco da p2 a p1
					Graphs.addEdgeWithVertices(grafo, a.getP2(), a.getP1(), ((double) -1)*a.getPeso());
				} else if(a.getPeso() > 0){
					//arco da p1 a p2
					Graphs.addEdgeWithVertices(grafo, a.getP1(), a.getP2(), a.getPeso());
				}

			}
		}
		
		System.out.println("Creato grafo\n# Vertici: "+grafo.vertexSet().size()+"\n# Archi: "+grafo.edgeSet().size());
		
	}
	
	public int getVertexSize() {
		return grafo.vertexSet().size();
	}
	
	public int getEdgeSize() {
		return grafo.edgeSet().size();
	}
	
	public List<Player> getTopPlayer(){
		
		List<Player> classifica = new LinkedList<Player>();
		Player best = null;
		int maxDegree = 0;
		
		for(Player vertice : grafo.vertexSet()) 
			if(grafo.outDegreeOf(vertice) > maxDegree) {
				maxDegree = grafo.outDegreeOf(vertice);
				best = vertice;
			}
		
		List<DefaultWeightedEdge> archiBest = new LinkedList<DefaultWeightedEdge>(grafo.outgoingEdgesOf(best));
		
		
		while(!archiBest.isEmpty()) {
			DefaultWeightedEdge trovato = null;
			Player menoScarso = null;
			double maxDe = 0;
			for(DefaultWeightedEdge edge : archiBest) {
				if(grafo.getEdgeWeight(edge) > maxDe) {
					maxDe = grafo.getEdgeWeight(edge);
					menoScarso = grafo.getEdgeTarget(edge);
					trovato = edge;
				}
			}
			
			archiBest.remove(trovato);
			classifica.add(menoScarso);
		}
		
		classifica.add(0, best);
		
		return classifica;
	}
	
	public List<Player> dreamTeam(int numGiocatori){
		
		List<Player> parziale = new LinkedList<Player>();
		
		best = new LinkedList<Player>();
		
		cerca(parziale, numGiocatori, parziale);
		
		return best;
		
	}
	
	public void cerca(List<Player> parziale, int numGiocatori, List<Player> rimanenti) {
		
		// caso terminale 
		if(parziale.size()==numGiocatori) 
			if(this.gradoTitolarita(parziale) > this.gradoTitolarita(best)) {
				best = new LinkedList<Player>(parziale);
				return;
			}
		
		for(Player p : this.grafo.vertexSet()) {
			if(!parziale.contains(p)) {
				parziale.add(p);
				//i "battuti" di p non possono più essere considerati
				List<Player> remainingPlayers = new LinkedList<>(this.grafo.vertexSet());
				remainingPlayers.removeAll(Graphs.successorListOf(grafo, p));
				cerca(parziale, numGiocatori, remainingPlayers);
				parziale.remove(p);
				
			}
		}
	}

	public double gradoTitolarita(List<Player> parziale) {
		
		if(parziale.isEmpty() || parziale == null)
			return 0.0;
		
		double titolarita = -1.0;
		for(Player player : parziale)
		{
		for(DefaultWeightedEdge edge : grafo.incomingEdgesOf(player)) 
			titolarita -= grafo.getEdgeWeight(edge);
		
		for(DefaultWeightedEdge edge : grafo.outgoingEdgesOf(player))
			titolarita += grafo.getEdgeWeight(edge);
		}
		return titolarita;
		
	}
}
