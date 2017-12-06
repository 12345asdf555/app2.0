package com.example.wifilisttest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/*
 * ����
cd G:\software\Program software\Android\adt-bundle-windows-x86_64-20140321\sdk\platform-tools
adb shell
cd /data/data/com.example.sqliteaction/databases/
sqlite3 StuDatabase.db
.table
.schema
*/

	//create table Student (id integer primary key, name text, tel integer, height real);
	//select * from Student;
	//insert into Student (id,name,tel,height) values('10003', 'XiaoMing', '15102530000', '165.58');
	//insert into Student (id,name,tel) values('10004', 'XiaoWang', '15102530011');
	
	//��Ϊ������Щ����)�����±��� ���ݿ�һ��ע�ⲻҪƴд����
	//ע����ʹ��sqliteʱ��Ҫ���";"����

//����Զ����� �̳�SQLiteOpenHelper
public class MySQLiteOpenHelper extends SQLiteOpenHelper {

	public Context mContext;
	
	//����ѧ����(ѧ��,����,�绰,���) ����ѧ��
	public static final String createTableStu = "create table Tenghan (" +
			"electricity test, " +
			"voltage test, " +
			"sensor_Num test, " +
			"machine_id test, " +
			"welder_id test, " +
			"code test, " +
			"year test, " +
			"month test, " +
			"day test, " +
			"hour test, " +
			"minute test, " +
			"second test, " +
			"status test)";
	
	//������ ���붨����ʾ�Ĺ��캯�� ��д���� 
	public MySQLiteOpenHelper(Context context, String name, CursorFactory factory, 
			int version) {
		super(context, name, factory, version);
		mContext = context;
	}
	
	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub
		arg0.execSQL(createTableStu);
		Toast.makeText(mContext, "Created", Toast.LENGTH_SHORT).show();		
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		arg0.execSQL("drop table if exists Student");
		onCreate(arg0);
		Toast.makeText(mContext, "Upgraged", Toast.LENGTH_SHORT).show();
	}
}
