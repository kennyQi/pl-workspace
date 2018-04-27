package hg.common.util.image;

/**
 * @类功能说明：枚举——图片格式类型
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014年11月5日 上午11:16:04
 */
public enum ImageType {
	JPG {
		public String getType() {
			return "jpg";
		}
	},

	JPEG {
		public String getType() {
			return "jpeg";
		}
	},

	BMP {
		public String getType() {
			return "bmp";
		}
	},

	GIF {
		public String getType() {
			return "gif";
		}
	},

	PNG {
		public String getType() {
			return "png";
		}
	};

	public abstract String getType();
}