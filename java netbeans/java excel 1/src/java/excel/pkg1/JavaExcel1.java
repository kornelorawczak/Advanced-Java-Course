/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java.excel.pkg1;
import java.io.File;
import java.io.IOException;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.*;
import jxl.write.Number;

/**
 *
 * @author marex
 */
public class JavaExcel1 {
    
    
    public static void main(String[] args) throws Exception {
        File f=new File("C:\\Users\\marex\\Desktop\\test.xls");
        Workbook wb=Workbook.getWorkbook(f);
        Sheet s=wb.getSheet(0);
        int row=s.getRows();
        int col=s.getColumns();
        for (int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                Cell c=s.getCell(j,i);
                System.out.print(c.getContents()+"\t\t");
                
            }
            System.out.println("");
        }
        String D1=s.getCell(0,0).getContents();
        System.out.println(D1);
        double readl=Double.parseDouble(D1);
        double result = 1+readl;
        System.out.println(result);
    }
    
}
