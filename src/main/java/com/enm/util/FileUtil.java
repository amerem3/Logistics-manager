package com.enm.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil
{
	public static void WriteFileLine(String f, List<String> bufferline)
	{
		try
		{
			PrintWriter writer = new PrintWriter(f, "UTF-8");
			for(String l : bufferline)writer.println(l);
			writer.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		
	}
	
	public static List<String> ReadFileLine(String f)
	{
		List<String> bufferline = new ArrayList<String>();
		
		BufferedReader br;
		try
		{
			br = new BufferedReader(new FileReader(f));
			try
			{
			    String line = br.readLine();
			    bufferline.add(line);
			    while (line != null)
			    {
			        line = br.readLine();
			        bufferline.add(line);
			    }
			}
			finally {br.close();}
		}
		catch (IOException e) {}
		
		return bufferline;
	}
	
	public static List<String> ReadFileLine(InputStreamReader in)
	{
		List<String> bufferline = new ArrayList<String>();
		
		BufferedReader br;
		try
		{
			br = new BufferedReader(in);
			try
			{
			    String line = br.readLine();
			    bufferline.add(line);
			    while (line != null)
			    {
			        line = br.readLine();
			        bufferline.add(line);
			    }
			}
			finally {br.close();}
		}
		catch (IOException e) {}
		
		return bufferline;
	}
}
