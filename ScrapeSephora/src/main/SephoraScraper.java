package main;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import main.ScrapingMechanism;


/**
 * Main. Just runs the code, method by method, in order to keep it modular.
 * @author Or Peled.
 *
 */
public class SephoraScraper {

	private static ScrapingMechanism scraperInstance;
	protected static ProductObject productItem;
	public static final String HTML_AND_JPG_DIR = "/temp/";
	public static final String ENCODING_TYPE = "UTF-8";

	public static void main(String[] args)
	{
		// Checking if link was given
		if (args == null || args[0].isEmpty())
		{

			System.out.println("Please type in a Sephora web-based link as the first argument");
			System.exit(0);
		}

		// Creating the scraper instance
		scraperInstance = new ScrapingMechanism(args[0]);
		// Connecting
		scraperInstance.Connect();
		// Getting what we need
		productItem = scraperInstance.getContentAndPopulateObject();
		//Writing to file
		writeToFile();
		System.out.println("Success!");
		System.out.println("Please check the project's directory under temp folder.");
		System.out.println("It holds an html file with the requested attributes.");



	}
	/**
	 * This method will write the object's html file into the temp directory.
	 */
	private static void writeToFile()
	{
		File fileToWriteTo = new File(System.getProperty("user.dir") + HTML_AND_JPG_DIR + 
				productItem.getNameOfProduct() + ".html");
		try 
		{
			// Writing the html file into the temp directory.
			FileUtils.writeStringToFile(fileToWriteTo, productItem.toHtml(), ENCODING_TYPE);
		} 
		catch (IOException cantWrite) 
		{
			System.out.println("Couldn't write the html file.");
			cantWrite.printStackTrace();
			System.exit(1);
		}
	}



}
