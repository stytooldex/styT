package nico.styTool;

public class SHA_dex{
private static final boolean hexcase = false;  
    private static final int chrsz = 8;  
 
    // 得到字符串SHA-1值的方法  
    public static String hex_sha1(String s) {
        s = (s == null) ? "" : s;  
        return binb2hex(core_sha1(str2binb(s), s.length() * chrsz));  
    }  
    
    public static String hex_sha1_withsalt(String s, String salt)
    {
    	s = s + "{" + salt + "}";
    	return hex_sha1(s);
    }
    
    private static String binb2hex(int[] binarray) {  
        String hex_tab = "0123456789abcdef";
        String str = "";  
 
        for (int i = 0; i < binarray.length * 4; i++) {  
            char a = hex_tab.charAt((binarray[i >> 2] >> ((3 - i % 4) * 8 + 4)) & 0xf);
            char b = hex_tab.charAt((binarray[i >> 2] >> ((3 - i % 4) * 8)) & 0xf);
            str += (Character.valueOf(a).toString() + Character.valueOf(b).toString());
        }  
        return str;  
    }  
 
    private static String binb2str(int[] bin) {  
        String str = "";  
        int mask = (1 << chrsz) - 1;  
 
        for (int i = 0; i < bin.length * 32; i += chrsz) {  
            str += (char) ((bin[i >> 5] >>> (24 - i % 32)) & mask);  
        }  
        return str;  
    }  
 
    private static int[] core_sha1(int[] x, int len) {  
        int size = (len >> 5);  
        x = strechbinarray(x, size);  
        x[len >> 5] |= 0x80 << (24 - len % 32);  
        size = ((len + 64 >> 9) << 4) + 15;  
        x = strechbinarray(x, size);  
        x[((len + 64 >> 9) << 4) + 15] = len;  
 
        int[] w = new int[80];  
        int a = 1732584193;  
        int b = -271733879;  
        int c = -1732584194;  
        int d = 271733878;  
        int e = -1009589776;  
 
        for (int i = 0; i < x.length; i += 16) {  
            int olda = a;  
            int oldb = b;  
            int oldc = c;  
            int oldd = d;  
            int olde = e;  
 
            for (int j = 0; j < 80; j++) {  
                if (j < 16) {  
                    w[j] = x[i + j];  
                } else {  
                    w[j] = rol(w[j - 3] ^ w[j - 8] ^ w[j - 14] ^ w[j - 16], 1);  
                }  
 
                int t = safe_add(safe_add(rol(a, 5), sha1_ft(j, b, c, d)), safe_add(safe_add(e, w[j]), sha1_kt(j)));  
 
                e = d;  
                d = c;  
                c = rol(b, 30);  
                b = a;  
                a = t;  
            }  
 
            a = safe_add(a, olda);  
            b = safe_add(b, oldb);  
            c = safe_add(c, oldc);  
            d = safe_add(d, oldd);  
            e = safe_add(e, olde);  
        }  
 
        int[] retval = new int[5];  
 
        retval[0] = a;  
        retval[1] = b;  
        retval[2] = c;  
        retval[3] = d;  
        retval[4] = e;  
 
        return retval;  
    }  
 
    private static int rol(int num, int cnt) {  
        return (num << cnt) | (num >>> (32 - cnt));  
    }  
 
    private static int safe_add(int x, int y) {  
        int lsw = (x & 0xffff) + (y & 0xffff);
        int msw = (x >> 16) + (y >> 16) + (lsw >> 16);  
 
        return (msw << 16) | (lsw & 0xffff);  
    }  
 
    private static int sha1_ft(int t, int b, int c, int d) {  
        if (t < 20)  
            return (b & c) | ((~b) & d);  
 
        if (t < 40)  
            return b ^ c ^ d;  
 
        if (t < 60)  
            return (b & c) | (b & d) | (c & d);  
 
        return b ^ c ^ d;  
    }  
 
    private static int sha1_kt(int t) {  
        return (t < 20) ? 1518500249 : (t < 40) ? 1859775393 : (t < 60) ? -1894007588 : -899497514;  
    }  
 
    public static String str_sha1(String s) {  
        s = (s == null) ? "" : s;  
 
        return binb2str(core_sha1(str2binb(s), s.length() * chrsz));  
    }  
 
    private static int[] str2binb(String str) {  
        str = (str == null) ? "" : str;  
 
        int[] tmp = new int[str.length() * chrsz];  
        int mask = (1 << chrsz) - 1;  
 
        for (int i = 0; i < str.length() * chrsz; i += chrsz) {  
            tmp[i >> 5] |= ((int) (str.charAt(i / chrsz)) & mask) << (24 - i % 32);  
        }  
 
        int len = 0;  
        for (int i = 0; i < tmp.length && tmp[i] != 0; i++, len++)  
            ;  
 
        int[] bin = new int[len];

        System.arraycopy(tmp, 0, bin, 0, len);
 
        return bin;  
    }  
 
    private static int[] strechbinarray(int[] oldbin, int size) {  
        int currlen = oldbin.length;  
 
        if (currlen >= size + 1) {  
            return oldbin;  
        }  
 
        int[] newbin = new int[size + 1];  
        for (int i = 0; i < size; newbin[i] = 0, i++)  
            ;

        System.arraycopy(oldbin, 0, newbin, 0, currlen);
 
        return newbin;  
    }  
} 
