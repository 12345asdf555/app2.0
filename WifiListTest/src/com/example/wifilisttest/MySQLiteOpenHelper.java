package com.example.wifilisttest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/*
 * 命令
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
	
	//因为粗心少些给‘)’导致崩溃 数据库一定注意不要拼写错误
	//注意在使用sqlite时需要添加";"结束

//添加自定义类 继承SQLiteOpenHelper
public class MySQLiteOpenHelper extends SQLiteOpenHelper {

	public Context mContext;
	
	//创建学生表(学号,姓名,电话,身高) 主键学号
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
	
	//抽象类 必须定义显示的构造函数 重写方法 
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
