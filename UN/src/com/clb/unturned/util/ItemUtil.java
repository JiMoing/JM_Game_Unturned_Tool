package com.clb.unturned.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.clb.common.model.FileUtil;
import com.clb.common.model.IOUtil;
import com.clb.common.util.StringUtil;
import com.clb.unturned.Constant;
import com.clb.unturned.model.BaseItem;
import com.clb.unturned.model.ItemFactory;

public class ItemUtil {

	// /**
	// * 提取UN资源文件
	// *
	// * @param dirPath
	// * @param newDirPath
	// */
	// @Deprecated
	// public static void copyFile(String dirPath, String newDirPath) {
	// FileUtil.copyFiles(dirPath, newDirPath, Constant.FILE_SUFFIX_EN);
	// System.out.println("提取UN资源文件，完成");
	// }

	/**
	 * 提取UN资源文件
	 * 
	 * @param dirPath
	 * @param newDirPath
	 */
	public static void copyFileBat(String dirPath, String newDirPath) {
		FileUtil.copyFiles(dirPath, newDirPath, Constant.FILE_SUFFIX_BAT);
		System.out.println("提取UN资源文件，完成");
	}

	/**
	 * 合并 多方翻译，打印到 excel
	 * 
	 * @param outFile
	 *            输出文件名
	 * @param file
	 *            翻译资源
	 */
	public static void mergeResToFile(String outFile, String... fileName) {
		// 读取资源 合并
		Map<String, BaseItem> map = new HashMap<String, BaseItem>();
		for (String f : fileName) {
			File file = new File(f);
			if (file.isDirectory()) {
				map = getStringByDir(map, file);
			} else {
				map = getStringByFile(map, file, false);
			}
		}

		// 打印到excel
		writerToExcel(outFile, map);
		System.out.println("合并翻译并打印，完成");
	}

	/**
	 * 读取翻译文件，创建对应资源文件
	 * 
	 * @param dir
	 * @param file
	 */
	public static void createTranslatorFiles(String dir, String file, String key) {
		Map<String, BaseItem> map = new HashMap<String, BaseItem>();
		map = getStringByFile(map, new File(file), true);
		writerAllToFiles(dir, map, key);
		System.out.println("读取翻译文件，创建对应资源文件，完成");
	}

	private static Map<String, BaseItem> getStringByDir(Map<String, BaseItem> map, File dir) {
		return readItem(map, dir, FileUtil.getAllFile(dir, Constant.FILE_SUFFIX_EN));
	}

	private static Map<String, BaseItem> getStringByFile(Map<String, BaseItem> map, File f, boolean isCreate) {
		// 翻译开始字段
		int STRING_START = 3;

		try {
			Workbook book = Workbook.getWorkbook(f);
			// 获得第一个工作表对象
			Sheet sheet = book.getSheet(0);
			List<String> types = new ArrayList<String>();
			// 得到单元格
			for (int r = 0; r < sheet.getRows(); r++) {
				if (r == 0) {
					for (int c = STRING_START; c < sheet.getColumns(); c += 2) {
						Cell cell = sheet.getCell(c, r);
						String value = cell.getContents();
						if (value.contains(Constant.EXCEL_SPLIT)) {
							types.add(value.split(Constant.EXCEL_FORMAT_SPLIT)[0]);
						}
					}
					continue;
				}
				String path = sheet.getCell(0, r).getContents();
				String fields = sheet.getCell(2, r).getContents();

				BaseItem item = map.get(path);
				if (null == item) {
					if (isCreate) {
						item = ItemFactory.create(path);
					} else {
						continue;
					}
				}
				item.filePath = path;

				if (StringUtil.isEmptyJson(item.getAllFields())) {
					item.setFieldsByString(fields);
				}

				for (int c = STRING_START; c < sheet.getColumns(); c++) {
					int typeNum = (c - STRING_START) / 2;
					Cell cell = sheet.getCell(c, r);
					String value = cell.getContents();

					if (!StringUtil.isEmpty(value)) {
						if ((c - STRING_START) % 2 == 0) {
							item.addName(types.get(typeNum), value);
						} else {
							item.addDepict(types.get(typeNum), value);
						}
					}
				}

				map.put(path, item);
			}

			book.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	private static void writerToExcel(String outFile, Map<String, BaseItem> map) {
		try {
			File excel = new File(outFile);
			if (excel.exists()) {
				excel.delete();
			}
			// 创建workbook
			WritableWorkbook book = Workbook.createWorkbook(excel);
			// 表示生成一个名称为sheet1的sheet(工作表)，参数0表示第一页
			WritableSheet sheet = book.createSheet("sheet1", 0);
			// 指明单元格的位置是第一行第一列，并且内容为"test"
			Label label = new Label(0, 0, "test");
			// 把该label加入到工作表中(注意只有可写的workbook和可写的sheet才能做这些增加,删除....操作)

			// 设置字体种类和黑体显示,字体为Arial,字号大小为10,采用黑体显示
			WritableFont titleFont = new WritableFont(WritableFont.createFont("宋体"), 14, WritableFont.BOLD);
			// 生成一个单元格样式控制对象
			WritableCellFormat titleFormate = new WritableCellFormat(titleFont);
			// 单元格中的内容水平方向居中
			titleFormate.setAlignment(jxl.format.Alignment.CENTRE);
			// 单元格的内容垂直方向居中
			titleFormate.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);

			// 设置字体种类和黑体显示,字体为Arial,字号大小为10,采用黑体显示
			WritableFont txtFont = new WritableFont(WritableFont.createFont("宋体"), 12);
			// 生成一个单元格样式控制对象
			WritableCellFormat txtFormate = new WritableCellFormat(txtFont);
			// 单元格中的内容水平方向居中
			txtFormate.setAlignment(jxl.format.Alignment.LEFT);
			// 单元格的内容垂直方向居中
			txtFormate.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);

			// 行
			int row = 0;
			// 列
			int column = 0;

			for (Map.Entry<String, BaseItem> entry : map.entrySet()) {
				BaseItem item = entry.getValue();
				if (row == 0) {
					// 设置行高
					sheet.setRowView(row, 600, false);
					// 添加标题
					sheet.setColumnView(column, 15);
					sheet.addCell(new Label(column++, row, Constant.EXCEL_FILE, titleFormate));
					sheet.setColumnView(column, 10);
					sheet.addCell(new Label(column++, row, Constant.EXCEL_ID, titleFormate));
					sheet.setColumnView(column, 15);
					sheet.addCell(new Label(column++, row, Constant.EXCEL_FIELD, titleFormate));
					for (String key : item.strMap.keySet()) {
						sheet.setColumnView(column, 30);
						sheet.addCell(new Label(column++, row, key + Constant.EXCEL_SPLIT + Constant.EXCEL_NAME, titleFormate));
						sheet.setColumnView(column, 50);
						sheet.addCell(new Label(column++, row, key + Constant.EXCEL_SPLIT + Constant.EXCEL_DEPICT, titleFormate));
					}

				}
				row++;
				column = 0;
				// 设置行高
				sheet.setRowView(row, 400, false);

				sheet.addCell(new Label(column++, row, item.filePath, txtFormate));
				sheet.addCell(new Label(column++, row, item.getID(), txtFormate));
				sheet.addCell(new Label(column++, row, item.getAllFields(), txtFormate));

				for (String key : item.strMap.keySet()) {
					sheet.addCell(new Label(column++, row, item.getName(key), txtFormate));
					sheet.addCell(new Label(column++, row, item.getDepict(key), txtFormate));
				}
			}

			book.write();
			book.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解析文件
	 * 
	 * @param filePath
	 * @param fs
	 * @return
	 */
	private static Map<String, BaseItem> readItem(Map<String, BaseItem> map, File dir, List<File> fs) {
		BufferedReader reader = null;
		Pattern p = Pattern.compile(Constant.NAME_FORMAT);
		Matcher m = null;
		for (File f : fs) {
			String path = f.getAbsolutePath().substring(dir.getAbsolutePath().length());
			BaseItem item = map.get(path);
			boolean isNew = false;
			if (null == item) {
				isNew = true;
				item = ItemFactory.create(path);
			}
			item.filePath = path;

			try {
				// 解析名字
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(f), Constant.UTF_8));
				String lineTxt = null;
				int i = 0;
				while ((lineTxt = reader.readLine()) != null) {
					if (lineTxt.length() == 0) {
						continue;
					}
					if (i == 0) {
						m = p.matcher(lineTxt);
						if (m.find()) {
							item.addName(dir.getName(), m.group(1));
						}
					} else {
						item.addDepict(dir.getName(), lineTxt);
					}
					i++;
				}

				if (isNew) {
					// 读取 属性
					File detail = new File(f.getParentFile(), f.getParentFile().getName() + Constant.FILE_SUFFIX_BAT);
					if (detail.exists()) {
						IOUtil.close(reader);
						reader = new BufferedReader(new InputStreamReader(new FileInputStream(detail), Constant.UTF_8));
						lineTxt = null;
						while ((lineTxt = reader.readLine()) != null) {
							if (lineTxt.length() == 0) {
								continue;
							} else if (lineTxt.startsWith(BaseItem.FIELD_TYPE)) {
								addItemField(item, lineTxt, BaseItem.FIELD_TYPE, BaseItem.FIELD_FORMAT_TYPE);
							} else if (lineTxt.startsWith(BaseItem.FIELD_ID)) {
								addItemField(item, lineTxt, BaseItem.FIELD_ID, BaseItem.FIELD_FORMAT_ID);
							} else if (lineTxt.startsWith(BaseItem.FIELD_X)) {
								addItemField(item, lineTxt, BaseItem.FIELD_X, BaseItem.FIELD_FORMAT_X);
							} else if (lineTxt.startsWith(BaseItem.FIELD_Y)) {
								addItemField(item, lineTxt, BaseItem.FIELD_Y, BaseItem.FIELD_FORMAT_Y);
							} else if (lineTxt.startsWith(BaseItem.FIELD_WIDTH)) {
								addItemField(item, lineTxt, BaseItem.FIELD_WIDTH, BaseItem.FIELD_FORMAT_WIDTH);
							} else if (lineTxt.startsWith(BaseItem.FIELD_HEIGHT)) {
								addItemField(item, lineTxt, BaseItem.FIELD_HEIGHT, BaseItem.FIELD_FORMAT_HEIGHT);
							} else if (lineTxt.startsWith(BaseItem.FIELD_STORAGE_X)) {
								addItemField(item, lineTxt, BaseItem.FIELD_STORAGE_X, BaseItem.FIELD_FORMAT_STORAGE_X);
							} else if (lineTxt.startsWith(BaseItem.FIELD_STORAGE_Y)) {
								addItemField(item, lineTxt, BaseItem.FIELD_STORAGE_Y, BaseItem.FIELD_FORMAT_STORAGE_Y);
							} else if (lineTxt.startsWith(BaseItem.FIELD_AMOUNT)) {
								addItemField(item, lineTxt, BaseItem.FIELD_AMOUNT, BaseItem.FIELD_FORMAT_AMOUNT_Y);
							} else if (lineTxt.startsWith(BaseItem.FIELD_RANGE)) {
								addItemField(item, lineTxt, BaseItem.FIELD_RANGE, BaseItem.FIELD_FORMAT_RANGE_Y);
							} else if (lineTxt.startsWith(BaseItem.FIELD_FIRERATE)) {
								addItemField(item, lineTxt, BaseItem.FIELD_FIRERATE, BaseItem.FIELD_FORMAT_FIRERATE_Y);
							} else if (lineTxt.startsWith(BaseItem.FIELD_FOOD)) {
								addItemField(item, lineTxt, BaseItem.FIELD_FOOD, BaseItem.FIELD_FORMAT_FOOD);
							} else if (lineTxt.startsWith(BaseItem.FIELD_ENERGY)) {
								addItemField(item, lineTxt, BaseItem.FIELD_ENERGY, BaseItem.FIELD_FORMAT_ENERGY);
							} else if (lineTxt.startsWith(BaseItem.FIELD_WATER)) {
								addItemField(item, lineTxt, BaseItem.FIELD_WATER, BaseItem.FIELD_FORMAT_WATER);
							} else if (lineTxt.startsWith(BaseItem.FIELD_DISINFECTANT)) {
								addItemField(item, lineTxt, BaseItem.FIELD_DISINFECTANT, BaseItem.FIELD_FORMAT_DISINFECTANT);
							} else if (lineTxt.startsWith(BaseItem.FIELD_HEALTH)) {
								addItemField(item, lineTxt, BaseItem.FIELD_HEALTH, BaseItem.FIELD_FORMAT_HEALTH);
							} else if (lineTxt.startsWith(BaseItem.FIELD_VISION)) {
								addItemField(item, lineTxt, BaseItem.FIELD_VISION, BaseItem.FIELD_FORMAT_VISION);
							} else if (lineTxt.startsWith(BaseItem.FIELD_VIRUS)) {
								addItemField(item, lineTxt, BaseItem.FIELD_VIRUS, BaseItem.FIELD_FORMAT_VIRUS);
							} else if (lineTxt.startsWith(BaseItem.FIELD_DURABILITY)) {
								addItemField(item, lineTxt, BaseItem.FIELD_DURABILITY, BaseItem.FIELD_FORMAT_DURABILITY);
							}
						}
					}
				}
				if (!StringUtil.isEmpty(item.getName(dir.getName()))) {
					map.put(path, item);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				IOUtil.close(reader);
			}
		}

		return map;
	}

	private static void addItemField(BaseItem item, String str, String nameField, String regex) {
		String value = getValue(str, regex, 1);
		if (!StringUtil.isEmpty(value)) {
			item.addField(nameField, value);
		}
	}

	private static String getValue(String str, String regex, int n) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);

		if (m.find()) {
			return m.group(n);
		}
		return "";
	}

	/**
	 * 写资源文件
	 * 
	 * @param dir
	 * @param map
	 */
	private static void writerAllToFiles(String dir, Map<String, BaseItem> map, String key) {
		for (Map.Entry<String, BaseItem> entry : map.entrySet()) {
			writerNameToTxt(dir, entry.getValue(), key);
		}
	}

	/**
	 * Name写到文件
	 * 
	 * @param dir
	 * @param name
	 */
	private static void writerNameToTxt(String dir, BaseItem item, String key) {
		BufferedWriter writer = null;
		try {
			File file = new File(dir + item.filePath);
			file.getParentFile().mkdirs();
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false), Constant.UTF_8));
			String name = item.getPrintName(key);
			if (StringUtil.isEmpty(name)) {
				System.out.println("error null：" + item.filePath);
				return;
			}
			writer.append(Constant.NAME_LAB).append(name);
			writer.newLine();
			writer.append(item.getPrintDepict(key));
			writer.newLine();
			writer.flush(); // 全部写入缓存中的内容
		} catch (Exception e) {
			System.out.println("error:" + item.filePath);
			e.printStackTrace();
		} finally {
			IOUtil.close(writer);
		}
	}
}