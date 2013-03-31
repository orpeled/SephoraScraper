package main;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import org.apache.commons.io.FileUtils;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * This represents the scraping mechanism.
 * Given a link, it returns an object array that holds the product objects.
 * @author Or Peled
 *
 */
public class ScrapingMechanism {

	ProductObject productToPopulate = new ProductObject();
	private String linkToScrape;
	private Document productDocument;
	public static final String EXCLUSIVE = "exclusive";
	public static final String HTML_AND_JPG_DIR = "/temp/";
	public static final String PHOTO_EXTENSION = ".jpg";

	/**
	 * Constructor.
	 * The link is given through the arguments.
	 * @param linkToScrape
	 */
	public ScrapingMechanism(String linkToScrape)
	{
		productToPopulate = new ProductObject();
		this.linkToScrape = linkToScrape;
	}

	/**
	 * This method connects to the web site using a static agent.
	 */
	protected void Connect()
	{
		try
		{
			// Connecting using a basic user agent.
			// In order to keep things simple, I've yet to implement a user agent alternator.
			productDocument = Jsoup.connect(linkToScrape).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11").get();

		}
		catch (Exception e)
		{
			System.out.println("Couldn't connect. \nPlease check your connection and try again");
			e.printStackTrace();

		}


	}

	/**
	 * Gets content and parses the object.
	 * @return a parsed object.
	 */
	protected ProductObject getContentAndPopulateObject()
	{
		Element parsedElement;
		String imageUrl;

		// Setting product's name
		parsedElement = productDocument.select("h1 .OneLinkNoTx").first();
		productToPopulate.setNameOfProduct(parsedElement.text());

		// Setting product's price
		parsedElement = productDocument.select("#primarySkuInfo_price .price").first();
		productToPopulate.setPriceInDollars(Float.parseFloat(parsedElement.text()));

		// Setting brand's name		
		parsedElement = productDocument.select("#productBrandImg").first();
		productToPopulate.setBrandName(parsedElement.attr("alt"));

		// Setting product description (asking for the first seems to give less information than the last element.
		parsedElement = productDocument.select("#product-desc p").last();
		// Trying to maintain line breaks
		String text = parsedElement.text().replaceAll("(?i)<br[^>]*>", "br2nl").replaceAll("\n", "br2nl");
		text = text.replaceAll("br2nl ", "\n").replaceAll("br2nl", "\n").trim();
		productToPopulate.setDescription(text);

		// Setting size of product.
		parsedElement = productDocument.select(".size .value").first();
		if (!parsedElement.text().isEmpty())
		{
		productToPopulate.setSizeOfProduct(parsedElement.text());
		}
		else
		{
			productToPopulate.setSizeOfProduct("Not Available");
		}
		
		// Is this product exclusive to Sephora ?
		parsedElement = productDocument.select(".flags").first();

		if (parsedElement.text().trim().equals(EXCLUSIVE))
		{
			productToPopulate.setProductExclusive(true);	
		}
		else
		{
			productToPopulate.setProductExclusive(false);
		}

		// Getting product's photo
		parsedElement = productDocument.select(".hero-main-image img").first();
		productToPopulate.setPhotoOfProduct(parsedElement.attr("src"));

		URL linkToDownloadFrom = null;


		try
		{
			// Get the URL ready
			imageUrl = getDomainName(linkToScrape) + productToPopulate.getPhotoOfProduct();
			linkToDownloadFrom = new URL(imageUrl);

		}
		catch (MalformedURLException syntaxProblem )
		{
			System.out.println("There's a syntax problem in the photo's URL.");
			System.exit(1);
		}
		catch (URISyntaxException couldntGetDomain)
		{
			System.out.println("Couldn't get the domain name.");
			System.exit(1);
		}
		// Using external library FileUtils to d/l the image				
		File destinationDirectory = new File(System.getProperty("user.dir") + HTML_AND_JPG_DIR + 
				productToPopulate.getNameOfProduct() + PHOTO_EXTENSION);
		try
		{
			FileUtils.copyURLToFile(linkToDownloadFrom, destinationDirectory);
		}
		catch (IOException problemGettingImage)
		{
			System.out.println("There's a problem downloading the image.");
			System.exit(1);
		}

		return productToPopulate;
	}

	/**
	 * Using this method to get the domain name. 
	 * It's considerably weak since it needs to be used just for Sephora.com.
	 * Uses URI.
	 * @param url
	 * @return
	 * @throws URISyntaxException
	 */
	public static String getDomainName(String url) throws URISyntaxException {
		URI uri = new URI(url);		
		String domain = "http://" + uri.getHost();
		return domain;
	}

}

