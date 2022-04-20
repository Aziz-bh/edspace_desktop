/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.edspace.gui.document;

import edu.edspace.entities.Document;
import edu.edspace.entities.Matiere;
import edu.edspace.entities.Niveau;
import edu.edspace.services.DocumentService;
import edu.edspace.services.MatiereService;
import edu.edspace.services.NiveauService;
import edu.edspace.utils.MyConnection;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author MeriamBI
 */
public class ListDocFrontController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private ComboBox<String> niveau_cb;
    @FXML
    private ComboBox<String> matiere_cb;
    @FXML
    private ImageView add_iv;
    @FXML
    private Label reinitialiser_label;
    @FXML
    private ImageView home_iv;
    @FXML
    private ScrollPane scroll;
    @FXML
    private GridPane grid;
    
    private List<Matiere> mats = new ArrayList();
    private List<Niveau> niveaux = new ArrayList();
    private List<Document> docs = new ArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        MyConnection.getInstance().getCnx();
        initImages();
        initDisplay();
    }    
    
    
    @FXML
    private void addDoc(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/document/AddDoc.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(ListDocFrontController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void reinitialiserFiltre(MouseEvent event) {
        niveau_cb.setValue(null);
        matiere_cb.setValue(null);
        initDisplay();
    }
    
    private void initDisplay() {
        DocumentService ds = new DocumentService();
        docs = ds.listDocs();
        if (docs.isEmpty()) {
            //display "empty"
        } else {
            initGrid(docs);
        }
        niveau_cb.setItems(niveauxList());
        niveau_cb.setPromptText("Tous les niveaux");

        niveau_cb.valueProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue ov, String oldVal, String newVal) {
                matiere_cb.setItems(matieresListFiltered(niveau_cb.getValue()));
                matiere_cb.setPromptText("Toutes les matières");
            }
        });

        matiere_cb.valueProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue ov, String oldVal, String newVal) {
                docs = ds.filterByNiveauMatiere(niveau_cb.getValue(), newVal);
                grid.getChildren().clear();
                if (docs.isEmpty()){
                    //display "empty"
                }else{
                    initGrid(docs);
                }
                
            }
        });
    }
    
    private void initGrid(List<Document> docs) {
        int column = 0;
        int row = 0;
        for (int i = 0; i < docs.size(); i++) {
            try {
                FXMLLoader fXMLLoader = new FXMLLoader();
                fXMLLoader.setLocation(getClass().getResource("/edu/edspace/gui/document/DocR.fxml"));
                AnchorPane anchorPane = fXMLLoader.load();

                DocRController docRController = fXMLLoader.getController();
                docRController.setData(docs.get(i));

                if (column == 3) {
                    column = 0;
                    row++;
                }
                grid.add(anchorPane, column++, row);

                GridPane.setMargin(anchorPane, new Insets(10));
            } catch (IOException ex) {
                Logger.getLogger(DocsListController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //list of niveaux in ObservableList
    private ObservableList<String> niveauxList() {
        ObservableList<String> oblistN = FXCollections.observableArrayList();
        NiveauService ns = new NiveauService();
        niveaux = ns.listeNiveaux();
        for (int i = 0; i < niveaux.size(); i++) {
            oblistN.add(niveaux.get(i).getId());
        }
        return oblistN;
    }

    //list of matieres filtered by niveau in ObservableList
    private ObservableList<String> matieresListFiltered(String niveau) {
        ObservableList<String> oblistM = FXCollections.observableArrayList();
        MatiereService ns = new MatiereService();
        mats = ns.filterByNiveau(niveau);
        for (int i = 0; i < mats.size(); i++) {
            oblistM.add(mats.get(i).getId());
        }
        return oblistM;
    }
    
    public void initImages() {

        File fileHome = new File("images/home_grey.png");
        Image homeI = new Image(fileHome.toURI().toString());
        
        File fileAdd = new File("images/add-new_grey.png");
        Image addI = new Image(fileAdd.toURI().toString());

        home_iv.setImage(homeI);        
        add_iv.setImage(addI);
        
    }

    @FXML
    private void getHome(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/FrontHome.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(ListDocFrontController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
