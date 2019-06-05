package application;

public class ProduitList {
	private  String barcode;
	private String produitName;
	private  String priceIn;
	private  String priceOut;
	private  String catId;
	private String date;
	ProduitList(String barcode, String produitName, String priceIn, 
			String priceOut, String catId,String date){
		super();
		this.barcode = barcode;
		this.produitName = produitName;
		this.priceIn = priceIn;
		this.priceOut = priceOut;
		this.catId = catId;
		this.date =date;
		
	}
	public String getBarcode() {
		return barcode;
	}
	public String getProduitName() {
		return produitName;
	}
	public String getPriceIn() {
		return priceIn;
	}
	public String getPriceOut() {
		return priceOut;
	}
	public String getCatId() {
		return catId;
	}
	public String getCateId() {
		return catId;
	}
    public String getDate() {
    	
    	return date;
    }
    
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public void setProduitName(String produitName) {
		this.produitName = produitName;
	}
  
	public void setPriceIn(String priceIn) {
		this.priceIn = priceIn;
	}
	public void setPriceOut(String priceOut) {
		this.priceOut = priceOut;
	}
	public void setCatId(String catId) {
		this.catId = catId;
	}
	public void setCateId(String cateId) {
		this.catId = cateId;
	}
	public void setDate(String date) {
		
		this.date = date;
	}
}