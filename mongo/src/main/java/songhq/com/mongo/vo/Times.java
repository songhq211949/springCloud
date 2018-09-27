package songhq.com.mongo.vo;



import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * 播放次数
 * 
 * @author chengcong
 *
 */
@Document(collection = "times")
public class Times implements Serializable {

	private static final long serialVersionUID = 8896901077020907184L;

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
	 * 处理后的播放次数
	 */
	private Long modifiedTimes;

	/**
	 * 更新时间
	 */
	private Date updateTime;

	/**
	 * 半自动修饰播放次数
	 */
	private Long semiAutomaticTimes;

	/**
	 * 人工手机修饰的播放次数
	 */
	private Long manualTimes;

	/**
	 * 是否强制返回semiAutomaticTimes开关
	 */
	private String flag;

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

	public Long getModifiedTimes() {
		return modifiedTimes;
	}

	public void setModifiedTimes(Long modifiedTimes) {
		this.modifiedTimes = modifiedTimes;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getSemiAutomaticTimes() {
		return semiAutomaticTimes;
	}

	public void setSemiAutomaticTimes(Long semiAutomaticTimes) {
		this.semiAutomaticTimes = semiAutomaticTimes;
	}

	public Long getManualTimes() {
		return manualTimes;
	}

	public void setManualTimes(Long manualTimes) {
		this.manualTimes = manualTimes;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contid == null) ? 0 : contid.hashCode());
		result = prime * result + ((flag == null) ? 0 : flag.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((manualTimes == null) ? 0 : manualTimes.hashCode());
		result = prime * result + ((modifiedTimes == null) ? 0 : modifiedTimes.hashCode());
		result = prime * result + ((semiAutomaticTimes == null) ? 0 : semiAutomaticTimes.hashCode());
		result = prime * result + ((times == null) ? 0 : times.hashCode());
		result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
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
		Times other = (Times) obj;
		if (contid == null) {
			if (other.contid != null)
				return false;
		} else if (!contid.equals(other.contid))
			return false;
		if (flag == null) {
			if (other.flag != null)
				return false;
		} else if (!flag.equals(other.flag))
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
		if (modifiedTimes == null) {
			if (other.modifiedTimes != null)
				return false;
		} else if (!modifiedTimes.equals(other.modifiedTimes))
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
		if (updateTime == null) {
			if (other.updateTime != null)
				return false;
		} else if (!updateTime.equals(other.updateTime))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Times [id=" + id + ", contid=" + contid + ", times=" + times + ", modifiedTimes=" + modifiedTimes
				+ ", updateTime=" + updateTime + ", semiAutomaticTimes=" + semiAutomaticTimes + ", manualTimes="
				+ manualTimes + ", flag=" + flag + "]";
	}

}
