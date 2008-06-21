

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URISyntaxException;
import java.util.Date;
import GeneralUtilities.IProgressNotificationHandler;
import GeneralUtilities.ProgressNotificator;
import Tokenizer.IToken;
import Tokenizer.ITokenizedWebPage;
import Tokenizer.TokenizerException;
import WebModel.URIWebPage;
import XMLTokenizer.Token;
import XMLTokenizer.TokenTypeHierarchy;
import XMLTokenizer.TokenTypePersistanceException;
import XMLTokenizer.TokenTypePersistence;
import XMLTokenizer.Tokenizer;

public class PruebaTokenizador {

	/**
	 * @param args
	 * @throws TokenTypePersistanceException 
	 * @throws URISyntaxException 
	 * @throws TokenizerException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws TokenTypePersistanceException, URISyntaxException, TokenizerException, IOException {
		///////////////////////////////////////////////////////////////////////////////////////////
		// El notificador OPCIONAL
		
		IProgressNotificationHandler handler = new IProgressNotificationHandler() {

			public void endProgressNotifications() {
				System.err.println("FIN");
				
			}
			public void messageEvent(int levelMessage, String message) {
				System.err.println(message);
				
			}
			
			public void progressEvent(double value, double progress) {
				System.err.println(progress*100);
				
			}
			public void startProgressNotifications() {
				System.err.println("INICIO");				
			}
			
		};
		
		ProgressNotificator notificator = new ProgressNotificator();
		notificator.addProgressNotificationHandler(handler);
		/////////////////////////////////////////////////////////////////////////////////////////
		
		// Primero creamos la jerarquia
		TokenTypePersistence persistance = new TokenTypePersistence("nuevo dtd.xml");
		TokenTypeHierarchy hierarchy = persistance.load();
		
		// Creamos la p‡gina web
		//URIWebPage page = new URIWebPage(new File("amazon1.txt").toURI().toString());
		URIWebPage page = new URIWebPage(new File("eii.html").toURI().toString());
		
		// Creamos el tokenizador
		Tokenizer tokenizer = new Tokenizer(hierarchy);
		
		// OPCIONAL: le asignamos el notificador
		tokenizer.setProgressNotificator(notificator);
		
		// Tokenizo
		System.err.println("INIT");
		
		ITokenizedWebPage twp = tokenizer.tokenize(page);
		
		System.err.println("FIN");
		
		System.err.println("\n\n*****************************************************************\n");
		 
		FileOutputStream fos = new FileOutputStream("out.txt");
		OutputStreamWriter osw = new OutputStreamWriter(fos,"UTF-8");
		BufferedWriter buff = new BufferedWriter(osw);
		
		for (IToken token : twp) {
			buff.write(tokenToString(token)+"\n");
		}
		
		buff.close();
	}
	
	public static String tokenToString (IToken it) {
		Token t = (Token)it;
		String retString = t.getTokenType().getName() + "(";
		retString += t.getText() +")";
		return retString;
	}

}
