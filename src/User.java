
public class User {

	private String Nom;
	private String Email;
	private String Matricule;
	
	public User(String Nom,String Email,String Matricule)
	{
		this.Nom = Nom;
		this.Email = Email;
		this.Matricule = Matricule;
	}
	
	public String getNom() { return this.Nom;}
	public String getEmail() { return this.Email;}
	public String getMatricule() { return this.Matricule;}	
}
