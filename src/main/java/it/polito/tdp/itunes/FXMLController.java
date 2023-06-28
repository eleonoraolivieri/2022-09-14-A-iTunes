/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.itunes;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;


import it.polito.tdp.itunes.model.Album;
import it.polito.tdp.itunes.model.Model;
import it.polito.tdp.itunes.model.Track;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnComponente"
    private Button btnComponente; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnSet"
    private Button btnSet; // Value injected by FXMLLoader

    @FXML // fx:id="cmbA1"
    private ComboBox<Album> cmbA1; // Value injected by FXMLLoader

    @FXML // fx:id="txtDurata"
    private TextField txtDurata; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="txtX"
    private TextField txtX; // Value injected by FXMLLoader

    @FXML
    void doComponente(ActionEvent event) {
    	
     	Album a = this.cmbA1.getValue();
    
    	
    	if (a==null) {
    		this.txtResult.setText("Please select an album");
    		return;
    	}
    	
    	txtResult.appendText("La componente connessa di: " + a + " è: " + this.model.componentiConnesse(a));
    	txtResult.appendText("\n" + "Durata componente: " + this.model.durataConnesse(a));
    
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	int durata;
    	try {
    		durata = Integer.parseInt(txtDurata.getText());
    		
    	} catch (NumberFormatException e) {
    		txtResult.setText("Inserisci una durata");
     
    		return;
    	}
    	
    	if(durata<0) {
   		 this.txtResult.setText("Durata must be a nonnegative integer.");
   		 return;
   	 }
    	
    	this.model.creaGrafo(durata);
    	List<Album> vertici = this.model.getVertici();
    	
    	this.txtResult.setText("Grafo creato, con " + vertici.size() + " vertici e " + this.model.getNArchi()+ " archi\n");
    	this.cmbA1.getItems().clear();
    	this.cmbA1.getItems().addAll(vertici);
    	
    }

    @FXML
    void doEstraiSet(ActionEvent event) {
    	
    	
    	Album a = this.cmbA1.getValue();
    	if(a == null) {
    		txtResult.appendText("Seleziona un album!");
    		return ;
    	}
    	int dTot;
    	try {
    		dTot = Integer.parseInt(txtX.getText());
    	}catch (NumberFormatException e) {
    		txtResult.appendText("Inseririci un valore numerico per la soglia");
    		return ;
    	}
    	
    	if(!this.model.grafoCreato()) {
    		txtResult.appendText("Crea prima il grafo!");
    		return ;
    	}
    	
    	txtResult.appendText("\n  LISTA ALBUM MIGLIORE: \n");
    	for(Album a2 : this.model.cercaLista(a, dTot)) {
    		txtResult.appendText(a2 + "\n");
    	}

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnComponente != null : "fx:id=\"btnComponente\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSet != null : "fx:id=\"btnSet\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbA1 != null : "fx:id=\"cmbA1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtDurata != null : "fx:id=\"txtDurata\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX != null : "fx:id=\"txtX\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }

}
