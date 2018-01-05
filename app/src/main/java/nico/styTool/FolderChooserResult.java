package nico.styTool;

import android.os.Parcel;
import android.os.Parcelable;

public class FolderChooserResult
implements Parcelable
{
    public static final Parcelable.Creator<FolderChooserResult> CREATOR;
    public static final String TAG;
    public String selectedFolder;

    static {
        TAG = FolderChooserResult.class.getSimpleName();
        CREATOR = new Parcelable.Creator<FolderChooserResult>(){

            public FolderChooserResult createFromParcel(Parcel parcel)
	    {
                FolderChooserResult folderChooserResult = new FolderChooserResult();
                folderChooserResult.selectedFolder = parcel.readString();
                return folderChooserResult;
            }

            public FolderChooserResult[] newArray(int n2)
	    {
                return new FolderChooserResult[n2];
            }
        };
    }

    public int describeContents()
    {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2)
    {
        parcel.writeString(this.selectedFolder);
    }

}

