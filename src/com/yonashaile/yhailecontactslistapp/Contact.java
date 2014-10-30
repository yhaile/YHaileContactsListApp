package com.yonashaile.yhailecontactslistapp;

import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author yhaile
 *
 */
public class Contact {
	private static final String JSON_ID = "id";
    private static final String JSON_LNAME = "lastName";
    private static final String JSON_FNAME = "firstName";
    private static final String JSON_EMAIL = "email";
    private static final String JSON_CELL = "cellPhone";
    private static final String JSON_WORK = "workPhone";
    private static final String JSON_COMMENT = "comment";
    
    private UUID mId;
    private String mLastName;
    private String mFirstName;
    private String mEmail;
    private String mCell;
    private String mWork;
    private String mComment;
    
    public Contact() {
        mId = UUID.randomUUID();        
    }

    /**
     * @param json
     * @throws JSONException
     */
    public Contact(JSONObject json) throws JSONException {
        mId = UUID.fromString(json.getString(JSON_ID));
        mLastName = json.getString(JSON_LNAME);
        mFirstName = json.getString(JSON_FNAME);
        mEmail = json.getString(JSON_EMAIL);
        mCell = json.getString(JSON_CELL);
        mWork = json.getString(JSON_WORK);
        mComment = json.getString(JSON_COMMENT);
    }

    /**
     * @return json
     * @throws JSONException
     */
    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_ID, mId.toString());
        json.put(JSON_LNAME, mLastName);
        json.put(JSON_FNAME, mFirstName);
        json.put(JSON_EMAIL, mEmail);
        json.put(JSON_CELL, mCell);
        json.put(JSON_WORK, mWork);
        json.put(JSON_COMMENT, mComment);
        return json;
    }

    @Override
    public String toString() {
        return mLastName;
    }

    /**
     * @return mId
     */
    public UUID getId() {
        return mId;
    }
    
    // Get methods for all contact variables
    /**
     * @return mLastName
     */
    public String getLastName() {
        return mLastName;
    }
    
    /**
     * @return mFirstName
     */
    public String getFirstName() {
        return mFirstName;
    }
    
    /**
     * @return mEmail
     */
    public String getEmail() {
        return mEmail;
    }

    /**
     * @return mCell
     */
    public String getCell() {
        return mCell;
    }

    /**
     * @return mWork
     */
    public String getWork() {
        return mWork;
    }

    /**
     * @return mComment
     */
    public String getComment() {
        return mComment;
    }
    
    
    // Set methods for the Contact Object    
    /**
     * @param lastName
     */
    public void setLastName(String lastName) {
        mLastName = lastName;
    }
    
    /**
     * @param firstName
     */
    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }
    
    /**
     * @param email
     */
    public void setEmail(String email) {
        mEmail = email;
    }
    
    /**
     * @param cell
     */
    public void setCell(String cell) {
        mCell = cell;
    }
    
    /**
     * @param work
     */
    public void setWork(String work) {
        mWork = work;
    }
    
    /**
     * @param comment
     */
    public void setComment(String comment) {
        mComment = comment;
    }    
}