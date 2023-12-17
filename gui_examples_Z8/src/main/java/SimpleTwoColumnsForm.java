import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class SimpleTwoColumnsForm {
    private Label labelFirstName;
    private Label labelLastName;
    private Label labelBirthDate;
    private Text firstName;
    private Text lastName;
    private DateTime birthDate;
    private Label labelDaneOsobowe;

    public void initGui() {
        Display display = new Display ();
        final Shell shell = new Shell(display);
        GridLayout gl=new GridLayout(2, false);
        gl.horizontalSpacing=50;
        shell.setLayout(gl);
        shell.setBackground(new Color(null, 255,200,100));
        GridData gd=new GridData();
        GridData gd2=new GridData();
        gd.widthHint=100;
        gd2.grabExcessHorizontalSpace=true;
        gd2.horizontalAlignment=SWT.FILL;
        labelFirstName=new Label(shell, SWT.NONE);
        labelFirstName.setText("Podaj imię");
        firstName=new Text(shell,SWT.BORDER);
        firstName.setForeground(display.getSystemColor(SWT.COLOR_GREEN));
        labelLastName=new Label(shell, SWT.NONE);
        labelLastName.setText("Podaj nazwisko");
        labelLastName.setFont(new Font(Display.getDefault(),"Cambria",20,5));
        labelLastName.setBackground(display.getSystemColor(SWT.COLOR_BLUE));
        labelLastName.setForeground(display.getSystemColor(SWT.COLOR_WHITE));
        labelLastName.setLayoutData(gd2);
        lastName=new Text(shell,SWT.BORDER);
        lastName.setLayoutData(gd);
        labelBirthDate=new Label(shell, SWT.NONE);
        labelBirthDate.setText("Podaj datę urodzenia");
        birthDate=new DateTime(shell, SWT.DROP_DOWN);
        shell.open ();
        shell.setSize(600,400);
        shell.layout();
        while (!shell.isDisposed ()) {
            if (!display.readAndDispatch ()) display.sleep ();
        }
        display.dispose ();

    }
    public static  void main(String[] args){
        SimpleTwoColumnsForm sftc=new SimpleTwoColumnsForm();
        sftc.initGui();
    }
}