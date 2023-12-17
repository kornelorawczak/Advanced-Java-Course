import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;



public class SimpleForm {

    private Button button1;
    private Button button2;
    private Text text;

    public void initGui(){
        Display display = new Display ();
        final Shell shell = new Shell(display); // Zdefiniowanie układu (layout) I utworzenie widgetów (kontrolek)

        GridLayout gl = new GridLayout(2, true);
        gl.horizontalSpacing = 100;
        gl.verticalSpacing = 100;
        gl.marginRight=50;
        gl.marginLeft=50;
        shell.setLayout(gl);

        button1 = new Button(shell, SWT.NONE);
        button1.setText("Przycisk pierwszy");
        button2 = new Button(shell, SWT.NONE);
        button2.setText("Przycisk drugi");
        text = new Text(shell, SWT.NONE);

        shell.open ();
        shell.layout(); // Ułożenie widgetów zgodnie z układem, w tym miejscu
                // można też użyć shell.pack() do skompaktowania układu
        while (!shell.isDisposed ()) {
            if (!display.readAndDispatch ()) display.sleep ();
        }
        display.dispose();
    }

    public static void main(String[]Args){
        SimpleForm sf=new SimpleForm();
        sf.initGui();
        String a ="ABC";
        String b = "ABC";
        if(a==b){
            System.out.println("HaloKurwa");
        }
    }

}
