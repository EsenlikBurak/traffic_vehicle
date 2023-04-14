import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import acm.graphics.*;
import acm.program.*;
import acm.util.RandomGenerator;
public class TrafficVehicle3 extends GraphicsProgram {
	public static final int APPLICATION_WIDTH = 1000;
	public static final int APPLICATION_HEIGHT = 650;
	int secim =0;
	GLabel start, exit, returnToMenu ;
	public void run() {
		addMouseListeners();
		metinleriEkle();
		if(secim==1) {
			addKeyListeners();
			//sifirla();
			removeAll();
			//Yolu Yaptik
			GLine ilkSerit = new GLine (getWidth()/3,0, getWidth()/3,getHeight());
			add(ilkSerit);
			GLine sonSerit = new GLine (2*getWidth()/3,0, 2*getWidth()/3,getHeight());
			add(sonSerit);
			//Araba tasarliyoruz ve en ortaya koyarak baslatiyoruz
			mainCar = new GImage("MainCar.jpeg");
			mainCar.scale(1.50);
			add(mainCar,X,Y );			
			animasyon();
			removeAll();
			//sifirla();
		}else if(secim == 2) {
			System.exit(0);
		}		
		run();		
	}
	private GImage mainCar = null;
	// private GImage randomCar = null;
	public void keyPressed(KeyEvent a) {
		if(a.getKeyCode() == KeyEvent.VK_RIGHT){
			if(mainCar.getX()+300 < 1000) {
				mainCar.setLocation(mainCar.getX()+300, Y);
			}
		}
		if(a.getKeyCode() == KeyEvent.VK_LEFT){
			if(mainCar.getX()-300 > 0) {
				mainCar.setLocation(mainCar.getX()-300, Y);
			}
		}
	}
	//Bu kisimda rastgele sekilde arac g�nderiyoruz ve carpisma ksimini ayarliyoruz
	private GImage[] arabaGonder() {///////////
		GImage[] randomCar = new GImage[3];
		for(int i = 0; i < randomCar.length; i++) {
			randomCar[i] = new GImage("RandomCar.jpeg");
			randomCar[i].scale(1.50);
			randomCar[i].setLocation(i*getWidth()/3+57, getHeight());
			add(randomCar[i]);
		}
		return randomCar;
	}
	private void animasyon() {
		returnToMenu = new GLabel("Return to Menu");
		returnToMenu.setFont("Times New Roman-15");
		add(returnToMenu, 0 , returnToMenu.getHeight() );

		GImage[] randomCar = arabaGonder();
		int sayac = 0;
		while(true) {
			for(int i = 0; i < randomCar.length; i++) {
				//rastgele aracin hareket sinirlarini belirliyoruz
				randomCar[i].move(0,10);

				if(randomCar[i].getY() - 300 > getHeight() && rgen.nextInt(100)==0) {//100'de 1 ihtimalle araba gondermesi icin && rgen.nextInt(100)==0 yazdik
					randomCar[i].setLocation(randomCar[i].getX(), -randomCar[i].getHeight());
					sayac++;
				}
				//Random carin ysi ile maincarinysi esit ise ekrana label ekle
				GObject duranArac = getElementAt(mainCar.getX(),mainCar.getY());
				GObject gelenArac = getElementAt(randomCar[i].getX(),randomCar[i].getY());
				if(duranArac == gelenArac) {
					removeAll();
					GLabel crash = new GLabel("Hey, You Crashed!!");
					crash.setFont("Times New Roman-50");
					add(crash, getWidth()/2-100, getHeight()/2);
				}                
			}
			if(sayac == 5) {//////////
				removeAll();
				GLabel win = new GLabel("YOU WIN!!");
				win.setFont("Times New Roman-50");
				add(win,getWidth()/2-100, getHeight()/2);
				break;
			}if(secim == 3) {
				removeAll();
				break;
			}    
			pause(15);
		}
	}
	private void metinleriEkle() {
		start = new GLabel("Start");
		start.setFont("Times New Roman-30");
		add(start, getWidth()/2-start.getWidth()/2-20, getHeight()/2+start.getHeight());

		exit = new GLabel("Exit");
		exit.setFont("Times New Roman-30");
		add(exit, getWidth()/2+exit.getWidth()/2, getHeight()/2+exit.getHeight());
	}
	/*private void sifirla() {
		startX = 50;
		secim=0;
		removeAll();     
	}*/
	public void mouseClicked(MouseEvent fare) {
		GObject obje = getElementAt(fare.getX(),fare.getY());
		if(obje != null) {
			if(obje == start) {
				secim=1;
			}else if(obje == exit) {
				secim=2;
			}else if(obje == returnToMenu) {
				secim=3;
			}
		}
	}
	private final int X = 425;  //mainCar'in baslangic x'si
	private final int Y = 550;  //mainCarin y'si
	private final int startY = -200; //randomCarin y'si
	private int startX = 50; //randomCarin x'si
	GImage[] randomCar;
	private RandomGenerator rgen = RandomGenerator.getInstance();
}