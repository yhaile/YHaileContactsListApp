package com.yonashaile.yhailecontactslistapp;

import java.util.UUID;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import com.yonashaile.yhailecontactslistapp.R;
import java.util.ArrayList;

/**
 * @author yhaile
 *
 */
public class ContactsPagerActivity extends FragmentActivity  implements ContactFragment.Callbacks {
    ViewPager mViewPager;

    /* (non-Javadoc)
     * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create viewpager object
        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);

        final ArrayList<Contact> contacts = ContactsList.get(this).getContacts();

        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public int getCount() {
                return contacts.size();
            }
            @Override
            public Fragment getItem(int pos) {
                UUID contactId =  contacts.get(pos).getId();
                return ContactFragment.newInstance(contactId);
            }
        }); 

        UUID contactId = (UUID)getIntent().getSerializableExtra(ContactFragment.EXTRA_CONTACT_ID);
        //Add fragments to viewpager
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getId().equals(contactId)) {
                mViewPager.setCurrentItem(i);
                break;
            } 
        }
    }

	@Override
	public void onContactUpdated(Contact contact) {
		// TODO Auto-generated method stub
		
	}
}