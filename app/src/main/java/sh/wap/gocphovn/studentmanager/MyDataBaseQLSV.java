package sh.wap.gocphovn.studentmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import sh.wap.gocphovn.studentmanager.model.Student;

public class MyDataBaseQLSV extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME="QLSV_manager";
    private static final String TABLE_NAME="tbQLSV";
    private static final String ID="id";
    private static final String IMAGE="image";
    private static final String NAME="name";
    private static final String MSSV="mssv";
    private static final String BIRTHDAY="birthday";
    private static final String ADDRESS="address";
    private static final String SEX="sex";
    private static final String FAVORITES="favorites";

    private static final String QueryCreateTB="create table "+TABLE_NAME+"("+
            ID +" integer primary key,"+
            IMAGE+" text,"+
            NAME+" text,"+
            MSSV+" text,"+
            BIRTHDAY+" text,"+
            ADDRESS+" text,"+
            SEX+" integer,"+
            FAVORITES+" text)";

    public MyDataBaseQLSV(Context context)
    {
        super(context,DATABASE_NAME,null, VERSION);
    }

    //được gọi nếu table này chưa đc tạo
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            db.execSQL(QueryCreateTB);
            db.execSQL("insert into tbQLSV(name,mssv,birthday,address,sex,favorites) values('Đỗ Văn Cường','20172986','01/07/1999','Phú Xuyên Hà Nội',0,'sport,readbook')");
            db.execSQL("insert into tbQLSV(name,mssv,birthday,address,sex,favorites) values('Nguyễn Huy Định','20173016','21/02/1999','Kim Sơn Ninh Bình',0,'sport,travel')");
            db.execSQL("insert into tbQLSV(name,mssv,birthday,address,sex,favorites) values('Nguyễn Lý Thái Dương','20173064','30/11/1999','Hạ Long',0,'sport,readbook')");
            db.execSQL("insert into tbQLSV(name,mssv,birthday,address,sex,favorites) values('Nguyễn Trường Giang','20173078','14/01/1999','Thạch Thất Hà Nội',0,'sport,readbook')");
            db.execSQL("insert into tbQLSV(name,mssv,birthday,address,sex,favorites) values('Nguyễn Văn Hải','20173089','20/01/1999','Phú Xuyên Hà Nội',0,'sport,travel,readbook')");
            db.execSQL("insert into tbQLSV(name,mssv,birthday,address,sex,favorites) values('Lương Thị Linh','20173236','27/10/1999','Nghệ An',1,'sport,readbook')");
            db.execSQL("insert into tbQLSV(name,mssv,birthday,address,sex,favorites) values('Lê Hải Quân','20173316','10/04/1999','Hà Nội',0,'sport,readbook')");
            db.execSQL("insert into tbQLSV(name,mssv,birthday,address,sex,favorites) values('Vũ Duy Trường','20173427','19/11/1999','Hải Phòng',0,'sport,travel,readbook')");
            db.execSQL("insert into tbQLSV(name,mssv,birthday,address,sex,favorites) values('Trần Thị Ánh Ngọc','20173288','27/10/1999','Hà Nội',1,'sport,readbook')");
            db.execSQL("insert into tbQLSV(name,mssv,birthday,address,sex,favorites) values('Mai Thị Minh Anh','20172954','12/10/1999','Lạng Sơn',1,'sport,readbook')");
            db.execSQL("insert into tbQLSV(name,mssv,birthday,address,sex,favorites) values('Lê Thị Huyền Thanh','20173371','18/05/1999','Hà Tĩnh',1,'sport,readbook')");

            db.setTransactionSuccessful();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            db.endTransaction();
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public Long addStudent(Student student)
    {
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(IMAGE,student.getImage());
        values.put(NAME,student.getFullname());
        values.put(MSSV,student.getMssv());
        values.put(BIRTHDAY,student.getBirthday());
        values.put(ADDRESS,student.getAddress());
        values.put(SEX,student.getSex());
        values.put(FAVORITES,student.getFavorites());

        Long id= db.insert(TABLE_NAME,null,values);
        db.close();
        return id; //nếu id=-1 là lỗi
    }
    public int updateStudent(Student student)
    {
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(IMAGE,student.getImage());
        values.put(NAME,student.getFullname());
        values.put(MSSV,student.getMssv());
        values.put(BIRTHDAY,student.getBirthday());
        values.put(ADDRESS,student.getAddress());
        values.put(SEX,student.getSex());
        values.put(FAVORITES,student.getFavorites());

        int succ=db.update(TABLE_NAME,values,ID +"=?",new String[]{String.valueOf(student.getId())});
        if(succ>0) //thành công
        {

        }
        db.close();
        return succ;
    }
    public List<Student> getAllStudent()
    {
        List<Student> list=new ArrayList<>();

        String select="select * from "+TABLE_NAME;
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=db.rawQuery(select,null);
        cursor.moveToPosition(-1);
        while (cursor.moveToNext())
        {
            Student student=new Student();
            student.setId(cursor.getInt(0));
            student.setImage(cursor.getString(1));
            student.setFullname(cursor.getString(cursor.getColumnIndex("name")));
            student.setMssv(cursor.getString(cursor.getColumnIndex("mssv")));
            student.setBirthday(cursor.getString(4));
            student.setAddress(cursor.getString(5));
            student.setSex(cursor.getInt(6));
            student.setFavorites(cursor.getString(7));
            list.add(student);
        }
        db.close();
        return list;
    }
    public int deleteStudent(Student student)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(TABLE_NAME,ID+"=?",new String[]{String.valueOf(student.getId())});
    }
}
