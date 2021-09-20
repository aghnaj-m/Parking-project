
public class Park {

	private int numero;
	private String name;
	private int price;
	private String duration;
	private String distance;
	private float latitude;
	private float longitude;
	
	public Park(int numero,String name,int price,String duration, String distance,float latitude,float longitude)
	{
		this.numero = numero;
		this.name = name;
		this.price = price;
		this.duration = duration;
		this.distance = distance;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	
	public int getNumero() { return numero;}
	public String getName() {return name;}
	public int getPrice() { return price;}
	public String getDuration() { return duration;}
	public String getDistance() {return distance;}
	public float getLatitude() { return latitude;}
	public float getLongitude() { return longitude;}
	
	
	public void setNumero(int numero) { this.numero = numero;}
	public void setName(String name) { this.name = name;}
	public void setPrice(int price) { this.price = price;}
	public void setDuration(String duration) {this.duration = duration;}
	public void setDistance(String distance) {this.distance = distance;}
	public void setLatitude(float latitude) {this.latitude = latitude;}
	public void setLongitude(float longitude) {this.longitude = longitude;}
	
}
