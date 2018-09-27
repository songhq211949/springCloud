package songhq.com.mongo.vo;



import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "manualResult")//collection 相当于mysql中的table，document相当于mysql中的一行数据
public class ManualResult implements Serializable {

	private static final long serialVersionUID = 833769868535751028L;

	
	//主键，代替mongo默认的主键，一般都会主键创建索引
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
	 * 人工设置播放次数
	 */
	private Long manualTimes;

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

	public Long getManualTimes() {
		return manualTimes;
	}

	public void setManualTimes(Long manualTimes) {
		this.manualTimes = manualTimes;
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
		result = prime * result + ((manualTimes == null) ? 0 : manualTimes.hashCode());
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
		ManualResult other = (ManualResult) obj;
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
		if (manualTimes == null) {
			if (other.manualTimes != null)
				return false;
		} else if (!manualTimes.equals(other.manualTimes))
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
		return "ManualResult [id=" + id + ", contid=" + contid + ", times=" + times + ", manualTimes=" + manualTimes
				+ ", createTime=" + createTime + "]";
	}

}
