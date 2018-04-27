/**
 * @ResultDto.java Create on 2015年1月29日下午2:03:03
 * Copyright (c) 2012 by www.hg365.com。
 */
package hg.fx.util;

public class ResultDto {
    boolean status;
    String text;
    String text2;
    /**
     * @return the status
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text
     *            the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

	public String getText2() {
		return text2;
	}

	public void setText2(String text2) {
		this.text2 = text2;
	}


    
}