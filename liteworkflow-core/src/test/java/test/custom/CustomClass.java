package test.custom;

/**
 * 自定义类
 * 
 * @author yuqs
 * @since 1.0
 */
public class CustomClass
{
	public String execute(String msg)
	{
		System.out.println("execute:" + msg);
		return "return " + msg;
	}
}
