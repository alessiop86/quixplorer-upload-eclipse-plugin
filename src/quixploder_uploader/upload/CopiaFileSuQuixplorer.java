package quixploder_uploader.upload;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

public class CopiaFileSuQuixplorer {

	HttpClient httpclient;
	
	public CopiaFileSuQuixplorer() {
		
		 httpclient = new DefaultHttpClient();
		
	}
	
	public void login() throws ClientProtocolException, IOException {
		
		HttpPost httppost = new HttpPost("http://localhost/src/index.php?action=login&order=name&srt=yes");

		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("p_user", "admin"));
		urlParameters.add(new BasicNameValuePair("p_pass", "pwd_admin"));
		urlParameters.add(new BasicNameValuePair("lang", "en"));		
		httppost.setEntity(new UrlEncodedFormEntity(urlParameters));
		
		
 HttpResponse response = httpclient.execute(httppost);
		 
 Test.stampaResponse(response);

	        //httpclient.getConnectionManager().shutdown();   
		
		
	}
	
	
	public void uploadFiles() throws ClientProtocolException, IOException {
		
		

	       HttpPost httppost = new HttpPost("http://localhost/src/index.php?action=upload&dir=uploads%2Fprova&order=name&srt=yes");
		 //HttpPost httppost = new HttpPost("http://localhost/upload/index.php");
		
		//HttpPost httppost = new HttpPost("http://localhost/src/prova.php");

	        File file = new File("F:/Users/masterof/workspace_plugin/UploadStandalone/src/test.php");
	        FileBody fb = new FileBody(file);
	        
	        File file2 = new File("c:/wamp/license.txt");
	        FileBody fb2 = new FileBody(file2);
	        
	        MultipartEntityBuilder builder = MultipartEntityBuilder.create();        
	        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
	        
	        

	        builder.addPart("userfile[]", fb);
	        builder.addPart("userfile[]", fb2);
	        builder.addTextBody("MAX_FILE_SIZE", "2097152");
	        builder.addTextBody("confirm", "true");
	        //builder.addTextBody("macAddress",  macAddress);
	        HttpEntity entity = builder.build();
		 
		 /*MultipartEntity entity = new MultipartEntity();
		 
		 //HttpPost httppost = new HttpPost("http://localhost/upload/index.php");
		 
		 entity.addPart("file", new FileBody(file));
		 httppost.setEntity(entity);

		 HttpResponse response = httpclient.execute(httppost);
		 */
			 httppost.setEntity(entity);

			 HttpResponse response = httpclient.execute(httppost);
		 Test.stampaResponse(response);
		
	}
	
	public static void executeOld() throws ClientProtocolException, IOException {
		
		
		 HttpClient httpclient = new DefaultHttpClient();

	       HttpPost httppost = new HttpPost("http://localhost/upload/index.php");

	        File file = new File("F:/Users/masterof/workspace_plugin/UploadStandalone/src/test.php");

	 
		 
		 MultipartEntity entity = new MultipartEntity();
		 
		 //HttpPost httppost = new HttpPost("http://localhost/upload/index.php");
		 
		 entity.addPart("file", new FileBody(file));
		 httppost.setEntity(entity);

		 HttpResponse response = httpclient.execute(httppost);
		 
		 
		 Test.stampaResponse(response);
		// When HttpClient instance is no longer needed, 
	        // shut down the connection manager to ensure
	        // immediate deallocation of all system resources
	        httpclient.getConnectionManager().shutdown();   
	}
	
	
	
	
}


