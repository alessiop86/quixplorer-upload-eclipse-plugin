package quixploder_uploader.upload;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;


public class Test {

	public static void main(String[] args) throws ClientProtocolException, IOException {


		String c = "/ci.php";
		
		int lastIndex = c.lastIndexOf('/');
		
		String path;
		if (lastIndex > -1)
			path = c.substring(0, lastIndex );
		else
			path = "";
		
		System.out.println ( path );
		
//		CopiaFileSuQuixplorer c = new CopiaFileSuQuixplorer();
//		c.login();
//		c.uploadFiles();

	
	}
	
	
	

}
