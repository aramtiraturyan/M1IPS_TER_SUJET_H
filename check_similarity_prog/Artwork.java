package similaritycheck;

public class Artwork {
	
	private String uri = "";
	private String catnb = "";
	private String name = "";
		
	public Artwork(String uri, String catnb, String name) {
		this.uri = uri;
		this.catnb = catnb;
		this.name = name;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getCatnb() {
		return catnb;
	}

	public void setCatnb(String catnb) {
		this.catnb = catnb;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
