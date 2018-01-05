package nico.styTool;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class FolderChooserConfig
implements Parcelable
{
    public static final Parcelable.Creator<FolderChooserConfig> CREATOR;
    public static final String TAG;
    public boolean expandMultipleRoots;
    public boolean expandSingularRoot;
    public boolean mustBeWritable;
    public List<String> roots;
    public boolean showHidden;
    public String subtitle;
    public String title;

    static {
        TAG = FolderChooserConfig.class.getSimpleName();
        CREATOR = new Parcelable.Creator<FolderChooserConfig>(){

            /*
             * Enabled aggressive block sorting
             */
            public FolderChooserConfig createFromParcel(Parcel parcel)
	    {
                boolean bl2 = true;
                FolderChooserConfig folderChooserConfig = new FolderChooserConfig();
                folderChooserConfig.title = parcel.readString();
                folderChooserConfig.subtitle = parcel.readString();
                folderChooserConfig.roots = new ArrayList();
                parcel.readStringList(folderChooserConfig.roots);
            folderChooserConfig.showHidden = parcel.readByte() != 0 && bl2;
            folderChooserConfig.mustBeWritable = parcel.readByte() != 0 && bl2;
            folderChooserConfig.expandSingularRoot = parcel.readByte() != 0 && bl2;
                if (parcel.readByte() == 0)
		{
                    bl2 = false;
                }
                folderChooserConfig.expandMultipleRoots = bl2;
                return folderChooserConfig;
            }

            public FolderChooserConfig[] newArray(int n2)
	    {
                return new FolderChooserConfig[n2];
            }
        };
    }

    public int describeContents()
    {
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void writeToParcel(Parcel parcel, int n2)
    {
        byte by2 = 1;
        parcel.writeString(this.title);
        parcel.writeString(this.subtitle);
        parcel.writeStringList(this.roots);
        byte by3 = this.showHidden ? by2 : 0;
        parcel.writeByte(by3);
        byte by4 = this.mustBeWritable ? by2 : 0;
        parcel.writeByte(by4);
        byte by5 = this.expandSingularRoot ? by2 : 0;
        parcel.writeByte(by5);
        if (!this.expandMultipleRoots)
	{
            by2 = 0;
        }
        parcel.writeByte(by2);
    }

}

