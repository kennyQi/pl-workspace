package hsl.app.service.local.dzp.region;

import hg.dzpw.dealer.client.api.v1.request.RegionQO;
import hg.dzpw.dealer.client.api.v1.response.RegionResponse;
import hg.dzpw.dealer.client.common.util.DealerApiClient;
import hg.dzpw.dealer.client.dto.meta.RegionDTO;
import hsl.app.dao.dzp.region.DZPAreaDAO;
import hsl.app.dao.dzp.region.DZPCityDAO;
import hsl.app.dao.dzp.region.DZPProvinceDAO;
import hsl.domain.model.dzp.meta.DZPArea;
import hsl.domain.model.dzp.meta.DZPCity;
import hsl.domain.model.dzp.meta.DZPProvince;
import hsl.pojo.qo.dzp.region.DZPAreaQO;
import hsl.pojo.qo.dzp.region.DZPCityQO;
import hsl.pojo.qo.dzp.region.DZPProvinceQO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 电子票务-省份,地区，城市Service
 * Created by hgg on 2016/3/7.
 */
@Service
@Transactional
public class RegionLocalService {

    @Autowired
    private DZPAreaDAO dZPAreaDao;
    @Autowired
    private DZPCityDAO dZPCityDao;
    @Autowired
    private DZPProvinceDAO dZPProviceDao;
    @Autowired
    private DealerApiClient  apiClient;
    /**
     * 同步省市区
     */
    public void syncRegion(){
        //处理地区数据
        pullArea();
    }

    //处理省份数据
    public void pullProvince(){
        RegionQO qo = new RegionQO();
        qo.setType(1);
        RegionResponse regionResponse = apiClient.send(qo, RegionResponse.class);
        List<RegionDTO> regions = regionResponse.getRegions();
        if(CollectionUtils.isEmpty(regions)){
            return;
        }

        for(RegionDTO region : regions){
            saveOrUpdateProvince(region);
        }
    }
    //处理城市数据
    public void pullCity(){
        RegionQO qo = new RegionQO();
        qo.setType(2);
        RegionResponse regionResponse = apiClient.send(qo, RegionResponse.class);
        List<RegionDTO> regions = regionResponse.getRegions();
        if(CollectionUtils.isEmpty(regions)){
            return;
        }

        for(RegionDTO region : regions){
            saveOrUpdateCity(region);
        }
    }

    //处理地区数据
    public Integer pullArea(){
        RegionQO qo = new RegionQO();
        qo.setType(3);
        RegionResponse regionResponse = apiClient.send(qo, RegionResponse.class);
        List<RegionDTO> regions = regionResponse.getRegions();
        if(CollectionUtils.isEmpty(regions)){
            return -1;
        }

        for(RegionDTO region : regions){
            saveOrUpdateArea(region);
        }
        return regions.size();
    }

    /**
     * 保存或者更新省份
     * @param region
     */
    private void saveOrUpdateProvince(RegionDTO region){
        String code = region.getCode();
        DZPProvinceQO qo = new DZPProvinceQO();
        qo.setId(region.getId());
        DZPProvince province = dZPProviceDao.queryUnique(qo);
        if(province == null){
            province = new DZPProvince();
            province.setName(region.getName());
            province.setId(code);
            dZPProviceDao.save(province);
        }else{
            province.setName(region.getName());
            dZPProviceDao.update(province);
        }
    }

    /**
     * 保存或者更新城市
     * @param region
     */
    private void saveOrUpdateCity(RegionDTO region){

        String cityId = region.getCode();
        String proviceCode =  region.getParentCode();

        DZPProvinceQO provinceQo = new DZPProvinceQO();
        provinceQo.setId(proviceCode);
        DZPProvince fromProvince = dZPProviceDao.queryUnique(provinceQo);

        DZPCityQO qo = new DZPCityQO();
        qo.setId(cityId);
        DZPCity city = dZPCityDao.queryUnique(qo);
        if(city == null){
            city = new DZPCity();
            city.setName(region.getName());
            city.setFromProvince(fromProvince);
            city.setId(cityId);
            dZPCityDao.save(city);
        }else{
            city.setName(region.getName());
            city.setFromProvince(fromProvince);
            dZPCityDao.update(city);
        }
    }

    /**
     * 保存或者更新地区
     * @param region
     */
    private void saveOrUpdateArea(RegionDTO region){

        String cityCode = region.getParentCode();
        //查询城市
        DZPCityQO cityQo = new DZPCityQO();
        cityQo.setId(cityCode);
        DZPCity city = dZPCityDao.queryUnique(cityQo);
        //查询是否存在
        String code = region.getCode();
        DZPAreaQO qo = new DZPAreaQO();
        qo.setId(code);
        DZPArea dZPArea = dZPAreaDao.queryUnique(qo);
        if(dZPArea == null){
            dZPArea = new DZPArea();
            dZPArea.setName(region.getName());
            dZPArea.setFromCity(city);
            dZPArea.setId(code);
            dZPAreaDao.save(dZPArea);
        }else{
            dZPArea.setName(region.getName());
            dZPArea.setFromCity(city);
            dZPAreaDao.update(dZPArea);
        }
    }
}
