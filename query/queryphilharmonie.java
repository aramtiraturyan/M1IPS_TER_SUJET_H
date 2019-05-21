package query;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.regex.Pattern;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.util.FileManager;

public class queryphilharmonie {

	
	public static void main(String[] args) throws IOException, ArrayIndexOutOfBoundsException {

	     boolean bName;
	     File repertoire = new File("C:\\inputfolder");
	     
	 File[] files=repertoire.listFiles();

	 for(int i = 0; i <= files.length ; i++){

	            String fileName = files[i].getName();
	            Pattern uName = Pattern.compile("[a-zA-Z0-9_.+-]+\\.ttl");
	             java.util.regex.Matcher mUname = uName.matcher(fileName);
	             bName = mUname.matches();
	          if (bName) {
	        	  
	        	  FileManager.get().addLocatorClassLoader(query.class.getClassLoader());
	               
	        	  Model model2 = FileManager.get().loadModel(files[i].getAbsolutePath());
	        	
	        	    String queryString = "PREFIX mus: <http://data.doremus.org/ontology#>"
	        	    		+ "PREFIX ecrm: <http://erlangen-crm.org/current/>"
	        	    		+ "PREFIX foaf: <http://xmlns.com/foaf/0.1/>"
	        	    		+ "PREFIX efrbroo: <http://erlangen-crm.org/efrbroo/>"
	        	    		+ "CONSTRUCT { ?s foaf:name ?composerName; mus:U41_has_catalogue_number ?catalogueNumber }"
	        	    		+ "WHERE {"
	        	    		+ "?s mus:U16_has_catalogue_statement/ecrm:P3_has_note ?catalogueNumber."
	        	    		+ "?s1 efrbroo:R17_created ?s; ecrm:P9_consists_of ?tmp . ?tmp mus:U31_had_function <http://data.doremus.org/vocabulary/function/composer> ;"
	        	    		+ "ecrm:P14_carried_out_by/foaf:name ?composerName ."
	        	    		+ " }";
	        	 
	        	    	        	    
	        	    	          
	        	          Query query = QueryFactory.create(queryString);
	                      QueryExecution qexec= QueryExecutionFactory.create(query, model2);
	                      Model resultModel = qexec.execConstruct();
	                   	                   
	                  OutputStream out = new FileOutputStream("C:\\outputfolder\\result.ttl", true);
	         	       
	         	      RDFDataMgr.write(out, resultModel, Lang.TURTLE);       	  	                  		
	                  System.out.println(fileName + " is written. Done!");
	                      	                      
	                    qexec.close();
	             	    
	                      }
	             }
	}
}