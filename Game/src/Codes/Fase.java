package Codes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.border.Border;

public class Fase extends JPanel implements ActionListener, MouseListener, MouseMotionListener {

	private Image fundo;
	private static boolean[] movement = new boolean[] { false, false, false, false };
	// direito baixo esquerdo cima
	private static boolean[] EnemyMove = new boolean[] { false, false, false, false };
	public int direction, enemyQuantit = 0, enemyCount = 0, preCount = 0, deadEnemys = 0;
	public boolean generate = true;
	public int enemysDamage = 5;
	private Timer timer;
	public List<Enemy> enemys = new ArrayList<>();

	public int WindowWidth = 900, WindowHeight = 600;
	public Player player1 = new Player(this.WindowWidth / 2, this.WindowHeight / 2);
	public Tilemap tilemap = new Tilemap();
	public int[] scroll = { 0, 0 };
	public int invencibilityTime = 15;
	public int manaQuant = 30;

	public List<Speel> speels = new ArrayList<>();
	int actualSpeels = 0;
	int money = 0;
	int speelType = 0;
	int speelSpeed = 2;
	int speelScale = 1;
	
	public List<Coins> coins = new ArrayList<>();

	JProgressBar lifeBar = new JProgressBar();
	JProgressBar manaBar = new JProgressBar();
	JLabel label = new JLabel(); 
	JLabel price1 = new JLabel(); 
	JLabel price2 = new JLabel(); 
	JLabel price3 = new JLabel(); 
	JLabel price4 = new JLabel(); 
	JLabel labelDead = new JLabel(); 
	ImageIcon image = new ImageIcon("src\\res\\Coins\\1.png");
	Image newImage = image.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	ImageIcon finalimage = new ImageIcon(newImage);
	JButton button1 = new JButton();
	JButton button2 = new JButton();
	JButton button3 = new JButton();
	JButton button4 = new JButton();
	
	ImageIcon blue = new ImageIcon("src\\res\\Speels\\blue\\1.png");
	ImageIcon dark = new ImageIcon("src\\res\\Speels\\dark\\1.png");
	ImageIcon green = new ImageIcon("src\\res\\Speels\\green\\1.png");
	ImageIcon orange = new ImageIcon("src\\res\\Speels\\orange\\1.png");
	
	
	Border border = BorderFactory.createLineBorder(new Color(125, 107, 6),3);
	
	Random random = new Random();

	public Fase() {		
		
		button1.setBounds(700, 70, 150, 50);
		button1.setText("20 £");
		button1.setForeground(new Color(105, 93, 17));
		button1.setFont(new Font("MV Boli",Font.PLAIN,20));
		button1.addActionListener(this);	
		button1.setFocusable(false);
		button1.setIcon(blue);
		button1.setBackground(Color.lightGray);
		button1.setBorder(BorderFactory.createEtchedBorder());
		
		button2.setBounds(700, 130, 150, 50);
		button2.setText("40 £");
		button2.setForeground(new Color(105, 93, 17));
		button2.setFont(new Font("MV Boli",Font.PLAIN,20));
		button2.addActionListener(this);	
		button2.setFocusable(false);
		button2.setIcon(orange);
		button2.setBackground(Color.lightGray);
		button2.setBorder(BorderFactory.createEtchedBorder());
		
		button3.setBounds(700, 190, 150, 50);
		button3.setText("60 £");
		button3.setForeground(new Color(105, 93, 17));
		button3.setFont(new Font("MV Boli",Font.PLAIN,20));
		button3.addActionListener(this);	
		button3.setFocusable(false);
		button3.setIcon(green);
		button3.setBackground(Color.lightGray);
		button3.setBorder(BorderFactory.createEtchedBorder());
		
		button4.setBounds(700, 250, 150, 50);
		button4.setText("80 £");
		button4.setForeground(new Color(105, 93, 17));
		button4.setFont(new Font("MV Boli",Font.PLAIN,20));
		button4.addActionListener(this);	
		button4.setFocusable(false);
		button4.setIcon(dark);
		button4.setBackground(Color.lightGray);
		button4.setBorder(BorderFactory.createEtchedBorder());
		
		label.setIcon(finalimage);
		label.setText("Total :" + money);
		label.setHorizontalTextPosition(JLabel.LEFT); 
		label.setVerticalTextPosition(JLabel.TOP); 
		label.setForeground(new Color(0, 0, 0)); 
		label.setFont(new Font("MV Boli",Font.PLAIN,20)); 
		label.setIconTextGap(0); 
		label.setBackground(new Color(179, 153, 7)); 
		label.setOpaque(true); 
		label.setBorder(border); 
		label.setVerticalAlignment(JLabel.CENTER); 
		label.setHorizontalAlignment(JLabel.CENTER); 
		label.setBounds(650, 10, 200, 50); //x, y, width, height
		
		labelDead.setText("Você morreu :(");
		labelDead.setHorizontalTextPosition(JLabel.CENTER); 
		labelDead.setVerticalTextPosition(JLabel.CENTER); 
		labelDead.setForeground(new Color(0, 0, 0)); 
		labelDead.setFont(new Font("MV Boli",Font.PLAIN,20)); 
		labelDead.setBackground(new Color(255, 0 ,0)); 
		labelDead.setOpaque(true); 
		labelDead.setVerticalAlignment(JLabel.CENTER); 
		labelDead.setHorizontalAlignment(JLabel.CENTER); 
		labelDead.setBounds(WindowWidth/2 - 100, 50, 200, 150); 
		
		lifeBar.setMaximum((int) player1.getLifeLimit());
		lifeBar.setValue((int) player1.getLife());
		lifeBar.setBounds(10, 10, 250, 30);
		lifeBar.setStringPainted(true);
		lifeBar.setFont(new Font("MV Boli", Font.BOLD, 25));
		lifeBar.setForeground(Color.red);
		lifeBar.setBackground(Color.black);

		manaBar.setMaximum((int) player1.getManaLimit());
		manaBar.setValue((int) player1.getMana());
		manaBar.setBounds(10, 50, 250, 30);
		manaBar.setStringPainted(true);
		manaBar.setFont(new Font("MV Boli", Font.BOLD, 25));
		manaBar.setForeground(Color.BLUE);
		manaBar.setBackground(Color.black);

		setLayout(null);
		add(lifeBar);
		add(manaBar);
		add(label);
		add(button1);
		add(button2);
		add(button3);
		add(button4);

		setFocusable(true);
		setDoubleBuffered(true);

		ImageIcon referencia = new ImageIcon("src\\res\\preto.png");
		fundo = referencia.getImage();

		addKeyListener(new TecladoAdapter());
		addMouseListener(this);
		addMouseMotionListener(this);

		timer = new Timer(5, this);
		timer.start();
		enemyQuantit = 10;
		preCount = enemyQuantit;
		enemyCount = enemyQuantit;
	}

	public void mousePressed(MouseEvent e) {
		
	}

	private class TecladoAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent tecla) {
			int codigo = tecla.getKeyCode();
			
			if (codigo == KeyEvent.VK_RIGHT) {
				actualSpeels += 1;
				int direction = 1;
				double mana = player1.getMana();
				if (mana > 30) {
					Speel speel1;
					speels.add(speel1 = new Speel(player1.getX(), player1.getY(), direction, speelSpeed, speelType, speelScale));
					player1.setMana(mana - manaQuant);
				}
			} else if (codigo == KeyEvent.VK_LEFT) {
				actualSpeels += 1;
				int direction = 2;
				double mana = player1.getMana();
				if (mana > 30) {
					Speel speel1;
					speels.add(speel1 = new Speel(player1.getX(), player1.getY(), direction, speelSpeed, speelType, speelScale));
					player1.setMana(mana - manaQuant);
				}
			} else if (codigo == KeyEvent.VK_DOWN) {
				actualSpeels += 1;
				int direction = 3;
				double mana = player1.getMana();
				if (mana > 30) {
					Speel speel1;
					speels.add(speel1 = new Speel(player1.getX(), player1.getY(), direction, speelSpeed, speelType, speelScale));
					player1.setMana(mana - manaQuant);
				}
			} else if (codigo == KeyEvent.VK_UP) {
				actualSpeels += 1;
				int direction = 4;
				double mana = player1.getMana();
				if (mana > 30) {
					Speel speel1;
					speels.add(speel1 = new Speel(player1.getX(), player1.getY(), direction, speelSpeed, speelType, speelScale));
					player1.setMana(mana - manaQuant);
				}
			}
			
			
			
			if (codigo == KeyEvent.VK_A) {
				direction = 2;
				movement[2] = true;
			} else if (codigo == KeyEvent.VK_W) {
				direction = 3;
				movement[3] = true;
			} else if (codigo == KeyEvent.VK_D) {
				direction = 0;
				movement[0] = true;
			} else if (codigo == KeyEvent.VK_S) {
				direction = 1;
				movement[1] = true;
			}
		}

		@Override

		public void keyReleased(KeyEvent tecla) {
			int codigo = tecla.getKeyCode();
			if (codigo == KeyEvent.VK_A) {
				movement[2] = false;
			} else if (codigo == KeyEvent.VK_W) {
				movement[3] = false;
			} else if (codigo == KeyEvent.VK_D) {
				movement[0] = false;
			} else if (codigo == KeyEvent.VK_S) {
				movement[1] = false;
			}
		}

	}

	public void Collides() {
		Rectangle player_rect = player1.PlayerRect();

		List<MushRects> tempRects = tilemap.getMushRects();

		int count = 0;
		List<Integer> collides = new ArrayList<>();

		for (MushRects mushRects2 : tempRects) {
			if (player_rect.intersects(mushRects2.getRect())) {
				player1.buffs(mushRects2.getType());
				collides.add(count);
			}
			count++;
		}

		for (Integer integer : collides) {
			tempRects.remove((int) integer);
		}
		
		// Remover spells que colidem com enemies
		Iterator<Speel> spellIterator = speels.iterator();
		while (spellIterator.hasNext()) {
		    Speel spell = spellIterator.next();
		    Rectangle speelRect = spell.speelRect();

		    for (Iterator<Enemy> enemyIterator = enemys.iterator(); enemyIterator.hasNext();) {
		        Enemy enemy = enemyIterator.next();
		        Rectangle enemyRect = enemy.EnemyRect();

		        if (speelRect.intersects(enemyRect)) {
		        	Coins coin1;
		        	coins.add(coin1 = new Coins(enemyRect.x, enemyRect.y));
		            if (speelType != 1) {
		            	spellIterator.remove();
					}
		            enemyIterator.remove();
		            deadEnemys++;
		            if (deadEnemys == enemyQuantit) {
		            	preCount = enemyQuantit;
						enemyQuantit = preCount + 5;
						generate = true;
						deadEnemys = 0;
					}
		        }
		    }
		}
		
		Iterator<Coins> coinsIterator = coins.iterator();
		while (coinsIterator.hasNext()) {
			Coins coins = (Coins) coinsIterator.next();
			Rectangle coinRect = coins.coinRect();
		
			if (player_rect.intersects(coinRect)) {
				money++;
				coinsIterator.remove();
			}
			
		}

		
	}

	public void generateEnemys() {	
		if (generate) {
			enemysDamage = enemysDamage + 1;
			for (int i = 0; i < enemyQuantit; i++) {
				double velo = random.nextDouble() * 2;
				Enemy enemy = new Enemy(random.nextInt(0, 1420), random.nextInt(0, 1420), random.nextInt(2), velo);
				enemy.setDamage(enemysDamage);
				enemys.add(enemy);
			}
			generate = false;
		}
	}

	public void manageEnemys(Graphics g, Rectangle player_rect) {
		Iterator<Enemy> enemyIterator = enemys.iterator();

		while (enemyIterator.hasNext()) {
			Enemy enemy = enemyIterator.next();

//			if (enemy.die) {
//				enemyIterator.remove();
//				continue;
//			}

			if (player_rect.x > enemy.EnemyRect().x) {
				EnemyMove[0] = true;
				EnemyMove[2] = false;
			}
			if (player_rect.x < enemy.EnemyRect().x) {
				EnemyMove[2] = true;
				EnemyMove[0] = false;
			}
			if (player_rect.y > enemy.EnemyRect().y) {
				EnemyMove[1] = true;
				EnemyMove[3] = false;
			}
			if (player_rect.y < enemy.EnemyRect().y) {
				EnemyMove[3] = true;
				EnemyMove[1] = false;
			}

			if (player_rect.x == enemy.EnemyRect().x) {
				EnemyMove[0] = false;
				EnemyMove[2] = false;
			}
			if (player_rect.y == enemy.EnemyRect().y) {
				EnemyMove[1] = false;
				EnemyMove[3] = false;
			}

			enemy.setMovement(EnemyMove);

			if (player_rect.intersects(enemy.EnemyRect())) {
				if (invencibilityTime == 0) {
					player1.setLife(player1.getLife() - enemy.getDamage());
					invencibilityTime = 15;
				}
			}

			enemy.update();
			enemy.render(g, this.scroll);
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		lifeBar.setMaximum((int) player1.getLifeLimit());
		manaBar.setMaximum((int) player1.getManaLimit());
		lifeBar.setValue((int) player1.getLife());
		manaBar.setValue((int) player1.getMana());
		lifeBar.setString((int) player1.getLife() + "/" + (int) player1.getLifeLimit() + " HP");
		manaBar.setString((int) player1.getMana() + "/" + (int) player1.getManaLimit() + " Mana");
		label.setText("Total :" + money);
		
		Rectangle player_rect = player1.PlayerRect();

		this.scroll[0] += (int) (((player_rect.x + (player_rect.width / 2)) - (this.WindowWidth / 2) - this.scroll[0]) / 10);
		this.scroll[1] += (int) (((player_rect.y + (player_rect.height / 2)) - (this.WindowHeight / 2) - this.scroll[1]) / 10);

		Graphics2D graficos = (Graphics2D) g;
		graficos.drawImage(fundo, -1, 0, null);

		tilemap.render(graficos, this.scroll);
		player1.update(movement);
		
		if (player1.dead) {
			player1.render(graficos, 4, movement, this.scroll);
			add(labelDead);
	        labelDead.setVisible(true); 
	        labelDead.repaint(); 
	        Timer closeTimer = new Timer(3000, new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                System.exit(0);
	            }
	        });
	        closeTimer.setRepeats(false);
	        closeTimer.start();
			
		}else {
			player1.render(graficos, direction, movement, this.scroll);		
		}
		
		Collides();

		generateEnemys();

		manageEnemys(graficos, player_rect);
		if (invencibilityTime > 0) {
			invencibilityTime -= 1;
		}

		for (Speel speel : speels) {
			speel.update();
			speel.render(graficos, scroll);
		}
		
		for (Coins coin : coins) {
			coin.render(graficos, scroll);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {		
		if(e.getSource()==button1) {
			if (money >= 20) {
				speelType = 0;
				speelSpeed = 3;
				money = money - 20;
				manaQuant = 30;
			}
		}	
		if(e.getSource()==button2) {
			if (money >= 40) {
				speelType = 3;
				speelSpeed = 4;
				money = money - 40;
				manaQuant = 30;
			}
		}	
		if(e.getSource()==button3) {
			if (money >= 60) {
				speelType = 2;
				speelSpeed = 5;
				money = money - 60;
				manaQuant = 25;
			}
		}	
		if(e.getSource()==button4) {
			if (money >= 80) {
				speelType = 1;
				speelSpeed = 6;
				speelScale = 2;
				money = money - 80;
				manaQuant = 15;
			}
		}	
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
