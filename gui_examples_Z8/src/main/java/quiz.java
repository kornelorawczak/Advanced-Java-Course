import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;


public class quiz {
    int wrongcounter=0;
    int pointcounter=0;
    public void initGui(){
        Display display = Display.getDefault();
        final Shell shell = new Shell(display);
        createContents(shell);
        shell.open ();
        shell.setSize(450,600);
        shell.layout();
        while (!shell.isDisposed ()) {
            if (!display.readAndDispatch ()) display.sleep ();
        }
        display.dispose ();
    }
    public void createContents(Composite shell) {
        String pytania[] = {"Stolicą Polski jest: ", "Rzeka, która przepływa przez Wrocław to: ", "Stolicą Niemiec jest: ", "Stolicą Francji jest: ", "Stolicą województwa zachodnio-pomorskiego jest: ", "Stolicami województwa Lubuskiego są: ", "Człowiek pochodzi od: ", "Producentem sprzętu elektronicznego nie jest: ", "Stolicą USA jest: ", "Producentem konsol do gier nie jest: "};
        String odpowiedzi[][] = {{"Gniezno", "Kraków", "Warszawa", "Wrocław"}, {"Warta", "Wisła", "Dunaj", "Odra"}, {"Berlin", "Monachium", "Drezno", "Hamburg"}, {"Paryż", "Luwr", "Neapol", "Monako"}, {"Gdańsk", "Szczecin", "Wrocław,", "Gorzów Wielkpolski"}, {"Gorzów Wielkopolski", "Tarnów", "Jasna Góra", "Zielona Góra"}, {"Dinozaurów", "Płazów", "Małpy", "Gadów"}, {"Razer", "Steelseries", "Sony", "Pepco"}, {"Detroit", "Waszyngton", "Nowy Orlean", "Nowy Jork"}, {"Sony", "Nintendo", "Microsoft", "Steelseries"}};
        String poprawneodpowiedzi[] = {"Warszawa", "Odra", "Berlin", "Paryż", "Szczecin", "Gorzów Wielkopolski", "Małpy", "Pepco", "Waszyngton", "Steelseries"};
        GridLayout gl = new GridLayout(1, true);
        gl.horizontalSpacing = 20;
        gl.verticalSpacing = 5;
        gl.marginLeft = 0;
        gl.marginRight = 0;
        shell.setLayout(new FillLayout());
        Label wszystkieindeksypytan[] = new Label[10];
        Label wszystkiepytania[] = new Label[10];
        Button wszystkieodpowiedzi[][] = new Button[10][4];
        GridData idpyt = new GridData();
        idpyt.widthHint=60;
        GridData pyt = new GridData();
        pyt.widthHint=400;
        pyt.heightHint=20;
        GridData odp = new GridData();
        odp.widthHint=120;
        GridData chk = new GridData();
        chk.widthHint=500;
        chk.heightHint=50;
        ScrolledComposite sc = new ScrolledComposite(shell,SWT.H_SCROLL+SWT.V_SCROLL);
        GridData grp = new GridData();
        grp.heightHint=50;
        grp.verticalAlignment=SWT.CENTER;
        Composite child = new Composite(sc,SWT.NONE);
        child.setLayout(gl);
        for (int i = 0; i < 10; i++) {
            wszystkieindeksypytan[i] = new Label(child, SWT.NONE);
            wszystkieindeksypytan[i].setText("Pytanie " + (i+1) + ":");
            wszystkieindeksypytan[i].setLayoutData(idpyt);
            wszystkiepytania[i] = new Label(child, SWT.NONE);
            wszystkiepytania[i].setText(pytania[i]);
            wszystkiepytania[i].setLayoutData(pyt);
            wszystkiepytania[i].setFont(new Font(Display.getDefault(),"Times New Roman",12,6));

            Group gpyt = new Group(child,SWT.NONE);
            gpyt.setLayout(new GridLayout(2,true));
            gpyt.setLayoutData(grp);
            for (int j = 0; j < 4; j++) {
                wszystkieodpowiedzi[i][j] = new Button(gpyt, SWT.RADIO);
                wszystkieodpowiedzi[i][j].setText(odpowiedzi[i][j]);
                wszystkieodpowiedzi[i][j].setLayoutData(odp);
            }

        }
        Button check = new Button(child, SWT.BORDER + SWT.FLAT);
        check.setFocus();
        Label punkty= new Label(child,SWT.BORDER);
        punkty.setLayoutData(chk);
        check.setText("Sprawdź quiz: ");
        int wrong[]=new int[10];

        String wroong="";
        for (int i=0;i<=wrongcounter;i++){
            wroong+=(wrong[wrongcounter]+", ");
        }

        String finalWroong = wroong;
        check.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                for (int i=0; i<10;i++){
                    for(int j=0;j<4;j++){
                        if(wszystkieodpowiedzi[i][j].getSelection()){
                            if(odpowiedzi[i][j]==poprawneodpowiedzi[i]){
                                pointcounter +=1;
                            }
                            else{
                                wrong[wrongcounter]=i+1;
                                wrongcounter +=1;
                            }
                        }
                    }
                }
                if((wrongcounter >0)&(wrongcounter <10)){
                    punkty.setText("Zdobyłeś "+ pointcounter+" punktów. Błędy w zadaniach: "+ finalWroong);
                }
                else if(wrongcounter==0){
                    punkty.setText(+pointcounter+"Gratulacje! Wszystkie odpowiedzi były poprawne!");
                }
                else if(wrongcounter==10){
                    punkty.setText("Wstyd! Wszystko źle!");
                }
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent selectionEvent) {

            }
        });

        sc.setContent(child);
        sc.setMinSize(300,1600);
        sc.setExpandHorizontal(true);
        sc.setExpandVertical(true);


    }
    //odstęp między grupą a pytaniem i dlaczego nie liczy poprawnie oraz linie 97-99
    public static  void main(String[] args){
        quiz q=new quiz();
        q.initGui();
    }
}
