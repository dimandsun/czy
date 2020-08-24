package com.czy.util.io.office;

import org.apache.poi.ss.usermodel.CellType;

/**
 * @author chenzy
 * @date 2020-08-24
 */
public class ExcelHead {
    private String key;
    private CellType cellType;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public CellType getCellType() {
        return cellType;
    }

    public void setCellType(CellType cellType) {
        this.cellType = cellType;
    }
}
