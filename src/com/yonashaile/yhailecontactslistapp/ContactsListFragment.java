package com.yonashaile.yhailecontactslistapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.yonashaile.yhailecontactslistapp.R;
import java.util.ArrayList;

/**
 * @author yhaile
 *
 */
public class ContactsListFragment extends ListFragment {
    private ArrayList<Contact> mContacts;
    private boolean mSubtitleVisible;
    private Callbacks mCallbacks;
    

    
    String fullName;
    
    /* (non-Javadoc)
     * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle("Contacts");
        mContacts = ContactsList.get(getActivity()).getContacts();
        ContactAdapter adapter = new ContactAdapter(mContacts);
        setListAdapter(adapter);
        setRetainInstance(true);
        mSubtitleVisible = false;
    }

    @TargetApi(11)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, parent, savedInstanceState);
        getActivity().setTitle("Contacts");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {   
            if (mSubtitleVisible) {
                getActivity().getActionBar().setSubtitle("sub-title");
            }
        }
        
        // Set list view
        ListView listView = (ListView)v.findViewById(android.R.id.list);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            registerForContextMenu(listView);
        } else {
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
            listView.setMultiChoiceModeListener(new MultiChoiceModeListener() {
            
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    MenuInflater inflater = mode.getMenuInflater();
                    inflater.inflate(R.menu.contact_context_menu, menu);
                    return true;
                }
            
                public void onItemCheckedStateChanged(ActionMode mode, int position,
                        long id, boolean checked) {
                }
            
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.menu_item_delete_contact:
                            ContactAdapter adapter = (ContactAdapter)getListAdapter();
                            ContactsList contactLab = ContactsList.get(getActivity());
                            for (int i = adapter.getCount() - 1; i >= 0; i--) {
                                if (getListView().isItemChecked(i)) {
                                    contactLab.deleteContact(adapter.getItem(i));
                                }
                            }
                            mode.finish(); 
                            adapter.notifyDataSetChanged();
                            return true;
                        default:
                            return false;
                    }
                }
          
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }
                
                public void onDestroyActionMode(ActionMode mode) {

                }
            });
           
        }

        return v;
    }
    
    public void onListItemClick(ListView l, View v, int position, long id) {
    	 Contact c = ((ContactAdapter)getListAdapter()).getItem(position);
    	 mCallbacks.onContactSelected(c);
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ((ContactAdapter)getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.contact_list_menu, menu);
    }

    @TargetApi(11)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	 Contact contact = new Contact();
         ContactsList.get(getActivity()).addContact(contact);
         ((ContactAdapter)getListAdapter()).notifyDataSetChanged();
         mCallbacks.onContactSelected(contact);
         return true;         
    }
    
    // Create context menu for long press delete
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.contact_context_menu, menu);
    }

    // Set Context menu item
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
        int position = info.position;
        ContactAdapter adapter = (ContactAdapter)getListAdapter();
        Contact contact = adapter.getItem(position);

        switch (item.getItemId()) {
            case R.id.menu_item_delete_contact:
                ContactsList.get(getActivity()).deleteContact(contact);
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    // Set array adapter for listview items
    private class ContactAdapter extends ArrayAdapter<Contact> {
        public ContactAdapter(ArrayList<Contact> contacts) {
            super(getActivity(), android.R.layout.simple_list_item_1, contacts);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Inflate view if none is given
            if (null == convertView) {
                convertView = getActivity().getLayoutInflater()
                    .inflate(R.layout.list_item_contact, null);
            }

            // configure this Contact's view
            Contact c = getItem(position);
            String properLastName;
            String properFirstName;
            
            // convert last and first name to proper names depending on what's entered or not
            if(c.getLastName() != null && c.getFirstName() != null){  
            	properLastName = c.getLastName().substring(0, 1).toUpperCase() + c.getLastName().substring(1);
            	properFirstName = c.getFirstName().substring(0, 1).toUpperCase() + c.getFirstName().substring(1);
            	fullName = properLastName + ", " + properFirstName;
            } else if(c.getLastName() == null && c.getFirstName() == null){
            	fullName = "#No Name Entered#";
            } else if (c.getLastName() == null && c.getFirstName() != null){
                properFirstName = c.getFirstName().substring(0, 1).toUpperCase() + c.getFirstName().substring(1);
            	fullName = "#no last name#, " + properFirstName;
            } else if (c.getLastName() != null && c.getFirstName() == null){
                properLastName = c.getLastName().substring(0, 1).toUpperCase() + c.getLastName().substring(1);
            	fullName =  properLastName + ", #no first name#";
            }
            
            // Set text view that is listview item with contact full name
            TextView titleTextView = (TextView)convertView.findViewById(R.id.contact_list_item_titleTextView);
            titleTextView.setText(fullName);
            
           // Set text view that is listview item with contact email
            TextView emailTextView = (TextView)convertView.findViewById(R.id.contact_list_item_titleTextView3);
            emailTextView.setText(c.getEmail());
            
            // Set contacts cell number if entered as textview in listview items
            if(c.getCell() != null){            	
            	TextView phoneTextView = (TextView)convertView.findViewById(R.id.contact_list_item_titleTextView2);
                phoneTextView.setText(c.getCell());
            }
            
            // Alternate listview item colors
            if ( position % 2 == 0){
                convertView.setBackgroundResource(R.drawable.back);
            }else{
            	  convertView.setBackgroundResource(R.drawable.back_o);
          }
            
            return convertView;
        }
    }
    
    /**
     * Required interface for hosting activities.
     */
    public interface Callbacks {
        void onContactSelected(Contact contact);
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
    public void updateUI() {
        ((ContactAdapter)getListAdapter()).notifyDataSetChanged();
    }
   
}