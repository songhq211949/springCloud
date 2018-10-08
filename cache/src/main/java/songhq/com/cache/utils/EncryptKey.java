package songhq.com.cache.utils;


public class EncryptKey implements java.security.Key {

	public String getAlgorithm() {

		return "DES";
	}

	public byte[] getEncoded() {
		byte[] bb = new byte[8];
		bb[0] = 55;
		bb[1] = -62;
		bb[2] = 55;
		bb[3] = -8;
		bb[4] = 14;
		bb[5] = 93;
		bb[6] = -51;
		bb[7] = -22;

		return bb;
	}

	public String getFormat() {
		return "RAW";
	}

}