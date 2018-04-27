package hg.dzpw.dealer.client.common;

import hg.dzpw.dealer.client.api.v1.request.CloseTicketOrderCommand;
import hg.dzpw.dealer.client.api.v1.request.CreateTicketOrderCommand;
import hg.dzpw.dealer.client.api.v1.request.PayToTicketOrderCommand;
import hg.dzpw.dealer.client.api.v1.response.CreateTicketOrderResponse;
import hg.dzpw.dealer.client.api.v1.response.PayToTicketOrderResponse;
import hg.dzpw.dealer.client.common.util.DealerApiClient;
import hg.dzpw.dealer.client.dto.tourist.TouristDTO;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.apache.commons.io.IOUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@SuppressWarnings("serial")
public class TestGUI extends JFrame {

	private JPanel contentPane;
	private JTextField dealerKey;
	private JTextField secretKey;
	private JTextField url;
	private JTextField ticketPolicyId;
	private JTextField travelDate;
	private JTextField ticketPolicyVersion;
	private JTextField price;
	private JTextField name;
	private JTextField address;
	private JTextField idNo;
	private JTextField dealerOrderId;
	private JTextField dealerOrderId2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestGUI frame = new TestGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TestGUI() {
		setResizable(false);
		setTitle("经销商接口测试工具");
		final File f = new File("c:/dealer-tool-config.json");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("dealerKey", dealerKey.getText() == null ? ""
						: dealerKey.getText());
				map.put("secretKey", secretKey.getText() == null ? ""
						: secretKey.getText());
				map.put("url", url.getText() == null ? "" : url.getText());
				try {
					IOUtils.write(JSON.toJSONString(map, true), new FileOutputStream(f));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 674, 541);
		contentPane = new JPanel();
		contentPane.setToolTipText("");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(5, 112, 658, 244);
		contentPane.add(tabbedPane);
		
		final JPanel createOrderPanel = new JPanel();
		tabbedPane.addTab("创建订单", null, createOrderPanel, null);
		createOrderPanel.setLayout(null);
		
		JLabel label_2 = new JLabel("总价:");
		label_2.setFont(new Font("Dialog", Font.PLAIN, 12));
		label_2.setBounds(454, 40, 71, 18);
		createOrderPanel.add(label_2);
		
		JLabel lblid = new JLabel("门票政策ID:");
		lblid.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblid.setBounds(12, 12, 71, 18);
		createOrderPanel.add(lblid);
		
		JLabel label_3 = new JLabel("门票政策版本:");
		label_3.setFont(new Font("Dialog", Font.PLAIN, 12));
		label_3.setBounds(405, 10, 98, 18);
		createOrderPanel.add(label_3);
		
		JLabel label_4 = new JLabel("游玩日期:");
		label_4.setFont(new Font("Dialog", Font.PLAIN, 12));
		label_4.setBounds(22, 42, 62, 18);
		createOrderPanel.add(label_4);
		
		ticketPolicyId = new JTextField();
		ticketPolicyId.setText("65cc5301b2a54639998f16291d81b29b");
		ticketPolicyId.setColumns(10);
		ticketPolicyId.setBounds(80, 10, 157, 22);
		createOrderPanel.add(ticketPolicyId);
		
		travelDate = new JTextField();
		travelDate.setText("20150620");
		travelDate.setColumns(10);
		travelDate.setBounds(80, 40, 157, 22);
		createOrderPanel.add(travelDate);
		
		ticketPolicyVersion = new JTextField();
		ticketPolicyVersion.setText("3");
		ticketPolicyVersion.setColumns(10);
		ticketPolicyVersion.setBounds(484, 8, 157, 22);
		createOrderPanel.add(ticketPolicyVersion);
		
		price = new JTextField();
		price.setText("1");
		price.setColumns(10);
		price.setBounds(484, 38, 157, 22);
		createOrderPanel.add(price);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "\u6E38\u5BA2\u4FE1\u606F", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(12, 72, 629, 130);
		createOrderPanel.add(panel_3);
		panel_3.setLayout(null);
		
		JLabel label_5 = new JLabel("姓名:");
		label_5.setBounds(45, 30, 40, 18);
		label_5.setFont(new Font("Dialog", Font.PLAIN, 12));
		panel_3.add(label_5);
		
		JLabel label_6 = new JLabel("地址:");
		label_6.setFont(new Font("Dialog", Font.PLAIN, 12));
		label_6.setBounds(422, 30, 63, 18);
		panel_3.add(label_6);
		
		JLabel label_7 = new JLabel("证件类型:");
		label_7.setFont(new Font("Dialog", Font.PLAIN, 12));
		label_7.setBounds(22, 60, 63, 18);
		panel_3.add(label_7);
		
		JLabel label_8 = new JLabel("证件号码:");
		label_8.setFont(new Font("Dialog", Font.PLAIN, 12));
		label_8.setBounds(400, 60, 63, 18);
		panel_3.add(label_8);
		
		final JComboBox idType = new JComboBox();
		idType.setModel(new DefaultComboBoxModel(new String[] {"身份证"}));
		idType.setFont(new Font("Dialog", Font.PLAIN, 12));
		idType.setToolTipText("");
		idType.setBounds(82, 58, 157, 22);
		panel_3.add(idType);
		
		name = new JTextField();
		name.setText("李四");
		name.setColumns(10);
		name.setBounds(82, 28, 157, 22);
		panel_3.add(name);
		
		address = new JTextField();
		address.setText("地球上");
		address.setColumns(10);
		address.setBounds(460, 28, 157, 22);
		panel_3.add(address);
		
		idNo = new JTextField();
		idNo.setText("123456");
		idNo.setColumns(10);
		idNo.setBounds(460, 58, 157, 22);
		panel_3.add(idNo);
		
		final JPanel payToPanel = new JPanel();
		tabbedPane.addTab("支付订单", null, payToPanel, null);
		payToPanel.setLayout(null);
		
		JLabel lblid_1 = new JLabel("经销商订单ID:");
		lblid_1.setBounds(12, 12, 97, 18);
		lblid_1.setFont(new Font("Dialog", Font.PLAIN, 12));
		payToPanel.add(lblid_1);
		
		dealerOrderId = new JTextField();
		dealerOrderId.setColumns(10);
		dealerOrderId.setBounds(96, 10, 286, 22);
		payToPanel.add(dealerOrderId);
		
		final JPanel closeToPanel = new JPanel();
		tabbedPane.addTab("关闭订单", null, closeToPanel, null);
		closeToPanel.setLayout(null);
		
		JLabel label_9 = new JLabel("经销商订单ID:");
		label_9.setFont(new Font("Dialog", Font.PLAIN, 12));
		label_9.setBounds(12, 12, 97, 18);
		closeToPanel.add(label_9);
		
		dealerOrderId2 = new JTextField();
		dealerOrderId2.setColumns(10);
		dealerOrderId2.setBounds(96, 10, 286, 22);
		closeToPanel.add(dealerOrderId2);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(5, 5, 658, 95);
		contentPane.add(panel_1);
		panel_1.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "\u7ECF\u9500\u5546\u4FE1\u606F", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		panel_1.setToolTipText("");
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("经销商KEY:");
		lblNewLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblNewLabel.setBounds(12, 33, 71, 18);
		panel_1.add(lblNewLabel);
		
		dealerKey = new JTextField();
		dealerKey.setText("ASCXDF");
		dealerKey.setBounds(81, 31, 157, 22);
		panel_1.add(dealerKey);
		dealerKey.setColumns(10);
		
		JLabel label = new JLabel("经销商密钥:");
		label.setFont(new Font("Dialog", Font.PLAIN, 12));
		label.setBounds(256, 33, 71, 18);
		panel_1.add(label);
		
		secretKey = new JTextField();
		secretKey.setText("ASCXDF");
		secretKey.setBounds(326, 31, 320, 22);
		panel_1.add(secretKey);
		secretKey.setColumns(10);
		
		JLabel label_1 = new JLabel("接口地址:");
		label_1.setFont(new Font("Dialog", Font.PLAIN, 12));
		label_1.setBounds(22, 63, 71, 18);
		panel_1.add(label_1);
		
		url = new JTextField();
		url.setText("http://127.0.0.1:8081/dzpw-dealer-api/api");
		url.setColumns(10);
		url.setBounds(81, 63, 565, 22);
		panel_1.add(url);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(5, 368, 658, 133);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		final JButton submit = new JButton("提交");
		submit.setBounds(560, 101, 98, 28);
		panel_2.add(submit);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 658, 95);
		panel_2.add(scrollPane);
		
		final JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("请求中...:\r\n");
				submit.setEnabled(false);
				try {
					DealerApiClient client = new DealerApiClient(url.getText(),
							dealerKey.getText(), secretKey.getText());
					Object response = "未调用接口";
					// 下单
					if (createOrderPanel.isShowing()) {
						CreateTicketOrderCommand command = new CreateTicketOrderCommand();
						command.setDealerOrderId(UUID.randomUUID().toString());
						command.setPrice(Double.valueOf(price.getText()));
						command.setTicketPolicyId(ticketPolicyId.getText());
						command.setTicketPolicyVersion(Integer.valueOf(ticketPolicyVersion.getText()));
						List<TouristDTO> list = new ArrayList<TouristDTO>();
						TouristDTO tourist = new TouristDTO();
						tourist.setName(name.getText());
						tourist.setAddress(address.getText());
						tourist.setIdType(1);
						tourist.setIdNo(idNo.getText());
						list.add(tourist);
						command.setTravelDate(new SimpleDateFormat("yyyyMMdd").parse(travelDate.getText()));
						command.setTourists(list);

						try {
							CreateTicketOrderResponse r = client.send(command,
									CreateTicketOrderResponse.class);
							response = r;
							dealerOrderId.setText(r.getTicketOrder().getBaseInfo().getDealerOrderId());	
							dealerOrderId2.setText(r.getTicketOrder().getBaseInfo().getDealerOrderId());	
						} catch (Exception e2) { }
					} 
					// 支付
					else if(payToPanel.isShowing()){
						PayToTicketOrderCommand command = new PayToTicketOrderCommand();
						command.setDealerOrderId(dealerOrderId.getText());
						response = client.send(command, PayToTicketOrderResponse.class);
					}
					// 关闭
					else if(closeToPanel.isShowing()){
						CloseTicketOrderCommand command = new CloseTicketOrderCommand();
						command.setDealerOrderId(dealerOrderId.getText());
						response = client.send(command, PayToTicketOrderResponse.class);
					}
					textArea.append("返回结果:\r\n");
					textArea.append(JSON.toJSONString(response, true));
					textArea.append("\r\n");
				} catch (Exception e2) {
					StringWriter sw = new StringWriter();
					e2.printStackTrace(new PrintWriter(sw));
					textArea.append(sw.toString());
					textArea.append("\r\n");
				}
				submit.setEnabled(true);
			}
		});
		
		// 读取配置
		try {
			String json = IOUtils.toString(new FileInputStream(f));
			JSONObject jo = (JSONObject) JSONObject.parse(json);
			if (jo.getString("dealerKey") != null)
				dealerKey.setText(jo.getString("dealerKey"));
			if (jo.getString("secretKey") != null)
				secretKey.setText(jo.getString("secretKey"));
			if (jo.getString("url") != null)
				url.setText(jo.getString("url"));
		} catch (Exception e) { }
	}
}
