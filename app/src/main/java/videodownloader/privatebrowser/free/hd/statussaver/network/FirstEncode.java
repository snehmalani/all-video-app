package videodownloader.privatebrowser.free.hd.statussaver.network;

import java.security.MessageDigest;
import java.util.Random;

public class FirstEncode {
    private static final char[] CHARS = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};

    public static String firstOccur(String str, String str2) {
        if (str != null && str2 != null) {
            try {
                String v4 = v4(str2);
                String v42 = v4(v0(v4, 0, 16));
                String v43 = v4(v0(v4, 16, 16));
                char[] cArr = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
                Random random = new Random();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < 4; i++) {
                    sb.append(cArr[Math.abs(random.nextInt(35))]);
                }
                StringBuilder sb2 = new StringBuilder();
                sb2.append(v42);
                sb2.append(v4(v42 + ((Object) sb)));
                String sb3 = sb2.toString();
                StringBuilder sb4 = new StringBuilder();
                sb4.append("0000000000");
                sb4.append(v0(v4(str + v43), 0, 16));
                sb4.append(str);
                byte[] v3 = v3(sb4.toString().getBytes("GBK"), sb3);
                return ((Object) sb) + v2(v3);
            } catch (Exception unused) {
                throw new RuntimeException(unused);
            }
        }
        return "";
    }

    private static String v0(String str, int i, int i2) {
        if (i < 0) {
            i2 = i + 16;
            if (i2 <= 0) {
                return "";
            }
            i = 0;
        } else if (i > str.length()) {
            return "";
        }
        if (str.length() - i < i2) {
            i2 = str.length() - i;
        }
        return str.substring(i, i2 + i);
    }

    public static String v2(byte[] var0) {
        StringBuilder sb = new StringBuilder();
        int length = var0.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            int i2 = i + 1;
            int i3 = var0[i] & 255;
            if (i2 == length) {
                char[] cArr = CHARS;
                sb.append(cArr[i3 >>> 2]);
                sb.append(cArr[(i3 & 3) << 4]);
                sb.append("==");
                break;
            }
            int i4 = i2 + 1;
            int i5 = var0[i2] & 255;
            if (i4 == length) {
                char[] cArr2 = CHARS;
                sb.append(cArr2[i3 >>> 2]);
                sb.append(cArr2[((i3 & 3) << 4) | ((i5 & 240) >>> 4)]);
                sb.append(cArr2[(i5 & 15) << 2]);
                sb.append("=");
                break;
            }
            int i6 = var0[i4] & 255;
            char[] cArr3 = CHARS;
            sb.append(cArr3[i3 >>> 2]);
            sb.append(cArr3[((i3 & 3) << 4) | ((i5 & 240) >>> 4)]);
            sb.append(cArr3[((i5 & 15) << 2) | ((i6 & 192) >>> 6)]);
            sb.append(cArr3[i6 & 63]);
            i = i4 + 1;
        }
        return sb.toString();
    }

    private static byte[] v3(byte[] bArr, String str) {
        if (bArr == null || str == null) {
            return null;
        }
        byte[] bArr2 = new byte[bArr.length];
        byte[] bytes = str.getBytes();
        byte[] bArr3 = new byte[256];
        for (int i = 0; i < 256; i++) {
            bArr3[i] = (byte) i;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < 256; i3++) {
            i2 = ((((bArr3[i3] + 256) % 256) + i2) + bytes[i3 % bytes.length]) % 256;
            byte b = bArr3[i3];
            bArr3[i3] = bArr3[i2];
            bArr3[i2] = b;
        }
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 0; i6 < bArr.length; i6++) {
            i4 = (i4 + 1) % 256;
            i5 = (((bArr3[i4] + 256) % 256) + i5) % 256;
            byte b2 = bArr3[i4];
            bArr3[i4] = bArr3[i5];
            bArr3[i5] = b2;
            bArr2[i6] = (byte) (bArr[i6] ^ v5(bArr3[(v5(bArr3[i4]) + v5(bArr3[i5])) % 256]));
        }
        return bArr2;
    }

    private static String v4(String str) {
        StringBuilder sb = new StringBuilder();
        try {
            for (byte b : MessageDigest.getInstance("MD5").digest(str.getBytes())) {
                String hexString = Integer.toHexString(b & 255);
                if (hexString.length() == 1) {
                    hexString = "0" + hexString;
                }
                sb.append(hexString);
            }
        } catch (Exception unused) {
            throw new RuntimeException(unused);
        }
        return sb.toString();
    }

    private static int v5(byte b) {
        return (b + 256) % 256;
    }
}
