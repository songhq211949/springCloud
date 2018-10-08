package songhq.com.cache.utils;


import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTMLUtils {

	// private static Pattern pImg = Pattern.compile(
	// "<\\s*img(.+?)src=[\"'](.*?)[\"']\\s*/?\\s*>", Pattern.MULTILINE);
	// private static Pattern pHtml = Pattern.compile("<[^>]+>",
	// Pattern.CASE_INSENSITIVE);

	private static final Pattern SPAN_P = Pattern
			.compile("<span\\s+style=[\\\\]*[\\'\\\"]([^\\'\\\"]*)[\\\\]*[\\'\\\"]\\s*>(.*?)</span>");
	private static final Pattern RGB_FUNC_P = Pattern
			.compile("rgb\\(([\\d|\\s]*), ([\\d|\\s]*), ([\\d|\\s]*)\\)");
	// public static final Pattern WDML_REG =
	// Pattern.compile("((</?[B|U]>)|(\\s))*\\\\n((</?[B|U]>)|(\\s))*");
	private static final Pattern WDML_P = Pattern
			.compile("((</?[B|U]>)|(\\s))*\\\\n((</?[B|U]>)|(\\s))*");
	// 空格
	private static final String SPACE_REGX = "((&#160;)|(&nbsp;)|(\\s)){1,2}";
	private static final String SPACE_REPLACE = " ";

	// 换行样式
	private static final String BR_REGX = "<br\\s*\\\\*\\/?>";
	private static final String BR_REPLACE = "\\\\n";

	// 加粗开始
	private static final String STRONG_BEGIN_REGX = "<strong[^><]*>";
	private static final String B_BEGIN_REGX = "<b[^><]*>";

	private static final String STRONG_BEGIN_REPLACE = "<B>";

	// 加粗结束
	private static final String STRONG_END_REGX = "<\\\\*/strong>";
	private static final String B_END_REGX = "<\\\\*/b>";

	private static final String STRONG_END_REPLACE = "</B>";

	// 下滑线开始
	private static final String ULINE_BEGIN_REGX = "<u[^><]*>";
	private static final String ULINE_BEGIN_REPLACE = "<U>";
	// 下滑线结束
	private static final String ULINE_END_REGX = "<\\\\*/u>";
	private static final String ULINE_END_REPLACE = "</U>";

	// 清空html注释
	private static final String HTML_NOTES_REGX = "<!--(?s).*?-->";
	// 清空html标签
	private static final String HTML_LABEL_REGX = "<\\\\*/?[^/?(B)|(U)|(C)|(N)][^><]*>";

	// 左转义符
	private static final String LEFT_ESCAPE = "&lt;";
	private static final String LEFT_REPLACE = "<";
	// 右转义符
	private static final String RIGHT_ESCAPE = "&gt;";
	private static final String RIGHT_REPLACE = ">";

	// 转义符
	private static final String HTML_ESCAPE = "&amp;";
	private static final String HTML_ESCAPE_REPLACE = "&";

	private static Map<String, String> colorMap = new HashMap<String, String>();
	static {
		colorMap.put("lightpink", "ffb6c1");
		colorMap.put("pink", "ffc0cb");
		colorMap.put("crimson", "dc143c");
		colorMap.put("lavenderblush", "fff0f5");
		colorMap.put("palevioletred", "db7093");
		colorMap.put("hotpink", "ff69b4");
		colorMap.put("deeppink", "ff1493");
		colorMap.put("mediumvioletred", "c71585");
		colorMap.put("orchid", "da70d6");
		colorMap.put("thistle", "d8bfd8");
		colorMap.put("plum", "dda0dd");
		colorMap.put("violet", "ee82ee");
		colorMap.put("magenta", "ff00ff");
		colorMap.put("fuchsia", "ff00ff");
		colorMap.put("darkmagenta", "8b008b");
		colorMap.put("purple", "800080");
		colorMap.put("mediumorchid", "ba55d3");
		colorMap.put("darkvoilet", "9400d3");
		colorMap.put("darkorchid", "9932cc");
		colorMap.put("indigo", "4b0082");
		colorMap.put("blueviolet", "8a2be2");
		colorMap.put("mediumpurple", "9370db");
		colorMap.put("mediumslateblue", "7b68ee");
		colorMap.put("slateblue", "6a5acd");
		colorMap.put("darkslateblue", "483d8b");
		colorMap.put("lavender", "e6e6fa");
		colorMap.put("ghostwhite", "f8f8ff");
		colorMap.put("blue", "0000ff");
		colorMap.put("mediumblue", "0000cd");
		colorMap.put("midnightblue", "191970");
		colorMap.put("darkblue", "00008b");
		colorMap.put("navy", "000080");
		colorMap.put("royalblue", "4169e1");
		colorMap.put("cornflowerblue", "6495ed");
		colorMap.put("lightsteelblue", "b0c4de");
		colorMap.put("lightslategray", "778899");
		colorMap.put("slategray", "708090");
		colorMap.put("doderblue", "1e90ff");
		colorMap.put("aliceblue", "f0f8ff");
		colorMap.put("steelblue", "4682b4");
		colorMap.put("lightskyblue", "87cefa");
		colorMap.put("skyblue", "87ceeb");
		colorMap.put("deepskyblue", "00bfff");
		colorMap.put("lightblue", "add8e6");
		colorMap.put("powderblue", "b0e0e6");
		colorMap.put("cadetblue", "5f9ea0");
		colorMap.put("azure", "f0ffff");
		colorMap.put("lightcyan", "e1ffff");
		colorMap.put("paleturquoise", "afeeee");
		colorMap.put("cyan", "00ffff");
		colorMap.put("aqua", "00ffff");
		colorMap.put("darkturquoise", "00ced1");
		colorMap.put("darkslategray", "2f4f4f");
		colorMap.put("darkcyan", "008b8b");
		colorMap.put("teal", "008080");
		colorMap.put("mediumturquoise", "48d1cc");
		colorMap.put("lightseagreen", "20b2aa");
		colorMap.put("turquoise", "40e0d0");
		colorMap.put("auqamarin", "7fffaa");
		colorMap.put("mediumaquamarine", "00fa9a");
		colorMap.put("mediumspringgreen", "f5fffa");
		colorMap.put("mintcream", "00ff7f");
		colorMap.put("springgreen", "3cb371");
		colorMap.put("seagreen", "2e8b57");
		colorMap.put("honeydew", "f0fff0");
		colorMap.put("lightgreen", "90ee90");
		colorMap.put("palegreen", "98fb98");
		colorMap.put("darkseagreen", "8fbc8f");
		colorMap.put("limegreen", "32cd32");
		colorMap.put("lime", "00ff00");
		colorMap.put("forestgreen", "228b22");
		colorMap.put("green", "008000");
		colorMap.put("darkgreen", "006400");
		colorMap.put("chartreuse", "7fff00");
		colorMap.put("lawngreen", "7cfc00");
		colorMap.put("greenyellow", "adff2f");
		colorMap.put("olivedrab", "556b2f");
		colorMap.put("beige", "6b8e23");
		colorMap.put("lightgoldenrodyellow", "fafad2");
		colorMap.put("ivory", "fffff0");
		colorMap.put("lightyellow", "ffffe0");
		colorMap.put("yellow", "ffff00");
		colorMap.put("olive", "808000");
		colorMap.put("darkkhaki", "bdb76b");
		colorMap.put("lemonchiffon", "fffacd");
		colorMap.put("palegodenrod", "eee8aa");
		colorMap.put("khaki", "f0e68c");
		colorMap.put("gold", "ffd700");
		colorMap.put("cornislk", "fff8dc");
		colorMap.put("goldenrod", "daa520");
		colorMap.put("floralwhite", "fffaf0");
		colorMap.put("oldlace", "fdf5e6");
		colorMap.put("wheat", "f5deb3");
		colorMap.put("moccasin", "ffe4b5");
		colorMap.put("orange", "ffa500");
		colorMap.put("papayawhip", "ffefd5");
		colorMap.put("blanchedalmond", "ffebcd");
		colorMap.put("navajowhite", "ffdead");
		colorMap.put("antiquewhite", "faebd7");
		colorMap.put("tan", "d2b48c");
		colorMap.put("brulywood", "deb887");
		colorMap.put("bisque", "ffe4c4");
		colorMap.put("darkorange", "ff8c00");
		colorMap.put("linen", "faf0e6");
		colorMap.put("peru", "cd853f");
		colorMap.put("peachpuff", "ffdab9");
		colorMap.put("sandybrown", "f4a460");
		colorMap.put("chocolate", "d2691e");
		colorMap.put("saddlebrown", "8b4513");
		colorMap.put("seashell", "fff5ee");
		colorMap.put("sienna", "a0522d");
		colorMap.put("lightsalmon", "ffa07a");
		colorMap.put("coral", "ff7f50");
		colorMap.put("orangered", "ff4500");
		colorMap.put("darksalmon", "e9967a");
		colorMap.put("tomato", "ff6347");
		colorMap.put("mistyrose", "ffe4e1");
		colorMap.put("salmon", "fa8072");
		colorMap.put("snow", "fffafa");
		colorMap.put("lightcoral", "f08080");
		colorMap.put("rosybrown", "bc8f8f");
		colorMap.put("indianred", "cd5c5c");
		colorMap.put("red", "ff0000");
		colorMap.put("brown", "a52a2a");
		colorMap.put("firebrick", "b22222");
		colorMap.put("darkred", "8b0000");
		colorMap.put("maroon", "800000");
		colorMap.put("white", "ffffff");
		colorMap.put("whitesmoke", "f5f5f5");
		colorMap.put("gainsboro", "dcdcdc");
		colorMap.put("lightgrey", "d3d3d3");
		colorMap.put("silver", "c0c0c0");
		colorMap.put("darkgray", "a9a9a9");
		colorMap.put("gray", "808080");
		colorMap.put("dimgray", "696969");
		colorMap.put("black", "000000");
	}

	public static String cleanHtml(String htmlStr) {
		if (StringUtil.isNullStr(htmlStr)) {
			return "";
		}

		try {
			// 将所有img标签替换成¡¿¡src="url",width=,height=¡¿¡
			StringBuffer buffer = new StringBuffer(htmlStr);
			int imgStart = buffer.indexOf("<img ");
			while (imgStart >= 0) {
				int srcStart = buffer.indexOf("src=\"", imgStart);
				int srcEnd = buffer.indexOf("\"", srcStart + 5);
				int imgEnd = buffer.indexOf(">", srcEnd);
				if (imgEnd == -1) {
					break;
				}
				String imgStr = buffer.substring(imgStart, imgEnd);
				String widthAttr = getWidthAttr(imgStr);
				String heightAttr = getHeightAttr(imgStr);
				StringBuffer imgReplace = new StringBuffer("¡¿¡");
				imgReplace.append(buffer.substring(srcStart, srcEnd + 1));
				if (StringUtil.isNullStr(widthAttr)) {
					imgReplace.append(",width=auto");
				} else {
					imgReplace.append(",").append(widthAttr);
				}
				if (StringUtil.isNullStr(heightAttr)) {
					imgReplace.append(",height=auto");
				} else {
					imgReplace.append(",").append(heightAttr);
				}
				imgReplace.append("¡¿¡");
				buffer.replace(imgStart, imgEnd + 1, imgReplace.toString());
				imgStart = buffer.indexOf("<img ");
			}

			String html = buffer.toString();
			// 替换空格
			html = html.replaceAll(SPACE_REGX, SPACE_REPLACE);
			// 替换换行
			html = html.replaceAll(BR_REGX, BR_REPLACE);

			// 替换加粗
			html = html.replaceAll(STRONG_BEGIN_REGX, STRONG_BEGIN_REPLACE);
			html = html.replaceAll(STRONG_END_REGX, STRONG_END_REPLACE);

			html = html.replaceAll(B_BEGIN_REGX, STRONG_BEGIN_REPLACE);
			html = html.replaceAll(B_END_REGX, STRONG_END_REPLACE);

			// 替换下滑线
			html = html.replaceAll(ULINE_BEGIN_REGX, ULINE_BEGIN_REPLACE);
			html = html.replaceAll(ULINE_END_REGX, ULINE_END_REPLACE);

			// 转化颜色
			html = translateColoreValue(html);

			// 清空html注释
			html = html.replaceAll(HTML_NOTES_REGX, "");

			// 清空html标签
			html = html.replaceAll(HTML_LABEL_REGX, "");

			// 替换转义符
			html = html.replaceAll("&nbsp;", " ");
			html = html.replaceAll(LEFT_ESCAPE, LEFT_REPLACE);
			html = html.replaceAll(RIGHT_ESCAPE, RIGHT_REPLACE);
			html = html.replaceAll(HTML_ESCAPE, HTML_ESCAPE_REPLACE);
			html = html.replaceAll("[\\u3000]", "  ");
			html = html.trim();

			// 清除段首段尾的空格符
			Matcher m = WDML_P.matcher(html);
			while (m.find()) {
				String wdmlLable = m.group(0);
				String trim_wdmlLable = wdmlLable.replaceAll("\\s", "");
				html = html.replace(wdmlLable, trim_wdmlLable);
			}
			html = html.replaceAll("\\s*\\\\n\\s*", "\\\\n");

			// 清除多余换行符
			html = html.replaceAll("(\\\\n){3,}", "\\\\n\\\\n");

			// 去掉首尾的换行符
			html = html.replaceAll("^(\\\\n)+", "");
			html = html.replaceAll("(\\\\n)+$", "");

			return html;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String translateColoreValue(String html) {
		Matcher m = SPAN_P.matcher(html);
		while (m.find()) {
			String src = m.group(0);
			Map<String, String> styles = parseStyle2Map(m.group(1));
			String content = m.group(2);
			String color = getColoreValue(styles.get("color"));
			String dest = content;
			if (!StringUtil.isNullStr(color)) {
				dest = "<C:" + color + ">" + content + "<N>";
			}
			html = html.replace(src, dest);
		}
		return html;
	}

	public static Map<String, String> parseStyle2Map(String styleStr) {
		Map<String, String> styles = new HashMap<String, String>();
		if (styleStr != null) {
			String[] arr1 = styleStr.split("[;]");
			if (arr1 != null && arr1.length > 0) {
				for (String prop : arr1) {
					String[] arr2 = prop.split(":");
					if (arr2 != null && arr2.length > 1) {
						styles.put(arr2[0].toLowerCase().trim(), arr2[1].trim());
					}
				}
			}
		}
		return styles;
	}

	public static String getColoreValue(String color) {
		if (color == null)
			return "";
		Matcher m = RGB_FUNC_P.matcher(color);
		if (m.find()) {
			int r = StringUtil.nullToInteger(m.group(1));
			int g = StringUtil.nullToInteger(m.group(2));
			int b = StringUtil.nullToInteger(m.group(3));
			color = toHexString(r) + toHexString(g) + toHexString(b);
		} else if (color.startsWith("#")) {
			color = color.substring(1);
		} else if (colorMap.containsKey(color.toLowerCase())) {
			color = colorMap.get(color.toLowerCase());
		}
		return color;
	}

	private static String toHexString(int i) {
		String s = Integer.toHexString(i);
		if (s.length() == 1)
			s = "0" + s;
		else if (s.length() > 2)
			s = s.substring(s.length() - 2);
		return s;
	}

	private static String getWidthAttr(String img) {
		String[] split = img.split(" ");
		for (String attr : split) {
			if (attr.startsWith("width=")) {
				return attr;
			}
		}
		return null;
	}

	private static String getHeightAttr(String img) {
		String[] split = img.split(" ");
		for (String attr : split) {
			if (attr.startsWith("height=")) {
				return attr;
			}
		}
		return null;
	}

//	public static void main(String[] args) {
//		String s = "<b>sdfsf</b>";
//		System.out.println(cleanHtml(s));
//	}
}
