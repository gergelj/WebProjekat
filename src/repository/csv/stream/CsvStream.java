/***********************************************************************
 * Module:  CsvStream.java
 * Author:  Geri
 * Purpose: Defines the Class CsvStream
 ***********************************************************************/

package repository.csv.stream;

import repository.csv.converter.ICsvConverter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class CsvStream <T> implements ICsvStream<T> {
	
   private String path;
   private ICsvConverter<T> converter;
   private String encoding = "UTF8";
    
   public CsvStream(String path, ICsvConverter<T> converter) {
	   this.path = path;
	   this.converter = converter;
   }
   
   public void saveAll(List<T> entities) {
		try {
			BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.path), this.encoding));
			
			for(T entity : entities) {
				wr.write(this.converter.toCsv(entity));
				wr.newLine();
			}
			
			wr.close();
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   }
   
   public List<T> readAll() {
	   List<T> entities = new ArrayList<T>();
	   
	   try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(new FileInputStream(this.path), this.encoding));
			
			String line = null;
			
			while((line=rd.readLine()) != null) {
				entities.add(this.converter.fromCsv(line));
			}
			
			rd.close();
	   } catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
	   return entities;
   }
   
   public void appendToFile(T entity) {
	   try {
			BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.path, true), this.encoding));
			
			wr.append(this.converter.toCsv(entity));
			wr.newLine();
			
			wr.close();
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   }

}