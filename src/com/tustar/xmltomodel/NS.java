/**
 * @author tustar
 * @date Sun Aug 10 23:50:17 CST 2014
 */

package com.tustar.xmltomodel;

import com.tustar.xmltomodel.R;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.app.*;
import android.support.v4.app.Fragment;
import android.support.v4.app.*;

public interface NS {



	public class NSActivityMain {  
 
		public LinearLayout mRootLayout;
		public TextView mHomeSplash;
		public TextView mActionSettings;
		public TextView mTabcontent;
		public NSIncludeLayout mInclude1;
		public TextView mIncludeText;
		public ViewStub mViewstub1;
		public NSViewstubLayout mViewstub1Layout;

		public NSActivityMain (Activity convertView) {
			mRootLayout = (LinearLayout)convertView.findViewById(R.id.root_layout);
			mHomeSplash = (TextView)convertView.findViewById(R.id.home_splash);
			mActionSettings = (TextView)convertView.findViewById(R.id.action_settings);
			mTabcontent = (TextView)convertView.findViewById(android.R.id.tabcontent);
			View mInclude1View = (View)convertView.findViewById(R.id.include_1);
			mInclude1 = new NSIncludeLayout(mInclude1View);
			mIncludeText = (TextView)convertView.findViewById(R.id.include_text);
			mViewstub1 = (ViewStub)convertView.findViewById(R.id.viewstub_1);
		}
	}
 

	public class NSFragmentMain {  
 
		public RelativeLayout mFragmentLayout;
		public TextView mFragmentSplash;
		public TextView mActionSettings;
		public TextView mTabcontent;
		public NSIncludeLayout mInclude1;
		public TextView mIncludeText;

		public NSFragmentMain (View convertView) {
			mFragmentLayout = (RelativeLayout)convertView.findViewById(R.id.fragment_layout);
			mFragmentSplash = (TextView)convertView.findViewById(R.id.fragment_splash);
			mActionSettings = (TextView)convertView.findViewById(R.id.action_settings);
			mTabcontent = (TextView)convertView.findViewById(android.R.id.tabcontent);
			View mInclude1View = (View)convertView.findViewById(R.id.include_1);
			mInclude1 = new NSIncludeLayout(mInclude1View);
			mIncludeText = (TextView)convertView.findViewById(R.id.include_text);
		}
	}
 

	public class NSIncludeLayout {  
 
		public TextView mIncludeText;

		public NSIncludeLayout (View convertView) {
			mIncludeText = (TextView)convertView.findViewById(R.id.include_text);
		}
	}
 

	public class NSViewstubLayout {  
 
		public TextView mViewstubSplash;

		public NSViewstubLayout (View convertView) {
			mViewstubSplash = (TextView)convertView.findViewById(R.id.viewstub_splash);
		}
	}
 }
 