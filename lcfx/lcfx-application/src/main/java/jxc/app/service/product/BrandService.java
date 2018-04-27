package jxc.app.service.product;

import hg.common.component.BaseServiceImpl;
import hg.pojo.command.CreateBrandCommand;
import hg.pojo.command.DeleteBrandCommand;
import hg.pojo.command.ImportBatchBrandCommand;
import hg.pojo.command.ModifyBrandCommand;
import hg.pojo.dto.product.BrandDTO;
import hg.pojo.exception.ProductException;
import hg.pojo.qo.BrandQO;
import hg.pojo.qo.CategoryQO;
import hg.pojo.qo.ProductQO;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jxc.app.dao.product.BrandDao;
import jxc.app.util.JxcLogger;
import jxc.domain.model.product.Brand;
import jxc.domain.model.product.Product;
import jxc.domain.model.product.ProductCategory;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
public class BrandService extends BaseServiceImpl<Brand, BrandQO, BrandDao> {
	
	@Autowired
	private BrandDao brandDao;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private JxcLogger logger;
	


	@Override
	protected BrandDao getDao() {
		return brandDao;
	}

	/**
	 * 新建商品品牌
	 * @param command
	 * @return
	 * @throws ProductException
	 */
	public void createBrand(CreateBrandCommand command) throws ProductException {
		
		//判断品牌中文名称是否重复
		if(brandNameIsExisted(command.getChineseName(),null)){
			
			//品牌中文名称重复
			throw new ProductException(ProductException.RESLUT_BRAND_NAME_REPEAT, "品牌中文名称已存在");
		}
		
		//品牌中文名称不重复，创建品牌
		Brand brand = new Brand();
		brand.createBrand(command);
		this.save(brand);
		
		logger.debug(this.getClass(), "czh", command.getOperatorName()+"添加商品品牌" + JSON.toJSONString(command), command.getOperatorName(), command.getOperatorType(), command.getOperatorAccount(), "");
	}

	/**
	 * 更新商品品牌
	 * @param command
	 * @return
	 * @throws ProductException
	 */
	public void updateBrand(ModifyBrandCommand command) throws ProductException {
		
		//判断品牌中文名称是否重复
		if(brandNameIsExisted(command.getChineseName(),command.getBrandId())){
			
			//品牌中文名称重复
			throw new ProductException(ProductException.RESLUT_BRAND_NAME_REPEAT, "品牌中文名称已存在");
		}
		
		//品牌中文名称不重复，修改品牌
		Brand brand = get(command.getBrandId());
		brand.modifyBrand(command);
		this.update(brand);
		
		logger.debug(this.getClass(), "czh", command.getOperatorName()+"修改商品品牌" + JSON.toJSONString(command), command.getOperatorName(), command.getOperatorType(), command.getOperatorAccount(), "");
	}
	
	/**
	 * 删除商品品牌
	 * @param command
	 * @throws ProductException 
	 */
	public void removeBrand(DeleteBrandCommand command) throws ProductException {
		
		List<String> ids = command.getBrandIdList();

		for (String id : ids) {
			//获取商品品牌
			Brand brand=this.get(id);
			
			if(checkBrandIsUse(id)){

				//品牌中文名称已使用
				throw new ProductException(ProductException.RESLUT_BRAND_USE, "品牌中文名称"+brand.getChineseName()+"已使用，不能删除");
			}

			
			brand.setStatusRemoved(true);
			update(brand);
				
			logger.debug(this.getClass(), "czh", command.getOperatorName()+"删除商品品牌" + brand.getChineseName(), command.getOperatorName(), command.getOperatorType(), command.getOperatorAccount(), "");
		}
	}


	/**
	 * 导出商品品牌
	 * @param qo
	 * @return
	 */
	public HSSFWorkbook exportBrand(BrandQO qo) {
		List<Brand> brandList = queryList(qo);
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet("商品品牌");
		// 表格的字段
		String[] headers = "中文名称,英文名称,品牌介绍".split(",");
		// 属性的字段
		String[] column = "chineseName,englishName,remark".split(",");

		// 产生表格标题行
		HSSFRow row = sheet.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}
		// 遍历集合数据，产生数据行
		Iterator<Brand> it = brandList.iterator();
		int index = 0;
		while (it.hasNext()) {
			index++;
			row = sheet.createRow(index);
			Brand t = (Brand) it.next();

			for (int i = 0; i < headers.length; i++) {
				HSSFCell cell = row.createCell(i);
				String getMethodName = "get"
						+ column[i].substring(0, 1).toUpperCase()
						+ column[i].substring(1);
				try {
					Class tCls = t.getClass();
					Method getMethod = tCls.getMethod(getMethodName,
							new Class[] {});
					Object value = getMethod.invoke(t, new Object[] {});

					// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
					if (value != null) {
						String textValue = value.toString();
						cell.setCellValue(textValue);
					}
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		return workbook;
	}

	/**
	 * 导入商品品牌
	 * @param command
	 * @return
	 * @throws ProductException 
	 */
	public String importBrand(ImportBatchBrandCommand command) throws ProductException {
		List<BrandDTO> data =  command.getBrandList();
		List<CreateBrandCommand> commands = new ArrayList<CreateBrandCommand>();
		if (data != null && data.size() > 0) {
			JSONArray message = new JSONArray();
			JSONObject object = null;
			Brand brand = null;
			BrandQO qo = null;
			CreateBrandCommand brandcommand = null;
			for (int i = 0; i < data.size(); i++) {
				BrandDTO brandDTO = data.get(i);
				qo = new BrandQO();
				qo.setChineseName(brandDTO.getChineseName());
				brand = queryUnique(qo);
				if (brand == null) {
					brandcommand = new CreateBrandCommand();
					brandcommand.setChineseName(brandDTO.getChineseName());
					brandcommand.setEnglishName(brandDTO.getEnglishName());
					brandcommand.setRemark(brandDTO.getRemark());
					brandcommand.setOperatorAccount(command.getOperatorAccount());
					brandcommand.setOperatorName(command.getOperatorName());
					brandcommand.setOperatorType(command.getOperatorType());
					commands.add(brandcommand);
				} else {
					object = new JSONObject();
					object.put("行号", i + 2);
					object.put("错误", "该品牌已存在");
					message.add(object);
				}
			}
			if(message.size()>0)
			return message.toJSONString();
		}
		importBatchBrand(commands);
		logger.debug(this.getClass(), "czh", "批量导入商品品牌" + commands.size() + "个", command.getOperatorName(), command.getOperatorType(), command.getOperatorAccount(), "");
		return "";
	}
	
	/**
	 * 批量导入商品品牌
	 * @param commands
	 * @throws ProductException 
	 */
	private void importBatchBrand(List<CreateBrandCommand> commands) throws ProductException{
		for(CreateBrandCommand command:commands){
			createBrand(command);
		}
	}
	
	
	/**
	 * 判断商品品牌中文名是否存在
	 * @param brandId
	 * @return
	 */
	public boolean brandNameIsExisted(String name, String modifyId) {
		// 名称唯一
		BrandQO qo = new BrandQO();
		qo.setChineseName(name);
		Brand brand= queryUnique(qo);
		// 添加时
		if (modifyId == null) {
			if (brand != null) {
				return true;
			}
			return false;
		} else {// 修改时
			if (brand != null && !modifyId.equals(brand.getId())) {
				return true;
			}
			return false;
		}

	}
	
	/**
	 * 判断商品品牌是否已使用
	 * @param command
	 * @return 
	 */
	public boolean checkBrandIsUse(String brandId){

		ProductQO qo = new ProductQO();
		qo.setBrandId(brandId);
		List<Product> productList = productService.queryList(qo);
		
		if(productList != null && productList.size() > 0){
			//true表示已使用
			return true;
		}
		return false;
	}
}
