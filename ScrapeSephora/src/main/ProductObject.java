package main;
/**
 * This holds the product object, that'll be populated and then written into console.
 * The product's photo is saved under temp directory inside the project's folder.
 * @author orpeled
 *
 */
public class ProductObject {

	private String nameOfProduct;
	private float PriceInDollars;
	private String brandName;
	// Add a serialization to the picture
	private String linkToProductPhoto;
	private boolean isProductExclusive;
	private StringBuilder productDescription;
	private String sizeOfProduct;


	// Constructor.
	public ProductObject()
	{
		productDescription = new StringBuilder();
	}

	// Some getters and setters.
	public void setNameOfProduct(String nameOfProduct) {
		this.nameOfProduct = nameOfProduct;
	}

	public String getNameOfProduct() {
		return nameOfProduct;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setPhotoOfProduct(String photoOfProduct) {
		this.linkToProductPhoto = photoOfProduct;
	}

	public String getPhotoOfProduct() {
		return linkToProductPhoto;
	}

	public void setProductExclusive(boolean isProductExclusive) {
		this.isProductExclusive = isProductExclusive;
	}

	public boolean isProductExclusive() {
		return isProductExclusive;
	}

	public void setDescription(String description) {
		if (productDescription.length() == 0)
		{
			this.productDescription.append(description);
		}

	}

	public StringBuilder getDescription() {
		return productDescription;
	}

	public void setPriceInDollars(float priceInDollars) {
		PriceInDollars = priceInDollars;
	}

	public float getPriceInDollars() {
		return PriceInDollars;
	}

	public void setSizeOfProduct(String sizeOfProduct) {
		this.sizeOfProduct = sizeOfProduct;
	}

	public String getSizeOfProduct() {
		return sizeOfProduct;
	}

	public String toString()
	{
		return nameOfProduct;
	}

	/**
	 * This spits the information into an html.
	 * @return
	 */
	public String toHtml()
	{

		StringBuilder html = new StringBuilder();
		html.append( "<!doctype html>\n" );
		html.append( "<html lang='en'>\n" );
		html.append( "<head>\n" );
		html.append( "<meta charset='utf-8'>\n" );
		html.append( "<title>The Product Page</title>\n" );
		html.append( "</head>\n\n" );

		html.append( "<body>\n" );
		html.append( "<h1>Attributes Requested:</h1>\n" );
		// Make a list in HTML
		html.append( "<ul>\n" );
		// Spit it all out  
		html.append( "<li> <b>Name</b>: " + this.getNameOfProduct() + "</li>\n" );
		html.append( "<li> <b>Brand Name</b>: " + this.getBrandName() + "</li>\n" );
		html.append( "<li> <b>Price</b>: $" + this.getPriceInDollars() + "</li>\n" );
		html.append( "<li> <b>Size</b>: " + this.getSizeOfProduct() + "</li>\n" );
		html.append( "<li> <b>Description</b>: " + this.getDescription() + "</li>\n" );
		html.append( "<li> <b>Is This Product Exclusive</b>: " + this.isProductExclusive() + "</li>\n" );
		// Add the photo.
		html.append( "<img id=\"product_pic\" src=\"" + System.getProperty("user.dir") + "/temp/" + this.getNameOfProduct() + ".jpg\"" + " alt=\"product_pic\">" );
		html.append( "</ul>\n" );
		html.append( "</body>\n\n" );

		html.append( "</html>" );

		return html.toString();

	}
}



