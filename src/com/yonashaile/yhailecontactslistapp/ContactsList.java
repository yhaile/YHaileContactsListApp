package com.yonashaile.yhailecontactslistapp;

import java.util.ArrayList;
import java.util.UUID;

import android.content.Context;

import android.util.Log;
import android.widget.Toast;

public class ContactsList {
    private static final String TAG = "ContactsList";
    private static final String FILENAME = "contacts.json";

    private ArrayList<Contact> mContacts;
    private ContactsJSONSerializer mSerializer;

    private static ContactsList sContactsList;
    private Context mAppContext;

    /**
     * @param appContext
     */
    private ContactsList(Context appContext) {
        mAppContext = appContext;
        mSerializer = new ContactsJSONSerializer(mAppContext, FILENAME);

        try {
            mContacts = mSerializer.loadContacts();
        } catch (Exception e) {
            mContacts = new ArrayList<Contact>();
            Log.e(TAG, "Error loading contacts: ", e);
        }
    }

    /**
     * @param c
     * @return sContactsList
     */
    public static ContactsList get(Context c) {
        if (sContactsList == null) {
            sContactsList = new ContactsList(c.getApplicationContext());
        }
        return sContactsList;
    }

    /**
     * @param id
     * @return null
     */
    public Contact getContact(UUID id) {
        for (Contact c : mContacts) {
            if (c.getId().equals(id))
                return c;
        }
        return null;
    }
    
    /**
     * @param c
     */
    public void addContact(Contact c) {
        mContacts.add(c);
        saveContacts();
    }

    /**
     * @return mContacts
     */
    public ArrayList<Contact> getContacts() {
        return mContacts;
    }

    /**
     * @param c
     */
    public void deleteContact(Contact c) {
        mContacts.remove(c);
        saveContacts();
    }

    /**
     * @return false
     */
    public boolean saveContacts() {
        try {
            mSerializer.saveContacts(mContacts);
            Log.d(TAG, "contacts saved to file");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error saving contacts: " + e);
            Toast.makeText(mAppContext, "Error saving contacts: " + e,Toast.LENGTH_LONG).show();
            return false;
        }
    }
}