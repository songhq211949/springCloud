package songhq.com.cache.utils;


import java.util.Calendar;
import java.util.Date;

/**
 * 评论排序值计算工具类
 * 
 * @author liou
 * 
 */
public class SortScoreUtil {

	/* 评论置顶后在评论创建时间上增加的年数 */
	public static final int TOP_ADD_YEARS = 10000;

	/*
	 * 评论点赞后每次点赞在评论创建时间上增加的年数 10年后或者点赞数超过1000次排序失效
	 */
	public static final int LIKE_ADD_YEARS = 10;

	/**
	 * 利用 是否置顶 和 点赞数 来计算排序值
	 * @param top
	 * @param likeCount
	 * @param createTime
	 * @return
	 */
	public static long getSortScore(Boolean top, Long likeCount, Date createTime) {
		Calendar c = Calendar.getInstance();
		c.setTime(createTime);
		if (top != null && top.booleanValue()) {
			c.add(Calendar.YEAR, TOP_ADD_YEARS);
		}
		if (likeCount != null && likeCount > 0) {
			c.add(Calendar.YEAR, LIKE_ADD_YEARS * likeCount.intValue());
		}
		return c.getTimeInMillis();
	}

	public static long toTopTime(Date createTime) {
		Calendar c = Calendar.getInstance();
		c.setTime(createTime);
		c.add(Calendar.YEAR, TOP_ADD_YEARS);
		return c.getTimeInMillis();
	}

	/**
	 * 
	 * @param top
	 * @param likeCount
	 * @param createTime
	 * @return log2(likeCount)+ createTime(seconds)/45000
	 */
	public static double topicHotScore(boolean top, long likeCount,
			Date createTime) {
		if (createTime == null) {
			return 0;
		}
		if (likeCount <= 0) {
			likeCount = 1;
		}
		double score = Math.log(likeCount) / 0.6931471805599453
				+ (createTime.getTime() - 1465948800000L) / 45000000;
		if (top) {
			score += 7012.8;
		}
		return score;
	}

	/**
	 * 
	 * @param top
	 * @param likeCount
	 * @param readCount
	 * @param createTime
	 * @return log2(likeCount)+log10(readCount)+ createTime(seconds)/45000
	 */
	public static double topicHotScore(boolean top, long likeCount,
			long readCount, Date createTime) {
		if (createTime == null) {
			return 0;
		}
		if (likeCount <= 0) {
			likeCount = 1;
		}
		if (readCount <= 0) {
			readCount = 1;
		}
		double score = Math.log(likeCount) / 0.6931471805599453
				+ Math.log10(readCount) + createTime.getTime() / 45000000;
		if (top) {
			score += 7012.8;
		}
		return score;
	}

}
