package songhq.com.cache.utils;


public class DoubanUtil {

	public static final String BASE_DOUBAN_URL = "http://api.douban.com";
	public static final String LONG_COMMENT_URL = BASE_DOUBAN_URL
			+ "/v2/movie/subject/%s/reviews?apikey=%s&start=%s&count=%s";
	public static final String LONG_COMMENT_TEXT_URL = "http://movie.douban.com/j/review/%s/fullinfo?show_works=False";
	public static final String DOUBAN_SCORE_URL = "http://api.douban.com/v2/movie/subject/%s/rating?apikey=%s";
	public static final String DOUBAN_SCORE_FORMAT = "{\"max\":%s,\"min\":%s,\"average\":%s,\"stars\":\"%s\",\"details\":{\"1\":%.1f,\"2\":%.1f,\"3\":%.1f,\"4\":%.1f,\"5\":%.1f}}";
	public static String LONG_COMMENT_TEXT_END = "<div class=";//"<div class=\"clear\">"
	public static String API_KEY = "0edfff6473843ebe2c9ffe2d5a8cbc3a";
	public static String SECRETE = "4407addfa7de1db";

}
