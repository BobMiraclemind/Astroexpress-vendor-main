package com.ashishandroid.ashishkumar.try1;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.ashishandroid.ashishkumar.Adapters.LangSpinnerDropDownAdapter;
import com.ashishandroid.ashishkumar.Adapters.MartialStatusSpinnerDropDownAdapter;
import com.ashishandroid.ashishkumar.LangSpinnerDropDownInterface;
import com.ashishandroid.ashishkumar.MartialStatusSpinnerDropDownInterface;
import com.ashishandroid.ashishkumar.ModelClass.StateVO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Register extends AppCompatActivity implements View.OnClickListener, LangSpinnerDropDownInterface, MartialStatusSpinnerDropDownInterface {
    EditText name, email, password, mobile, address, landline, experience,
            longbio, shortbio, account, aadhaar, languages, hours, chatprice, callprice, cancelledcheque, otherLanguage;
    Button mRegisterbtn;
    TextView mLoginPageBack;
    FirebaseAuth mAuth;
    DatabaseReference mdatabase;
    public String Name, Email, Password, uid, Mobile, Address, Landline, Dateofbirth, Experience, Longbio, Shortbio, Account, Aadhaar, Languages, Hours, Callp, Chatp;
    ProgressDialog mDialog;

    public String chattime = "00:00", calltime = "00:00", reporttime = "00:00", chatstatus = "Offline", callstatus = "Offline", reportstatus = "Offline";

    public String verified = "false";


    public String lannguageCode = null;
    public String martialStatusCode = null;


    public String offers = "";
    EditText accholdername, accnumber, ifsccode, bankname, banbranch;
    public String Accholdername, Accnumber, Ifsccode, Bankname, Banbranch;

    // new fields added --

    EditText pancard, profilepic2, profilepic3, profilepic4;
    public String Pancard;

    Bitmap bitmap1, bitmap2, bitmap3, bitmap4, bitmap5, bitmap6, bitmap7, bitmap8, bitmap9;
    EditText txtdata, editskillcerti, editmasters, editintrovideo, profilepic;
    ImageView imgview, imgview2, imgview3, imgview4, imgview5, imgview6;
    Uri FilePathUri1, FilePathUri2, FilePathUri3, FilePathUri4, FilePathUri5, FilePathUri6, FilePathUri7, FilePathUri8, FilePathUri9;
    StorageReference storageReference;
    DatabaseReference databaseReference;

    int Image_Request_Code1 = 2, Image_Request_Code2 = 3, Image_Request_Code3 = 4, Image_Request_Code4 = 5, Image_Request_Code5 = 6, Image_Request_Code6 = 7, Image_Request_Code7 = 8, Image_Request_Code8 = 9, Image_Request_Code9 = 10;
    ProgressDialog progressDialog;

    DatabaseReference databaseReference1;

    ImageView imgproview2, imgproview3, imgproview4;

    Spinner skillsSpinner, languageSpinner, genderSpinner, workingPortalsSpinner;

    public DatePickerDialog datePickerDialog;
    public Button dateButton;

    public final String[] genderSelected = {""};
    public final String[] portalSelected = {""};

    public Set<String> languageSelected = new HashSet<>();
    public Set<String> martialStatusSelected = new HashSet<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();

        name = (EditText) findViewById(R.id.editName);
        email = (EditText) findViewById(R.id.editEmail);
        password = (EditText) findViewById(R.id.editPassword);
        otherLanguage = (EditText) findViewById(R.id.etOtherLanguage);
        mobile = (EditText) findViewById(R.id.editmobile);
        address = (EditText) findViewById(R.id.editaddress);
        landline = (EditText) findViewById(R.id.editlandline);
//        dateofbirth = (EditText)findViewById(R.id.editdate);
        experience = (EditText) findViewById(R.id.editexperience);
        longbio = (EditText) findViewById(R.id.editlongbio);
        shortbio = (EditText) findViewById(R.id.editshortbio);
        aadhaar = (EditText) findViewById(R.id.editaadhaar);
//        languages = (EditText)findViewById(R.id.editlanguage);
        hours = (EditText) findViewById(R.id.edithours);
        chatprice = (EditText) findViewById(R.id.chatprice);
        callprice = (EditText) findViewById(R.id.callprice);
        cancelledcheque = (EditText) findViewById(R.id.cancelledcheque);

        accholdername = (EditText) findViewById(R.id.accountname);
        accnumber = (EditText) findViewById(R.id.accnumber);
        ifsccode = (EditText) findViewById(R.id.ifsccode);
        banbranch = (EditText) findViewById(R.id.bankbranch);
        bankname = (EditText) findViewById(R.id.bankname);

        profilepic2 = (EditText) findViewById(R.id.profilepic2);
        profilepic3 = (EditText) findViewById(R.id.profilepic3);
        profilepic4 = (EditText) findViewById(R.id.profilepic4);

        //dropdown menu checkbox list
        ArrayList<String> listMartialStatus = new ArrayList<>();
        ArrayList<String> listLang = new ArrayList<>();

        // drop down menu spinner
        skillsSpinner = (Spinner) findViewById(R.id.marital_status_spinner);
        languageSpinner = (Spinner) findViewById(R.id.languages);
        genderSpinner = (Spinner) findViewById(R.id.gender);
        workingPortalsSpinner = (Spinner) findViewById(R.id.spWorkingPortals);

        String[] maritalStatus = {"Numerology", "Vedic Astrology", "Vastu", "Reiki", "Tarot card reading", "KP Astrology", "Oracle reading", "Nadi Reading", "Lal Kitaab", "Palmistry", "OTHERS"};
        for (int i = 0; i < maritalStatus.length; i++) {
            StateVO stateVOMartialStatus = new StateVO();
            stateVOMartialStatus.setTitle(maritalStatus[i]);
            stateVOMartialStatus.setSelected(false);
            listMartialStatus.add(maritalStatus[i]);
        }
        String headerTextMartial = "Select Expertise";
        MartialStatusSpinnerDropDownAdapter adapterMartial = new MartialStatusSpinnerDropDownAdapter(this, android.R.layout.simple_spinner_item, headerTextMartial, listMartialStatus, martialStatusSelected, this);
//        adapterMartial.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        skillsSpinner.setAdapter(adapterMartial);

        String[] lang = {"Assamese", "Bengali", "English", "Gujarati", "Hindi", "Kannada", "Kashmiri", "Konkani", "Malayalam", "Manipuri", "Marathi", "Nepali", "Oriya", "Punjabi", "Sanskrit", "Sindhi", "Tamil", "Telugu", "Urdu", "Bodo", "Santhali", "Maithili", "Dogri", "OTHERS"};
        for (int i = 0; i < lang.length; i++) {
            StateVO stateVOLang = new StateVO();
            stateVOLang.setTitle(lang[i]);
            stateVOLang.setSelected(false);
            listLang.add(lang[i]);
        }
        String headerTextLanguage = "Select language";
        Log.d("TAG", "before ");
        LangSpinnerDropDownAdapter adapterLanguage = new LangSpinnerDropDownAdapter(this, android.R.layout.simple_spinner_item, headerTextLanguage, listLang, languageSelected, this);
//        adapterLanguage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(adapterLanguage);
        Log.d("TAG", "after" + languageSelected);

        String[] genders = {"Select Gender", "Female", "Male", "Others"};

        ArrayAdapter adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, genders);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter2);
        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                genderSelected[0] = adapterView.getItemAtPosition(i).toString();
                Log.d("spinnnerValue", "genderSelected" + genderSelected[0]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        String[] portals = {"Working on other portals?", "Yes", "No"};

        ArrayAdapter adapter3 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, portals);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        workingPortalsSpinner.setAdapter(adapter3);


//        workingPortalsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                portalSelected[0] = adapterView.getItemAtPosition(i).toString();
//                Log.d("spinnnerValue", "portalSelected"+portalSelected[0]);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//            }
//        });


        // new fields
        pancard = (EditText) findViewById(R.id.pancard);


        mRegisterbtn = (Button) findViewById(R.id.buttonRegister);
        mLoginPageBack = (Button) findViewById(R.id.buttonLogin);
        dateButton = (Button) findViewById(R.id.btnDatePicker);

        // for authentication using FirebaseAuth.
        mAuth = FirebaseAuth.getInstance();
        mRegisterbtn.setOnClickListener(this);
        mLoginPageBack.setOnClickListener(this);

        //DatePicker
        initDatePicker();
        dateButton.setOnClickListener(this);
        dateButton.setText(getTodaysDate());
        mDialog = new ProgressDialog(this);
        mdatabase = FirebaseDatabase.getInstance().getReference().child("Astrologers");


        // for image export
        storageReference = FirebaseStorage.getInstance().getReference("Astrologers");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Astrologers");
        txtdata = (EditText) findViewById(R.id.txtdata);
        editskillcerti = (EditText) findViewById(R.id.editskillcerti);
        editmasters = (EditText) findViewById(R.id.editmasters);
        editintrovideo = (EditText) findViewById(R.id.editintrovideo);
        profilepic = (EditText) findViewById(R.id.profilepic);
        imgview = (ImageView) findViewById(R.id.image_view);
        imgview2 = (ImageView) findViewById(R.id.image_view2);
        imgview3 = (ImageView) findViewById(R.id.image_view3);
        imgview4 = (ImageView) findViewById(R.id.image_view4);
        imgview5 = (ImageView) findViewById(R.id.image_view5);
        imgview6 = (ImageView) findViewById(R.id.image_view6);

        imgproview2 = (ImageView) findViewById(R.id.imgproview2);
        imgproview3 = (ImageView) findViewById(R.id.imgproview3);
        imgproview4 = (ImageView) findViewById(R.id.imgproview4);

        progressDialog = new ProgressDialog(Register.this);// context name as per your project name


//        dateofbirth.setInputType(InputType.TYPE_NULL);
//        // date picker
//        dateofbirth.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDateDialog(dateofbirth);
//            }
//        });


        txtdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), Image_Request_Code1);
            }
        });

        editskillcerti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent();
                intent1.setType("image/*");
                intent1.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent1, "Select Image"), Image_Request_Code3);
            }
        });

        editmasters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent();
                intent4.setType("image/*");
                intent4.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent4, "Select Image"), Image_Request_Code2);
            }
        });

        editintrovideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent();
                intent2.setType("video/*");
                intent2.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent2, "Select Video"), Image_Request_Code4);
            }
        });


        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent();
                intent3.setType("image/*");
                intent3.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent3, "Select Profile Image"), Image_Request_Code5);
            }
        });


        cancelledcheque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent();
                intent3.setType("image/*");
                intent3.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent3, "Upload Cancelled Cheque"), Image_Request_Code6);
            }
        });


        profilepic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent();
                intent3.setType("image/*");
                intent3.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent3, "Upload Profile pic 2"), Image_Request_Code7);
            }
        });


        profilepic3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent();
                intent3.setType("image/*");
                intent3.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent3, "Upload Profile pic 3"), Image_Request_Code8);
            }
        });


        profilepic4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent();
                intent3.setType("image/*");
                intent3.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent3, "Upload Profile pic 4"), Image_Request_Code9);
            }
        });


        // for image export


    }

//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
//    {
//        if(languageSpinner.getItemAtPosition(position).toString().equals("Others"))
//        {
//            otherLanguage.setVisibility(View.VISIBLE);
//        }
//    }

    // Date picker
    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
                Dateofbirth = date;
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month) {
        if (month == 1)
            return "JAN";
        if (month == 2)
            return "FEB";
        if (month == 3)
            return "MAR";
        if (month == 4)
            return "APR";
        if (month == 5)
            return "MAY";
        if (month == 6)
            return "JUN";
        if (month == 7)
            return "JUL";
        if (month == 8)
            return "AUG";
        if (month == 9)
            return "SEP";
        if (month == 10)
            return "OCT";
        if (month == 11)
            return "NOV";
        if (month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

    public void openDatePicker() {
        datePickerDialog.show();
    }


    // for image export
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code1 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri1 = data.getData();

            try {
                bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri1);
                imgview.setImageBitmap(bitmap1);

            } catch (IOException e) {

                e.printStackTrace();
            }
        }

        if (requestCode == Image_Request_Code2 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri2 = data.getData();

            try {
                bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri2);
                imgview2.setImageBitmap(bitmap2);
            } catch (IOException e) {

                e.printStackTrace();
            }
        }

        if (requestCode == Image_Request_Code3 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri3 = data.getData();

            try {
                bitmap3 = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri3);
                imgview3.setImageBitmap(bitmap3);
            } catch (IOException e) {

                e.printStackTrace();
            }
        }

        if (requestCode == Image_Request_Code4 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri4 = data.getData();

            try {
                bitmap4 = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri4);
                imgview4.setImageResource(R.drawable.introv);
            } catch (IOException e) {

                e.printStackTrace();
            }
        }


        if (requestCode == Image_Request_Code5 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri5 = data.getData();

            try {
                bitmap5 = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri5);
                imgview5.setImageBitmap(bitmap5);
            } catch (IOException e) {

                e.printStackTrace();
            }
        }

        if (requestCode == Image_Request_Code6 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri6 = data.getData();

            try {
                bitmap6 = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri6);
                imgview6.setImageBitmap(bitmap6);
            } catch (IOException e) {

                e.printStackTrace();
            }
        }


        if (requestCode == Image_Request_Code7 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri7 = data.getData();

            try {
                bitmap7 = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri7);
                imgproview2.setImageBitmap(bitmap7);
            } catch (IOException e) {

                e.printStackTrace();
            }
        }

        if (requestCode == Image_Request_Code8 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri8 = data.getData();

            try {
                bitmap8 = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri8);
                imgproview3.setImageBitmap(bitmap8);
            } catch (IOException e) {

                e.printStackTrace();
            }
        }

        if (requestCode == Image_Request_Code9 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri9 = data.getData();

            try {
                bitmap9 = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri9);
                imgproview4.setImageBitmap(bitmap9);
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }


    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }


    private void uploadVideo(Uri FilePathUri, String UID, String Document_Name) {

        if (FilePathUri != null) {
            StorageReference storageReference2 = storageReference.child(UID).child(Document_Name + "." + GetFileExtension(FilePathUri));
            UploadTask uploadTask = storageReference2.putFile(FilePathUri);
            uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful())
                        Toast.makeText(getApplicationContext(), "Upload Complete", Toast.LENGTH_SHORT).show();
                    //progressBarUpload.setVisibility(View.INVISIBLE);
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    //updateProgress(taskSnapshot);
                }
            });

        } else {
            Toast.makeText(getApplicationContext(), "Nothing to upload", Toast.LENGTH_SHORT).show();
        }
    }


    public void UploadImage(Uri FilePathUri, final String UID, String Document_Name) {

        if (FilePathUri != null) {

            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            StorageReference storageReference2 = FirebaseStorage.getInstance().getReference().child("Astrologers")
                    .child(UID).child(Document_Name + "." + GetFileExtension(FilePathUri));
            storageReference2.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            String TempImageName = txtdata.getText().toString().trim();
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Uploaded Successfully ", Toast.LENGTH_LONG).show();
                            // @SuppressWarnings("VisibleForTests")
                            // uploadinfo imageUploadInfo = new uploadinfo(TempImageName, taskSnapshot.getUploadSessionUri().toString());
                            //String ImageUploadId = databaseReference.push().getKey();
                            //databaseReference.child(ImageUploadId).setValue(imageUploadInfo);
                        }
                    });
        } else {

            Toast.makeText(Register.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void visibleOtherLanguageEditText(String a) {
        lannguageCode = a;
        if (lannguageCode != null && lannguageCode.equals("ClickedOthers")) {
            Log.d("TAG", "flag " + lannguageCode);
            findViewById(R.id.etOtherLanguage).setVisibility(View.VISIBLE);
//            selected_items.add(findViewById(R.id.etOtherLanguage).toString());
            Log.d("TAG", "visibleOtherLanguageEditText: " + languageSelected);
        } else if (lannguageCode != null && lannguageCode.equals("UnClickedOthers")) {
            Log.d("TAG", "flag " + lannguageCode);
            findViewById(R.id.etOtherLanguage).setVisibility(View.INVISIBLE);
//            selected_items.add(findViewById(R.id.etOtherLanguage).toString());
            Log.d("TAG", "visibleOtherLanguageEditText: " + languageSelected);
        }
    }

    @Override
    public void getValueFromLangSpinnerAdapter(Set<String> langselectedlistfromadapter) {
        languageSelected = langselectedlistfromadapter;
        Log.d("TAG", "getValueFromLangSpinnerAdapter: " + languageSelected);
    }

    @Override
    public void visibleOtherMartialStatusEditText(String b) {
        martialStatusCode = b;
        if (martialStatusCode != null && martialStatusCode.equals("ClickedOthers")) {
            Log.d("TAG", "flag " + martialStatusCode);
            findViewById(R.id.etOtherMartialStatus).setVisibility(View.VISIBLE);
//            selected_items.add(findViewById(R.id.etOtherLanguage).toString());
            Log.d("TAG", "visibleOtherLanguageEditText: " + martialStatusSelected);
        } else if (martialStatusCode != null && martialStatusCode.equals("UnClickedOthers")) {
            Log.d("TAG", "flag " + martialStatusCode);
            findViewById(R.id.etOtherMartialStatus).setVisibility(View.INVISIBLE);
//            selected_items.add(findViewById(R.id.etOtherLanguage).toString());
            Log.d("TAG", "visibleOtherLanguageEditText: " + martialStatusSelected);
        }
    }

    @Override
    public void getValueFromMartialStatusSpinnerAdapter(Set<String> expertiseSelectedListFromAdapter) {
        martialStatusSelected = expertiseSelectedListFromAdapter;
        Log.d("TAG", "getValueFromLangSpinnerAdapter: " + martialStatusSelected);
    }


    public static class uploadinfo {

        public String imageName;
        public String imageURL;

        public uploadinfo() {
        }

        public uploadinfo(String name, String url) {
            this.imageName = name;
            this.imageURL = url;
        }

        public String getImageName() {
            return imageName;
        }

        public String getImageURL() {
            return imageURL;
        }
    }

    // for image export
    @Override
    public void onClick(View v) {
        if (v == mRegisterbtn) {
            UserRegister();
            Log.d("TAG", "onClick: userregister");

        } else if (v == mLoginPageBack) {
            startActivity(new Intent(Register.this, login.class));
        } else if (v == dateButton) {
            openDatePicker();
        }
    }

    private void UserRegister() {

        if (TextUtils.isEmpty(name.getText())) {
            Toast.makeText(Register.this, "Enter Name", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(email.getText())) {
            Toast.makeText(Register.this, "Enter Email", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(password.getText())) {
            Toast.makeText(Register.this, "Enter Password", Toast.LENGTH_SHORT).show();
            return;
        } else if (password.getText().length() < 6) {
            Toast.makeText(Register.this, "Password must be greater then 6 digit", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(mobile.getText())) {
            Toast.makeText(Register.this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(address.getText())) {
            Toast.makeText(Register.this, "Enter Address", Toast.LENGTH_SHORT).show();
            return;
        } else if (mobile.getText().length() != 10) {
            Toast.makeText(Register.this, "Enter Valid Mobile Number", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(Dateofbirth)) {
            Toast.makeText(Register.this, "Enter DOB", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(experience.getText())) {
            Toast.makeText(Register.this, "Enter Experience", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(longbio.getText())) {
            Toast.makeText(Register.this, "Enter Long Bio", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(shortbio.getText())) {
            Toast.makeText(Register.this, "Enter Short Bio", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(aadhaar.getText())) {
            Toast.makeText(Register.this, "Enter Aadhaar Card Details", Toast.LENGTH_SHORT).show();
            return;
        } else if (aadhaar.getText().length() != 12) {
            Toast.makeText(Register.this, "Enter Valid Aadhaar Details", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(hours.getText())) {
            Toast.makeText(Register.this, "Enter Hours", Toast.LENGTH_SHORT).show();
            return;
        } else if (imgview.getDrawable() == null) {
            Toast.makeText(Register.this, "Document Not Selected", Toast.LENGTH_SHORT).show();
            return;
        } else if (imgview2.getDrawable() == null) {
            Toast.makeText(Register.this, "Document Not Selected", Toast.LENGTH_SHORT).show();
            return;
        } else if (imgview3.getDrawable() == null) {
            Toast.makeText(Register.this, "Document Not Selected", Toast.LENGTH_SHORT).show();
            return;
        } else if (imgview4.getDrawable() == null) {
            Toast.makeText(Register.this, "Intro Not Selected", Toast.LENGTH_SHORT).show();
            return;
        } else if (imgview6.getDrawable() == null) {
            Toast.makeText(Register.this, "Cancelled Cheque Not Selected", Toast.LENGTH_SHORT).show();
            return;
        } else if (imgproview2.getDrawable() == null) {
            Toast.makeText(Register.this, "Profile Pic 2 not selected", Toast.LENGTH_SHORT).show();
            return;
        } else if (imgproview3.getDrawable() == null) {
            Toast.makeText(Register.this, "Profile Pic 3 not selected", Toast.LENGTH_SHORT).show();
            return;
        } else if (imgproview4.getDrawable() == null) {
            Toast.makeText(Register.this, "Profile Pic 4 not selected", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(pancard.getText())) {
            Toast.makeText(Register.this, "PanCard Area empty", Toast.LENGTH_SHORT).show();
            return;
        } else if (pancard.getText().length() != 10) {
            Toast.makeText(Register.this, "PanCard number length is not Right", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(accholdername.getText())) {
            Toast.makeText(Register.this, "Please fill your account details", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(accnumber.getText())) {
            Toast.makeText(Register.this, "Please fill your account details", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(ifsccode.getText())) {
            Toast.makeText(Register.this, "Please fill your account details", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(banbranch.getText())) {
            Toast.makeText(Register.this, "Please fill your account details", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(bankname.getText())) {
            Toast.makeText(Register.this, "Please fill your account details", Toast.LENGTH_SHORT).show();
            return;
        }

        Name = name.getText().toString().trim();
        Email = email.getText().toString().trim();
        Password = password.getText().toString().trim();

        Mobile = mobile.getText().toString().trim();
        Address = address.getText().toString().trim();
        Landline = landline.getText().toString().trim();
//        Dateofbirth = dateofbirth.getText().toString().trim();
        Experience = experience.getText().toString().trim();
        Longbio = longbio.getText().toString().trim();
        Shortbio = shortbio.getText().toString().trim();
        Aadhaar = aadhaar.getText().toString().trim();
        Hours = hours.getText().toString().trim();
        Callp = "10";
        Chatp = "10";

        Pancard = pancard.getText().toString().trim();


        // String Accholdername, Accnumber, Ifsccode, Bankname, Banbranch;
        Accholdername = accholdername.getText().toString().trim();
        Accnumber = accnumber.getText().toString().trim();
        Ifsccode = ifsccode.getText().toString().trim();
        Bankname = bankname.getText().toString().trim();
        Banbranch = banbranch.getText().toString().trim();


        mDialog.setMessage("Creating User please wait...");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
        mAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    sendEmailVerification();
                    mDialog.dismiss();
                    OnAuth(task.getResult().getUser());

//                    FirebaseUser currentU = FirebaseAuth.getInstance().getCurrentUser();
//                    String uId = currentU.getUid();
//
//
//                    mAuth.signOut();
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(Register.this, login.class));

                        }
                    }, 10000);//time in milisecond
                } else {
                    Toast.makeText(Register.this, "error on creating user", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Email verification code using FirebaseUser object and using isSucccessful()function.
    private void sendEmailVerification() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(Register.this, "Check your Email for verification", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void OnAuth(FirebaseUser user) {
        createAnewUser(user.getUid());

    }

    private void createAnewUser(String uid) {
        User user = new User(
                getDisplayName(),
                getUserEmail(),
                uid,
                getMobile(),
                getAddress(),
                getAlternatemobileNo(),
                "" + getDateOfBirth(),
                getExperience(),
                getLongbio(),
                getShortbio(),
                getAadhaar(),
                getHours(),
                new Date().getTime(),
                getChatp(),
                getCallp(),
                getChattime(),
                getCalltime(),
                getReporttime(),
                getChatstatus(),
                getCallstatus(),
                getReportstatus(),
                getVerified(),
                getOffers(),
                getPancard()
        );

        mdatabase.child(uid).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                UploadImage(FilePathUri1, uid, "Educational_Degree");
                UploadImage(FilePathUri2, uid, "Profile_Image");
                UploadImage(FilePathUri3, uid, "Skill_certificate");
                uploadVideo(FilePathUri4, uid, "IntroVideo");
                UploadImage(FilePathUri5, uid, "Master");
                UploadImage(FilePathUri6, uid, "Cancelled_cheque");
                UploadImage(FilePathUri7, uid, "Profile_Image2");
                UploadImage(FilePathUri8, uid, "Profile_Image3");
                UploadImage(FilePathUri9, uid, "Profile_Image4");


                databaseReference = FirebaseDatabase.getInstance().getReference();

                databaseReference.child("Astrologers").child(uid).child("AccountDetails").child("AccountHolderName").setValue(accholdername.getText().toString());
                databaseReference.child("Astrologers").child(uid).child("AccountDetails").child("AccountNumber").setValue(accnumber.getText().toString());
                databaseReference.child("Astrologers").child(uid).child("AccountDetails").child("IFSCcode").setValue(ifsccode.getText().toString());
                databaseReference.child("Astrologers").child(uid).child("AccountDetails").child("BankName").setValue(bankname.getText().toString());
                databaseReference.child("Astrologers").child(uid).child("AccountDetails").child("BankBranch").setValue(banbranch.getText().toString());


                if (martialStatusSelected == null) {
                    Toast.makeText(Register.this, "Select your expertise area", Toast.LENGTH_SHORT).show();
                } else if (languageSelected == null) {
                    Toast.makeText(Register.this, "Select language", Toast.LENGTH_SHORT).show();
                } else if (genderSelected[0].equals("Select Gender")) {
                    Toast.makeText(Register.this, "Select Gender", Toast.LENGTH_SHORT).show();
                } else if (portalSelected[0].equals("Working on other portals?")) {
                    Toast.makeText(Register.this, "Working on other portals?", Toast.LENGTH_SHORT).show();
                }
                try {
                    databaseReference.child("Astrologers").child(uid).child("expertise").setValue(String.valueOf(martialStatusSelected));
                    databaseReference.child("Astrologers").child(uid).child("lang").setValue(languageSelected);
                    databaseReference.child("Astrologers").child(uid).child("gender").setValue(genderSelected[0]);
                    databaseReference.child("Astrologers").child(uid).child("portal").setValue(portalSelected[0]);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            }
        });
        //databaseReference.child(uid).setValue(image);


    }


    public String getDisplayName() {
        return Name;
    }

    public String getUserEmail() {
        return Email;
    }


    public String getMobile() {
        return Mobile;
    }

    public String getAddress() {
        return Address;
    }

    //  public String getal() {
    //  return Landline;
    // }

    public String getAlternatemobileNo() {
        return Landline;
    }

    public String getDateOfBirth() {
        return Dateofbirth;
    }

    public String getExperience() {
        return Experience;
    }

    public String getLongbio() {
        return Longbio;
    }

    public String getShortbio() {
        return Shortbio;
    }


    public String getAadhaar() {
        return Aadhaar;
    }


    public String getHours() {
        return Hours;
    }

    public String getCallp() {
        return Callp;
    }

    public String getChatp() {
        return Chatp;
    }

    public String getChattime() {
        return chattime;
    }

    public String getCalltime() {
        return calltime;
    }

    public String getReporttime() {
        return reporttime;
    }

    public String getChatstatus() {
        return chatstatus;
    }

    public String getCallstatus() {
        return callstatus;
    }

    public String getReportstatus() {
        return reportstatus;
    }

    public String getVerified() {
        return verified;
    }

    public String getOffers() {
        return offers;
    }

    public String getPancard() {
        return Pancard;
    }


//    private void showDateDialog(final EditText dateofbirth) {
//        final Calendar calendar=Calendar.getInstance();
//        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                calendar.set(Calendar.YEAR,year);
//                calendar.set(Calendar.MONTH,month);
//                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
//                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy");
//                dateofbirth.setText(simpleDateFormat.format(calendar.getTime()));
//
//            }
//        };
//
//        new DatePickerDialog(Register.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
//    }


}

