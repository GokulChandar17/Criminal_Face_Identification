public class Database
{
	String username;
	String password;
	String host;
	public Database()
	{
		try
		{
			java.io.FileInputStream datain=new java.io.FileInputStream("");
			java.io.DataInputStream datadin=new java.io.DataInputStream(datain);
			String line="";
			username="root";
			password="@#$5776267gc*";
			host=datadin.readLine();
		}
		catch(Exception e)
		{
		}
	}
}

class Test
{
	public static void main(String args[])
	{
		Database d1=new Database();
		System.out.println(d1.host+" " + d1.password +" " + d1.username);
	}
}