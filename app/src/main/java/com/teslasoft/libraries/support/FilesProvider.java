package com.teslasoft.libraries.support;

import androidx.core.content.FileProvider;
// import android.support.v4.content.FileProvider; /* DEPRECATED API */

public class FilesProvider extends FileProvider
{
	public String getFileProviderAuthority() {
        return "com.teslasoft.libraries.support.fileprovider";
	}
}
