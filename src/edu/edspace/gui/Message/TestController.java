/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.edspace.gui.Message;

import edu.edspace.entities.Message;
import edu.edspace.services.ClasseService;
import edu.edspace.services.MessageService;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * FXML Controller class
 *
 * @author aa
 */
public class TestController implements Initializable {

    @FXML
    private Pane pane1;
    @FXML
    private TextField text;
    @FXML
    private Button location;
    @FXML
    private Button send;
    @FXML
    private ScrollPane sp_main;
    @FXML
    private AnchorPane anchor1;
    @FXML
    public VBox vbox;
    @FXML
    private Button translate;
    @FXML
    private ImageView logo_chat;
    @FXML
    private Button chatgroupe;

    public    MessageService ms=new MessageService();
    public int uid=1;
   public Socket socket;
   public OutputStream out;
   public PrintWriter ostream;
  public   BufferedReader in;
     public String serverAddr;
    public int port;
public Client client;
public Thread clientThread;

  ClasseService cs=new ClasseService();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            refreshchat();
  
        // TODO
    }    
    
     
    public void refreshchat(){
            port=2018;
            
             pane1.setVisible(false);
        ClasseService cs =new ClasseService();
        String niveau=cs.getOneById(cs.getStudent(uid).getClasse_id()).getNiveau().getId();
        String classe=cs.getOneById(cs.getStudent(uid).getClasse_id()).getClasse();
         chatgroupe.setText("CLASSE : "+niveau+" "+classe);
        initImages();
       setmes();
            
         /*      try{
    
    
                client = new Client(this);
                clientThread = new Thread(client);
                clientThread.start();
                
            }
            catch(Exception ex){
                System.out.println(ex);
            }
       
 */

       
       setmes();
      //  setOthermes(); 
    }
    
    
  
    
    
    public void setmes(){
          for (Message temp : ms.listeMessageClasse(cs.getOneById(cs.getStudent(uid).getClasse_id()).getId())) {
              
              if(temp.getUser().getId()==uid){
              HBox hBox=new HBox();
              hBox.setAlignment(Pos.CENTER_LEFT);
              hBox.setPadding(new Insets(5,5,5,10));
                         System.out.println(temp);
                           Text text=new Text(temp.getContent());
                           text.setFill(Color.LIGHTGREEN);
                              text.setFont(Font.font("SANS_SERIF", 13));
                           TextFlow textFlow=new TextFlow(text);
                           textFlow.setStyle("-fx-background-color: rgb(233,233,235)"+ " -fx-background-radius: 20px");
                           textFlow.setPadding(new Insets(5,0,5,100));
                           hBox.getChildren().add(textFlow);
                           vbox.getChildren().add(hBox);
                           
                        
           // items.add(temp.getUsername()+" "+temp.getPrenom());
        }else{
                   HBox hBox=new HBox();
              hBox.setAlignment(Pos.CENTER_LEFT);
              hBox.setPadding(new Insets(5,5,5,0));
                         System.out.println(temp);
                           Text text=new Text(temp.getContent());
                              text.setFont(Font.font("SANS_SERIF", 13));
                           text.setFill(Color.VIOLET);
                           TextFlow textFlow=new TextFlow(text);
                           textFlow.setStyle("-fx-background-color: rgb(233,233,235)"+ " -fx-background-radius: 20px");
                           textFlow.setPadding(new Insets(5,0,5,0));
                           hBox.getChildren().add(textFlow);
                           vbox.getChildren().add(hBox);
                  
              }
          }
        
    }
    
    
     public void addmes(String tt){
       
              HBox hBox=new HBox();
              hBox.setAlignment(Pos.CENTER_LEFT);
              hBox.setPadding(new Insets(5,5,5,10));
                         
                           Text text1=new Text(tt);
                           TextFlow textFlow=new TextFlow(text1);
                           textFlow.setStyle("-fx-background-color: rgb(233,233,235)"+ " -fx-background-radius: 20px");
                           textFlow.setPadding(new Insets(5,0,5,100));
                           hBox.getChildren().add(textFlow);
                           vbox.getChildren().add(hBox);
                           
                        
           // items.add(temp.getUsername()+" "+temp.getPrenom());
        }
        
    
    
    
     public void setOthermes(){
          for (Message temp : ms.listeOtherMessageClasse(uid)) {
              HBox hBox=new HBox();
              hBox.setAlignment(Pos.CENTER_LEFT);
              hBox.setPadding(new Insets(5,5,5,10));
                         System.out.println(temp);
                           Text text=new Text(temp.getContent());
                           TextFlow textFlow=new TextFlow(text);
                           textFlow.setStyle("-fx-background-color: rgb(233,233,235)"+ " -fx-background-radius: 20px");
                           textFlow.setPadding(new Insets(5,100,5,0));
                           hBox.getChildren().add(textFlow);
                           vbox.getChildren().add(hBox);
                           
                        
           // items.add(temp.getUsername()+" "+temp.getPrenom());
        }
        
    }
    
    
     public void initImages() {
         File filechat = new File("images/chat1.png");
        Image chat = new Image(filechat.toURI().toString());

       
        logo_chat.setImage(chat);

    }
     

    @FXML
    private void sendMessage(ActionEvent event) {
           
        
        if(!text.getText().isEmpty()){
       
        
        
        
        
        
        
        
        
        Message m = new Message();
        
        m.setClasse(cs.getOneById(cs.getStudent(uid).getClasse_id()));
        m.setUser(ms.getuser(uid));
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        m.setPostDate(date);
        m.setContent(text.getText());
         //addmes(ms.switcher(m).getContent());
        client.send(m);
        ms.ajouterMessage(m);
        
        
        
        }
        text.setText("");
    }
    
    
    

    @FXML
    private void location(ActionEvent event) {
             String mylocation=JsonReader.show("102.156.219.112");
              
              ClasseService cs=new ClasseService();
        
        
        
        Message m = new Message();
        
        m.setClasse(cs.getOneById(cs.getOneById(cs.getStudent(uid).getClasse_id()).getId()));
        m.setUser(ms.getuser(1));
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        m.setPostDate(date);
        m.setContent("MY LOCATION RIGHT NOW IS AT AT : "+mylocation.toUpperCase() +" weather description :"+JsonReader.weather(mylocation));
        
        //addmes("I'M AT : "+mylocation);
         client.send(m);
        ms.ajouterMessage(m);
    }
    
      @FXML
    void open(MouseEvent event) {
        
        pane1.setVisible(true);

    }
    
        @FXML
    void closechat(ActionEvent event) {
 pane1.setVisible(false);
    }
    
       @FXML
    void translatem(ActionEvent event) {
        try {
            text.setText(JsonReader.translate("en", "fr",text.getText()));
        } catch (IOException ex) {
            Logger.getLogger(AllMessagesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }
    
    
}
