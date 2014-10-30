package com.yonashaile.yhailecontactslistapp;

import java.util.UUID;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.yonashaile.yhailecontactslistapp.R;

/**
 * @author yhaile
 *
 */
public class ContactFragment extends Fragment {
    public static final String EXTRA_CONTACT_ID = "contact.Contact_ID";
    
    // Set variables for contact entries
    Contact mContact;
    EditText mFirstName;
    EditText mLastName;
    EditText mEmail;
    EditText mCell;
    EditText mWork;
    EditText mComment;
    
   private Callbacks mCallbacks;
    /**
     * Required interface for hosting activities.
     */
    public interface Callbacks {
        void onContactUpdated(Contact contact);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallbacks = (Callbacks)activity;
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }




    /**
     * @param contactId
     * @return fragment
     */
    public static ContactFragment newInstance(UUID contactId) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CONTACT_ID, contactId);

        ContactFragment fragment = new ContactFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        UUID contactId = (UUID)getArguments().getSerializable(EXTRA_CONTACT_ID);
        mContact = ContactsList.get(getActivity()).getContact(contactId);

        setHasOptionsMenu(true);
    }

    @Override
    @TargetApi(11)
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contact, parent, false);
    
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        
        // Link to widgets in fragment layout 
        mFirstName = (EditText)v.findViewById(R.id.editText1);
        mLastName = (EditText)v.findViewById(R.id.editText2);
        mEmail = (EditText)v.findViewById(R.id.editText3);
        mCell = (EditText)v.findViewById(R.id.editText4);
        mWork = (EditText)v.findViewById(R.id.editText5);
        mComment = (EditText)v.findViewById(R.id.editText6);
        
        // Set entries in widgets to cantact variables
        mFirstName.setText(mContact.getFirstName());
        mLastName.setText(mContact.getLastName());
        mEmail.setText(mContact.getEmail());
        mCell.setText(mContact.getCell());
        mWork.setText(mContact.getWork());
        mComment.setText(mContact.getComment());
        
        // Add text change listener and thier required method for each contact variable
        mFirstName.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                mContact.setFirstName(c.toString());
                mCallbacks.onContactUpdated(mContact);
            }
            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }
            public void afterTextChanged(Editable c) {
                // this one too
            }
        });
        mLastName.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                mContact.setLastName(c.toString());
                mCallbacks.onContactUpdated(mContact);
            }
            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });
        mEmail.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                mContact.setEmail(c.toString());
                mCallbacks.onContactUpdated(mContact);
                
            }
            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });
        mCell.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                mContact.setCell(c.toString());
                mCallbacks.onContactUpdated(mContact);
            }
            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });
        mWork.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                mContact.setWork(c.toString());
            }
            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });
        mComment.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                mContact.setComment(c.toString());
            }
            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });
        
        return v; 
    }

    @Override
    public void onPause() {
        super.onPause();
        ContactsList.get(getActivity()).saveContacts();
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(getActivity());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        } 
    }
}