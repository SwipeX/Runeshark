package bot.script;

public class ScriptData {

	public Class<?> scriptClass;
	public String name;
	public String[] authors;
	public double version;
	public String category;
	public String description;

	public ScriptData(Class<?> sClass, String sName, String[] sAuthors,
			double sVersion, String sCategory, String sDescription) {
		this.scriptClass = sClass;
		this.name = sName;
		this.authors = sAuthors;
		this.version = sVersion;
		this.category = sCategory;
		this.description = sDescription;
	}

}
