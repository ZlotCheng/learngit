package com.example.testmodule;

import java.io.File;
import java.io.IOException;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import android.R.drawable;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener,OnTouchListener{

	
    //��¼ÿ�δ�excel�����ȡ�õĸ���
	int Excel_i=1;
	
	//��¼��һ���������
	float start_x=0,start_y=0;
    Button button1=null;
    ImageView imageView=null;
    
    private Bitmap Wholebitmap,imageview_bitmap;
    
    //���廭��
    private Canvas imageview_canvas;
    
    //���廭��
    private Paint imageview_paint;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		button1=(Button)findViewById(R.id.button1);
		imageView=(ImageView)findViewById(R.id.imageView1);
		button1.setOnClickListener(this);
		imageview_bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.blank); 
		
		
		WindowManager wm1 = this.getWindowManager();
		int width1 = wm1.getDefaultDisplay().getWidth();
		int height1 = wm1.getDefaultDisplay().getHeight();
		
		
		
		Wholebitmap= Bitmap.createBitmap(width1,
				height1,
				imageview_bitmap.getConfig());
		//����һ�����ʣ�
		imageview_canvas=new Canvas(Wholebitmap);
		
		//��������
		imageview_paint = new Paint();
		// ���û��ʵ���ɫΪ��ɫ
		imageview_paint.setColor(Color.BLUE);
		// ���û��ʵıʴ�ϸΪ5
		imageview_paint.setStrokeWidth(5);
		
		imageView.setImageBitmap(Wholebitmap);
		imageView.setOnTouchListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		
		
		// TODO Auto-generated method stub
	//	imageview_canvas.drawLine(50, 50, 50, 1000, imageview_paint);
	//	imageview_canvas.drawLine(0, 0, 1000, 1000, imageview_paint);
		
		
		if(readExcel("mnt/sdcard/��ͤ��.xls",Excel_i,4)==null)
		{
			Toast.makeText(MainActivity.this, "��ֵ����������null ", Toast.LENGTH_SHORT).show();
		}
	//	imageview_canvas.d
		String a=readExcel("mnt/sdcard/��ͤ��.xls",Excel_i,4);
		String b=readExcel("mnt/sdcard/��ͤ��.xls",Excel_i,5);
		//������xls���л�ȡ��������л��ƣ�
		//1.0�汾����ʵ�ִӱ���ж�ȡ��Ȼ��ֱ�ӻ�����imageview�ϣ�������Ҫ�Ĺ�������
		//�����ж����ǵڼ����㣬�����Ϊ���������������1����ʼ�㣬���û��ߣ���2��Ĭ����һ����Ϊ�ߵĳ����㣨3����һ��ѡ��İ�ť��ȥ��ѡ������
		float Node_x=Float.parseFloat(a);
		float Node_y=Float.parseFloat(b);
		
		try {
			imageview_canvas.drawCircle(Node_x, Node_y, 5, imageview_paint);
			if(Excel_i!=1)
			{
				imageview_canvas.drawLine(start_x, start_y, Node_x, Node_y, imageview_paint);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		start_x=Node_x;
		start_y=Node_y;
        imageview_canvas.drawBitmap(Wholebitmap,0,0,null);
		
		imageView.invalidate();
		Excel_i++;
	//	Toast.makeText(MainActivity.this, imageview_canvas.getWidth()+"   "+imageview_canvas.getHeight(), Toast.LENGTH_LONG).show();
	
		
		Toast.makeText(MainActivity.this, a, Toast.LENGTH_SHORT).show();
		
	}
	
    //��ȡexcel�ڵ����� 
     public String readExcel(String path,int x,int y)
     {
     	String content="";
     	try {
				Workbook book=Workbook.getWorkbook(new File(path));
				Sheet sheet=book.getSheet(0);
				//�õ�x��y�����ڵ�Ԫ�������
				String cellStr=sheet.getRow(x)[y].getContents();
				content=cellStr;
				
			} catch (BiffException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IndexOutOfBoundsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
     	return content;
     }

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
}
