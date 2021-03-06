package com.packages.helper.PackageHelper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import net.dongliu.apk.parser.ApkFile;
import net.dongliu.apk.parser.bean.ApkMeta;
import net.dongliu.apk.parser.bean.ApkSigner;
import net.dongliu.apk.parser.bean.UseFeature;

public class MainWindow implements IDropHandler {

	private DragFrame frame;
	// 在中间添加一个panel
	private JPanel panelMain = new JPanel();
	
	// 布局对象
	private GridBagLayout gridBagLayout;
	
	// 组件管理对象
	private GridBagConstraints gridBagConstraints;
	
	// 用于显示信息
	private JLabel labelInfo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new DragFrame();
		frame.setDropHandler(this);
		frame.setTitle("AppHelper --Bangcle 林奇");
		
		

		// 设置中间控件容器的布局

        gridBagLayout=new GridBagLayout(); //实例化布局对象
        gridBagLayout.columnWeights = new double[] {1, 2};
        gridBagLayout.rowWeights = new double[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 ,1};
        panelMain.setLayout(gridBagLayout);                     //jf窗体对象设置为GridBagLayout布局
        gridBagConstraints=new GridBagConstraints();//实例化这个对象用来对组件进行管理
        gridBagConstraints.fill=GridBagConstraints.BOTH;//该方法是为了设置如果组件所在的区域比组件本身要大时的显示情况 
        //NONE：不调整组件大小。 
        //HORIZONTAL：加宽组件，使它在水平方向上填满其显示区域，但是不改变高度。 
        //VERTICAL：加高组件，使它在垂直方向上填满其显示区域，但是不改变宽度。 
        //BOTH：使组件完全填满其显示区域。 


		frame.setBounds(100, 100, 519, 372);
		BorderLayout borderLayout = new BorderLayout();
		borderLayout.setHgap(15);
		borderLayout.setVgap(15);
		frame.getContentPane().setLayout(borderLayout);

		JButton btnAbout = new JButton();
		btnAbout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new About().setVisible(true);

			}
		});
		btnAbout.setText("About");
		btnAbout.setHorizontalAlignment(JButton.LEFT);
		btnAbout.setBackground(Color.white);
		frame.getContentPane().add(btnAbout, BorderLayout.NORTH);

		frame.getContentPane().add(panelMain, BorderLayout.CENTER);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		labelInfo = new JLabel();
		labelInfo.setBorder(new EmptyBorder(10, 10, 10, 10));
		labelInfo.setText("请将包文件拖入到窗体空白处");
		frame.getContentPane().add(labelInfo, BorderLayout.SOUTH);

	}

	private void handleFile(File file) {
		// 清空Panel下的所有控件
		panelMain.removeAll();
		panelMain.repaint();
		panelMain.validate();

		Map<String, Object> infoMap = new LinkedHashMap<String, Object>();
		
		int index = file.getName().lastIndexOf(".");
		String extName = file.getName().substring(index);
		System.out.println("extName=" + extName);
		if (extName.toLowerCase().equals(".apk")) {
			try (ApkFile apkFile = new ApkFile(file)) {
				ApkMeta apkMeta = apkFile.getApkMeta();
				
				infoMap.put("应用名称", apkMeta.getName());
				infoMap.put("应用包名", apkMeta.getPackageName());
				infoMap.put("版本信息", apkMeta.getVersionName());

				List<ApkSigner> signers = apkFile.getApkSingers();
				ApkSigner signer = signers.get(0);
				infoMap.put("证书MD5", signers.get(0).getCertificateMetas().get(0).getCertBase64Md5());
				infoMap.put("内部版本信息", apkMeta.getPlatformBuildVersionCode());
				for (UseFeature feature : apkMeta.getUsesFeatures()) {
					System.out.println(feature.getName());
				}
				labelInfo.setText("解析了一个apk文件");
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
		} else if (extName.toLowerCase().equals(".ipa")) {
			// ipa文件，apple应用
			Map<String, Object> maps = IPAHelper.parseIpa(file);
			infoMap.put("应用名称", maps.get("appName"));
			infoMap.put("应用包名", maps.get("appId"));
			infoMap.put("版本信息", maps.get("versionName"));
			infoMap.put("开发者签名", maps.get("appName") + "(此项还未开发完毕)");
				labelInfo.setText("解析了一个ipa文件");
		} else {
			labelInfo.setText("只能处理apk或ipa文件");
			return;
		}
		infoMap.put("文件MD5", MessageDigestHelper.getFileMD5(file).toUpperCase());
		infoMap.put("文件SHA1", MessageDigestHelper.getFileSha1(file).toUpperCase());
		DecimalFormat df = new DecimalFormat("#,###");
		double size = (double) (file.length() * 100) / 100;
		infoMap.put(
				"文件大小", df.format(size) + "字节 (" + + +(double) (file.length() * 100 / 1024 / 1024) / 100 + "MB)");
		drawInfos(infoMap);
		panelMain.repaint();
		panelMain.validate();
	}

	
	/**
	 * 绘制控件
	 * @param infoMap
	 */
	private void drawInfos(Map<String, Object> infoMap) {
		int i = 0;
		
		// TODO Auto-generated method stub
		for (String key : infoMap.keySet()) {
			
			
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = i;
			JLabel label = new JLabel();
			
			label.setText(key);
			label.setHorizontalAlignment(JLabel.RIGHT);
			panelMain.add(label);
			gridBagLayout.setConstraints(label, gridBagConstraints);
			
			gridBagConstraints.gridx = 1;
			gridBagConstraints.gridy = i;
			JTextField text = new JTextField();
			Object obj = infoMap.get(key);
			String val = "";
			if (obj != null)
			{
				val = obj.toString();
			}
				
				
			text.setText(val);
			gridBagLayout.setConstraints(text, gridBagConstraints);
			panelMain.add(text);
			
			
			i++;
		}
	}

	@Override
	public void handleDrop(List<File> files) {
		// TODO Auto-generated method stub
		if (files == null || files.size() == 0) {
			return;
		}
		if (files.size() > 1) {
			System.out.println(files.size());
			labelInfo.setText("每次只能处理一个文件！");
			return;
		}
		handleFile(files.get(0));
	}

}