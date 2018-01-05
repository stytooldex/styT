package nico.styTool;
/**
 * Created by apple on 15/9/24.
 */
public class GetNumberFromString {

    public static Long change(String str){
        str=str.trim();
        String str2="";
        if(str != null && !"".equals(str)){
            for(int i=0;i<str.length();i++){
                if(str.charAt(i)>=48 && str.charAt(i)<=57){
                    str2+=str.charAt(i);
                }
            }

        }
        Long i=i= Long.valueOf(str2);
        return i;
    }
}
