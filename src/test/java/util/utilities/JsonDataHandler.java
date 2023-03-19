package util.utilities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import regression.magentoluma.tests.BaseTestAT;

public class JsonDataHandler extends BaseTestAT{
public static JsonObject jsonObject;
	
	/**
	* @author Snigdhadeb Samanta
	* Description : This function will read the whole json test-data from test_data_v1.json file
	* @param
	* @return an instance of JsonObject
	*/
	public static JsonObject readJsonFile(){
		Gson gson = new Gson();
		String testdatda_path = ".//src//test//java//util//resources//test_data_v1.json";
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(testdatda_path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return gson.fromJson(reader, JsonObject.class);
	}
	
	/**
	* @author Snigdhadeb Samanta
	* Description : This function will fetch the value of the json key passed as an argument.
	* @implNote: !!! THE VALUE OF jsonObject WILL BE SET FROM '@BeforeSuite'
	* @param String data name
	* @return the value of the json key as StringBuffer
	 * @throws FileNotFoundException 
	*/
	public String getTestData(String dataname){
		return jsonObject.getAsJsonObject(test_type).getAsJsonObject(testcasename).get(dataname).getAsString();
	}
	
	public ArrayList<String> getTestDataAsJsonArray(String dataname){
		JsonArray ja =  jsonObject.getAsJsonObject(test_type).getAsJsonObject(testcasename).get(dataname).getAsJsonArray();
		ArrayList<String> arrlist = new ArrayList<String>();
		for(int i=0;i<ja.size();i++) {
			arrlist.add(ja.get(i).getAsString());
		}
		return arrlist;
	}
}
