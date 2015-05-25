package com.itpro.mobileapp.mobilevod.account;


import android.app.DatePickerDialog;
import android.app.Dialog;

import android.os.Build;
import android.os.PersistableBundle;
import android.support.v4.app.DialogFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;

import com.andreabaccega.widget.FormEditText;
import com.itpro.mobileapp.mobilevod.BaseActivity;
import com.itpro.mobileapp.mobilevod.R;
import com.itpro.mobileapp.mobilevod.util.Common;

import java.util.Calendar;

public class AccountRegisterActivity extends BaseActivity {
    EditText mEdit;
    FormEditText txtName;
    FormEditText txtEmail;
    FormEditText txtPassword;
    FormEditText txtRePassword;
    EditText txtBirthDay;
    FormEditText txtPhoneNum;
    ScrollView scRegister;

    private int startScroll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.appPrimaryDarkColor));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_register_activity);

        setUpToolBar();
        setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button btRegister=(Button)findViewById(R.id.btRegister);


        txtName=(FormEditText)findViewById(R.id.txtName);
        txtEmail=(FormEditText)findViewById(R.id.txtEmail);
        txtPassword=(FormEditText)findViewById(R.id.txtPassword);
        txtRePassword=(FormEditText)findViewById(R.id.txtRePassword);
        txtBirthDay=(EditText)findViewById(R.id.txtBirthDay);
        txtPhoneNum=(FormEditText)findViewById(R.id.txtPhoneNum);


        scRegister =(ScrollView)findViewById(R.id.scRegister);


       // txtName.setError("fuck you");
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FormEditText[] allFields = {txtName,txtEmail,txtPassword,txtRePassword};


                boolean allValid = true;
                boolean requestedFocus=false;
                for (FormEditText field : allFields) {
                    allValid = field.testValidity() && allValid;
                    if(!field.testValidity()&&!requestedFocus){
                        requestedFocus=true;
                        field.requestFocus();
                    }
                }
                String phone=txtPhoneNum.getText().toString();
                if(phone!=null && !phone.equals("")){
                    allValid = txtPhoneNum.testValidity() && allValid;
                    if(!txtPhoneNum.testValidity()&&!requestedFocus){
                        requestedFocus=true;
                        txtPhoneNum.requestFocus();
                    }
                }
                if(allValid){
                    if(!txtPassword.getText().toString().equals(txtRePassword.getText().toString())){
                        allValid=false;
                        txtRePassword.setError("The confirm is not match!");
                        txtRePassword.requestFocus();
                    }
                }

                if (allValid) {
                    // YAY
                } else {
                    // EditText are going to appear with an exclamation mark and an explicative message.
                }
            }
        });
        txtBirthDay=(EditText)findViewById(R.id.txtBirthDay);
        txtBirthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate(v);
            }
        });
        Common.hideKeyBoard(this);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);

        txtName.setSelected(true);
    }

    public void selectDate(View view) {
        DialogFragment newFragment = new SelectDateFragment();
        newFragment.show(getSupportFragmentManager(), "DatePicker");
    }
    public void populateSetDate(int year, int month, int day) {
        //mEdit = (EditText)findViewById(R.id.editText1);
        //mEdit.setText(month + "/" + day + "/" + year);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        //MenuItem m=menu.add("fuck");

       // m.setVisible(false);
       // m.setEnabled(false);
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            startScroll=scRegister.getScrollY();
        }
        if (event.getAction() == MotionEvent.ACTION_UP){
            int endScroll=scRegister.getScrollY();
            int scrolled=endScroll-startScroll;
            //Log.i("anhnt","scrolled="+scrolled);
            if(scrolled>20|| scrolled<-20){

                return true;
            }
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);








     }
    class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, yy, mm, dd);
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            populateSetDate(yy, mm+1, dd);
        }
    }
}
