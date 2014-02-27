package supersql.sql.templates.factory;

public class Options
{

	private boolean checkIfExists;
	private String componentToUpdate;

	public boolean isCheckIfExists()
	{
		return checkIfExists;
	}

	public String getComponentToUpdate()
	{
		return componentToUpdate;
	}

	public Options(boolean checkIfExists, String componentToUpdate)
	{
		super();
		this.checkIfExists = checkIfExists;
		this.componentToUpdate = componentToUpdate;
	}

}
