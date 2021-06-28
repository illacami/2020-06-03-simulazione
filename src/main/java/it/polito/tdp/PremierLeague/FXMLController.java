/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.PremierLeague.model.Model;
import it.polito.tdp.PremierLeague.model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnTopPlayer"
    private Button btnTopPlayer; // Value injected by FXMLLoader

    @FXML // fx:id="btnDreamTeam"
    private Button btnDreamTeam; // Value injected by FXMLLoader

    @FXML // fx:id="txtK"
    private TextField txtK; // Value injected by FXMLLoader

    @FXML // fx:id="txtGoals"
    private TextField txtGoals; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	double avgGoal = -1.0;
    	try {
    		String text = txtGoals.getText();
    		avgGoal = Double.parseDouble(text);
    	}catch(NumberFormatException e) {
    		txtResult.setText("ERRORE: inserire un valore numerico con usando \".\" per le cifre decimali");
    		return;
    	}
    	
    	if(avgGoal>0) {
    		model.creaGrafo(avgGoal);
    		txtResult.appendText("\nGrafo creato\n# Vertici: "+model.getVertexSize()+"\n# Archi: "+model.getEdgeSize()+"\n");
    	}

    }

    @FXML
    void doDreamTeam(ActionEvent event) {

    	int maxPlayer = 0;
    	try {
    		String text = txtK.getText();
    		maxPlayer = Integer.parseInt(text);
    	}catch(NumberFormatException e) {
    		txtResult.setText("ERRORE: inserire un valore numerico nella casella \"# giocatori(k)\"");
    	}
    	 txtResult.appendText("\n \n***** DREAM TEAM *****");
    	for(Player p : model.dreamTeam(maxPlayer))
    		txtResult.appendText("\n"+p);
    }

    @FXML
    void doTopPlayer(ActionEvent event) {
    	
    	List<Player> classifica = new LinkedList<Player>(model.getTopPlayer());
    	
    	txtResult.appendText("\nTOP PLAYER: "+classifica.get(0)+"\n");
    	
    	classifica.remove(0);
    	
    	txtResult.appendText("\nAVVERSARI BATTUTI: \n");
    	
    	for(Player p : classifica)
    		txtResult.appendText("\n"+p);
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnTopPlayer != null : "fx:id=\"btnTopPlayer\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnDreamTeam != null : "fx:id=\"btnDreamTeam\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtGoals != null : "fx:id=\"txtGoals\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
