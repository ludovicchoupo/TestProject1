package application;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javax.swing.text.DateFormatter;
import org.controlsfx.control.Notifications;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;



public class ProduitsController implements Initializable{
	@SuppressWarnings("unused")
	private DateFormatter timeFormatter;
	
	Connection connection = null;
	 ObservableList<ProduitList> data =FXCollections.observableArrayList();
	
	@FXML
	Button ajouter;
	@FXML
	private TableView<ProduitList> tbl_produit;
	@FXML
	private TableColumn<?, ?> columnbarcode;
	@FXML
	private TableColumn<?,?> columproduit;
	@FXML
	private TableColumn<?,?> columdp;
	@FXML
	private TableColumn<?,?> columpricein;
	@FXML
	private TableColumn<?,?> columpriceout;
	@FXML
	private TableColumn<?,?> columcateid;
	@FXML
	private TableColumn<?,?> columcategoriename;
	@FXML
	private TextField txt_Pid;
	@FXML
	private TextField txt_barcode;
	@FXML
	private TextField txt_pricein;
	@FXML
	private TextField txt_priceout;
	@FXML
	private TextField txt_cateID;
	@FXML
	private TextField txt_produitnom;
	@FXML
	private DatePicker dp;
	
	
	public void handleAddProduct() throws SQLException {
		
		try {
			LocalDate ld = dp.getValue();
			String sql ="insert into produits(P_id,barcode,ProduitName,PriceIn,cat_Id,PriceOut,date)values(?,?,?,?,?,?,?)";
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setString(1, autoProduitID());
        pst.setString(2, txt_barcode.getText());
        pst.setString(3, txt_produitnom.getText());
        pst.setString(4,txt_pricein.getText());
        pst.setString(6,txt_priceout.getText());
        pst.setString(5, txt_cateID.getText());
        pst.setDate(7, java.sql.Date.valueOf(ld.toString()));
         pst.execute();
         clear();
         txt_Pid.setText(""+autoProduitID());
       // JOptionPane.showMessageDialog(null, "Data SuccessFully Saved");
         Image img = new Image("file:///C:/Users/ludoviska/eclipse-workspace/Quincaillerie/src/image/ok.png");
     	Notifications notificationBuilder = Notifications.create()
     	.title("Notification")
     	.text("Donees sauvegardes").darkStyle()
     	.graphic(new ImageView(img));
          notificationBuilder.show();
         
        data.clear();
        loadDataFromDatabase(); 
        pst.close();
        	
		}catch(Exception e){
	   }
	}
	public void DeleteData(ActionEvent event) throws SQLException {
		try{
			
			String sql="delete from produits where ProduitName=?";
			PreparedStatement pst = connection.prepareStatement(sql);
			pst.setString(1, txt_produitnom.getText());
			int i = pst.executeUpdate();
			if(i==1) {
				
			System.out.println("data deleted");
				 loadDataFromDatabase(); 
				 clear();
				 txt_search.setText("");
			}
			
			
		}catch(Exception e) {
			
		}
	}
	
@FXML
Image img;
public void HandleUpdate(ActionEvent event) {
	Image img = new Image("file:///C:/Users/ludoviska/eclipse-workspace/Quincaillerie/src/image/ok.png");
	Notifications notificationBuilder = Notifications.create()
	.title("Notification")
	.text("Votre base de donnees a ete mise a jour").darkStyle()
	.graphic(new ImageView(img));
     notificationBuilder.show();	
	//.showWarning();
	
}

	@FXML
	Button update;
public void clearData(ActionEvent event) {
	
	 clear();
	 txt_Pid.setText(""+autoProduitID());
	
  }
	public void clear() {
		txt_barcode.setText("");
		txt_produitnom.setText("");
		txt_pricein.setText("");
		txt_cateID.setText("");
		txt_priceout.setText("");
		txt_Pid.setText("");
		dp.setValue(null);
		
		
		
	
		
	}
	private String autoProduitID() {
		
		String P_id="IVN00";
		try {
			String sql = "select max(P_id) from produits";
			PreparedStatement st = connection.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			if(rs.next()) {
				P_id = rs.getString(1);
				int n = Integer.parseInt(P_id.substring(4)) + 1;
				int  x=String.valueOf(n).length();
				P_id =P_id.substring(0, 5-x) + String.valueOf(n);
			}
			st.close();
			rs.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return P_id;
		
	}
	@FXML
	TextField txt_search;
	 public void searchData(ActionEvent event) throws SQLException {
		 if(txt_search.getText().equals("")){	 
			 loadDataFromDatabase(); 
			 txt_Pid.setText(""+autoProduitID()); 
		 }
		 else {
			 
			 data.clear();
		
		 try {
			 String sql="select * from produits where ProduitName LIKE '%"+txt_search.getText()+"%'"
					 + "UNION select * from produits where P_id LIKE '%"+txt_search.getText()+"%'"; 
			 PreparedStatement pst = connection.prepareStatement(sql);
			 ResultSet rs =pst.executeQuery();
			 while(rs.next()) {
				 data.add(new ProduitList(
							rs.getString(2),
							rs.getString(3),
							rs.getString(4),
							rs.getString(6),
							rs.getString(5),
							rs.getString(7)
								));	
			 }
			     tbl_produit.setItems(null);
			tbl_produit.setItems(data);	 
			 pst.close();
		 }catch(IllegalArgumentException e) {
			 e.printStackTrace();
		   }
		 }
		}
	 @FXML
	 Button Print;
	 public void Report(ActionEvent event) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				
				Connection com  = DriverManager.getConnection("jdbc:mysql://localhost:3306/quincaillerie","root","");
				String ReportPath="C:\\Users\\ludoviska\\Downloads\\Compressed\\New folder\\Reportproduits.jrxml";
				 JasperReport jasperReport = JasperCompileManager.compileReport(ReportPath);
				 JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, com);
			     JasperViewer.viewReport(jasperPrint);
			     com.close();
			}
		catch(Exception e) {
			e.printStackTrace();
		   }
			
	 }
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		connection = DBConnection.dbConnector();
		txt_Pid.setText(""+autoProduitID());
		txt_Pid.setDisable(true);
		columnbarcode.setCellValueFactory(new PropertyValueFactory<>("barcode"));
		columproduit.setCellValueFactory(new PropertyValueFactory<>("produitName"));
		columpricein.setCellValueFactory(new PropertyValueFactory<>("priceIn"));
		columpriceout.setCellValueFactory(new PropertyValueFactory<>("priceOut"));
		columcateid.setCellValueFactory(new PropertyValueFactory<>("catId"));
		columdp.setCellValueFactory(new PropertyValueFactory<>("date"));
		loadDataFromDatabase();
		tbl_produit.setItems(null);
		tbl_produit.setItems(data);
		//setCellTable();
		//loadDataFromDatabase();
		//LoadData();
	}
	//private void setCellTable() {
		
	//}
	        
		
	private void loadDataFromDatabase() {
		data.clear();
			PreparedStatement pst = null;
			 ResultSet rs = null;
		try {
			String sql ="select * from produits";
			pst = connection.prepareStatement(sql);
			rs= pst.executeQuery();
			while(rs.next()) {
				data.add(new ProduitList(
				rs.getString(2),
				rs.getString(3),
				rs.getString(4),
				rs.getString(6),
				rs.getString(5),
				rs.getString(7)
					));
				
			}
		
		pst.close();
		rs.close();
	}
	catch(Exception e) {
		System.err.println(e);
	 }
	}
}
