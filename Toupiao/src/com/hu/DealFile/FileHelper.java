package com.hu.DealFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.util.EncodingUtils;

import com.hu.DataClass.Student;

public class FileHelper {
	public String res;

	public void getString(String fileName) throws IOException {
		res = readFileSdcardFile(fileName);
	}

	public String readFileSdcardFile(String fileName) throws IOException {
		String res = "";
		try {
			FileInputStream fin = new FileInputStream(fileName);
			int length = fin.available();
			byte[] buffer = new byte[length];
			fin.read(buffer);
			res = EncodingUtils.getString(buffer, "UTF-8");
			fin.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	public void analyzeData(ArrayList<Student> arrayList)
	{
		String[] clasStrings=res.split("$");
		int lenOfclas=clasStrings.length;
		for(int i=0;i<lenOfclas;i++)
		{
			String[] s=clasStrings[i].split("#");
			Student student=new Student();
			student.sid=s[0];
			student.name=s[1];
			student.phone=s[2];
			student.classPost=s[3];
			student.schoolPost=s[4];
			student.politicalStatus=s[5];
			student.PartyClass=s[6];
		}
	}
}
