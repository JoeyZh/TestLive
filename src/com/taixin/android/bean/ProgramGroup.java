package com.taixin.android.bean;
/**
 * 节目分组信息
 * @author xixiyue
 *
 */
public class ProgramGroup {
	private String group_name;//组名
	private int count;//分组节目数目
	private int index;//节目索引
	private String ts_id;//节目ts_id
	private String service_id;//节目serVice_id
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getTs_id() {
		return ts_id;
	}
	public void setTs_id(String ts_id) {
		this.ts_id = ts_id;
	}
	public String getService_id() {
		return service_id;
	}
	public void setService_id(String service_id) {
		this.service_id = service_id;
	}
	@Override
	public String toString() {
		return "ProgramGroup [group_name=" + group_name + ", count=" + count
				+ ", index=" + index + ", ts_id=" + ts_id + ", service_id="
				+ service_id + "]";
	}
	
}
