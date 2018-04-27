package lxs.domain.model.app;

import hg.common.component.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * @类功能说明：各种介绍、协议、许可等等信息展示表
 * @类修改者：
 * @修改日期：2015年10月10日下午1:54:06
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：cangs
 * @创建时间：2015年10月10日下午1:54:06
 */
@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_APP + "INTRODUCTION")
public class Introduction extends BaseModel {
	/**
	 * 景区简介
	 */
	public static final Integer JQJJ = 1;
	/**
	 * 旅游协议
	 */
	public static final Integer LYXY = 2;
	/**
	 * 经营许可
	 */
	public static final Integer JYXK = 3;

	/**
	 * 分类
	 */
	@Column(name = "INTRODUCTION_TYPE", columnDefinition = M.NUM_COLUM)
	private Integer introductionType;

	/**
	 * 内容
	 */
	@Column(name = "INTRODUCTION_CONTENT", columnDefinition = "LONGTEXT")
	private String introductionContent;

	public Integer getIntroductionType() {
		return introductionType;
	}

	public void setIntroductionType(Integer introductionType) {
		this.introductionType = introductionType;
	}

	public String getIntroductionContent() {
		return introductionContent;
	}

	public void setIntroductionContent(String introductionContent) {
		this.introductionContent = introductionContent;
	}

}
