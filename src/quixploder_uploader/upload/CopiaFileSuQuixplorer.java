package quixploder_uploader.upload;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.DefaultedHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;

import quixplorer_uploader.QuiXploderException;

public class CopiaFileSuQuixplorer {

	private String url;
	
	private HttpClient httpclient;

	public CopiaFileSuQuixplorer() {

		httpclient = new DefaultHttpClient();

	}

	public void login(String url, String user, String password) throws QuiXploderException {

		if (!url.substring(url.length() - 1).equals('/'))
			url += "/";
		
		this.url = url;
		
		HttpPost httppost = new HttpPost(url + "index.php?action=login&order=name&srt=yes");

		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("p_user", user));
		urlParameters.add(new BasicNameValuePair("p_pass", password));
		urlParameters.add(new BasicNameValuePair("lang", "en"));

		HttpResponse response = null;
		try {
			httppost.setEntity(new UrlEncodedFormEntity(urlParameters));
			response = httpclient.execute(httppost);
		} catch (IOException e) {
			e.printStackTrace();
			throw new QuiXploderException("Errore durante la chiamata HTTP al server (Server offline?)");
		}

		String output = stringaResponseContent(response);

		//qua potrei fare un controllo migliore per verificare che non ho messo un indirizzo sbagliato...
		if (!output.contains("QuiXplorer"))
			throw new QuiXploderException("L'indirizzo fornito non contiene un'istanza di QuiXplorer");
		
		if (output.contains("ERROR(S)"))
			throw new QuiXploderException("Username/Password errati");
			

	}

	public void caricaFiles(String folder, List<File> files) throws QuiXploderException {

		HttpPost httppost;
		try {
			httppost = new HttpPost(url + "index.php?action=upload&dir=" + URLEncoder.encode(folder, "UTF-8") + "&order=name&srt=yes");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new QuiXploderException("Folder specificata contiene caratteri non validi ???");
		}
	


		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

		Iterator<File> iterFiles = files.iterator();
		while (iterFiles.hasNext()) {
			File f = iterFiles.next();
			FileBody fb = new FileBody(f);
			builder.addPart("userfile[]", fb);
		}
		
		builder.addTextBody("MAX_FILE_SIZE", "2097152");
		builder.addTextBody("confirm", "true");		
		HttpEntity entity = builder.build();
	
		httppost.setEntity(entity);

		HttpResponse response;
		try {
			response = httpclient.execute(httppost);
		} catch (IOException e) {			
			e.printStackTrace();
			throw new QuiXploderException("Errore durante la chiamata HTTP al server (Server offline?)");
		}

		String output = stringaResponseContent(response);
		
		//TODO
		//validazione esito positivo/negativo upload su quixplorer
		
		//if cartella sbagliata
		//throw new Exception("Impossibile caricare i file nella cartella " + folder);
		
		//if estensione sbagliata
		//throw new Exception("Impossibile caricare i file nella cartella " + folder);
		
	}

	public static String stringaResponseContent(HttpResponse res) throws QuiXploderException {
		HttpEntity entity = res.getEntity();

		StringWriter writer = new StringWriter();
		try {
			IOUtils.copy(entity.getContent(), writer, "UTF-8");
		} catch (IllegalStateException | IOException e) {

			e.printStackTrace();
			throw new QuiXploderException("Errore nella lettura della risposta HTTP");
		}
		return writer.toString();

	}

}
