package com.yonashaile.yhailecontactslistapp;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.yonashaile.yhailecontactslistapp.R;

/**
 * @author yhaile
 *
 */
public class ContactListActivity  extends SingleFragmentActivity
implements ContactsListFragment.Callbacks, ContactFragment.Callbacks {

    @Override
    protected Fragment createFragment() {
        return new ContactsListFragment();
    }
    public void onContactUpdated(Contact contact) {
        FragmentManager fm = getSupportFragmentManager();
        ContactsListFragment listFragment = (ContactsListFragment)
                       fm.findFragmentById(R.id.fragmentContainer);
        listFragment.updateUI();
    }

    
    //overriding getLayoutResId() to return R.layout.activity_twopane
    @Override
    protected int getLayoutResId() {
    	return R.layout.activity_masterdetail;
    }

    
    public void onContactSelected(Contact contact) {
    	
    	 if (findViewById(R.id.detailFragmentContainer) == null) {
    	        // Start an instance of CrimePagerActivity
    	        Intent i = new Intent(this, ContactsPagerActivity.class);
    	        i.putExtra(ContactFragment.EXTRA_CONTACT_ID, contact.getId());
    	        startActivity(i);
    	    } else {
    	        FragmentManager fm = getSupportFragmentManager();
    	        FragmentTransaction ft = fm.beginTransaction();
    	        Fragment oldDetail = fm.findFragmentById(R.id.detailFragmentContainer);
    	        Fragment newDetail = ContactFragment.newInstance(contact.getId());
    	        if (oldDetail != null) {
    	            ft.remove(oldDetail);
    	        }
    	        ft.add(R.id.detailFragmentContainer, newDetail);
    	        ft.commit();
    	    }
    }

    
	
}