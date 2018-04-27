package zzpl.api.client.response.jp;

import com.alibaba.fastjson.JSON;

import zzpl.api.client.base.ApiResponse;

public class GJBookResponse extends ApiResponse {

	public static void main(String[] args) {
		System.out.println(JSON.toJSON(new GJBookResponse()));
	}
}
