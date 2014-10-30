package com.yonashaile.yhailecontactslistapp;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;
import android.content.Context;

public class ContactsJSONSerializer {
	    private Context mContext;
	    private String mFilename;

	    /**
	     * @param c
	     * @param f
	     */
	    public ContactsJSONSerializer(Context c, String f) {
	        mContext = c;
	        mFilename = f;
	    }

	    /**
	     * @return contacts
	     * @throws IOException
	     * @throws JSONException
	     */
	    public ArrayList<Contact> loadContacts() throws IOException, JSONException {
	        ArrayList<Contact> contacts = new ArrayList<Contact>();
	        BufferedReader reader = null;
	        try {
	            // open and read the file into a StringBuilder
	            InputStream in = mContext.openFileInput(mFilename);
	            reader = new BufferedReader(new InputStreamReader(in));
	            StringBuilder jsonString = new StringBuilder();
	            String line = null;
	            while ((line = reader.readLine()) != null) {
	                // line breaks are omitted and irrelevant
	                jsonString.append(line);
	            }
	            // parse the JSON using JSONTokener
	            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
	            // build the array of contacts from JSONObjects
	            for (int i = 0; i < array.length(); i++) {
	                contacts.add(new Contact(array.getJSONObject(i)));
	            }
	        } catch (FileNotFoundException e) {
	            // we will ignore this one, since it happens when we start fresh
	        } finally {
	            if (reader != null){
	                reader.close();
	            }
	        }
	        return contacts;
	    }

	    /**
	     * @param contacts
	     * @throws JSONException
	     * @throws IOException
	     */
	    public void saveContacts(ArrayList<Contact> contacts) throws JSONException, IOException {
	        // build an array in JSON
	        JSONArray array = new JSONArray();
	        for (Contact c : contacts)
	            array.put(c.toJSON());

	        // write json file to disk
	        Writer writer = null;
	        try {
	            OutputStream out = mContext.openFileOutput(mFilename, Context.MODE_PRIVATE);
	            writer = new OutputStreamWriter(out);
	            writer.write(array.toString());
	        } finally {
	            if (writer != null)
	                writer.close();
	        }
	    }
	}