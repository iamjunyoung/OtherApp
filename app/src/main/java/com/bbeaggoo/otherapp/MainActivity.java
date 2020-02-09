package com.bbeaggoo.otherapp;

import android.content.ComponentName;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmResults;

// https://realm.io/kr/docs/java/latest/ 여기를 전부 따라해봐도 좋을듯.
// https://youngest-programming.tistory.com/81 여기도 다 해볼것
public class MainActivity extends AppCompatActivity {

    static final String TAG = "OtherApp";
    Realm realm = null;
    private LinearLayout rootLayout = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rootLayout = ((LinearLayout) findViewById(R.id.container));
        rootLayout.removeAllViews();
        
        realm = Realm.getDefaultInstance();
        
        //basicCRUD(realm);

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                String info;
                info = complexReadWrite();
                info += complexQuery();
                return info;
            }
            @Override
            protected void onPostExecute(String result) {
                showStatus(result);
            }
        }.execute();
    }

    private void basicCRUD(Realm realm) {
        showStatus("Perform basic Create/Read/Update/Delete (CRUD) operations...");

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // Add a person
                Person person = realm.createObject(Person.class);
                person.setId(1);
                person.setName("Young Person");
                person.setAge(14);
            }
        });

        final Person person = realm.where(Person.class).findFirst();
        Log.d(TAG, "" + person);
        showStatus(person.getId() + " : " + person.getName() + " : " + person.getAge());
    }

    private String complexReadWrite() {
        String status = "\nPerforming complex Read/Write operation ... ";

        Realm realm = Realm.getDefaultInstance();

        /*
        // 하나의 트랜잭션안에서 10명의 사람(Person) 데이터를 추가
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Dog fido = realm.createObject(Dog.class);
                fido.name = "fido";

                for (int i = 0; i < 10; i++) {
                    Person person = realm.createObject(Person.class);
                    person.setId(i);
                    person.setName((char)(i + 65)+"");
                    person.setAge(i);
                    person.setDog(fido);

                    for (int j = 0; j < i; j++) {
                        Cat cat = realm.createObject(Cat.class);
                        cat.name = "Cat_" + j;
                        person.getCats().add(cat);
                    }
                }
            }
        });
        */
        status += "\nNumber of persons : " + realm.where(Person.class).count() + "\n";

        for (Person pers : realm.where(Person.class).findAll()) {
            String dogName;
            if (pers.getDog() == null) {
                dogName = "None";
            } else {
                dogName = pers.getDog().name;
            }
            status += "\nName : " + pers.getName() + "  / Age : " + pers.getAge()
                    + " / dogName : " + dogName + "/ catSize : " + pers.getCats().size();
        }

        realm.close();
        return status;
    }

    private void showStatus(String txt) {
        Log.i(TAG, txt);
        TextView tv = new TextView(this);
        tv.setText(txt);
        rootLayout.addView(tv);
    }


    // https://m.blog.naver.com/zxy826/220877639087
    // https://m.blog.naver.com/zxy826/220882767449
    private String complexQuery() {
        String status  = "\n\nPerforming complex Query operation ... ";
        Realm realm = Realm.getDefaultInstance();
        status += "\nNumber of persons : " +realm.where(Person.class).count();

        RealmResults<Person> results = realm.where(Person.class)
                .between("age", 6, 8)
                .beginsWith("name", "A")
                .findAll();
                //.beginsWith("name", "Person").findAll();
        Log.d(TAG, "Results : " + results);

        RealmResults<Person> results2 = realm.where(Person.class)
                .between("age", 6, 8)
                .beginsWith("name", "G")  // 4
                .findAll();

        status += "\nSize of result set : " + results.size();
        status += "\nSize of result2 set : " + results2.size();

        realm.close();
        return status;
    }

    public void onClick(View v) {
        ComponentName compo = new ComponentName("com.bbeaggoo.myapplication", "com.bbeaggoo.myapplication.BackgroundMonitorService");
        Intent intentForOb = new Intent();
        switch (v.getId()) {
            case R.id.startService:
                intentForOb.setComponent(compo);
                startService(intentForOb);

                Log.i(TAG, "Start BackgroundMonitorService Service");
                break;
            case R.id.stopService:
                intentForOb.setComponent(compo);
                stopService(intentForOb);

                Log.i(TAG, "End BackgroundMonitorService Service");
                break;
            case R.id.jsonTest:
                JSONUtil json = new JSONUtil(this);
                json.jsonParsing(json.getJsonString());
                Log.i(TAG, "Do it Json test");
                break;
        }
    }
}
