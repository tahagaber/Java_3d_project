package Widget;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Table extends JTable {

    public Table() {
        // بيانات افتراضية
        Object[][] data = {
                {1, "Tasnim", "Software", 3.8 , "Active"},
                {2, "Taha", "Software", 3.8 , "Active"},
                {3, "Nourin", "Software", 3.8 , "Active"},
                {4, "Ibrahim", "Software", 3.8 , "Active"},
                {9, "Menna", "Software", 3.8 , "Active"},
        };

        // أسماء الأعمدة
        String[] columns = {"ID", "Name", "Department","GPA","Status"};

        // إنشاء الموديل
        DefaultTableModel model = new DefaultTableModel(data, columns);
        this.setModel(model);

        // إعدادات بسيطة للجدول
        this.setFillsViewportHeight(true);
        this.setRowHeight(25);
    }
}
