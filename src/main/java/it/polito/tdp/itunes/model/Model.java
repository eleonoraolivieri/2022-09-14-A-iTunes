package it.polito.tdp.itunes.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.itunes.db.ItunesDAO;



public class Model {
	
	private ItunesDAO dao;
	private Graph<Album, DefaultEdge> grafo;
	private Map<Integer, Album> idMap;
	private List<Album> album;
	private List<Album> listaMigliore;
	
	public Model() {
		dao = new ItunesDAO();
		idMap = new HashMap<Integer, Album>();
		dao.getAllAlbums(idMap);
	}
	
	public void creaGrafo(int durata) {
		
		this.grafo = new SimpleGraph<>(DefaultEdge.class);
		
		this.album = dao.getVertici(durata,idMap);
		Graphs.addAllVertices(grafo, album);
		
		for(Adiacenza a : dao.getAdiacenze(durata,idMap)) {
			if(this.grafo.containsVertex(a.getV1()) && 
					this.grafo.containsVertex(a.getV2())) {
				DefaultEdge e = this.grafo.getEdge(a.getV1(), a.getV2());
				if(e == null) 
					Graphs.addEdgeWithVertices(grafo, a.getV1(), a.getV2());
			}
		}
		
		System.out.println("VERTICI: " + this.grafo.vertexSet().size());
		System.out.println("ARCHI: " + this.grafo.edgeSet().size());
	}
	
	public int componentiConnesse(Album album) {
		ConnectivityInspector<Album, DefaultEdge> inspector = new ConnectivityInspector<Album, DefaultEdge>(this.grafo);
		return inspector.connectedSetOf(album).size();
	}
	
	public double durataConnesse(Album album) {
		ConnectivityInspector<Album, DefaultEdge> inspector = new ConnectivityInspector<Album, DefaultEdge>(this.grafo);
		Set<Album> lista = inspector.connectedSetOf(album);
		double durataTot = 0;
		for(Album a: lista) {
			durataTot = durataTot + a.getDurata();
		}
		
		return durataTot;
	}
	
	public List<Album> getVertici(){
		return new ArrayList<Album>(this.grafo.vertexSet());
	}
	
	public int getNVertici(){
		return this.grafo.vertexSet().size();
	}
	
	
	/**
	 * Metodo che restituisce il numero di archi del grafo
	 * @return
	 */
	public int getNArchi(){
		return this.grafo.edgeSet().size();
	}
	

	
	public List<Album> cercaLista(Album a, int dTot){
		//recupero la componenete connessa di c
		Set<Album> componenteConnessa;
		ConnectivityInspector<Album, DefaultEdge> ci = 
				new ConnectivityInspector<>(this.grafo);
		componenteConnessa = ci.connectedSetOf(a);
		
		List<Album> albumValidi = new ArrayList<Album>();
		albumValidi.add(a);
		componenteConnessa.remove(a);
		albumValidi.addAll(componenteConnessa);
		
		List<Album> parziale = new ArrayList<>();
		listaMigliore = new ArrayList<>();
		parziale.add(a);
		
		cerca(parziale,albumValidi,dTot, 1);
		
		return listaMigliore;
	}
	
	private void cerca(List<Album> parziale, List<Album> albumValidi, int dTot, int L) {
		
		if(sommaMemoria(parziale) > dTot)
			return;
		
		//parziale è valida
		if(parziale.size() > listaMigliore.size()) {
			listaMigliore = new ArrayList<>(parziale);
		}
		
		if(L == albumValidi.size())
			return;
		
		parziale.add(albumValidi.get(L));
		cerca(parziale, albumValidi,dTot, L +1);
		parziale.remove(albumValidi.get(L));
		cerca(parziale,albumValidi,dTot, L+1);
	}
	
	private double sommaMemoria (List<Album> album) {
		double somma = 0;
		for(Album a : album) {
			somma += a.getDurata();
		}
		return somma;
	}
	
	public boolean grafoCreato() {
		if(this.grafo == null)
			return false;
		else 
			return true;
	}
	
	
	

	
	

	
	
}
