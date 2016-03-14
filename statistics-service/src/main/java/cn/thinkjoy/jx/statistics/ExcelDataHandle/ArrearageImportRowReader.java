package cn.thinkjoy.jx.statistics.ExcelDataHandle;

import cn.thinkjoy.jx.statistics.domain.BillingInfo;
import cn.thinkjoy.jx.statistics.service.IBillingInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SuZhou on 2015/12/21.
 */
@Service("arrearageImportRowReader")
public class ArrearageImportRowReader implements IRowReader {

    @Autowired
    private IBillingInfoService billingInfoService;

    /**
     * 欠费金额 权重
     */
//    @Value("${parameter.arrearsMoney}")
    private final int parameterArrearsMoney = 0;
    /**
     * 欠费等级 权重
     */
//    @Value("${parameter.arrearMonth}")
    private final int parameterArrearMonth = 1;
    /**
     * 欠费账龄 权重
     */
//    @Value("${parameter.arrearsAge}")
    private final int parameterArrearsAge = 0;
    /**
     * 欠费等级划分（第一等级）
     */
//    @Value("${arrears.first.level}")
    private final int firstLevel = 1;
    /**
     * 欠费等级划分（第二等级）
     */
//    @Value("${arrears.second.level}")
    private final int secondLevel = 2;

    @Override
    public void getRows(int sheetIndex, int curRow, List<String> rowlist) {
        if(curRow == 0) {
            return;
        }
        BillingInfo bi = new BillingInfo();
        for (int i=0; i<rowlist.size(); i++) {
            Map mm = new HashMap();
            String eAge = rowlist.get(1);
            String eMoney = rowlist.get(2);
            String monthNum = rowlist.get(3);

            bi.setPhoneNum(rowlist.get(0));
            bi.seteAge(eAge);
            bi.seteMoney(eMoney);
            bi.seteLevel(getLevel(Float.parseFloat(eMoney), Integer.parseInt(monthNum), Integer.parseInt(eAge)));
        }
        //将数据插入数据库
        int i = billingInfoService.importInsert(bi);

    }

    //计算欠费等级
    private String getLevel(float arrearsMoney,int arrearsMonth,int arrearsAge){

        float levelValue = arrearsMoney * parameterArrearsMoney + arrearsMonth * parameterArrearMonth + arrearsAge * parameterArrearsAge;
        if(levelValue<firstLevel){
            return "1";
        }else{
            if(levelValue<secondLevel){
                return "2";
            }else{
                return "3";
            }
        }
    }

}
