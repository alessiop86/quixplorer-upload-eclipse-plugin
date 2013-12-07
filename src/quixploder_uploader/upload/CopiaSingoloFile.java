package quixploder_uploader.upload;
import java.io.File;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;


public class CopiaSingoloFile {

	
	public static void execute() throws ClientProtocolException, IOException {
		
		
		 HttpClient httpclient = new DefaultHttpClient();

	       HttpPost httppost = new HttpPost("http://localhost/upload/index.php");

	        File file = new File("F:/Users/masterof/workspace_plugin/UploadStandalone/src/test.php");

	 
		 
		 MultipartEntity entity = new MultipartEntity();
		 
		 //HttpPost httppost = new HttpPost("http://localhost/upload/index.php");
		 
		 entity.addPart("file", new FileBody(file));
		 httppost.setEntity(entity);

		 HttpResponse response = httpclient.execute(httppost);
		 
		 System.out.println(response.toString());
		// When HttpClient instance is no longer needed, 
	        // shut down the connection manager to ensure
	        // immediate deallocation of all system resources
	        httpclient.getConnectionManager().shutdown();   
	}
	
}


