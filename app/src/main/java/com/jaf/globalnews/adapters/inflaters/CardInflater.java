package com.jaf.globalnews.adapters.inflaters;

import android.annotation.TargetApi;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.jaf.globalnews.adapters.BaseInflaterAdapter;
import com.jaf.globalnews.adapters.CardItemData;
import com.jaf.globalnews.adapters.IAdapterViewInflater;


import globalnews.jaf.com.globalnews.R;
import globalnews.jaf.com.globalnews.util.URLImageParser;

/**
 * Created with IntelliJ IDEA.
 * User: Justin
 * Date: 10/6/13
 * Time: 12:47 AM
 */
public class CardInflater implements IAdapterViewInflater<CardItemData>
{
	@Override
	public View inflate(final BaseInflaterAdapter<CardItemData> adapter, final int pos, View convertView, ViewGroup parent)
	{
		ViewHolder holder;

		if (convertView == null)
		{
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			convertView = inflater.inflate(R.layout.list_item_card, parent, false);
			holder = new ViewHolder(convertView);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}

		final CardItemData item = adapter.getTItem(pos);
		holder.updateDisplay(item);

		return convertView;
	}

	private class ViewHolder
	{
		private View m_rootView;
		private TextView m_text1;
		//private TextView m_text2;

		public ViewHolder(View rootView)
		{
			m_rootView = rootView;
			m_text1 = (TextView) m_rootView.findViewById(R.id.text1);
			//m_text2 = (TextView) m_rootView.findViewById(R.id.text2);
			//m_text2.getSettings().setBuiltInZoomControls(true);
			//m_text2.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
			//m_text2.getSettings().setUseWideViewPort(true);
			//m_text2.setInitialScale(1);
			rootView.setTag(this);
		}

        public void updateDisplay(CardItemData item)
		{
			m_text1.setText(item.getNewsHeader());

            //URLImageParser imageParser = new URLImageParser(m_text2, m_text2.getContext());
            //String htmlContent=item.getNewsDescription();

           // htmlContent = htmlContent.replaceAll("<a[^>]*>(.*?)</a>", "");
            //htmlContent = htmlContent.replaceAll("<[^>]*>", "");
            //System.out.println("Html 1: "+htmlContent);
            //Spanned htmlSpan = Html.fromHtml(htmlContent, imageParser, null);
			//m_text2.setText(htmlSpan);
		}
	}
}
