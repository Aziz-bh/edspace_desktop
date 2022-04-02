/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.edspace.tests;

import edu.edspace.entities.Document;
import edu.edspace.entities.Matiere;
import edu.edspace.services.DocumentService;
import edu.edspace.services.MatiereService;
import edu.edspace.utils.MyConnection;
import edu.edspace.utils.Statics;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author MeriamBI
 */
public class MainClass {

    public static void main(String[] args) {
        MyConnection.getInstance().getCnx();
        gestionDocument();
    }

    public static void gestionMatiere() {
        System.out.println("******************TEST CRUD MATIERE******************");
        MatiereService ms = new MatiereService(); //instanciation du service MatiereService
        //TEST AFFICHAGE LISTE DES MATIERES
        System.out.println("=> La liste des matières:\n" + ms.listMatieres());

        //TEST AJOUT MATIERE
        Matiere m = new Matiere("testfx", "3A");
        ms.ajouterMatiere(m);
        System.out.println("=> La liste des matières après ajout:\n" + ms.listMatieres());

        //TEST MODIFIER MATIERE
        String oldId = m.getId();
        m.setId("testfxupdate");
        m.setNiveau("3B");
        ms.modifierMatiere(m, oldId);
        System.out.println("=> La liste des matières après modification:\n" + ms.listMatieres());

        //TEST SUPPRIMER MATIERE
        ms.supprimerMatiere(m);
        System.out.println("=> La liste des matières après suppression:\n" + ms.listMatieres());
        
        //TEST LIST MATIERES FILTERED BY NIVEAU
        System.out.println("=> La liste des matières filtrée par niveau:\n" + ms.filterByNiveau("3A"));   
    }

    public static void gestionDocument() {
        System.out.println("\n******************TEST CRUD DOCUMENT******************");
        DocumentService ds = new DocumentService(); //instanciation du service DocumentService
        //TEST AFFICHAGE LISTE DES DOCUMENTS
        System.out.println("=> La liste des documents:\n" + ds.listDocs());

        //TEST AJOUT DOCUMENT
        String owner = "Anas Houissa"; //to_change
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime currentDate = LocalDateTime.now();
        File fichier = new File(Statics.myDocs + "logo.png"); //to_change
        Document doc = new Document(0, "logo.png", formatter.format(currentDate), owner, null, "", "3A", "matiereA", "", fichier);
        Document url = new Document(0, "urltest", formatter.format(currentDate), owner, "https://github.com/KnpLabs/snappy", null, "3B", "matiereB", "url", null);
        ds.ajouterDocument(doc);
        ds.ajouterDocument(url);
        System.out.println("=> La liste des documents après ajout:\n" + ds.listDocs());

        //TEST MODIFIER MATIERE
        doc.setMatiere("matiereB");
        doc.setNiveau("3B");
        ds.modifierDocument(doc);
        System.out.println("=> La liste des documents après modification:\n" + ds.listDocs());
        //TEST SUPPRIMER DOCUMENT
        ds.supprimerDocument(doc);
        System.out.println("=> La liste des documents après suppression:\n" + ds.listDocs());
        
        //TEST LIST DOCUMENTS FILTERED BY OWNER
        System.out.println("=> La liste des documents de l'utilisateur courant:\n" + ds.filterByOwner(owner));
        
        //TEST LIST DOCUMENTS FILTERED BY NIVEAU AND MATIERE
        System.out.println("=> La liste des documents filtrée par niveau et matière:\n" + ds.filterByNiveauMatiere("3A","PiDev"));
        
        //SIGNALER DOCUMENT
        ds.signalerDocument(url);
    }
}
