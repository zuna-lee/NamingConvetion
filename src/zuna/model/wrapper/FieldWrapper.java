package zuna.model.wrapper;

import java.sql.Connection;
import java.util.HashMap;

import zuna.model.Element;
import zuna.model.MyField;

public class FieldWrapper extends Wrapper{

	public FieldWrapper(Connection conn){
		super(conn);
	}

	public void putEntity(String key, Element value){
		if(value instanceof MyField){
			MyField o = (MyField)value;
		}else{
			return;
		}
	}
	
	public void getEntity(String key){
		
	}
	
	public HashMap<String, MyField> getEntityList(String project){
		return null;
	}
}
