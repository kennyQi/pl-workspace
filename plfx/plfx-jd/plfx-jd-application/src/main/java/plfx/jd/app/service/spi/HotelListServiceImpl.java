package plfx.jd.app.service.spi;

import hg.common.page.Pagination;
import hg.common.util.DateUtil;
import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang.time.DateUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import plfx.jd.app.component.base.BaseJDSpiServiceImpl;
import plfx.jd.app.service.local.HotelListLocalService;
import plfx.jd.app.service.local.HotelLocalService;
import plfx.jd.domain.model.hotel.YLAvailPolicy;
import plfx.jd.domain.model.hotel.YLFacilitiesV2;
import plfx.jd.domain.model.hotel.YLHelpfulTips;
import plfx.jd.domain.model.hotel.YLHotel;
import plfx.jd.domain.model.hotel.YLHotelDetail;
import plfx.jd.domain.model.hotel.YLHotelImage;
import plfx.jd.domain.model.hotel.YLHotelList;
import plfx.jd.domain.model.hotel.YLHotelRoom;
import plfx.jd.domain.model.hotel.YLLocation;
import plfx.jd.domain.model.hotel.YLReview;
import plfx.jd.domain.model.hotel.YLServiceRank;
import plfx.jd.domain.model.hotel.YLSupplier;
import plfx.jd.pojo.dto.plfx.hotel.YLHotelDTO;
import plfx.jd.pojo.qo.YLHotelListQO;
import plfx.jd.pojo.qo.YLHotelQO;
import plfx.jd.spi.inter.HotelDetailLocalService;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：线路订单service实现
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年01月27日下午2:55:10
 * @版本：V1.0
 *
 */
@Service("hotelDetailLocalService")
public class HotelListServiceImpl extends BaseJDSpiServiceImpl<YLHotelDTO, YLHotelQO, HotelListLocalService> implements HotelDetailLocalService {

	private static ExecutorService executor = Executors.newFixedThreadPool(2);
	
	@Autowired
	private HotelListLocalService hotelListLocalService;
	
	@Autowired
	private HotelLocalService hotelLocalService;
	

	@Override
	public boolean saveOrUpdateHotelList() {
		this.saveOrUpdateHotelListByThread();
		return true;
	}

	@Override
	public boolean saveOrUpdateHotelDetail(boolean flag) {
		this.saveOrUpdateHotelByThread(flag);
		return true;
	}
	
	@Override
	protected Class<YLHotelDTO> getDTOClass() {
		
		return YLHotelDTO.class;
	}

	@Override
	protected HotelListLocalService getService() {
		return hotelListLocalService;
	}

	@Override
	public YLHotelDTO searchByHotelId(YLHotelQO qo) {
		
		return hotelListLocalService.searchByHotelId(qo);
	}
	
	/****
	 * 
	 * @方法功能说明：更新酒店列表
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年7月21日下午3:58:06
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	public void saveOrUpdateHotelListByThread(){
		HgLogger.getInstance().info("yaosanfeng", "HotelListServiceImpl->saveOrUpdateHotelListByThread->酒店列表更新或保存操作开始");
		long startTime = System.currentTimeMillis();
		try {
			String hotelListUrl = "http://api.elong.com/xml/v2.0/hotel/hotellist.xml";
			SAXReader reader = new SAXReader();
			URL url = new URL(hotelListUrl);
			Document document = reader.read(url);
			Element root = document.getRootElement();
			Element element = root.element("Hotels");
			List<Element> hList = element.elements();
	    	for (Element e : hList) {
					List<Attribute> attributes = e.attributes();
					YLHotelList newYLHotel=new YLHotelList();
					for (Attribute attr : attributes) {	
						if (attr.getName().equals("HotelId")) {
							newYLHotel.setHotelId(attr.getValue());
						} else if (attr.getName().equals("UpdatedTime")) {
							newYLHotel.setUpdatedTime(DateUtil.parseDateTime(attr.getValue()));
						} else if (attr.getName().equals("Modification")) {
							newYLHotel.setModification(attr.getValue());
						} else if (attr.getName().equals("Products")) {
							newYLHotel.setProducts(attr.getValue());
						} else if (attr.getName().equals("Status")) {
							newYLHotel.setStatus(Integer.parseInt(attr.getValue()));
						}	
					}
					YLHotelListQO ylHotelListQO = new YLHotelListQO();
					ylHotelListQO.setHotelId(newYLHotel.getHotelId());
					YLHotelList ylHotel = hotelListLocalService.queryUnique(ylHotelListQO);
					if(null == ylHotel){
						newYLHotel.setId(UUIDGenerator.getUUID());
					//	System.out.println("插入新数据");
						hotelListLocalService.save(newYLHotel);
					}else{
						if(newYLHotel.getUpdatedTime().after(ylHotel.getUpdatedTime())){
							ylHotel.setHotelId(newYLHotel.getHotelId());
							ylHotel.setModification(newYLHotel.getModification());
							ylHotel.setProducts(newYLHotel.getProducts());
							ylHotel.setStatus(newYLHotel.getStatus());
							ylHotel.setUpdatedTime(newYLHotel.getUpdatedTime());
							//System.out.println("更新老数据");
							hotelListLocalService.update(ylHotel);
						}
					}
					
				}	
    	 } catch (Exception e) {
			e.printStackTrace();
		 }			
    	long endTime = System.currentTimeMillis();
    	HgLogger.getInstance().info("yaosanfeng","updateHotelListByThread->酒店列表更新执行完毕:"+(endTime-startTime)/1000/60);
	}
	
	/****
	 * 
	 * @方法功能说明：批量更新或save酒店详情
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年7月21日下午3:58:41
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	public void saveOrUpdateHotelByThread(boolean flag){
		HgLogger.getInstance().info("yaosanfeng", "HotelListServiceImpl->saveOrUpdateHotelByThread->酒店详情更新或save操作开始");
		long startTime = System.currentTimeMillis();
		Future<Integer> future = null;
		int pageSize = 50;
		int pageNo = 1;
		YLHotelListQO qo = new YLHotelListQO();
		qo.setStatus(0);//状态可用
		if(flag == false){
			qo.setUpdatedTime(DateUtils.addDays(new Date(), -1));//艺龙更新时间>当前时间减去1天就更新
		}
    	Pagination pagination = new Pagination();
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
    	pagination.setCondition(qo);
    	pagination = hotelListLocalService.queryPagination(pagination);
		HgLogger.getInstance().info("yaosanfeng", "HotelListServiceImpl->saveOrUpdateHotelByThread->酒店详情更新操作开始"+JSON.toJSONString(pagination));
    	for(int j=1;j<pagination.getTotalPage();j++){
        	future = executor.submit(new BatchSaveHotel(pageSize, j ,flag));
        	try {
        		future.get();
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		} catch (ExecutionException e) {
    			e.printStackTrace();
    		}
    	}
    	long endTime = System.currentTimeMillis();
    	HgLogger.getInstance().info("yaosanfeng","saveOrUpdateHotelByThread->酒店详情更新执行完毕:"+(endTime-startTime)/1000/60);
	}
	
	/****
	 * 
	 * @类功能说明：批量添加酒店详情内部线程类
	 * @类修改者：
	 * @修改日期：
	 * @修改说明：
	 * @公司名称：浙江汇购科技有限公司
	 * @部门：技术部
	 * @作者：yaosanfeng
	 * @创建时间：2015年7月21日下午3:45:52
	 * @版本：V1.0
	 *
	 */
	private class BatchSaveHotel implements  Callable<Integer>{
		private int pageSize;
		private int pageNo;
		private boolean flag;
		public BatchSaveHotel(int pageSize,int pageNo,boolean flag){
			this.pageSize = pageSize;
			this.pageNo = pageNo;
			this.flag = flag;
		}
		@Override
		public Integer call() throws Exception {
			HgLogger.getInstance().info("yaosanfeng", "BatchSaveHotel->call->酒店详情更新操作开始");
			YLHotelListQO qo = new YLHotelListQO();
			qo.setStatus(0);//可用
			if(flag == false){
			   qo.setUpdatedTime(DateUtils.addDays(new Date(), -1));//艺龙更新的时间>当前时间减去1天,就做更新或保存
			}
			Pagination pagination = new Pagination();
			pagination.setPageNo(pageNo);
			pagination.setPageSize(pageSize);
	    	pagination.setCondition(qo);
	    	pagination = hotelListLocalService.queryPagination(pagination);
	    	List<YLHotelList> hotelList = (List<YLHotelList>) pagination.getList();
	    	try{
    			for(YLHotelList hotel:hotelList){
    					SAXReader reader = new SAXReader();
	    				String hotelDetailUrl = "http://api.elong.com/xml/v2.0/hotel/cn/{hotelIdBYAfterTwo}/{hotelId}.xml";
	    				String hotelId = hotel.getHotelId();
	    				String newHotelId = hotelId.substring(hotelId.length()-2, hotelId.length());
	    				hotelDetailUrl = hotelDetailUrl.replaceAll("\\{hotelIdBYAfterTwo\\}", newHotelId).replaceAll("\\{hotelId\\}", hotelId);
	    				URL url = new URL(hotelDetailUrl);
	    				Document document = reader.read(url);
	    				Element root = document.getRootElement();
	    				List<Element> childrenList = root.elements();
	    				//根据酒店id查询酒店详情
	    				YLHotelQO ylHotelQO = new YLHotelQO();
	    				ylHotelQO.setHotelId(root.attributeValue("Id"));
	    				YLHotel ylHotel = hotelLocalService.queryUnique(ylHotelQO);
	    				if(flag == false){
	    					if(ylHotel !=null){
		    					//因为设置级联,所以删除主表,子表也一起删除
		    					//目前更新操作无法判断,只好采取删除一条记录在保存的效果
		    					hotelLocalService.deleteById(ylHotel.getId());
		    					//System.out.println("删除已有记录");
		    				}else{
		    					ylHotel = new YLHotel();
		    					ylHotel.setHotelId(root.attributeValue("Id"));
		    					ylHotel.setId(UUIDGenerator.getUUID());
		    					this.saveOrUpdateYLHotel(ylHotel,childrenList);
		    				}
	    				}else{
	    					
	    					if(ylHotel == null){
	    						ylHotel = new YLHotel();
		    					ylHotel.setHotelId(root.attributeValue("Id"));
		    					ylHotel.setId(UUIDGenerator.getUUID());
		    					this.saveOrUpdateYLHotel(ylHotel,childrenList);
	    					}
	    				}
	    				
	    				
	    			
    			}
        	}catch(Exception e){
    			e.printStackTrace();
    		}
			return hotelList.size();
		}
		
		public  void saveOrUpdateYLHotel(YLHotel ylHotel, List<Element> childrenList) {
			
			YLHotelDetail ylHotelDetail = new YLHotelDetail();
			for(Element e:childrenList){
	            if(e.getName().equals("Detail")){
				for (Iterator ii = e.elementIterator(); ii.hasNext();) {
					Element detailElement = (Element) ii.next(); 
					if(detailElement.getName().equals("Address")){
						ylHotelDetail.setAddress(detailElement.getText());
					}else if(detailElement.getName().equals("BaiduLat")){
						ylHotelDetail.setBaiduLat(detailElement.getText());
					}else if(detailElement.getName().equals("BaiduLon")){
						ylHotelDetail.setBaiduLon(detailElement.getText());
					}else if(detailElement.getName().equals("BrandId")){
						ylHotelDetail.setBrandId(detailElement.getText());
					}else if(detailElement.getName().equals("Category")){
						ylHotelDetail.setCategory(detailElement.getText());
					}else if(detailElement.getName().equals("ConferenceAmenities")){
						ylHotelDetail.setConferenceAmenities(detailElement.getText());
					}else if(detailElement.getName().equals("CreditCards")){
						ylHotelDetail.setCreditCards(detailElement.getText());
					}else if(detailElement.getName().equals("Description")){
						ylHotelDetail.setDescription(detailElement.getText());
					}else if(detailElement.getName().equals("DiningAmenities")){
						ylHotelDetail.setDiningAmenities(detailElement.getText());
					}else if(detailElement.getName().equals("District")){
						ylHotelDetail.setDistrict(detailElement.getText());
					}else if(detailElement.getName().equals("EstablishmentDate")){
						ylHotelDetail.setEstablishmentDate(detailElement.getText());
					}else if(detailElement.getName().equals("Facilities")){
						ylHotelDetail.setFacilities(detailElement.getText());
					}else if(detailElement.getName().equals("Features")){
						ylHotelDetail.setFeatures(detailElement.getText());
					}else if(detailElement.getName().equals("Fax")){
						ylHotelDetail.setFax(detailElement.getText());
					}else if(detailElement.getName().equals("GeneralAmenities")){
						ylHotelDetail.setGeneralAmenities(detailElement.getText());
					}else if(detailElement.getName().equals("GoogleLat")){
						ylHotelDetail.setGoogleLat(detailElement.getText());
					}else if(detailElement.getName().equals("GoogleLon")){
						ylHotelDetail.setGoogleLon(detailElement.getText());
					}else if(detailElement.getName().equals("GroupId")){
						ylHotelDetail.setGroupId(detailElement.getText());
					}else if(detailElement.getName().equals("HasCoupon")){
						ylHotelDetail.setHasCoupon(detailElement.getText());
					}else if(detailElement.getName().equals("IntroEditor")){
						ylHotelDetail.setIntroEditor(detailElement.getText());
					}else if(detailElement.getName().equals("IsApartment")){
						ylHotelDetail.setIsApartment(detailElement.getText());
					}else if(detailElement.getName().equals("IsEconomic")){
						ylHotelDetail.setIsEconomic(detailElement.getText());
					}else if(detailElement.getName().equals("Name")){
						ylHotelDetail.setName(detailElement.getText());
					}else if(detailElement.getName().equals("Phone")){
						ylHotelDetail.setPhone(detailElement.getText());
					}else if(detailElement.getName().equals("RecreationAmenities")){
						ylHotelDetail.setRecreationAmenities(detailElement.getText());
					}else if(detailElement.getName().equals("RenovationDate")){
						ylHotelDetail.setRenovationDate(detailElement.getText());
					}else if(detailElement.getName().equals("RoomAmenities")){
						ylHotelDetail.setRoomAmenities(detailElement.getText());	
					}else if(detailElement.getName().equals("StarRate")){
						ylHotelDetail.setStarRate(detailElement.getText());
				    }else if(detailElement.getName().equals("Traffic")){
				    	ylHotelDetail.setTraffic(detailElement.getText());
				    }else if(detailElement.getName().equals("CityId")){
				    	ylHotelDetail.setCityId(detailElement.getText());
				    }else if(detailElement.getName().equals("BusinessZone")){
				    	ylHotelDetail.setBusinessZone(detailElement.getText());
				    }
	                if(detailElement.getName().equals("Suppliers")){
	                	List<Element> supplierElementList = detailElement.elements();
	                	List<YLSupplier> supplierList=new ArrayList<YLSupplier>();
	                	for(Element supplierElement:supplierElementList){
	                		List<Attribute> attributes = supplierElement.attributes();
	                		YLSupplier ylSupplier = new YLSupplier();
	                		ylSupplier.setId(UUIDGenerator.getUUID());
	                		ylSupplier.setYlHotel(ylHotel);
							for (Attribute attr : attributes) {
								ylSupplier.setHotelCode(attr.getValue());
								ylSupplier.setStatus(attr.getValue());
								ylSupplier.setSupplierId(attr.getValue());
								ylSupplier.setWeekendEnd(attr.getValue());
								ylSupplier.setWeekendStart(attr.getValue());
							}
							List<Element> elementList = supplierElement.elements();
							for(Element element:elementList){
								if(element.getName().equals("HelpfulTips")){
									List<Attribute> attri = element.attributes();
									YLHelpfulTips ylHelpfulTips=new YLHelpfulTips();
									for (Attribute attr : attri) {
										ylHelpfulTips.setEndDate(DateUtil.parseDate3(attr.getValue()));
										ylHelpfulTips.setStartDate(DateUtil.parseDate3(attr.getValue()));
										ylHelpfulTips.setHelpfulTipsDescrption(element.getText());
									}
									ylSupplier.setHelpfulTips(ylHelpfulTips);
								}
	                            if(element.getName().equals("AvailPolicy")){
	                            	List<Attribute> attri = element.attributes();
	                            	YLAvailPolicy  ylAvailPolicy=new YLAvailPolicy();
									for (Attribute attr : attri) {
										ylAvailPolicy.setEndDate(DateUtil.parseDate3(attr.getValue()));
										ylAvailPolicy.setStartDate(DateUtil.parseDate3(attr.getValue()));
										ylAvailPolicy.setAvailPolicyDescrption(element.getText());
									}
									ylSupplier.setAvailPolicy(ylAvailPolicy);
								}
							}
							supplierList.add(ylSupplier);
	                	}
	                	ylHotel.setSupplierList(supplierList);
	                
					}
	                else if(detailElement.getName().equals("FacilitiesV2")){
	                	YLFacilitiesV2 ylFacilitiesV2 = new YLFacilitiesV2();
	                	for (Iterator facilitiesV2 = e.elementIterator(); facilitiesV2.hasNext();) { 
							Element facilitiesV2Element = (Element) facilitiesV2.next();
							if(facilitiesV2Element.getName().equals("GeneralAmenities")){
								ylFacilitiesV2.setGeneralAmenities(facilitiesV2Element.getText());
							}else if(facilitiesV2Element.getName().equals("RecreationAmenities")){
								ylFacilitiesV2.setRecreationAmenities(facilitiesV2Element.getText());
							}else if(facilitiesV2Element.getName().equals("ServiceAmenities")){
								ylFacilitiesV2.setServiceAmenities(facilitiesV2Element.getText());
							}	
	                	}
	                	ylHotel.setFacilitiesV2(ylFacilitiesV2);
					}else if(detailElement.getName().equals("ServiceRank")){
						List<Attribute> attri = detailElement.attributes();
	                	YLServiceRank ylServiceRank = new YLServiceRank();
						for (Attribute attr : attri) {
							if(attr.getName().equals("BookingSuccessRate")){
								ylServiceRank.setBookingSuccessRate(attr.getValue());
							}else if(attr.getName().equals("BookingSuccessScore")){
								ylServiceRank.setBookingSuccessScore(attr.getValue());
							}else if(attr.getName().equals("ComplaintRate")){
								ylServiceRank.setComplaintRate(attr.getValue());
							}else if(attr.getName().equals("ComplaintScore")){
								ylServiceRank.setComplaintScore(attr.getValue());
							}else if(attr.getName().equals("InstantConfirmRate")){
								ylServiceRank.setInstantConfirmRate(attr.getValue());
							}else if(attr.getName().equals("InstantConfirmScore")){
								ylServiceRank.setInstantConfirmScore(attr.getValue());
							}else if(attr.getName().equals("SummaryRate")){
								ylServiceRank.setSummaryRate(attr.getValue());
							}else if(attr.getName().equals("SummaryScore")){
								ylServiceRank.setSummaryScore(attr.getValue());
							}	
						}
						ylHotel.setServiceRank(ylServiceRank);
					}  
				}
				ylHotel.setYlHotelDetail(ylHotelDetail); 
			}else if(e.getName().equals("Rooms")){
				List<YLHotelRoom> roomList = new ArrayList<YLHotelRoom>();
				List<Element> roomsElementList = e.elements();	
				for(Element room:roomsElementList){
					if(room.getName().equals("Room")){
						List<Attribute> attri = room.attributes();
						YLHotelRoom ylHotelRoom=new YLHotelRoom();
						ylHotelRoom.setId(UUIDGenerator.getUUID());
						ylHotelRoom.setYlHotel(ylHotel);
						for (Attribute attr : attri) {
							if(attr.getName().equals("Area")){
								ylHotelRoom.setArea(attr.getValue());
							}else if(attr.getName().equals("BedType")){
								ylHotelRoom.setBedType(attr.getValue());
							}else if(attr.getName().equals("BroadnetAccess")){
								ylHotelRoom.setBroadnetAccess(attr.getValue());
							}else if(attr.getName().equals("BroadnetFee")){
								ylHotelRoom.setBroadnetFee(attr.getValue());
							}else if(attr.getName().equals("Capacity")){
								ylHotelRoom.setCapacity(attr.getValue());
							}else if(attr.getName().equals("Description")){
								ylHotelRoom.setDescription(attr.getValue());
							}else if(attr.getName().equals("Facilities")){
								ylHotelRoom.setFacilities(attr.getValue());
							}else if(attr.getName().equals("Floor")){
								ylHotelRoom.setFloor(attr.getValue());
							}else if(attr.getName().equals("Name")){
								ylHotelRoom.setName(attr.getValue());
							}else if(attr.getName().equals("Id")){
								ylHotelRoom.setRoomId(attr.getValue());
							}
						}
						roomList.add(ylHotelRoom);	
					}	
				}
				ylHotel.setRoomList(roomList);
			}else if(e.getName().equals("Images")){
				List<Element> imageElementList = e.elements();
				List<YLHotelImage> newImageList = new ArrayList<YLHotelImage>();
				for(Element imageElement:imageElementList){
					YLHotelImage ylHotelImages = new YLHotelImage();
					ylHotelImages.setId(UUIDGenerator.getUUID());
					ylHotelImages.setYlHotel(ylHotel);
	        		List<Attribute> attributes = imageElement.attributes();
					for (Attribute attr : attributes) {
						if(attr.getName().equals("AuthorType")){
							ylHotelImages.setAuthorType(attr.getValue());
						}else if(attr.getName().equals("Type")){
							ylHotelImages.setType(attr.getValue());
						}else if(attr.getName().equals("IsCoverImage")){
							ylHotelImages.setIsCoverImage(attr.getValue());
						}
					}
					List<Element> imageList = imageElement.elements();
					for(Element element :imageList){	
						if(element.getName().equals("RoomId")){
							ylHotelImages.setRoomId(element.getText());
						}else if(element.getName().equals("Locations")){
							List<YLLocation> locationList = new ArrayList<YLLocation>();
							List<Element> locationsElementList = element.elements();
							for(Element locationElement:locationsElementList){
								YLLocation ylLocation = new YLLocation();
								ylLocation.setId(UUIDGenerator.getUUID());
								ylLocation.setYlHotelImage(ylHotelImages);
								if(locationElement.getName().equals("Location")){
									ylLocation.setUrl(locationElement.getText());
								}
								List<Attribute> attribute = locationElement.attributes();
								for (Attribute attr : attribute) {
									if(attr.getName().equals("Size")){
										ylLocation.setSize(attr.getValue());
									}else if(attr.getName().equals("WaterMark")){
										ylLocation.setWaterMark(Integer.parseInt(attr.getValue()));
									}
								}
								locationList.add(ylLocation);
							}
							ylHotelImages.setLocationsList(locationList);
						}
					}
					newImageList.add(ylHotelImages);
	        	}
				ylHotel.setImageList(newImageList);
			}else if(e.getName().equals("Review")){
				YLReview ylReview = new YLReview();
				List<Attribute> attribute = e.attributes();
				for (Attribute attr : attribute) {
					if(attr.getName().equals("Count")){
						ylReview.setCount(attr.getValue());
					}else if(attr.getName().equals("Good")){
						ylReview.setGood(attr.getValue());
					}else if(attr.getName().equals("Poor")){
						ylReview.setPoor(attr.getValue());
					}else if(attr.getName().equals("Score")){
						ylReview.setScore(attr.getValue());
					}
				}
				ylHotel.setYlReview(ylReview);
			}
		 }
				//System.out.println("酒店save");
				hotelLocalService.save(ylHotel);	
		}
			
	}

	
}
