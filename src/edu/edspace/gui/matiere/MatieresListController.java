/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.edspace.gui.matiere;

import edu.edspace.entities.Matiere;
import edu.edspace.entities.Niveau;
import edu.edspace.services.DocumentService;
import edu.edspace.services.MatiereService;
import edu.edspace.services.NiveauService;
import edu.edspace.services.UserService;
import edu.edspace.utils.MyConnection;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author MeriamBI
 */
public class MatieresListController implements Initializable {

    @FXML
    private ComboBox<String> niveau_cb;
    @FXML
    private ImageView logo_iv;
    @FXML
    private Button btnOverview;
    @FXML
    private ImageView home_iv;
    @FXML
    private Button btnNews;
    @FXML
    private ImageView tabaff_iv;
    @FXML
    private Button btnOrders;
    @FXML
    private ImageView users_iv;
    @FXML
    private Button btnCustomers;
    @FXML
    private ImageView niveaux_iv;
    @FXML
    private Button btnMenus;
    @FXML
    private ImageView classe_iv;
    @FXML
    private Button btnMatiere;
    @FXML
    private ImageView matieres_iv;
    @FXML
    private Button btnSettings;
    @FXML
    private ImageView club_iv;
    @FXML
    private Button btnEmploi;
    @FXML
    private ImageView offre_iv;
    @FXML
    private Button btnSignout1;
    @FXML
    private ImageView forum_iv;
    @FXML
    private Button btnCentrePartage;
    @FXML
    private ImageView centre_iv;
    @FXML
    private Button btnSignout3;
    @FXML
    private ImageView signOut_iv;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Label reinitialiser_label;
    @FXML
    private TableColumn<Matiere, String> mat_col;
    @FXML
    private TableColumn<Niveau, String> niv_col;

    @FXML
    private TableView<Matiere> table;
    @FXML
    private ComboBox<String> filtreN_cb;
    @FXML
    private TextField matiere_tf;
    @FXML
    private Button ajouter_btn;
    @FXML
    private ImageView warning_iv;
    @FXML
    private Label error_l;

    private List<Matiere> mats = new ArrayList();
    private List<Niveau> niveaux = new ArrayList();

    @FXML
    private Button update_btn;
    @FXML
    private Button delete_btn;

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
    private void handleClicks(ActionEvent event) {
    }

    @FXML
    private void reinitialiserFiltre(MouseEvent event) {
        filtreN_cb.setValue(null);
        SortedList<Matiere> sortedData = new SortedList<>(matieresList());
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
    }

    @FXML
    private void addMatiere(MouseEvent event) {
        String nomMat = matiere_tf.getText();
        String niveau = niveau_cb.getValue();
        if (nomMat != null && nomMat.length() != 0 && niveau != null && niveau.length() != 0) {
            MatiereService ms = new MatiereService();
            if (ms.findMatiereById(nomMat)) {
                String title = "etette";
                String header = "qljdb";
                String content = "ldjbc";
                showAlert(AlertType.WARNING, title, header, content);
            } else {
                Matiere m = new Matiere(nomMat, niveau);
                ms.ajouterMatiere(m);
                warning_iv.setVisible(false);
                error_l.setVisible(false);
                refreshTable();
            }
        } else {
            error_l.setText("Veuillez remplir tous les champs pour effectuer l'ajout!");
            warning_iv.setVisible(true);
            error_l.setVisible(true);
        }
    }

    @FXML
    private void updateMatiere(MouseEvent event) {
        Matiere matiere = table.getSelectionModel().getSelectedItem();
        if (matiere != null) {
            // Create the custom dialog
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Mise à jour Box");
            dialog.setHeaderText("Mise à jour de la matière " + matiere.getId());

            // Set the icon
            File fileRefresh = new File("images/refresh_green.png");
            Image refresh = new Image(fileRefresh.toURI().toString());
            dialog.setGraphic(new ImageView(refresh));
            // Set the button types
            ButtonType saveButtonType = new ButtonType("Enregistrer", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButtonType = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);

            dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, cancelButtonType);

            // Create the nom and niveau labels and fields
            //grid setting
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));
            //nom tf init
            TextField tf = new TextField();
            tf.setPromptText("Nom");
            tf.setText(matiere.getId());
            //niveau combobox init
            ComboBox<String> cb = new ComboBox<>();
            cb.setItems(niveauxList());
            cb.setValue(matiere.getNiveau());

            //add tf and cb to the grid +lables
            grid.add(new Label("Nom de la matière:"), 0, 1);
            grid.add(tf, 1, 1);
            grid.add(new Label("Niveau concerné:"), 0, 2);
            grid.add(cb, 1, 2);
            dialog.getDialogPane().setContent(grid);
            Optional<ButtonType> result = dialog.showAndWait();
            if (result.get() == saveButtonType) {
                //controle de saisie
                if (tf.getText() != null && tf.getText().length() != 0) {
                    DocumentService ds = new DocumentService();
                    int relatedDocs = ds.countRelatedDocs(matiere);
                    //check if related docs exist then can't delete
                    if (relatedDocs == 0) {
                        Matiere newMat = new Matiere(tf.getText(), cb.getValue());
                        MatiereService ms = new MatiereService();
                        ms.modifierMatiere(newMat, matiere.getId());
                        warning_iv.setVisible(false);
                        error_l.setVisible(false);
                        refreshTable();
                    } else {
                        String title = "Erreur survenue lors de la mise à jour";
                        String header = "Impossible de mettre à jour cette matière";
                        String content = "Il existe un ou plusieurs document(s) dans le centre de partage concerné(s) par cette matière";
                        showAlert(AlertType.ERROR, title, header, content);
                    }
                } else {
                    error_l.setText("Veuillez remplir tous les champs pour effectuer la MÀJ!");
                    warning_iv.setVisible(true);
                    error_l.setVisible(true);
                }
            }
        } else { //si aucune matière sélectionnée à partir du tableau
            String title = "";
            String header = "Aucune matière n'a été sélectionnée";
            String content = "Veuillez sélectionner une ligne depuis la table des matières";
            showAlert(AlertType.WARNING, title, header, content);
        }

    }

    @FXML
    private void deleteMatiere(MouseEvent event) {
        Matiere matiere = table.getSelectionModel().getSelectedItem();
        if (matiere != null) {
            String title = "Confirmation de la suppression";
            String header = "Êtes-vous sur de bien vouloir supprimer cette matière?";
            String content = "Cette matière sera supprimée définitivement";
            final Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.setResizable(true);
            //ButtonType confirmButtonType = new ButtonType("Oui", ButtonData.OK_DONE);
            //ButtonType denyButtonType = new ButtonType("Non", ButtonData.NO);
            //alert.getDialogPane().getButtonTypes().addAll(confirmButtonType, denyButtonType);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {

                DocumentService ds = new DocumentService();
                int relatedDocs = ds.countRelatedDocs(matiere);
                //check if related docs exist then can't delete
                if (relatedDocs == 0) {
                    MatiereService ms = new MatiereService();
                    ms.supprimerMatiere(matiere);
                    refreshTable();
                } else {
                    alert.close();
                    String titleErr = "Erreur survenue lors de la suppression";
                    String headerErr = "Impossible de supprimer cette matière";
                    String contentErr = "Il existe un ou plusieurs document(s) dans le centre de partage concerné(s) par cette matière";
                    showAlert(AlertType.ERROR, titleErr, headerErr, contentErr);
                }
            } else {
                alert.close();
            }
        } else { //si aucune matière sélectionnée à partir du tableau
            String title = "";
            String header = "Aucune matière n'a été sélectionnée";
            String content = "Veuillez sélectionner une ligne depuis la table des matières";
            showAlert(AlertType.WARNING, title, header, content);
        }

    }

    private void initDisplay() {
        MatiereService ms = new MatiereService();
        mats = ms.listMatieres();
        //remplissage tableau
        mat_col.setCellValueFactory(new PropertyValueFactory<>("id")); //affecter l'attribut id à la colonne matiere 
        niv_col.setCellValueFactory(new PropertyValueFactory<>("niveau")); //affecter l'attribut niveau à la colonne niveau 
        table.setItems(matieresList()); //remplir le tableu avec la liste des matieres
        //init du filtre par niveau

        filtreN_cb.setItems(niveauxList());
        filtreN_cb.setPromptText("Tous les niveaux");
        niveau_cb.setItems(niveauxList()); //init du cb choix niveau pour l'ajout 
        filtreN_cb.valueProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue ov, String oldVal, String newVal) {
                SortedList<Matiere> sortedData = new SortedList<>(matieresListFiltered(newVal));
                sortedData.comparatorProperty().bind(table.comparatorProperty());
                table.setItems(sortedData);
            }
        });
    }

    private void refreshTable() {
        mats.clear();
        initDisplay();
    }

    //list of matieres in ObservableList
    private ObservableList<Matiere> matieresList() {
        ObservableList<Matiere> oblistM = FXCollections.observableArrayList();
        MatiereService ns = new MatiereService();
        mats = ns.listMatieres();
        for (int i = 0; i < mats.size(); i++) {
            oblistM.add(mats.get(i));
        }
        return oblistM;
    }

    //list of matieres in ObservableList
    private ObservableList<Matiere> matieresListFiltered(String niveau) {
        ObservableList<Matiere> oblistM = FXCollections.observableArrayList();
        MatiereService ns = new MatiereService();
        mats = ns.filterByNiveau(niveau);
        for (int i = 0; i < mats.size(); i++) {
            oblistM.add(mats.get(i));
        }
        return oblistM;
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

    //alert dialog sample
    private void showAlert(AlertType alertType, String title, String header, String content) {
        final Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.setResizable(true);
        alert.showAndWait();
    }

    //images init
    public void initImages() {
        File fileLogo = new File("images/logo1.png");
        Image logoI = new Image(fileLogo.toURI().toString());

        File fileHome = new File("images/stats_grey.png");
        Image homeI = new Image(fileHome.toURI().toString());

        File fileTab = new File("images/announcement_grey.png");
        Image tabI = new Image(fileTab.toURI().toString());

        File fileLevel = new File("images/level_grey.png");
        Image levelI = new Image(fileLevel.toURI().toString());

        File fileClass = new File("images/class-management_grey.png");
        Image classI = new Image(fileClass.toURI().toString());

        File fileBook = new File("images/book_grey.png");
        Image bookI = new Image(fileBook.toURI().toString());

        File fileForum = new File("images/forum2_grey.png");
        Image forumI = new Image(fileForum.toURI().toString());

        File fileOffre = new File("images/briefcase_grey.png");
        Image offreI = new Image(fileOffre.toURI().toString());

        File fileDocs = new File("images/file_grey.png");
        Image docsI = new Image(fileDocs.toURI().toString());

        File fileUsers = new File("images/users_grey.png");
        Image usersI = new Image(fileUsers.toURI().toString());

        File fileClub = new File("images/org_grey.png");
        Image clubI = new Image(fileClub.toURI().toString());

        File fileOut = new File("images/logout_grey.png");
        Image outI = new Image(fileOut.toURI().toString());

        File fileWarning = new File("images/warning_red.png");
        Image warningI = new Image(fileWarning.toURI().toString());

        logo_iv.setImage(logoI);
        home_iv.setImage(homeI);
        tabaff_iv.setImage(tabI);
        users_iv.setImage(usersI);
        niveaux_iv.setImage(levelI);
        classe_iv.setImage(classI);
        matieres_iv.setImage(bookI);
        club_iv.setImage(clubI);
        offre_iv.setImage(offreI);
        forum_iv.setImage(forumI);
        centre_iv.setImage(docsI);
        signOut_iv.setImage(outI);
        warning_iv.setImage(warningI);
    }

    //SIDEBAR
    @FXML
    private void getNewsView(MouseEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/news/allNews.fxml"));
            rootPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void getAllDocsView(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/document/DocsList.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void getEmploiView(MouseEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/emploi/allEmploi.fxml"));
            rootPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //categories offre
    @FXML
    private void getCatEmploiView(MouseEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/emploi/allcategoryEmploi.fxml"));
            rootPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //FORUM 
    @FXML
    private void getForum(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/ThreadList.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void getDashboardView(MouseEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/HomeBack.fxml"));
            rootPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //NIVEAU D’ETUDES 
    @FXML
    private void getNiveaux(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/Niveau/AllNiveau.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //CLASSES 
    @FXML
    private void getClasses(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/Niveau/AllNiveau.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void getAllMatieresView(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/matiere/MatieresList.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void getUsers(MouseEvent event) {
        try {
            //instance mtaa el crud
            //redirection
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/AllAdmins.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //news Category  
    @FXML
    private void getCatNewsView(MouseEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/news/allCategoryNews.fxml"));
            rootPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //CLUBS UNIVERSITAIRES
    @FXML
    private void displayClubs(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/Clubs/ClubListAdmin.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void getOut(MouseEvent event) {
        UserService US = new UserService();
        US.logout();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/User/Login.fxml"));
        try {
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
