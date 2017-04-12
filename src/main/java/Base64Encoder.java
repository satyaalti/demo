import java.io.UnsupportedEncodingException;

import javax.xml.bind.DatatypeConverter;

public class Base64Encoder {

	public static void main(String[] args) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		
		byte[] message = "admin:cppalti".getBytes("UTF-8");
		String encoded = DatatypeConverter.printBase64Binary(message);
		byte[] decoded = DatatypeConverter.parseBase64Binary(encoded);

		System.out.println(encoded);
		System.out.println(new String(decoded, "UTF-8"));

	}

}
