package cn.thinkjoy.jx.statistics.ExcelDataHandle;

import java.util.List;

/**
 * Created by Mike on 2015/12/21.
 */
public interface IRowReader {
    /**业务逻辑实现方法
     * @param sheetIndex
     * @param curRow
     * @param rowlist
     */
    public  void getRows(int sheetIndex,int curRow, List<String> rowlist);
}
