package songhq.com.mongo.vo;


import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "semiAutomaticResult")
public class SemiAutomaticResult implements Serializable {

	private static final long serialVersionUID = -3289366887204851049L;

	@Id
	private BigInteger id;

	/**
	 * 节目ID
	 */
	private String contid;

	/**
	 * 真实播放次数
	 */
	private Long times;

	/**
	 * 半自动修饰播放次数
	 */
	private Long semiAutomaticTimes;

	/**
	 * 创建时间
	 */
	private Date createTime;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getContid() {
		return contid;
	}

	public void setContid(String contid) {
		this.contid = contid;
	}

	public Long getTimes() {
		return times;
	}

	public void setTimes(Long times) {
		this.times = times;
	}

	public Long getSemiAutomaticTimes() {
		return semiAutomaticTimes;
	}

	public void setSemiAutomaticTimes(Long semiAutomaticTimes) {
		this.semiAutomaticTimes = semiAutomaticTimes;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contid == null) ? 0 : contid.hashCode());
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((semiAutomaticTimes == null) ? 0 : semiAutomaticTimes.hashCode());
		result = prime * result + ((times == null) ? 0 : times.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SemiAutomaticResult other = (SemiAutomaticResult) obj;
		if (contid == null) {
			if (other.contid != null)
				return false;
		} else if (!contid.equals(other.contid))
			return false;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (semiAutomaticTimes == null) {
			if (other.semiAutomaticTimes != null)
				return false;
		} else if (!semiAutomaticTimes.equals(other.semiAutomaticTimes))
			return false;
		if (times == null) {
			if (other.times != null)
				return false;
		} else if (!times.equals(other.times))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SemiAutomaticResult [id=" + id + ", contid=" + contid + ", times=" + times + ", semiAutomaticTimes="
				+ semiAutomaticTimes + ", createTime=" + createTime + "]";
	}

}
