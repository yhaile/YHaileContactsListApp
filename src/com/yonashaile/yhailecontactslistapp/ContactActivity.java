package com.yonashaile.yhailecontactslistapp;

import java.util.UUID;

import android.support.v4.app.Fragment;

/**
 * @author yhaile
 *
 */
public class ContactActivity extends SingleFragmentActivity {
	@Override
    protected Fragment createFragment() {
        UUID contactId = (UUID)getIntent()
            .getSerializableExtra(ContactFragment.EXTRA_CONTACT_ID);
        return ContactFragment.newInstance(contactId);
    }
}