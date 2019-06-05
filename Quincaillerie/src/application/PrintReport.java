package application;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class PrintReport extends javax.swing.JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void ShowReport() {
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
		JOptionPane.showMessageDialog(rootPane, e.getMessage());
	   }
		
	}
	
	
	
}
	           
			
