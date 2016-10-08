package hyj.util;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author asus
 */
public class ConfFileUtil {
	public static String readConfFile(String path) {
		InputStream is = null;
		String conf = "";
		try {
			is = ConfFileUtil.class.getClassLoader().getResourceAsStream(path);
			int l = is.available();
			byte[] b = new byte[l];
			if (is.read(b, 0, b.length) != -1) {
				conf = new String(b, "utf-8");
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return conf;
	}
}