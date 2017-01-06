package com.sample_unee_android;

public class AppSingleton {
	private static AppSingleton appSingleton = new AppSingleton();

	private App application;

	private AppSingleton() {
	}

	public static AppSingleton getInstance() {
		if (appSingleton == null)
			appSingleton = new AppSingleton();
		return appSingleton;
	}

	public App getApplication() {
		return application;
	}

	public void setApplication(App application) {
		this.application = application;
	}

}
