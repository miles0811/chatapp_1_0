package com.wowcow.chat10.Loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.wowcow.chat10.Utils.APIResponse;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

public abstract class BaseAsyncTaskLoader extends AsyncTaskLoader<APIResponse> {
	protected final Logger logger = Logger.getLogger(getClass().getName());

	public BaseAsyncTaskLoader(Context context) {
		super(context);
	}

	@Override
	protected void onStartLoading() {
		this.forceLoad();

	}

	@Override
	protected void onStopLoading() {
		this.cancelLoad();
	}

	@Override
	public APIResponse loadInBackground() {
		APIResponse response = new APIResponse();
		try {
			response.setResult(this.action());

		} catch (Exception e) {
			response.setException(e);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
		}
		return response;
	}

	protected abstract String action() throws Exception;
}
