
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import com.google.gson.*;

/*
 * This exercise is taken from hackerrank.
 * Read url content for secret keys list.
 * Use these keys to construct new urls where corresponding secret news are saved.
 * Read all new URLs contents and retrieve secret news.
 * Sort all secret news in alphabetical order.
 */


public class Main {
	
	public static void main(String [] arg) {
		//https://cdn.hackerrank.com/hackerrank/static/contests/capture-the-flag/secret/
		String [] keys = readURLJsonKeys("https://cdn.hackerrank.com/hackerrank/static/contests/capture-the-flag/secret/key.json");
		String [] secretNews = readURLJSONSecretNews("https://cdn.hackerrank.com/hackerrank/static/contests/capture-the-flag/secret/secret_json/", keys);
		Arrays.sort(secretNews);
		for (int i=0; i<secretNews.length; i++) {
			System.out.println(secretNews[i]);
		}
	}
	//Read url for JSON keys and returns an String array of keys. URL has below JSON data.
	/*
	 * {"v060r": "", "fxcne": "", "4ijzo": "", "7o40h": "", "vjkf0": "", "96xmi": "", "7hmxu": "",
	 *  "lefjd": "", "alien": "", "vejhb": "", "mz9a6": "", "tgihv": "", "utohw": "", "rp3g1": "",
	 * "fnsdm": "", "gstfd": "", "o020f": "", "i8z4b": "", "e9uak": "", "k9qxx": "", "9jh01": "",
	 * "89rcx": "", "yrxnh": "", "2y1b3": "", "xw5np": "", "276xh": "", "x2s57": "", "2b6f7": "",
	 * "t76dy": "", "il0d5": "", "ff8vi": "", "m7c30": "", "a5b38": "", "s94o9": "", "w17qs": "",
	 * "44bd3": "", "wnpxm": "", "mars": "", "epmqk": "", "ki0ag": "", "rs500": "", "etdbc": "",
	 * "nu5q2": "", "223j4": "", "ue9sp": "", "8bue1": "", "me6mc": "", "n0sh4": "", "3hqk3": "",
	 * "w8xh4": "", "i5cxs": "", "0rmqe": "", "kpisp": "", "jck0j": "", "k3dip": "", "oywsu": "",
	 * "eej3o": "", "wixg4": "", "62al1": "", "tjgzq": "", "jhpfy": "", "o4zx6": "", "wup33": "",
	 * "m88dt": "", "tvygb": "", "24hcw": "", "pjfb8": "", "3dfng": "", "wzeai": "", "0z6uz": "",
	 * "zaden": "", "pcxjo": "", "z732w": "", "jdjws": "", "byzms": ""}
	 */
	public static String [] readURLJsonKeys(String urlString) {
		
		String [] keys = null;
		BufferedReader br = null;
		URL url;
		try {
			url = new URL(urlString);
			try {
				br = new BufferedReader(new InputStreamReader(url.openStream()));
				String line=null;
				
				while ((line = br.readLine()) != null ) {
					JsonParser parser = new JsonParser();
					JsonElement elem = parser.parse(line);
					JsonObject obj = elem.getAsJsonObject();
					Set <Map.Entry<String, JsonElement>> set = obj.entrySet();
					keys = new String[set.size()];
					int i =0;
					for (Map.Entry<String, JsonElement> entry: set) {
						keys[i] = entry.getKey();
						i++;
					}
				}
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (br!=null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return keys;
	}
	
	public static String [] readURLJSONSecretNews(String urlString, String [] keys) {
		
		String [] secrets = new String [keys.length]; 
		int i = 0;
		URL url;
		BufferedReader br = null;
		try {
			for (String key: keys) {
				url = new URL(urlString + key + ".json");
				try {
					br = new BufferedReader(new InputStreamReader(url.openStream()));
					String line=null;
					
					while ((line = br.readLine()) != null ) {
						JsonParser parser = new JsonParser();
						JsonElement elem = parser.parse(line);
						JsonObject obj = elem.getAsJsonObject();
						Set <Map.Entry<String, JsonElement>> set = obj.entrySet();
						
						for (Map.Entry<String, JsonElement> entry: set) {
							if (entry.getKey().equalsIgnoreCase("news_title")) {
								secrets[i] = entry.getValue().getAsString();
								i++;
							}
						}
					}
				
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return secrets;
	}
}
