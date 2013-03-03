
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.fourspaces.couchdb.Database;
import com.fourspaces.couchdb.Document;
import com.fourspaces.couchdb.Session;
import com.fourspaces.couchdb.ViewResults;

public class PostCommentsExample {

	private static final Session dbSession = new Session("127.0.0.1", 5984);
	private static final String dbname = "posts_comments";
	
	public static void main(String[] args) {
		
		dbSession.createDatabase(dbname);
		List <String> listofdb = dbSession.getDatabaseNames();
		
		Database db = dbSession.getDatabase(dbname);		         			
		
		//createDocuments();
		
		int count = db.getDocumentCount();
		System.out.println("Total Documents: " + count);
		
		
		createViews(db);
		
		ViewResults results = db.getAllDocuments();
		
		printViewResults();		
		
		//dbSession.deleteDatabase(dbname);
	}
	public static void createViews( Database db ){
		Document viewDoc = new Document();
		viewDoc.setId("_design/postcommentview");
		                 
		String str = "{\"getcommentsperblog\": {\"map\": \"function(doc) { if (doc.type == 'comment')  emit(doc.post, {author: doc.author, content: doc.content}); } \"}," +
					 "\"viewCollation\": { \"map\": \"function(doc) {  if (doc.type == 'post') {    emit([doc._id, 0], doc);  } else if (doc.type == 'comment') {    emit([doc.post, 1], doc);  }\" }," +
					 "\"getallcomments\": { \"map\": \"function(doc) {  if (doc.type == 'comment')   emit(doc.author, {post: doc.post, content: doc.content});  }\" } }";
		         
		viewDoc.put("views", str); 
		db.saveDocument(viewDoc);
	}
	public static void createDocuments()
	{
		dbSession.createDatabase(dbname);
		List <String> listofdb = dbSession.getDatabaseNames();
		
		Database db = dbSession.getDatabase(dbname);
		         
		Document doc = new Document();
		         
		doc.setId("ABCDEF");
		doc.put("type", "comment");
		doc.put("post", "myslug");
		doc.put("author", "jack");
		doc.put("content", "");
		         
		db.saveDocument(doc);
		
		doc = new Document();
		doc.setId("DEFABC");
		doc.put("type", "comment");
		doc.put("post", "myslug");
		doc.put("author", "jane");
		doc.put("content", "");
		         
		db.saveDocument(doc);
		
		doc = new Document();
		doc.setId("myslug");
		doc.put("type", "post");
		doc.put("author", "john");
		doc.put("title", "My blog post");
		doc.put("content", "Bla bla bla …");
		         
		db.saveDocument(doc);
		
	}
	public static void printViewResults()
	{
		HttpClient httpclient = new DefaultHttpClient();
		 
		HttpGet get = new HttpGet("http://localhost:5984/"+dbname+"/_design/postcommentview/_view/getcommentsperblog?key=%22myslug%22");		
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
		//get = new HttpGet("http://localhost:5984/"+dbname+"/_design/postcommentview/_view/view1?startkey=[\"myslug\"]&endkey=[\"myslug\",2]");
	}
}
