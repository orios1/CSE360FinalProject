import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import java.awt.*;
import java.awt.event.*;


public class DatePicker{
	//Class variables.
	UtilDateModel model;
	JPanel buttonPane;
	JButton button;
	String date;
	
	/**
	 * Datepicker constructor that takes in menuBar instance.
	 * @param menubar
	 */
	public DatePicker(MenuBar menubar){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }

                JFrame frame = new JFrame("Attendance Date");
                button = new JButton("OK");
                button.addActionListener(new ActionListener() {
                	@Override
					public void actionPerformed(ActionEvent e) {
						//Environment for managing attendance.
                		date = getDate();
						menubar.attendanceManager = new AttendanceManager();
						menubar.attendanceManager.openAttendanceFile(menubar.roster);
						System.out.println(date);
						menubar.table.update();
						frame.dispose();
					}
                	
                });
                JPanel buttonPane = new JPanel();
                
                buttonPane.setLayout(new FlowLayout());
                buttonPane.add(button);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(300, 300);
                frame.add(new DatePane(),BorderLayout.PAGE_START);
                frame.add(buttonPane, BorderLayout.PAGE_END);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                
            }
        });
        
    }
    public class DatePane extends JPanel {

    	/**
    	 * 
    	 */
        public DatePane() {
            model = new UtilDateModel();
            Properties p = new Properties();
            p.put("text.today", "Today");
            p.put("text.month", "Month");
            p.put("text.year", "Year");
            JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
            JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
            setLayout(new GridBagLayout());
            add(datePicker);
            
        }

    }

    public class DateLabelFormatter extends AbstractFormatter {

        private String datePattern = "yyyy-MM-dd";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }

            return "";
        }

    }
    String getDate() {
    	return String.valueOf(model.getDay()) +  String.valueOf(model.getMonth()) + String.valueOf(model.getYear());
    }
    
}