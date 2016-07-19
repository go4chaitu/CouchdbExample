
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;








import java.util.List;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.fourspaces.couchdb.Database;
import com.fourspaces.couchdb.Document;
import com.fourspaces.couchdb.Session;
import com.fourspaces.couchdb.ViewResults;

public class CouchDBExample {

	private static final Session dbSession = new Session("127.0.0.1", 5984);
	private static final String dbname = "employee4";
	
	public static void main(String[] args) {
		
		dbSession.createDatabase(dbname);
		List <String> listofdb = dbSession.getDatabaseNames();
		
		Database db = dbSession.getDatabase(dbname);		         			
		
		createDocuments();
		
		int count = db.getDocumentCount();
		System.out.println("Total Documents: " + count);
		
		
		Document viewDoc = new Document();
		viewDoc.setId("_design/couchview");
		                 
		String str = "{\"javalanguage\": {\"map\": \"function(doc) { if (doc.Language == 'Java')  emit(null, doc) } \"}, \"java_and_se\": {\"map\": \"function(doc) { if (doc.Language == 'Java' & doc.Designation == 'SE')  emit(null, doc) } \"}}";
		         
		viewDoc.put("views", str); 
		db.saveDocument(viewDoc);
		
		ViewResults results = db.getAllDocuments();
		
		printViewResults();		
		
		//dbSession.deleteDatabase(dbname);
	}
	
	public static void createDocuments()
	{
		dbSession.createDatabase(dbname);
		List <String> listofdb = dbSession.getDatabaseNames();
		
		Database db = dbSession.getDatabase(dbname);
		         
		Document doc = new Document();
		         
		doc.setId("1");
		doc.put("EmpNO", "1");
		doc.put("Name", "Mike");
		doc.put("Group", "J2EECOE");
		doc.put("Designation", "Manager");
		doc.put("Language", "Java");
		         
		db.saveDocument(doc);
		
		doc = new Document();
		doc.setId("2");
		doc.put("EmpNO", "2");
		doc.put("Name", "Mike2");
		doc.put("Group", "J2EECOE2");
		doc.put("Designation", "Manager2");
		doc.put("Language", "Java2");
		         
		db.saveDocument(doc);
		
		doc = new Document();
		doc.setId("3");
		doc.put("EmpNO", "3");
		doc.put("Name", "Mike3");
		doc.put("Group", "J2EECOE3");
		doc.put("Designation", "Manager3");
		doc.put("Language", "Java3");
		         
		db.saveDocument(doc);
		
		doc = new Document();
		doc.setId("4");
		doc.put("EmpNO", "4");
		doc.put("Name", "Mike4");
		doc.put("Group", "J2EECOE4");
		doc.put("Designation", "Manager4");
		doc.put("Language", "Java");
		         
		db.saveDocument(doc);
		
		doc = new Document();
		doc.setId("5");
		doc.put("EmpNO", "5");
		doc.put("Name", "Mike5");
		doc.put("Group", "J2EECOE5");
		doc.put("Designation", "Manager5");
		doc.put("Language", "Java5");
		         
		db.saveDocument(doc);
		
		doc = new Document();
		doc.setId("6");
		doc.put("EmpNO", "6");
		doc.put("Name", "Mike6");
		doc.put("Group", "J2EECOE6");
		doc.put("Designation", "Manager6");
		doc.put("Language", "Java6");
		         
		db.saveDocument(doc);
		

		Document d = db.getDocument("1");
		
		System.out.println( d );
	}
	public static void printViewResults()
	{
		HttpClient httpclient = new DefaultHttpClient();
		 
		HttpGet get = new HttpGet("http://localhost:5984/"+dbname+"/_design/couchview/_view/javalanguage");
		try{ 
			HttpResponse response = httpclient.execute(get);
			HttpEntity entity=response.getEntity();
			InputStream instream = entity.getContent();
			 
			BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
			String strdata = null;
			 
			while( (strdata =reader.readLine())!=null)
			{
			       System.out.println(strdata);
			}
		}
		catch( IOException ioe ){
			System.out.println( ioe );
		}

	}
}
