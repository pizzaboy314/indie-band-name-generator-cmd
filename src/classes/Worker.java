package classes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;


public class Worker {

	/**
	 * 
	 */
	private static List<String> nounsList;
	private static List<String> adjsList;
	private static List<String> lastNamesList;
	private static List<String> firstMNamesList;
	private static List<String> firstFNamesList;
	
	private static List<String> grammars;
	
	private static Integer numNames = 20;
	private static Random rand;
	
	public static void main(String[] args) {
		rand = new Random();
		
		populateDictionaries();
		populateGrammars();
		
		StringBuilder sb = new StringBuilder();
		for(int i=0; i < numNames.intValue(); i++){
			sb.append(generateBandName(i) + "\n");
		}

		System.out.println(sb.toString());

	}
	
	public static String generateBandName(long seedInt){
		String grammarString = grammars.get(randInt(0,grammars.size()-1));
		
		String nounString1 = nounsList.get(randInt(0,nounsList.size()-1));
		String adjString1 = adjsList.get(randInt(0,adjsList.size()-1));
		String nounString2 = nounsList.get(randInt(0,nounsList.size()-1));
		String adjString2 = adjsList.get(randInt(0,adjsList.size()-1));
		
		
		String lastNameString = lastNamesList.get(randInt(0,lastNamesList.size()-1));
		String firstMNameString = firstMNamesList.get(randInt(0,firstMNamesList.size()-1));
		String firstFNameString = firstFNamesList.get(randInt(0,firstFNamesList.size()-1));
		
		String name = "";
		
		if(grammarString.equals("AdjNoun")){
			name = adjString1 + " " + nounString1;
		} else if(grammarString.equals("Adj Noun")){
			name = adjString1 + " " + nounString1;
		} else if(grammarString.equals("The Adj Nouns")){
			name = "The " + adjString1 + " " + nounString1 + "s";
		} else if(grammarString.equals("Noun Noun")){
			name = nounString1 + " " + nounString2;
		} else if(grammarString.equals("Male Last and the Adj Nouns")){
			name = firstMNameString + " " + lastNameString + " and the " + adjString1 + " " + nounString1 + "s";
		} else if(grammarString.equals("Female Last and the Adj Nouns")){
			name = firstFNameString + " " + lastNameString + " and the " + adjString1 + " " + nounString1 + "s";
		} else if(grammarString.equals("Adj Noun and the Adj Nouns")){
			name = adjString1 + " " + nounString1 + " and the " + adjString2 + " " + nounString2 + "s";
		} else if(grammarString.equals("Adj Last")){
			name = adjString1 + " " + lastNameString;
		} else if(grammarString.equals("The Nouns")){
			name = "The " + nounString1 + "s";
		} else if(grammarString.equals("Noun of Nouns")){
			name = nounString1 + " of " + nounString2 + "s";
		}
		
		return name;
	}
	
	public static void populateGrammars(){
		grammars = new ArrayList<String>();
		
		grammars.add("AdjNoun");
		grammars.add("Adj Noun");
		grammars.add("The Adj Nouns");
		grammars.add("Noun Noun");
		grammars.add("Male Last and the Adj Nouns");
		grammars.add("Female Last and the Adj Nouns");
		grammars.add("Adj Noun and the Adj Nouns");
		grammars.add("Adj Last");
		grammars.add("The Nouns");
		grammars.add("Noun of Nouns");
	}
	
	// https://stackoverflow.com/a/20389922
	public static int randInt(int min, int max) {

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
	public static void populateDictionaries(){
		String nounsURL = "https://raw.githubusercontent.com/pizzaboy314/indie-band-name-generator/master/dictionary%20files/nounslist.txt";
		String adjsURL = "https://raw.githubusercontent.com/pizzaboy314/indie-band-name-generator/master/dictionary%20files/adjectiveslist.txt";
		
		String lastNamesURL = "https://raw.githubusercontent.com/pizzaboy314/indie-band-name-generator/master/dictionary%20files/lastnames.txt";
		String firstMNamesURL = "https://raw.githubusercontent.com/pizzaboy314/indie-band-name-generator/master/dictionary%20files/firstmalenames.txt";
		String firstFNamesURL = "https://raw.githubusercontent.com/pizzaboy314/indie-band-name-generator/master/dictionary%20files/firstfemalenames.txt";
		
		nounsList = parseWords(nounsURL);
		adjsList = parseWords(adjsURL);
		lastNamesList = parseWords(lastNamesURL);
		firstMNamesList = parseWords(firstMNamesURL);
		firstFNamesList = parseWords(firstFNamesURL);
		
	}
	
	public static List<String> parseWords(String url){
		List<String> list = new ArrayList<String>();
		
		
		try {
			URL source = null;
			boolean valid = true;
			try {
				source = new URL(url);
			} catch (MalformedURLException e) {
				valid = false;
			}
			while (valid == false) {
				valid = true;
				url = (String) JOptionPane.showInputDialog(null, "Malformed URL format. Are you sure you copied the entire URL?\n" + "Try again:",
						"Provide URL", JOptionPane.PLAIN_MESSAGE, null, null, null);
				try {
					source = new URL(url);
				} catch (MalformedURLException e) {
					valid = false;
				}
			}

			BufferedReader in = new BufferedReader(new InputStreamReader(source.openStream()));

			String inputLine = in.readLine();
			while (inputLine != null) {
				String[] split = inputLine.trim().split("\\s|$");
				String s = split[0].toLowerCase();
				s = s.substring(0, 1).toUpperCase() + s.substring(1);

				list.add(s);
				inputLine = in.readLine();
			}

			
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

}
