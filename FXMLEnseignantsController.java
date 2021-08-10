/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dgsescuela.Enseignants;

import dgsescuela.DBConnection;
import dgsescuela.Etudiants.FXMLEtudiantsController;
import static dgsescuela.Etudiants.FXMLEtudiantsController.StageEtudiant;
import static dgsescuela.LoginPackage.loginController.adminStage;
import static dgsescuela.LoginPackage.loginController.rootAccueil;
import static dgsescuela.LoginPackage.loginController.sceneAccueil;
import dgsescuela.Modele.ModeleEnseignants;
import dgsescuela.Modele.ModeleEnseignantsStatic;
import dgsescuela.Modele.ModeleEtudiants;
import dgsescuela.Modele.ModeleEtudiantsStatic;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hdegd
 */
public class FXMLEnseignantsController implements Initializable {

    /**
     * Initializes the controller class.
     */
       @FXML
    private ObservableList<ModeleEnseignants> ListeEnseignants;
    
   @FXML
    private TableView<ModeleEnseignants> fxTableEnseignants;
     @FXML
    private TableColumn<ModeleEnseignants, String> fxIdEnseignants;
      @FXML
    private TableColumn<ModeleEnseignants, String> fxDateColumn;

    @FXML
    private TableColumn<ModeleEnseignants, String> fxNomEnseignants;

    @FXML
    private TableColumn<ModeleEnseignants, String> fxPrenomEnseignants;

    @FXML
    private TableColumn<ModeleEnseignants, String> fxTele;

    @FXML
    private TableColumn<ModeleEnseignants, String> fxEmail;
   
    @FXML
    private TableColumn<ModeleEnseignants, String> fxAdresse;
    
    @FXML
        private TextField fxRechercher;
    Connection conn;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
     public static Stage StageEnseignant= new Stage();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        initialisationData();
    }    
    
    public void initialisationData(){
         
           try {
               //initialisation de la liste d'objets etudiants
               ListeEnseignants = FXCollections.observableArrayList();
               
               //Connection
               conn= DBConnection.EtablirConnection();
               
               // initialisation de la table fx des etudiants
               initTable();
               
               // récupérer les données depuis la bdd
               uploadTableEnseignant();
               
           } catch (ClassNotFoundException ex) {
               Logger.getLogger(FXMLEnseignantsController.class.getName()).log(Level.SEVERE, null, ex);
           }
     }
     private void initTable() {

        fxIdEnseignants.setCellValueFactory(new PropertyValueFactory<>("idEnseignant"));
         fxDateColumn.setCellValueFactory(new PropertyValueFactory<>("DateAjout"));
        fxNomEnseignants.setCellValueFactory(new PropertyValueFactory<>("NomEnseignant"));
        fxPrenomEnseignants.setCellValueFactory(new PropertyValueFactory<>("PrenomEnseignant"));
        fxTele.setCellValueFactory(new PropertyValueFactory<>("TelEnseignant"));
        fxEmail.setCellValueFactory(new PropertyValueFactory<>("EmailEnseignant"));
        fxAdresse.setCellValueFactory(new PropertyValueFactory<>("AdresseEnseignant"));
    
    }
       
       public void uploadTableEnseignant(){
           
           try {
               ListeEnseignants.clear();
               
               String sql = "select IdEns,Date_Ajout,NomEns,PrenomEns,Tel,Email,adresse from `enseignant`" ;
               
               fxTableEnseignants.getItems().clear();
               pst = conn.prepareStatement(sql);
               rs = pst.executeQuery();
               
               while(rs.next()){
                   ModeleEnseignants newEnseignant = new ModeleEnseignants();
                   newEnseignant.setIdEnseignant(rs.getString(1));
                   newEnseignant.setDateAjout(rs.getString(2));
                   newEnseignant.setNomEnseignant(rs.getString(3));
                   newEnseignant.setPrenomEnseignant(rs.getString(4));
                   newEnseignant.setTelEnseignant(rs.getString(5));
                   newEnseignant.setEmailEnseignant(rs.getString(6));
                   newEnseignant.setAdresseEnseignant(rs.getString(7));
                   ListeEnseignants.add(newEnseignant);
                   //System.out.println("********************* date: "+newEtudiant.getDateAjout());
               }
               fxTableEnseignants.setItems(ListeEnseignants);
               pst.close();
               rs.close();
           } catch (SQLException ex) {
               Logger.getLogger(FXMLEnseignantsController.class.getName()).log(Level.SEVERE, null, ex);
           }
           
       }
           ModeleEnseignants CurrentObjet= new ModeleEnseignants();
    ModeleEnseignantsStatic CurrentObjetStatic = new ModeleEnseignantsStatic();
  
    private void selectCondidat(){
          CurrentObjet=(ModeleEnseignants)fxTableEnseignants.getSelectionModel().getSelectedItem();

          
          if(CurrentObjet==null || CurrentObjet.getIdEnseignant().equals(""))
          {
                 Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Erreur :   ");
                alert.setContentText("Veuillez Selectionner un enseignant d'abord!!!");
                alert.showAndWait();
          }else{
           CurrentObjetStatic.setIdEnseignant(CurrentObjet.getIdEnseignant());
               CurrentObjetStatic.setDateAjout(CurrentObjet.getDateAjout());
           CurrentObjetStatic.setNomEnseignant(CurrentObjet.getNomEnseignant());
            CurrentObjetStatic.setPrenomEnseignant(CurrentObjet.getPrenomEnseignant());
           CurrentObjetStatic.setTelEnseignant(CurrentObjet.getTelEnseignant());
           CurrentObjetStatic.setEmailEnseignant(CurrentObjet.getEmailEnseignant());
           CurrentObjetStatic.setAdresseEnseignant(CurrentObjet.getAdresseEnseignant());
     
          }

   
    }

    

/*------------------------------------------------Fenetre----------------------------------------*/

   public void FenetreEnseignant() throws IOException {

        rootAccueil = FXMLLoader.load(getClass().getResource("/dgsescuela/Enseignants/FXMLEnseignants.fxml"));
        sceneAccueil = new Scene(rootAccueil);

            adminStage.setScene(sceneAccueil);
            adminStage.show();
            adminStage.setMaximized(false);
            adminStage.setMaximized(true);

    }

   
   public void FenetreEtudiant() throws IOException {

        rootAccueil = FXMLLoader.load(getClass().getResource("/dgsescuela/Etudiants/FXMLEtudiants.fxml"));
        sceneAccueil = new Scene(rootAccueil);

            adminStage.setScene(sceneAccueil);
            adminStage.show();
            adminStage.setMaximized(false);
            adminStage.setMaximized(true);


    }

   
   public void FenetreFormation() throws IOException {


        rootAccueil = FXMLLoader.load(getClass().getResource("/dgsescuela/Formations/FXMLFormations.fxml"));
        sceneAccueil = new Scene(rootAccueil);

      
            adminStage.setScene(sceneAccueil);
            adminStage.show();
            adminStage.setMaximized(false);
            adminStage.setMaximized(true);


    }
   
   
   public void FenetreFrais() throws IOException {


        rootAccueil = FXMLLoader.load(getClass().getResource("/dgsescuela/Frais/FXMLFais.fxml"));
        sceneAccueil = new Scene(rootAccueil);

      
            adminStage.setScene(sceneAccueil);
            adminStage.show();
            adminStage.setMaximized(false);
            adminStage.setMaximized(true);
    }

   @FXML
   public void FenetreInscription() throws IOException  {


        rootAccueil = FXMLLoader.load(getClass().getResource("/dgsescuela/Inscriptions/FXMLInscriptions.fxml"));
        sceneAccueil = new Scene(rootAccueil);

      
            adminStage.setScene(sceneAccueil);
            adminStage.show();
            adminStage.setMaximized(false);
            adminStage.setMaximized(true);

    }
   
   
   public void FenetreStatistiques() throws IOException {


        rootAccueil = FXMLLoader.load(getClass().getResource("/dgsescuela/Statistiques/FXMLStatistiques.fxml"));
        sceneAccueil = new Scene(rootAccueil);

      
            adminStage.setScene(sceneAccueil);
            adminStage.show();
            adminStage.setMaximized(false);
            adminStage.setMaximized(true);



    }

   
   
   public void FenetreEmploisDuTemps() throws IOException {


        rootAccueil = FXMLLoader.load(getClass().getResource("/dgsescuela/Emplois/FXMLEmplois.fxml"));
        sceneAccueil = new Scene(rootAccueil);

      
            adminStage.setScene(sceneAccueil);
            adminStage.show();
            adminStage.setMaximized(false);
            adminStage.setMaximized(true);

    }

   public void FenetreAccueil() throws IOException {


        rootAccueil = FXMLLoader.load(getClass().getResource("/dgsescuela/AccueilPackage/FXMLAccueil.fxml"));
        sceneAccueil = new Scene(rootAccueil);

       adminStage.setScene(sceneAccueil);
            adminStage.show();
            adminStage.setMaximized(false);
            adminStage.setMaximized(true);

    }
   
   /*------------------------------------------------Fenetre----------------------------------------*/
/*---------------------------------------------------Méthode------------------------------------------*/
  
    public void AjouterEnseignant() throws IOException {
      
       Parent newRoot;
       Scene newScene;
       newRoot = FXMLLoader.load(getClass().getResource("/dgsescuela/Enseignants/FXMLAjouter.fxml"));
       newScene = new Scene(newRoot);

       StageEnseignant= new Stage();;
       StageEnseignant.setTitle("Ajouter Enseignant");
       StageEnseignant.setScene(newScene);
       StageEnseignant.showAndWait();
       
       

    }
    
     public void SupprimerEnseignant() throws IOException {

   selectCondidat();
   
        Parent newRoot;
        Scene newScene;
       newRoot = FXMLLoader.load(getClass().getResource("/dgsescuela/Enseignants/FXMLConfermationSuppression.fxml"));
       newScene = new Scene(newRoot);

       StageEnseignant= new Stage();;
       StageEnseignant.setTitle("Supprimer Enseignant");
       StageEnseignant.setScene(newScene);
       StageEnseignant.showAndWait();

    }
     
     public void ModifierEnseignant() throws IOException {



    selectCondidat();
   
       Parent newRoot;
       Scene newScene;
       newRoot = FXMLLoader.load(getClass().getResource("/dgsescuela/Enseignants/FXMLModifier.fxml"));
       newScene = new Scene(newRoot);

       StageEnseignant= new Stage();;
       StageEnseignant.setTitle("Modifier Enseignant");
       StageEnseignant.setScene(newScene);
       StageEnseignant.showAndWait();

    } 

     @FXML
     public void Recherche() throws SQLException{

        
        if(fxRechercher==null || fxRechercher.getText().equals(""))
        {
            System.out.println("hhhhh");
            uploadTableEnseignant();
            
        }else{
        ListeEnseignants.clear();
            RechercheDonnee();
    
  
        }
    }


 @FXML
     public void RechercheDonnee() throws SQLException{       
          String sql="select IdEns,Date_Ajout,NomEns,PrenomEns,Tel,Email,adresse from enseignant where IdEns ='"+fxRechercher.getText().toLowerCase()+"' OR NomEns= '"+fxRechercher.getText().toLowerCase()+"' OR PrenomEns= '"+fxRechercher.getText().toLowerCase()+"' OR Tel= '"+fxRechercher.getText().toLowerCase()+"' OR Date_Ajout = '"+fxRechercher.getText()+"' OR Email = '"+fxRechercher.getText()+"' OR adresse = '"+fxRechercher.getText()+"'";
            
        try {
            
       fxTableEnseignants.getItems().clear();
        pst = conn.prepareStatement(sql);
        rs = pst.executeQuery();
        while (rs.next()) {
            ModeleEnseignants Enseignant = new ModeleEnseignants();
            
            
            Enseignant.setIdEnseignant(rs.getString(1));
            Enseignant.setDateAjout(rs.getString(2));
            Enseignant.setNomEnseignant(rs.getString(3));
            Enseignant.setPrenomEnseignant(rs.getString(4));
            Enseignant.setTelEnseignant(rs.getString(5));
            Enseignant.setEmailEnseignant(rs.getString(6));
            Enseignant.setAdresseEnseignant(rs.getString(7));
            
            
            
            ListeEnseignants.add(Enseignant);
            fxTableEnseignants.setItems(ListeEnseignants);
        }
            System.out.println("l'étudiant est la ");
            pst.close();
           rs.close();
          
            
        } catch (SQLException ex) {
            Logger.getLogger(FXMLEnseignantsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
}