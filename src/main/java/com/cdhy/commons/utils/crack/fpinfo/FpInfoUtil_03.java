package com.cdhy.commons.utils.crack.fpinfo;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.cdhy.commons.utils.MoneyUpperUtil;

import net.sf.json.JSONObject;

public class FpInfoUtil_03 {
    public static Map<String, Object> parseFPInfo(JSONObject fp_Info) {
	Map<String, Object> fp_map = new HashMap<String, Object>();
	String template = fp_Info.getString("template");
	String fplx = fp_Info.getString("fplx");
	String fpxx = fp_Info.getString("fpxx");
	String hwxx = fp_Info.getString("hwxx");
	String jmbz = fp_Info.getString("jmbz");
	String sort = fp_Info.getString("sort");
	String rule = fp_Info.getString("ruleStr");
	//
	String[] rules = rule.split("☺");
	String splitstr = rules[0];
	//
	String fpxxs = fpxx.replaceAll(splitstr, "≡");
	String hwxxs = hwxx.replaceAll(splitstr, "≡");
	splitstr = "≡";
	String[] sortarray = sort.split("_");
	String[] tmpfpxx = fpxxs.split("≡");
	String cysj = tmpfpxx[tmpfpxx.length - 1];// 查验时间
	String[] tmpfp = new String[tmpfpxx.length - 4];
	for (int i = 3; i < tmpfpxx.length - 1; i++) {
	    tmpfp[i - 3] = tmpfpxx[i];
	}
	String[] newfpxx = new String[tmpfpxx.length - 4];
	for (int i = 0; i < tmpfpxx.length - 4; i++) {
	    int idx = Integer.parseInt(sortarray[i]);
	    if (idx < tmpfp.length) {
		newfpxx[i] = tmpfp[idx];
	    } else {
		newfpxx[i] = "";
	    }
	}
	String newfpxxstr = tmpfpxx[0] + "≡" + tmpfpxx[1] + "≡" + tmpfpxx[2] + "≡";
	for (int i = 0; i < newfpxx.length; i++) {
	    newfpxxstr = newfpxxstr + newfpxx[i] + "≡";
	}
	fpxxs = newfpxxstr + cysj;// 发票解密数据
	int cycs = 0;// 查验次数
	if (fpxxs != null && !"".equals(fpxxs)) {
	    String[] fpxxArr = fpxxs.split("≡");
	    cycs = Integer.parseInt(fpxxArr[3]) + 1;

	    // 发票数据
	    String fpdm = "" + fpxxArr[0];
	    fp_map.put("fp_dm", fpdm);// 代码
	    String fphm = "" + fpxxArr[1];
	    fp_map.put("fp_hm", fphm);// 号码
	    String fp_title = "" + fpxxArr[2];
	    fp_map.put("title", fp_title);// 标题
	    String cycsStr = "" + cycs;
	    fp_map.put("cycs", cycsStr);// 查验次数
	    String cysjStr = "" + fpxxArr[33];
	    fp_map.put("cysj", cysjStr);// 查验时间
	    //
	    String kprq = "" + formatKprq(fpxxArr[4], rules[3]);
	    fp_map.put("kprq", kprq);// fpxxArr[3]//开票日期
	    String jqbh = "" + fpxxArr[5];
	    fp_map.put("jqbh", jqbh);// 机器编号
	    //
	    String gfmc = "" + fpxxArr[6];
	    fp_map.put("gmf_mc", gfmc);// 购买方名称
	    String gfsbh = "" + fpxxArr[8];
	    fp_map.put("gmf_nsrsbh", gfsbh);// 购买方税号
	    String gmf_sfzhm = "" + fpxxArr[7];
	    fp_map.put("gmf_sfzhm", gmf_sfzhm);// 购买方身份证号码
	    // ----
	    String cllx = "" + fpxxArr[9];
	    fp_map.put("cllx", cllx);// 车辆类型 9
	    String cpxh = "" + fpxxArr[10];
	    fp_map.put("cpxh", cpxh);// 厂牌型号 10
	    String cd = "" + fpxxArr[11];
	    fp_map.put("cd", cd);// 产地 11
	    String hgzs = "" + fpxxArr[12];
	    fp_map.put("hgzs", hgzs);// 合格证号 12
	    String sjdh = "" + fpxxArr[14];
	    fp_map.put("sjdh", sjdh);// 商检单号
	    String fdjhm = "" + fpxxArr[15];
	    fp_map.put("fdjhm", fdjhm);// 发动机号码
	    String cjhm = "" + fpxxArr[16];
	    fp_map.put("cjhm", cjhm);// 车架号码
	    String jkzmsh = "" + fpxxArr[17];
	    fp_map.put("jkzmsh", jkzmsh);// 进口证明书号
	    String dw = "" + fpxxArr[29];
	    fp_map.put("dw", dw);// 吨位
	    String xcrs = "" + fpxxArr[30];
	    fp_map.put("xcrs", xcrs);// 限乘人数
	    //
	    String xfmc = "" + fpxxArr[18];
	    fp_map.put("xsf_mc", xfmc);// 销售方名称
	    String xfsbh = "" + formatSBH(fpxxArr[20], rules[1]);
	    fp_map.put("xsf_nsrsbh", xfsbh);// 销售方税号
	    String xfdzdh = "" + fpxxArr[22] + " " + fpxxArr[19];
	    fp_map.put("xsf_dzdh", xfdzdh);// 销售方电话地址
	    String xfyhzh = "" + fpxxArr[23] + " " + fpxxArr[21];
	    fp_map.put("xsf_yhzh", xfyhzh);// 销售方名称银行账号
	    //
	    String zzsse = getMoney(fpxxArr[25], rules[2]);
	    fp_map.put("zzsse", zzsse);// 增值税税额
	    String swjg_dm = fpxxArr[26];
	    fp_map.put("swjg_dm", swjg_dm);// 税务机关代码
	    String swjg_mc = fpxxArr[32];
	    fp_map.put("swjg_mc", swjg_mc);// 税务机关代码
	    String wspzhm = fpxxArr[28];
	    fp_map.put("wspzhm", wspzhm);// 完税凭证号码
	    //
	    String zzssl = fpxxArr[24];
	    fp_map.put("zzssl", zzssl);// 增值税税率
	    String hjje = getMoney(fpxxArr[13], rules[2]);
	    fp_map.put("hjje", hjje);// 合计金额
	    String jshj = getMoney(fpxxArr[27], rules[2]);
	    fp_map.put("jshj", jshj);// 价税合计
	    String jshj_dx = MoneyUpperUtil.toBigAmt(Double.parseDouble(jshj));
	    fp_map.put("jshj_dx", jshj_dx);// 价税合计大写
	    // String bz = "" + jmbz;
	    // fp_map.put("bz", bz);// 备注
	    // String jym = "" + fpxxArr[13];
	    // fp_map.put("jym", jym);// 校验码

	}
	return fp_map;
    }


    private static String formatKprq(String time, String add) {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	try {
	    Date d = sdf.parse(time);
	    d.setDate(d.getDate() + (0 - Integer.parseInt(add)));
	    return sdf.format(d);
	} catch (ParseException e) {
	    e.printStackTrace();
	    throw new RuntimeException(e);
	}

    }

    private static String formatSBH(String sbh, String str) {
	String[] s1 = str.split("_");
	for (int i = 0; i < s1.length; i++) {
	    sbh = chgchar(sbh, s1[i]);
	}
	return sbh;
    }

    private static String chgchar(String nsrsbh, String ss) {
	String a = ss.charAt(2) + "";
	String b = ss.charAt(0) + ""; // 反向替换，所以和java中是相反的
	nsrsbh = nsrsbh.replaceAll(a, "#");
	nsrsbh = nsrsbh.replaceAll(b, "%");
	nsrsbh = nsrsbh.replaceAll("#", b);
	nsrsbh = nsrsbh.replaceAll("%", a);
	return nsrsbh;
    }

    private static String getMoney(String je, String ss) {
	if (null == je || "".equals(je.trim())) {
	    return "";
	}
	double arg1 = Double.parseDouble(je.trim());
	int r1, r2;
	if (arg1 == Math.floor(arg1)) {
	    r1 = 0;
	} else {
	    r1 = ("" + arg1).split("\\.")[1].length();
	}
	double arg2 = Double.parseDouble(ss.trim());
	if (arg2 == Math.floor(arg2)) {
	    r2 = 0;
	} else {
	    r2 = ("" + arg2).toString().split("\\.")[1].length();
	}
	double m = Math.pow(10, Math.max(r1, r2));
	// alert(m);
	double r = (arg1 * m + arg2 * m) / m;
	BigDecimal rb = new BigDecimal(r, MathContext.DECIMAL128).setScale(2, RoundingMode.HALF_DOWN)
		.stripTrailingZeros();
	return rb.doubleValue() + "";
    }
}
